package pj.pjlike;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class commands
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) 
  {
    if ((sender instanceof Player)) {
      Player p = (Player)sender;
      if (lable.equals("pj")){
        if (args[0].equals("reload")) {
          try {
            main.config.load(main.configFile); } catch (FileNotFoundException e) {
            e.printStackTrace(); } catch (IOException e) {
            e.printStackTrace(); } catch (InvalidConfigurationException e) {
            e.printStackTrace();
          }try {
            main.config.save(main.configFile); } catch (IOException e) {
            e.printStackTrace();
          }try {
            main.mobconfig.load(main.mobFile); } catch (FileNotFoundException e) {
            e.printStackTrace(); } catch (IOException e) {
            e.printStackTrace(); } catch (InvalidConfigurationException e) {
            e.printStackTrace();
          }try {
            main.mobconfig.save(main.mobFile); } catch (IOException e) {
            e.printStackTrace();
          }p.sendMessage("Config reload!");
        }
        else if ((args[0].equals("setlobby")) && (args[1] != null)) {
          main.mobconfig.set(args[1] + ".LocationX", Double.valueOf(p.getLocation().getX()));
          main.mobconfig.set(args[1] + ".LocationY", Double.valueOf(p.getLocation().getY()));
          main.mobconfig.set(args[1] + ".LocationZ", Double.valueOf(p.getLocation().getZ()));
          main.mobconfig.set(args[1] + ".World", p.getLocation().getWorld().getName());
          main.mobconfig.set(args[1] + ".GUNS", Boolean.valueOf(true));
          reloadmobfilemode1();
          p.sendMessage("OK");
        }
        else if ((args[0].equals("create")) && (args[1] != null)) {
          if (!main.mobconfig.contains(args[1])) {
            main.mobconfig.createSection(args[1]);
            p.sendMessage("以create完成!");
            reloadmobfilemode1();
          } else {
            p.sendMessage("此名稱已被註冊!");
          }
        } else if ((args[0].equals("join")) && (args[1] != null)) {
          if (!main.mode.get(p.getName())) {
            main.mode.put(p.getName(), Boolean.valueOf(true));
            main.loc.put(p.getName(), p.getLocation());
            main.zone.put(p.getName(), args[1]);
            p.teleport(new Location(Bukkit.getServer().getWorld(main.mobconfig.getString(args[1] + ".World")), main.mobconfig
              .getDouble(args[1] + ".LocationX"), main.mobconfig
              .getDouble(args[1] + ".LocationY"), main.mobconfig
              .getDouble(args[1] + ".LocationZ")));
            if (main.mobconfig.getBoolean(args[1] + ".GUNS")) {
              p.getInventory().clear();
              p.getInventory().addItem(new ItemStack[] { main.AK47 });
              p.getInventory().addItem(new ItemStack[] { main.SHIELD });
              p.getInventory().addItem(new ItemStack[] { main.PYTHON });
              p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1000) });
              p.setGameMode(GameMode.ADVENTURE);
            }
            if (main.mobconfig.getBoolean(args[1] + ".SNOW")) {
              p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_SPADE, 1) });
            }
            p.sendMessage((String)main.zone.get(p.getName())); 
            } 
          else {
            p.sendMessage("請先使用/pj leave離開");
          }
        } 
        else if (args[0].equals("leave")) {
          main.mode.put(p.getName(), Boolean.valueOf(false));
          p.sendMessage("以離開遊戲!");
          p.teleport((Location)main.loc.get(p.getName()));
        }
        else if(args[0].equals("setspawn")){
        	main.config.set("重生點X", p.getLocation().getX());
        	main.config.set("重生點Y", p.getLocation().getY());
        	main.config.set("重生點Z", p.getLocation().getZ());
        	main.config.set("重生點W", p.getLocation().getWorld().getName());
        	main.config.set("重生點yaw", p.getLocation().getYaw());
        	main.config.set("重生點pitch", p.getLocation().getPitch());
        	p.sendMessage("OK!");
        }
        else if(args[0].equals("spawn")){
        	p.teleport(new Location(Bukkit.getServer().getWorld(main.config.getString("重生點W"))
        			,main.config.getDouble("重生點X")
        			,main.config.getDouble("重生點Y")
        			,main.config.getDouble("重生點Z")
        			,(float)main.config.get("重生點yaw")
        			,(float)main.config.getDouble("重生點pitch")));
        	p.sendMessage("傳送完畢!");
        }
        else if(args[0].equals("setlobby1")&&args[1]!=null){
        	main.mobconfig.set(args[1] + ".LocationX1", Double.valueOf(p.getLocation().getX()));
            main.mobconfig.set(args[1] + ".LocationY1", Double.valueOf(p.getLocation().getY()));
            main.mobconfig.set(args[1] + ".LocationZ1", Double.valueOf(p.getLocation().getZ()));
            main.mobconfig.set(args[1] + ".World1", p.getLocation().getWorld().getName());
            reloadmobfilemode1();
            p.sendMessage("OK");
        }
      }
    } else {
      sender.sendMessage("此指令只能使用在玩家上!");
    }return false;
  }
  public void reloadmobfilemode1() {
    try {
      main.mobconfig.save(main.mobFile); } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void reloadmobfilemode2() {
    try { main.mobconfig.load(main.mobFile); } catch (FileNotFoundException e) {
      e.printStackTrace(); } catch (IOException e) {
      e.printStackTrace(); } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    }try {
      main.mobconfig.save(main.mobFile); } catch (IOException e) {
      e.printStackTrace();
    }
  }
}