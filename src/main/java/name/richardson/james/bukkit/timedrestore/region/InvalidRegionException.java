/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * InvalidRegionException.java is part of TimedRestore.
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

/**
 * The class InvalidRegionException is thrown when a regionName provided to
 * {@link RestoreRegion} does not match any loaded regions.
 */
public class InvalidRegionException extends Exception {

	private static final long serialVersionUID = -983982421199522374L;

	private final String regionName;
	private final String worldName;

	public InvalidRegionException(final String worldName, final String regionName) {
		this.worldName = worldName;
		this.regionName = regionName;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public String getWorldName() {
		return this.worldName;
	}

}
