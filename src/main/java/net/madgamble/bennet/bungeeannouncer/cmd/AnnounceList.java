package net.madgamble.bennet.bungeeannouncer.cmd;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.manager.Announce;
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
		sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------"));
		sender.sendMessage(FontFormat.translateString("&eID - &fMessage"));
		for (Announce a : plugin.getAnnounceManager().getAnnouncer()) {
			sender.sendMessage(FontFormat.translateString("&a" + a.getId() + " &7- &f" + a.getText()));
		}
		sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------"));
	}
}
