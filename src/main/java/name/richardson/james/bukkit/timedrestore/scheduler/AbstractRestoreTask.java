/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractRestoreTask.java is part of TimedRestore.

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

package name.richardson.james.bukkit.timedrestore.scheduler;

import name.richardson.james.bukkit.timedrestore.persistence.TaskConfigurationEntry;

/**
 * This class provides a default implementation of the {@link RestoreTask} interface and should be inherited other
 * implementation of it. This is done to share a logger throughout the classes and set the {@link
 * TaskConfigurationEntry} for each task in a consistent way.
 */
public abstract class AbstractRestoreTask implements RestoreTask {

	private final TaskConfigurationEntry configuration;

	/**
	 * Instantiates a new abstract task.
	 *
	 * @param configuration the backing configuration for this task.
	 */
	public AbstractRestoreTask(final TaskConfigurationEntry configuration) {
		this.configuration = configuration;
	}

	public TaskConfigurationEntry getConfiguration() {
		return this.configuration;
	}

}
