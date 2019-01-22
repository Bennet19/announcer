package net.madgamble.bennet.bungeeannouncer.cmd;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceAdd extends Command {

	BungeeAnnouncer plugin;

	public AnnounceAdd(BungeeAnnouncer plugin) {
		super("announceadd");
		this.plugin = plugin;
	}

	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("bungeeannouncer.admin")) {
			sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
			return;
		}
		if (args.length < 1) {
			sender.sendMessage(FontFormat.translateString("&7Benutze: /announceadd <message>"));
			return;
		}
		String text = String.join(" ", args);
		plugin.getAnnounceManager().addAnnounce(text);
		sender.sendMessage(FontFormat.translateString("&aThe announcement has been added!"));
		try {
			plugin.sendUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
