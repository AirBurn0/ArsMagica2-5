package am2.commands;

import am2.lore.ArcaneCompendium;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class SetCompendiumState extends CommandBase {

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandName() {
		return "setcompendiumstate";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/setcompendiumstate {LOCKED/UNLOCKED}";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		if(var2.length != 1) {
			throw new WrongUsageException(this.getCommandUsage(var1));
		}

		if(var2[1].equalsIgnoreCase("locked")) {
			ArcaneCompendium.instance.setLockedState(true);
		}
		else if(var2[1].equalsIgnoreCase("unlocked")) {
			ArcaneCompendium.instance.setLockedState(false);
		}
		else {
			throw new WrongUsageException(this.getCommandUsage(var1));
		}
	}
}
