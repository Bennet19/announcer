package net.madgamble.bennet.bungeeannouncer.cmd;


import net.madgamble.bennet.bungeeannouncer.utils.FontFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceAdd
  extends Command
{
  BungeeAnnouncer plugin;
  
  public AnnounceAdd(BungeeAnnouncer plugin)
  {
    super("announce_add");
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    String announcement = "";
    if (!sender.hasPermission("bungeeannouncer.admin"))
    {
      sender.sendMessage(FontFormat.translateString("&cI`m sorry, but you do not have permission to perform this command."));
      return;
    }
    if (args.length < 1)
    {
      sender.sendMessage(FontFormat.translateString("&7Usage: /announce_add <message>"));
      return;
    }
    for (String data : args) {
      announcement = announcement + data + " ";
    }
    announcement = announcement.substring(0, announcement.length() - 1);
    
    this.plugin.getConfigStorage().addAnnouncement(announcement);
    sender.sendMessage(FontFormat.translateString("&aThe announcement has been added!"));
  }
}
