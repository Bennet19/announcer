package net.madgamble.bennet.bungeeannouncer.cmd;

import java.util.Map.Entry;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceList extends Command {

	BungeeAnnouncer plugin;

	public AnnounceList(BungeeAnnouncer plugin) {
		super("announce_list");
		this.plugin = plugin;
	}

	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("bungeeannouncer.admin")) {
			sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
			return;
		}
		if (args.length != 0) {
			sender.sendMessage(FontFormat.translateString("&7Usage: /announce_list"));
			return;
		}
		sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------\n&eID - &fMessage"));
		for (Entry<Integer, String> entry : plugin.getAnnounceManager().getAnnouncer().entrySet()) {
			sender.sendMessage(FontFormat.translateString("&a" + entry.getKey() + " &7- &f" + entry.getValue()));
		}
		sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------\n"));
	}
}
