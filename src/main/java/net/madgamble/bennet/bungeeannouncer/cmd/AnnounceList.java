package net.madgamble.bennet.bungeeannouncer.cmd;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.MainConfig;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import java.util.ArrayList;
import java.util.Iterator;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceList
  extends Command
{
  BungeeAnnouncer plugin;
  
  public AnnounceList(BungeeAnnouncer plugin)
  {
    super("announce_list");
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("bungeeannouncer.admin"))
    {
      sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
      return;
    }
    if (args.length != 0)
    {
      sender.sendMessage(FontFormat.translateString("&7Usage: /announce_list"));
      return;
    }
    sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------\n&eID - &fMessage"));
    
    Integer id = Integer.valueOf(0);
    Integer localInteger1;
    Integer localInteger2;
    for (Iterator i$ = this.plugin.getConfigStorage().announcements_global.iterator(); i$.hasNext(); localInteger2 = id = Integer.valueOf(id.intValue() + 1))
    {
      String announcement = (String)i$.next();
      sender.sendMessage(FontFormat.translateString("&a" + id + " - &f" + announcement));
      localInteger1 = id;
    }
    sender.sendMessage(FontFormat.translateString("&a-----------------------------------------------------\n"));
  }
}

