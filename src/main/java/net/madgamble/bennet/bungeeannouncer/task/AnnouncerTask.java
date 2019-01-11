package net.madgamble.bennet.bungeeannouncer.task;

import java.util.TimerTask;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.core.api.common.translation.T;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AnnouncerTask extends TimerTask {

	private final BungeeAnnouncer plugin;
	private int counter = 0;

	public AnnouncerTask(BungeeAnnouncer plugin) {
		this.plugin = plugin;
	}

	public void run() {
		if (plugin.getAnnounceManager().getAnnouncer().size() < 1) {
			return;
		}
		String announce = null;
		while((announce = plugin.getAnnounceManager().getAnnounce(counter)) == null) {
			next();
		}
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getName().equalsIgnoreCase("Heddo")) {
				T.send("bungeeannouncer.announce", p, ChatColor.translateAlternateColorCodes('&', announce));
			}
		}
		//T.broadcast("bungeeannouncer.announce", ChatColor.translateAlternateColorCodes('&', announce));
	}

	public void next() {
		this.counter += 1;
		if (this.counter == plugin.getAnnounceManager().getAnnouncer().size()) {
			this.counter = 0;
		}
	}
}
