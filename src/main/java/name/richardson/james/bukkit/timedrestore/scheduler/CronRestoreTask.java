/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CronRestoreTask.java is part of TimedRestore.

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

import it.sauronsoftware.cron4j.Scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import name.richardson.james.bukkit.utilities.logging.PrefixedLogger;

import name.richardson.james.bukkit.timedrestore.persistence.TaskConfigurationEntry;

/**
 * This {@link RestoreTask} implementation is responsible for the scheduling of the task itself. While {@link
 * BukkitRestoreTask} is responsible for the restore , this class is responsible for deciding when it happens and
 * creating a {@link BukkitRestoreTask} when the time is right.
 */
public class CronRestoreTask extends AbstractRestoreTask {

	final private static Scheduler scheduler = new Scheduler();

	private final Logger logger = PrefixedLogger.getLogger(CronRestoreTask.class);
	private final Plugin plugin;

	private String id;

	/**
	 * Deschedule any task schedule at a specific time from the {@link Scheduler}.
	 *
	 * @param schedule the schedule
	 */
	public static void deschedule(final String schedule) {
		CronRestoreTask.scheduler.deschedule(schedule);
	}

	public static boolean isSchedulerStarted() {
		return CronRestoreTask.scheduler.isStarted();
	}

	/**
	 * Start the region if it is not already started.
	 */
	public static void start() {
		if (!CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.start();
		}
	}

	/**
	 * Stop the region if it is not already stopped.
	 */
	public static void stop() {
		if (CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.stop();
		}
	}

	/**
	 * Instantiates a new CronRestoreTask. This task registers itself with the Cron4j region as soon as it is created for
	 * convenience.
	 *
	 * @param configuration the configuration
	 * @param plugin        the plugin
	 */
	public CronRestoreTask(final TaskConfigurationEntry configuration, final Plugin plugin) {
		super(configuration);
		this.plugin = plugin;
		this.schedule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see name.richardson.james.bukkit.timedrestore.region.Task#deschedule()
	 */
	public void deschedule() {
		CronRestoreTask.scheduler.deschedule(this.id);
		logger.log(Level.FINE, "Descheduled CronTimer at " + this.getConfiguration().getSchedule());
	}

	/**
	 * Start the restore operation by creating a {@link BukkitRestoreTask} with the {@link TaskConfigurationEntry} as the
	 * parameters. The {@link BukkitRestoreTask} handles the actual restore operation as soon as possible.
	 */
	public void run() {
		new BukkitRestoreTask(this.getConfiguration(), this.plugin, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see name.richardson.james.bukkit.timedrestore.region.Task#schedule()
	 */
	public void schedule() {
		this.id = CronRestoreTask.scheduler.schedule(this.getConfiguration().getSchedule(), this);
		if (!CronRestoreTask.scheduler.isStarted()) {
			CronRestoreTask.scheduler.start();
		}
		logger.log(Level.FINE, "Scheduled CronTimer at " + this.getConfiguration().getSchedule());
	}
}
