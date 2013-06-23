/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TaskConfigurationEntry.java is part of TimedRestore.

 TimedRestore is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 TimedRestore is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 TimedRestore. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.timedrestore.persistence;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

/**
 * The class TaskConfigurationEntry is responsible for representing the values in a individual {@link
 * ConfigurationSection} for use elsewhere in the plugin.
 */
public class TaskConfigurationEntry {

	public static final String REGION_KEY = "regions";
	public static final String SCHEDULE_KEY = "schedule";
	public static final String SNAPSHOT_KEY = "snapshot";
	public static final String WORLD_KEY = "world";

	private final String date;
	private final Logger logger = PrefixedLogger.getLogger(this);
	private final Set<String> regions;
	private final String schedule;
	private final String worldName;

	/**
	 * Instantiates a new task configuration entry based on an underlying {@link ConfigurationSection}.
	 *
	 * @param section the section to use as the backing store
	 */
	public TaskConfigurationEntry(final ConfigurationSection section) {
		this.schedule = section.getString(TaskConfigurationEntry.SCHEDULE_KEY);
		this.worldName = section.getString(TaskConfigurationEntry.WORLD_KEY);
		this.date = (section.getString(TaskConfigurationEntry.SNAPSHOT_KEY));
		this.regions = new LinkedHashSet<String>(section.getStringList(TaskConfigurationEntry.REGION_KEY));
		logger.log(Level.CONFIG, this.toString());
	}

	public Set<String> getRegions() {
		return this.regions;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public String getSnapshotDate() {
		return this.date;
	}

	public String getWorldName() {
		return this.worldName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder message = new StringBuilder();
		message.append("class: ");
		message.append(this.getClass().getSimpleName());
		message.append(", ");
		message.append("schedule: ");
		message.append(this.schedule);
		message.append(", ");
		message.append("worldName: ");
		message.append(this.worldName);
		message.append(", ");
		message.append("snapshot: ");
		message.append(this.date);
		message.append(", ");
		message.append("regions: ");
		message.append(this.regions);
		return message.toString();
	}
}
