
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceAdd;
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceList;
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceRemove;
import net.madgamble.bennet.bungeeannouncer.task.AnnounceTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeAnnouncer
  extends Plugin
{
  private MainConfig config;
  private AnnounceTask task;
  private Timer timer = new Timer();
  
  public void onEnable()
  {
    try
    {
      this.config = new MainConfig(this);
      this.config.init();
    }
    catch (Exception ex)
    {
      ProxyServer.getInstance().getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
      return;
    }
    registerCommands();
    
    sendAnnouncement();
  }
  
  public void onDisable()
  {
    this.timer.cancel();
    this.task.cancel();
  }
  
  public void sendAnnouncement()
  {
    this.task = new AnnounceTask(this);
    this.timer.schedule(this.task, 0L, getConfigStorage().settings_interval * 1000);
  }
  
  public MainConfig getConfigStorage()
  {
    return this.config;
  }
  
  public void registerCommands()
  {
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceAdd(this));
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceRemove(this));
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceList(this));
  }
  
  public AnnounceTask getAnnounceTask()
  {
    return this.task;
  }
}

