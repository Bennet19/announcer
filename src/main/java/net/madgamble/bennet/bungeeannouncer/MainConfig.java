package net.madgamble.bennet.bungeeannouncer;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MainConfig {

	private Configuration config;
	private File file;
	/**
	 * in minutes
	 */
	@Getter
	public int broadcastIntervall = 60;

	public MainConfig(BungeeAnnouncer plugin) {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		file = new File(plugin.getDataFolder(), "config.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		load();
	}
	
	public void save() throws IOException {
		config.set("broadcastIntervall", broadcastIntervall);
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
	}
	
	private void load() {
		if (!config.contains("broadcastIntervall")) {
			try {
				save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		broadcastIntervall = config.getInt("broadcastIntervall", 300);
	}
}
