package net.madgamble.bennet.bungeeannouncer.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.core.db.mysql.IMysqlConnector;

public class AnnounceManager {

	@Getter
	private final Map<Integer, String> announcer = new HashMap<Integer, String>();
	private final BungeeAnnouncer plugin;
	private final IMysqlConnector mysqlConnector;

	public AnnounceManager(BungeeAnnouncer plugin) {
		this.plugin = plugin;
		mysqlConnector = plugin.getMysqlConnector();
		try {
			//@formatter:off
			mysqlConnector.getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `bungeeAnnouncer` (" + 
					"`id` int(11) NOT NULL ," + 
					"`text`  varchar(256) NOT NULL," + 
					"PRIMARY KEY (`id`)"+
					");").executeUpdate();
			//@formatter:on
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute update:", e);
		}
		
		load();
	}
	
	public void load() {
		announcer.clear();
		try (Connection con = mysqlConnector.getConnection()) {
			try (PreparedStatement ps = con.prepareCall("SELECT * FROM `bungeeAnnouncer`")) {
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						announcer.put(rs.getInt("id"), rs.getString("text"));
					}
				}
			}
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute update:", e);
		}
	}

	public String getAnnounce(int id) {
		if (announcer.containsKey(id)) {
			return announcer.get(id);
		}
		return null;
	}
	
	private Integer getNextId() {
		int id = 0;
		while (announcer.containsKey(id)) {
			id++;
		}
		return id;
	}

	public void addAnnounce(String str) {
		int id = getNextId();
		announcer.put(id, str);
		update(id, str);
	}

	public void updateAnnounce(int id, String str) {
		announcer.put(id, str);
		update(id, str);
	}

	public void deleteAnnounce(Integer id) {
		if (announcer.containsKey(id)) {
			announcer.remove(id);
			delete(id);
		}
	}

	private void update(int id, String str) {
		try (Connection con = mysqlConnector.getConnection()) {
			try (PreparedStatement ps = con.prepareCall("INSERT INTO `bungeeAnnouncer` (id, text) VALUES (?, ?) ON DUPLICATE KEY UPDATE text=VALUES(text)")) {
				ps.setInt(1, id);
				ps.setString(2, str);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute update:", e);
		}
	}

	private void delete(int id) {
		try (Connection con = mysqlConnector.getConnection()) {
			try (PreparedStatement ps = con.prepareCall("DELETE FROM `bungeeAnnouncer` WHERE id=?")) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute delete:", e);
		}
	}
}
