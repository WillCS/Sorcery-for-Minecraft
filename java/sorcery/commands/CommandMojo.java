package sorcery.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import sorcery.lib.SpellHelper;

public class CommandMojo extends CommandBase {
	@Override
	public int compareTo(Object obj) {
		return this.getCommandName().compareTo(((ICommand)obj).getCommandName());
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	@Override
	public String getCommandName() {
		return "mojo";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		
		if(arguments.length <= 0)
			throw new WrongUsageException("Type '" + this.getCommandUsage(sender) + "' for help.");
		
		if(arguments[0].matches("set")) {
			commandSetMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("setMax")) {
			commandSetMaxMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("get")) {
			commandGetMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("getMax")) {
			commandGetMaxMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("regen")) {
			commandRegenMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("getRegen")) {
			commandGetRegenMojo(sender, arguments);
			return;
		} else if(arguments[0].matches("help")) {
			if(arguments.length <= 1) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " <command> <arguments>'"));
				sender.addChatMessage(new ChatComponentText("Available Commands:"));
				sender.addChatMessage(new ChatComponentText("> set : Set a Player's current Mojo"));
				sender.addChatMessage(new ChatComponentText("> setMax : Set a Player's maximum Mojo"));
				sender.addChatMessage(new ChatComponentText("> regen : Set whether a player can regenerate Mojo"));
				sender.addChatMessage(new ChatComponentText("> get : Check a Player's current Mojo"));
				sender.addChatMessage(new ChatComponentText("> getMax : Check a Player's maximum Mojo"));
				sender.addChatMessage(new ChatComponentText("> getRegen : check whether a player can regenerate Mojo"));
			} else if(arguments[1].equals("set")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " set <player> <amount> [true/false]'"));
			} else if(arguments[1].equals("setMax")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " setMax <player> <amount> [true/false]'"));
			} else if(arguments[1].equals("get")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " get <player>'"));
			} else if(arguments[1].equals("getMax")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " getMax <player>'"));
			} else if(arguments[1].equals("regen")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " regen <player> <true/false>'"));
			} else if(arguments[1].equals("regen")) {
				sender.addChatMessage(new ChatComponentText("Format: '" + this.getCommandName() + " getRegen <player>'"));
			} else
				throw new WrongUsageException("Format: '" + this.getCommandName() + "help <command>'");
			return;
		}
		
		throw new WrongUsageException(this.getCommandUsage(sender));
	}
	
	private void commandSetMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayerMP player = getPlayer(sender, arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		if(arguments.length <= 2)
			throw new WrongUsageException("Please enter a number.");
		
		if(parseInt(sender, arguments[2]) > SpellHelper.instance.getPlayerMaxMojo(player))
			arguments[2] = Integer.toString(SpellHelper.instance.getPlayerMaxMojo(player));
		
		SpellHelper.instance.setPlayerMojo(player, SpellHelper.instance.getPlayerMaxMojo(player) - parseInt(sender, arguments[2]));
		SpellHelper.instance.sendMojoPacketToPlayer(player, parseInt(sender, arguments[2]), SpellHelper.instance.getPlayerMaxMojo(player), SpellHelper.getInstance().getPlayerBurnOutTimer(player), SpellHelper.getInstance().getPlayerCanRegenMojo(player));
		
		if(arguments.length <= 2 && arguments[3].equals("true"))
			player.addChatComponentMessage(new ChatComponentText("Your current Mojo has been set to " + arguments[2] + "."));
		
		 notifyAdmins(sender, player.getCommandSenderName() + "'s Mojo has been set to " + arguments[2] + ".");
	}
	
	private void commandSetMaxMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayerMP player = getPlayer(sender, arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		if(arguments.length <= 2)
			throw new WrongUsageException("Please enter a number.");
		
		int mojo = SpellHelper.instance.getPlayerMojo(player) > parseInt(sender, arguments[2]) ? parseInt(sender, arguments[2]) : SpellHelper.instance.getPlayerMojo(player);
		SpellHelper.instance.setPlayerMaxMojo(player, parseInt(sender, arguments[2]));
		SpellHelper.instance.setPlayerMojo(player, mojo);
		SpellHelper.instance.sendMojoPacketToPlayer(player, mojo, parseInt(sender, arguments[2]), SpellHelper.getInstance().getPlayerBurnOutTimer(player), SpellHelper.getInstance().getPlayerCanRegenMojo(player));
		
		if(arguments.length <= 2 && arguments[3].equals("true"))
			player.addChatMessage(new ChatComponentText("Your maximum Mojo has been set to " + arguments[2] + "."));
		
		 notifyAdmins(sender, player.getCommandSenderName() + "'s maximum Mojo has been set to " + arguments[2] + ".");
	}
	
	private void commandGetMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		
		int mojo = SpellHelper.instance.getPlayerMaxMojo(player) - SpellHelper.instance.getPlayerMojo(player);
		sender.addChatMessage(new ChatComponentText(player.getCommandSenderName() + "'s current Mojo is " + mojo + "."));
	}
	
	private void commandGetMaxMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		
		int mojo = SpellHelper.instance.getPlayerMaxMojo(player);
		sender.addChatMessage(new ChatComponentText(player.getCommandSenderName() + "'s maximum Mojo is " + mojo + "."));
	}
	
	private void commandRegenMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		if(arguments.length <= 2 || (!arguments[2].equals("true") && !arguments[2].equals("false")))
			throw new WrongUsageException("Please enter a value.");
		
		boolean regen = arguments[2].equals("true") ? true : false;
		int mojo = SpellHelper.instance.getPlayerMojo(player);
		SpellHelper.instance.sendMojoPacketToPlayer(player, mojo, SpellHelper.instance.getPlayerMaxMojo(player), SpellHelper.getInstance().getPlayerBurnOutTimer(player), regen);
		
		if(arguments.length <= 2 && arguments[3].equals("true"))
			player.addChatMessage(new ChatComponentText("Your maximum Mojo has been set to " + arguments[2] + "."));
		
		 notifyAdmins(sender, player.getCommandSenderName() + " can" + (regen ? " " : "'t ") + "regenerate Mojo.");
	}
	
	private void commandGetRegenMojo(ICommandSender sender, String[] arguments) {
		if(arguments.length <= 1)
			throw new WrongUsageException("Please enter a player's name.");
		EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(arguments[1]);
		if(player == null)
			throw new WrongUsageException("There is no player called " + arguments[1] + ".");
		
		boolean regen = SpellHelper.instance.getPlayerCanRegenMojo(player);
		
		 sender.addChatMessage(new ChatComponentText(player.getCommandSenderName() + " can" + (regen ? " " : "'t ") + "regenerate Mojo."));
	}
	
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
	
}
