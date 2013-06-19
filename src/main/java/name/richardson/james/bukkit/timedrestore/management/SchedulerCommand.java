/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * SchedulerCommand.java is part of TimedRestore.
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
package name.richardson.james.bukkit.timedrestore.management;

import java.util.List;

import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.timedrestore.scheduler.CronRestoreTask;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandMatchers;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCommandSender;
import name.richardson.james.bukkit.utilities.matchers.BooleanMatcher;

/**
 * This command sets the status of the cron region.
 * 
 * Currently two options are available, start and stop.
 * 
 */
@CommandPermissions(permissions = { "timedrestore.scheduler" })
@CommandMatchers(matchers = { BooleanMatcher.class })
public class SchedulerCommand extends AbstractCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.command.Command#execute(java.util
	 * .List, org.bukkit.command.CommandSender)
	 */
	public void execute(final List<String> arguments, final CommandSender sender) {
		LocalisedCommandSender lsender = new LocalisedCommandSender(sender, this.localisation);
		if (arguments.isEmpty()) {
			lsender.error("must-specify-boolean");
		} else {
			final boolean state = Boolean.parseBoolean(arguments.get(0).toUpperCase());
			if (state) {
				CronRestoreTask.start();
				lsender.info("scheduler-started");
			} else {
				CronRestoreTask.stop();
				lsender.info("scheduler-stopped");
			}
		}
	}

}
