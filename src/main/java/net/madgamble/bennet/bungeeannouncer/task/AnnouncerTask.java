package net.madgamble.bennet.bungeeannouncer.task;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.MainConfig;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import java.util.ArrayList;
import java.util.TimerTask;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AnnounceTask
  extends TimerTask
{
  private int counter = 0;
  private String prefix = "";
  private ArrayList<String> announcements = new ArrayList();
  
  public AnnounceTask(BungeeAnnouncer plugin)
  {
    this.prefix = plugin.getConfigStorage().settings_prefix;
    this.announcements = plugin.getConfigStorage().announcements_global;
  }
  
  public void run()
  {
    if (this.announcements.size() > 0)
    {
      while (!((String)this.announcements.get(this.counter)).equals(")"))
      {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
          player.sendMessage(FontFormat.translateString(this.prefix + (String)this.announcements.get(this.counter)));
        }
        next();
        if (this.counter == 0) {
          break;
        }
      }
      if (((String)this.announcements.get(this.counter)).equals(")")) {
        next();
      }
    }
  }
  
  public void next()
  {
    this.counter += 1;
    if (this.counter == this.announcements.size()) {
      this.counter = 0;
    }
  }
}

