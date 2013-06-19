/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 TaskConfiguration.java is part of TimedRestore.

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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

/**
 * The class TaskConfiguration represents the base {@link YAMLStorage} and provides ways to access the configuration
 * objects within.
 */
public class TaskConfiguration extends YAMLStorage {

	public TaskConfiguration(final File file, final InputStream defaults)
	throws IOException {
		super(file, defaults);
	}

	public Set<TaskConfigurationEntry> getTasks() {
		final Set<TaskConfigurationEntry> set = new LinkedHashSet<TaskConfigurationEntry>();
		for (final String sectionKey : this.getConfiguration().getKeys(false)) {
			final ConfigurationSection section = this.getConfiguration().getConfigurationSection(sectionKey);
			set.add(new TaskConfigurationEntry(section));
		}
		return set;
	}

}
