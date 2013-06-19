/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 *
 * InvalidWorldException.java is part of TimedRestore.
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

import com.sk89q.worldedit.LocalWorld;

import org.bukkit.World;

/**
 * The class InvalidWorldException is thrown when a worldName provided to {@link RestoreRegion} does not match any
 * loaded worlds.
 */
public class InvalidWorldException extends Exception {

	private static final long serialVersionUID = -983982421199522374L;

	private final String worldName;

	/**
	 * Instantiates a new invalid world exception.
	 *
	 * @param world the world
	 */
	public InvalidWorldException(final LocalWorld world) {
		this.worldName = world.getName();
	}

	/**
	 * Instantiates a new invalid world exception.
	 *
	 * @param worldName the world name
	 */
	public InvalidWorldException(final String worldName) {
		this.worldName = worldName;
	}

	/**
	 * Instantiates a new invalid world exception.
	 *
	 * @param world the world
	 */
	public InvalidWorldException(final World world) {
		this.worldName = world.getName();
	}

	public String getWorldName() {
		return this.worldName;
	}

}
