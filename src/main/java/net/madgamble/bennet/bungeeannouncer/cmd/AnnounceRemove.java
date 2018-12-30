package net.madgamble.bennet.bungeeannouncer.cmd;

import net.madgamble.bennet.bungeeannouncer.BungeeAnnouncer;
import net.madgamble.bennet.bungeeannouncer.MainConfig;
import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceRemove
  extends Command
{
  BungeeAnnouncer plugin;
  
  public AnnounceRemove(BungeeAnnouncer plugin)
  {
    super("announce_remove");
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("bungeeannouncer.admin"))
    {
      sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
      return;
    }
    if (args.length != 1)
    {
      sender.sendMessage(FontFormat.translateString("&7Usage: /announce_remove <message>"));
      return;
    }
    try
    {
      int id = Integer.parseInt(args[0]);
      this.plugin.getConfigStorage().removeAnnouncement(sender, Integer.valueOf(id));
    }
    catch (NumberFormatException e)
    {
      sender.sendMessage(FontFormat.translateString("&4You can only use numeric values."));
      return;
    }
  }
}