package net.madgamble.bennet.bungeeannouncer.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.core.db.mysql.IMysqlConnector;

public class AnnounceManager {

	@Getter
	private final List<Announce> announcer = new ArrayList<Announce>();
	private final BungeeAnnouncer plugin;
	private final IMysqlConnector mysqlConnector;

	public AnnounceManager(BungeeAnnouncer plugin) {
		this.plugin = plugin;
		mysqlConnector = plugin.getMysqlConnector();
		try {
			//@formatter:off
			mysqlConnector.getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `bungeeAnnouncer` (" + 
					"`id` int(11) NOT NULL AUTO_INCREMENT," + 
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
						announcer.add(new Announce(rs.getInt("id"), rs.getString("text")));
					}
				}
			}
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute update:", e);
		}
	}

	public Announce getAnnounce(int id) {
		for (Announce a : announcer) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	public void addAnnounce(String text) {
		Announce a = new Announce(-1, text);
		announcer.add(a);
		a.setId(set(text));
	}

	public void updateAnnounce(int id, String text) {
		if (getAnnounce(id) != null) {
			announcer.get(id).setText(text);
			update(id, text);
		}
	}

	public void deleteAnnounce(Integer id) {
		if (getAnnounce(id) != null) {
			announcer.remove(getAnnounce(id));
			delete(id);
		}
	}

	private int set(String str) {
		try (Connection con = mysqlConnector.getConnection()) {
			try (PreparedStatement ps = con.prepareCall("INSERT INTO `bungeeAnnouncer` (text) VALUES (?)")) {
				ps.setString(1, str);
				return ps.executeUpdate();
			}
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, "Could not execute update:", e);
		}
		return -1;
	}

	private void update(int id, String str) {
		try (Connection con = mysqlConnector.getConnection()) {
			try (PreparedStatement ps = con.prepareCall("UPDATE `bungeeAnnouncer` SET text=? WHERE id=?")) {
				ps.setString(1, str);
				ps.setInt(2, id);
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
