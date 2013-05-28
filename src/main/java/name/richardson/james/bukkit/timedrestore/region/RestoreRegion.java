/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * RestoreRegion.java is part of TimedRestore.
 * 
 * TimedRestore is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * TimedRestore is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TimedRestore. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.timedrestore.region;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.snapshots.InvalidSnapshotException;
import com.sk89q.worldedit.snapshots.Snapshot;
import com.sk89q.worldedit.snapshots.SnapshotRepository;
import com.sk89q.worldedit.snapshots.SnapshotRestore;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localised;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

/**
 * This class represents a region of the world to be restored. It is responsible
 * for finding the {@link Polygonal2DRegion} registered with WorldGuard
 * {@link GlobalRegionManager} and then finding an appropriate snapshot in the
 * {@link SnapshotRepository} to use.
 */
public class RestoreRegion implements Localised {

	private static final ResourceBundle localisation = ResourceBundle.getBundle(ResourceBundles.MESSAGES.getBundleName());

	private static GlobalRegionManager manager;
	private static SnapshotRepository snapshots;
	private static List<LocalWorld> worlds;

	public static LocalWorld getLocalWorld(final String worldName) {
		for (final LocalWorld world : RestoreRegion.worlds) {
			if (world.getName().equalsIgnoreCase(worldName)) {
				return world;
			}
		}
		return null;
	}

	public static void setLocalWorlds(final List<LocalWorld> worlds) {
		if (worlds == null) {
			throw new IllegalArgumentException();
		}
		RestoreRegion.worlds = worlds;
	}

	public static void setRegionManager(final GlobalRegionManager manager) {
		if (manager == null) {
			throw new IllegalArgumentException();
		}
		RestoreRegion.manager = manager;
	}

	public static void setSnapshotRepository(final SnapshotRepository repo) {
		if (repo == null) {
			throw new IllegalArgumentException();
		}
		RestoreRegion.snapshots = repo;
	}

	private final Polygonal2DRegion region;

	/**
	 * Instantiates a new RestoreRegion.
	 * 
	 * This will attempt to find the region requested using the
	 * {@link GlobalRegionManager} and then find an associated snapshot in the
	 * {@link SnapshotRepository}.
	 * 
	 * @param worldName
	 *          the name of the {@link World} that the region resides in.
	 * @param regionName
	 *          the name of the {@link Polygonal2dRegion} to restore.
	 * @throws InvalidWorldException
	 *           thrown is the is no {@link World} loaded with that name.
	 * @throws InvalidRegionException
	 *           thrown if there is no {@link Polygonal2dRegion} matching the name
	 *           supplied.
	 */
	public RestoreRegion(final String worldName, final String regionName) throws InvalidWorldException, InvalidRegionException {
		final World world = Bukkit.getWorld(worldName);
		if (world == null) {
			throw new InvalidWorldException(worldName);
		}
		final ProtectedRegion worldGuardRegion = RestoreRegion.manager.get(world).getRegion(regionName);
		final LocalWorld localWorld = RestoreRegion.getLocalWorld(worldName);
		if ((localWorld == null) || (worldGuardRegion == null)) {
			throw new InvalidRegionException(worldName, regionName);
		}
		this.region = new Polygonal2DRegion(localWorld, worldGuardRegion.getPoints(), 0, world.getMaxHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.localisation.Localised#getMessage
	 * (java.lang.String)
	 */
	public String getMessage(final String key) {
		String message = RestoreRegion.localisation.getString(key);
		message = ColourFormatter.replace(message);
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.localisation.Localised#getMessage
	 * (java.lang.String, java.lang.Object[])
	 */
	public String getMessage(final String key, final Object... elements) {
		final MessageFormat formatter = new MessageFormat(RestoreRegion.localisation.getString(key));
		formatter.setLocale(Locale.getDefault());
		String message = formatter.format(elements);
		message = ColourFormatter.replace(message);
		return message;
	}

	/**
	 * Move any players who are currently in region to be restored to safety.
	 * 
	 * This prevents players from being encased in stone or other materials when
	 * the region is restored from the snapshot.
	 */
	public void movePlayersToSafety() {
		for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
			final double x = player.getLocation().getX();
			final double y = player.getLocation().getY();
			final double z = player.getLocation().getZ();
			final Vector vector = new Vector(x, y, z);
			if (this.region.contains(vector)) {
				player.sendMessage(this.getMessage("area-being-restored"));
				player.teleport(player.getWorld().getSpawnLocation());
				player.sendMessage(this.getMessage("teleported-to-safety"));
			}
		}
	}

	/**
	 * Restore the region.
	 * 
	 * This uses the associated {@link Snapshot} to restore a
	 * {@link Polygonal2dRegion} to previous point in time.
	 * 
	 * @param snapshotName
	 *          The name of the snapshot to use.
	 * @throws InvalidSnapshotException
	 *           Signals that the snapshot name supplied could not be found in the
	 *           {@link SnapshotRepository}
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 * @throws DataException
	 *           Signals that there was an issue loading the chunk store from the
	 *           snapshot.
	 */
	public void restore(final String snapshotName) throws InvalidSnapshotException, IOException, DataException {
		final Snapshot snapshot = RestoreRegion.snapshots.getSnapshot(snapshotName);
		final EditSession session = new EditSession(this.region.getWorld(), -1);
		final SnapshotRestore restore = new SnapshotRestore(snapshot.getChunkStore(), this.region);
		try {
			this.movePlayersToSafety();
			restore.restore(session);
		} catch (final MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}

}
