/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * StatusCommand.java is part of TimedRestore.
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
import name.richardson.james.bukkit.utilities.command.CommandPermissions;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundles;

/**
 * This command returns the status of the cron scheduler on request.
 */
@CommandPermissions(permissions = { "timedrestore.status" })
public class StatusCommand extends AbstractCommand {

	/**
	 * Instantiates a new status command.
	 */
	public StatusCommand() {
		super(ResourceBundles.MESSAGES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.command.Command#execute(java.util
	 * .List, org.bukkit.command.CommandSender)
	 */
	public void execute(final List<String> arguments, final CommandSender sender) {
		if (CronRestoreTask.isSchedulerStarted()) {
			sender.sendMessage(this.getMessage("scheduler-running"));
		} else {
			sender.sendMessage(this.getMessage("scheduler-not-running"));
		}
	}

}
