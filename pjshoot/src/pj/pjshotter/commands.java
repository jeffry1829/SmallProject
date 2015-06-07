package pj.pjshotter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands
  implements CommandExecutor
{
  Main instance;
  public commands(Main instance)
  {
    this.instance = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
    if ((sender instanceof Player)) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.GREEN + "=====pjExp By.PJ=====");
      }
      else if (lable.equals("pjexp")){
        if (args[0].equals("reload")) 
        {
          this.instance.load();
          sender.sendMessage("Config Reloaded!");
        }
        else if(args[0].equals("point"))
        {
        	sender.sendMessage("你目前的攻擊力點有"+instance.point.get(sender.getName()));
        }
        else if(args[0].equals("reset")&&sender.isOp())
        {
        	instance.point.put(sender.getName(), 0);
        	sender.sendMessage("reset Successfully!");
        }
    }
}
    else
    {
      sender.sendMessage("只有遊戲中玩家才能打指令!");
    }
      return false;
  }
}