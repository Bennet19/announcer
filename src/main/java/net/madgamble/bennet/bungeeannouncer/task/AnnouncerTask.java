package net.madgamble.bennet.bungeeannouncer.task;

import java.util.TimerTask;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.manager.Announce;
import net.madgamble.core.api.common.translation.T;
import net.md_5.bungee.api.ChatColor;

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
		if (counter >= plugin.getAnnounceManager().getAnnouncer().size()) {
			counter = 0;
		}
		Announce announce = plugin.getAnnounceManager().getAnnouncer().get(counter);
		T.broadcast("bungeeannouncer.announce", ChatColor.translateAlternateColorCodes('&', announce.getText()));
		counter++;
	}
}
