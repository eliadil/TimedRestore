/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * TimedRestorePlugin.java is part of TimedRestore.
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
package name.richardson.james.bukkit.timedrestore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.snapshots.SnapshotRepository;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.GlobalRegionManager;

import name.richardson.james.bukkit.timedrestore.management.ReloadCommand;
import name.richardson.james.bukkit.timedrestore.management.SchedulerCommand;
import name.richardson.james.bukkit.timedrestore.management.StatusCommand;
import name.richardson.james.bukkit.timedrestore.persistence.TaskConfiguration;
import name.richardson.james.bukkit.timedrestore.persistence.TaskConfigurationEntry;
import name.richardson.james.bukkit.timedrestore.region.RestoreRegion;
import name.richardson.james.bukkit.timedrestore.scheduler.CronRestoreTask;
import name.richardson.james.bukkit.timedrestore.scheduler.RestoreTask;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class TimedRestorePlugin and the main entry point for TimedRestore.
 */
public class TimedRestorePlugin extends AbstractPlugin {

	private final Set<RestoreTask> tasks = new LinkedHashSet<RestoreTask>();

	public String getArtifactID() {
		return "timed-restore";
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		try {
			this.initaliseWorldEdit();
			this.initaliseWorldGuard();
			this.loadConfiguration();
			this.setPermissions();
			this.registerCommands();
			this.updatePlugin();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attempt to reload the plugin configuration.
	 * 
	 * Will additionally clear and deschedule any currently running tasks.
	 * 
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public void reloadConfiguration() throws IOException {
		for (final RestoreTask task : this.tasks) {
			task.deschedule();
		}
		this.tasks.clear();
		this.getServer().getScheduler().cancelTasks(this);
		this.loadConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.plugin.AbstractPlugin#loadConfiguration
	 * ()
	 */
	@Override
	protected void loadConfiguration() throws IOException {
		super.loadConfiguration();
		final File file = new File(this.getDataFolder().getPath() + File.separatorChar + "tasks.yml");
		final InputStream defaults = this.getResource("tasks.yml");
		final TaskConfiguration configuration = new TaskConfiguration(file, defaults);
		for (final TaskConfigurationEntry entry : configuration.getTasks()) {
			final CronRestoreTask timer = new CronRestoreTask(entry, this);
			this.tasks.add(timer);
			timer.schedule();
		}
	}

	/**
	 * Attempt to get an instance of the {@link WorldEditPlugin}.
	 * 
	 * This then sets two static variables required for the {@link RestoreRegion}
	 * class: {@link SnapshotRepository} and a list of {@link LocalWorld} required
	 * to restore selections.
	 */
	private void initaliseWorldEdit() {
		final WorldEditPlugin plugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		this.getCustomLogger().log(Level.FINE, "Using " + plugin.getDescription().getFullName());
		RestoreRegion.setSnapshotRepository(plugin.getLocalConfiguration().snapshotRepo);
		RestoreRegion.setLocalWorlds(plugin.getServerInterface().getWorlds());
	}

	/**
	 * Attempt to get an instance of {@link WorldGuardPlugin}.
	 * 
	 * This then sets one static variable required for the {@link RestoreRegion}
	 * class: {@link GlobalRegionManager} for looking up regions to restore.
	 */
	private void initaliseWorldGuard() {
		final WorldGuardPlugin plugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		this.getCustomLogger().log(Level.FINE, "Using " + plugin.getDescription().getFullName());
		RestoreRegion.setRegionManager(plugin.getGlobalRegionManager());
	}

	/**
	 * Register all commands associated with this plugin.
	 */
	private void registerCommands() {
		final CommandManager commandManager = new CommandManager("tr");
		commandManager.addCommand(new ReloadCommand(this));
		commandManager.addCommand(new SchedulerCommand());
		commandManager.addCommand(new StatusCommand());
	}

}
