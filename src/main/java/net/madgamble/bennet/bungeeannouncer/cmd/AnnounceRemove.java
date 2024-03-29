package net.madgamble.bennet.bungeeannouncer.cmd;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceRemove extends Command {

	BungeeAnnouncer plugin;

	public AnnounceRemove(BungeeAnnouncer plugin) {
		super("announcedelete");
		this.plugin = plugin;
	}

	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("bungeeannouncer.admin")) {
			sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
			return;
		}
		if (args.length != 1) {
			sender.sendMessage(FontFormat.translateString("&7Benutze: /announcedelete <message>"));
			return;
		}
		try {
			int id = Integer.valueOf(args[0]);
			if (plugin.getAnnounceManager().getAnnounce(id) == null) {
				sender.sendMessage(FontFormat.translateString("&cAnnounce &6" + id + " &cdoes not exist."));
				return;
			}
			plugin.getAnnounceManager().deleteAnnounce(id);
			sender.sendMessage(FontFormat.translateString("&aAnnounce &6" + id + " &aremoved."));
			try {
				plugin.sendUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(FontFormat.translateString("&4You can only use numeric values."));
			return;
		}
	}
}
