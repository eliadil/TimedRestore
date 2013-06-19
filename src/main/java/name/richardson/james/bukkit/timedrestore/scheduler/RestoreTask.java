/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 RestoreTask.java is part of TimedRestore.

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
 * This interface represents any {@link Runnable} that may be used for restoring regions in TimedRestore.
 */
public interface RestoreTask extends Runnable {

	/**
	 * Deschedule this task from the region.
	 */
	public void deschedule();

	public TaskConfigurationEntry getConfiguration();

	/**
	 * Schedule this task with the region.
	 */
	public void schedule();

}
