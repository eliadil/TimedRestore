/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * CronRestoreTask.java is part of TimedRestore.
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
package name.richardson.james.bukkit.timedrestore.scheduler;

import it.sauronsoftware.cron4j.Scheduler;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import name.richardson.james.bukkit.timedrestore.persistence.TaskConfigurationEntry;

/**
 * This {@link RestoreTask} implementation is responsible for the scheduling of
 * the task itself. While {@link BukkitRestoreTask} is responsible for the
 * restore , this class is responsible for deciding when it happens and creating
 * a {@link BukkitRestoreTask} when the time is right.
 */
public class CronRestoreTask extends AbstractRestoreTask {

	final private static Scheduler scheduler = new Scheduler();

	/**
	 * Deschedule any task schedule at a specific time from the {@link Scheduler}.
	 * 
	 * @param schedule
	 *          the schedule
	 */
	public static final void deschedule(final String schedule) {
		CronRestoreTask.scheduler.deschedule(schedule);
	}

	public static final boolean isSchedulerStarted() {
		return CronRestoreTask.scheduler.isStarted();
	}

	/**
	 * Start the scheduler if it is not already started.
	 */
	public static final void start() {
		if (!CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.start();
		}
	}

	/**
	 * Stop the scheduler if it is not already stopped.
	 */
	public static final void stop() {
		if (CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.stop();
		}
	}

	final private Plugin plugin;

	/**
	 * Instantiates a new CronRestoreTask. This task registers itself with the
	 * Cron4j scheduler as soon as it is created for convenience.
	 * 
	 * @param configuration
	 *          the configuration
	 * @param plugin
	 *          the plugin
	 */
	public CronRestoreTask(final TaskConfigurationEntry configuration, final Plugin plugin) {
		super(configuration);
		this.plugin = plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see name.richardson.james.bukkit.timedrestore.scheduler.Task#deschedule()
	 */
	public void deschedule() {
		CronRestoreTask.scheduler.deschedule(this.getConfiguration().getSchedule());
		AbstractRestoreTask.LOGGER.log(Level.FINE, "Descheduled CronTimer at " + this.getConfiguration().getSchedule());
	}

	/**
	 * Start the restore operation by creating a {@link BukkitRestoreTask} with
	 * the {@link TaskConfigurationEntry} as the parameters. The
	 * {@link BukkitRestoreTask} handles the actual restore operation as soon as
	 * possible.
	 */
	public void run() {
		new BukkitRestoreTask(this.getConfiguration(), this.plugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see name.richardson.james.bukkit.timedrestore.scheduler.Task#schedule()
	 */
	public void schedule() {
		CronRestoreTask.scheduler.schedule(this.getConfiguration().getSchedule(), this);
		if (!CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.start();
		}
		AbstractRestoreTask.LOGGER.log(Level.FINE, "Scheduled CronTimer at " + this.getConfiguration().getSchedule());
	}
}
