/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SchedulerCommand.java is part of TimedRestore.

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

package name.richardson.james.bukkit.timedrestore.management;

import java.lang.ref.WeakReference;
import java.util.List;

import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.timedrestore.scheduler.CronRestoreTask;

import name.richardson.james.bukkit.utilities.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArguments;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;
import name.richardson.james.bukkit.utilities.command.argument.BooleanArgument;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCoreColourScheme;

/**
 * This command sets the status of the cron region.
 *
 * Currently two options are available, start and stop.
 */
@CommandPermissions(permissions = {"timedrestore.scheduler"})
@CommandArguments(arguments = {BooleanArgument.class})
public class SchedulerCommand extends AbstractCommand {

	private final LocalisedCoreColourScheme localisedColourScheme = new LocalisedCoreColourScheme(this.getResourceBundle());

	private WeakReference<CommandSender> sender;
	private boolean state;

	public void execute(final List<String> arguments, final CommandSender sender) {
		this.sender = new WeakReference<CommandSender>(sender);
		this.parseArguments(arguments);
		if (state) {
			CronRestoreTask.start();
			sender.sendMessage(localisedColourScheme.format(ColourScheme.Style.INFO, "scheduler-started"));
		} else {
			CronRestoreTask.stop();
			sender.sendMessage(localisedColourScheme.format(ColourScheme.Style.WARNING, "scheduler-stopped"));
		}
	}

	@Override
	protected void parseArguments(List<String> arguments) {
		try {
			super.parseArguments(arguments);
			this.state = (Boolean) this.getArguments().get(0).getValue();
		} catch (InvalidArgumentException e) {
			sender.get().sendMessage(localisedColourScheme.format(ColourScheme.Style.ERROR, "must-specify-boolean"));
		}
	}

}
