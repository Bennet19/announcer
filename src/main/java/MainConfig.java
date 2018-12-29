
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginDescription;

public class MainConfig 
extends Config
{
  public MainConfig(BungeeAnnouncer plugin)
  {
    this.CONFIG_FILE = new File("plugins" + File.separator + plugin.getDescription().getName(), "config.yml");
    this.CONFIG_HEADER = "BungeeAnnouncer - Global Server Announcments";
  }
  
  public int settings_interval = 1;
  public String settings_prefix = "Announcer: ";
  public ArrayList<String> announcements_global = new ArrayList() {};
  
  public void addAnnouncement(String announcement)
  {
    this.announcements_global.add(announcement);
    try
    {
      save();
    }
    catch (InvalidConfigurationException e)
    {
      ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Failed to save the config!", e);
    }
  }
  
  public void removeAnnouncement(CommandSender sender, Integer id)
  {
    if ((id.intValue() > this.announcements_global.size() - 1) || (id.intValue() < 0))
    {
      sender.sendMessage(FontFormat.translateString("&7No announcement exist with id: &a" + id));
      return;
    }
    ListIterator<String> listIterator = this.announcements_global.listIterator();
    Integer counter = Integer.valueOf(0);
    
    listIterator.next();
    if (this.announcements_global.size() > 1)
    {
      Integer localInteger1;
      for (Iterator i$ = this.announcements_global.iterator(); i$.hasNext(); localInteger1 = counter = Integer.valueOf(counter.intValue() + 1))
      {
        String announcement = (String)i$.next();
        if (counter == id)
        {
          listIterator.remove();
          try
          {
            save();
            sender.sendMessage(FontFormat.translateString("&aThe announcement has been deleted"));
          }
          catch (InvalidConfigurationException e)
          {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Failed to save the config!", e);
          }
          return;
        }
        listIterator.next();
        e = counter;
      }
    }
    else
    {
      this.announcements_global = new ArrayList();
      try
      {
        save();
        sender.sendMessage(FontFormat.translateString("&aThe announcement has been deleted"));
      }
      catch (InvalidConfigurationException e)
      {
        ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Failed to save the config!", e);
      }
    }
  }
}
