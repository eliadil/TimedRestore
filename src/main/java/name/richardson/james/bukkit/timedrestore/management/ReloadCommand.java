/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 *
 * ReloadCommand.java is part of TimedRestore.
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

import java.io.IOException;
import java.util.List;

import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.timedrestore.TimedRestorePlugin;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCommandSender;

/**
 * This command reloads the configuration of the plugin on request.
 */
@CommandPermissions(permissions = {"timedrestore.reload"})
public class ReloadCommand extends AbstractCommand {

	private final TimedRestorePlugin plugin;

	/**
	 * Instantiates a new reload command.
	 *
	 * @param plugin the plugin
	 */
	public ReloadCommand(final TimedRestorePlugin plugin) {
		this.plugin = plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.richardson.james.bukkit.utilities.command.Command#execute(java.util
	 * .List, org.bukkit.command.CommandSender)
	 */
	public void execute(final List<String> arguments, final CommandSender sender) {
		LocalisedCommandSender lsender = new LocalisedCommandSender(sender, this.localisation);
		try {
			this.plugin.reloadConfiguration();
			lsender.info("success");
		} catch (final IOException e) {
			lsender.error("failure");
		}
	}

}
