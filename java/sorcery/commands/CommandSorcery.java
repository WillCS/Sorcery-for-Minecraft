package sorcery.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import sorcery.core.Sorcery;

public class CommandSorcery extends CommandBase {
	@Override
	public int compareTo(Object obj) {
		return this.getCommandName().compareTo(((ICommand)obj).getCommandName());
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public String getCommandName() {
		return "sorcery";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		
		if(arguments.length <= 0)
			throw new WrongUsageException("Type '" + this.getCommandUsage(sender) + "' for help.");
		
		if(arguments[0].matches("version")) {
			commandVersion(sender, arguments);
			return;
		} else if(arguments[0].matches("help")) {
			sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " <command> <arguments>'"));
			sender.addChatMessage(new ChatComponentText("Available Commands:"));
			sender.addChatMessage(new ChatComponentText("> version : Version Info."));
			return;
		}
		
		throw new WrongUsageException(this.getCommandUsage(sender));
	}
	
	private void commandVersion(ICommandSender sender, String[] arguments) {
		String msg = "Currently running version " + Sorcery.instance.getVersion() + " of Sorcery.";
		sender.addChatMessage(new ChatComponentText(msg));
	}
	
}
