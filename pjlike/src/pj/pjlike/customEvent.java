package pj.pjlike;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class customEvent
  implements Listener
{
  private main instance;

  public customEvent(main instance)
  {
    this.instance = instance;
  }
  @EventHandler
  public void chat(AsyncPlayerChatEvent e) {
    if (main.config.getBoolean("聊天限制")) {
      String message = e.getMessage();
      if (e.getMessage().startsWith("!")) {
        e.setFormat(ChatColor.AQUA + "[全頻講話]" + ChatColor.RED + "♥" + "<" + e.getPlayer().getDisplayName() + ">" + ChatColor.RESET + message.replaceFirst("!", ""));
      }
      else if (!e.getMessage().startsWith("!")) {
        Location loc = e.getPlayer().getLocation();
        Player[] plist = Bukkit.getServer().getOnlinePlayers();
        for (int q = 0; q < plist.length; q++) {
          if (plist[q].isOp()) plist[q].sendMessage(ChatColor.AQUA + "[管理員監控-區域]" + ChatColor.RESET + "<" + e.getPlayer().getDisplayName() + ">" + ChatColor.RESET + e.getMessage());
          for (int i = 0; i < 50; i++)
            if (((int)loc.getX() + i == (int)plist[q].getLocation().getX()) || ((int)loc.getX() - i == (int)plist[q].getLocation().getX()))
            {
              plist[q].sendMessage(ChatColor.AQUA + "[區域]" + ChatColor.RESET + "<" + e.getPlayer().getDisplayName() + ">" + ChatColor.RESET + e.getMessage());
              e.setCancelled(true);
            }
        }
      }
    }
  }

  @EventHandler
  public void sign(SignChangeEvent e)
  {
    if ((e.getLine(0).equals("[pjlike]")) && (e.getLine(1) != null)) {
      Player p = e.getPlayer();
      e.setLine(2, "0");
      if (e.getLine(1).equals("")) e.setLine(1, p.getName());
      p.sendMessage("LIKE告示牌創建成功");
    } else if (e.getLine(0).equals("[pjhelp]")) {
      e.setLine(1, ChatColor.BLUE + "呼叫op求助鈴!");
    }
  }

  @EventHandler
  public void craft(CraftItemEvent e) {
    if (e.getCurrentItem().getType() == Material.HOPPER_MINECART) {
      Player p = (Player)e.getView().getPlayer();
      p.sendMessage(ChatColor.GREEN + "不能合成漏斗礦車!");
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void farm(final BlockBreakEvent e)
  {
    if (e.getBlock()!=null&&e.getBlock().getType() == Material.CROPS) {
      Player p = e.getPlayer();
      final PlayerInventory pinv = e.getPlayer().getInventory();
      if (pinv.getItemInHand().getType() == Material.SEEDS) {
        if (pinv.contains(Material.SEEDS)) {
          p.sendMessage("種子自動栽種!");
          Bukkit.getServer().getScheduler().runTaskLater(this.instance, new Runnable()
          {
            public void run() {
              int amount = pinv.getItemInHand().getAmount();
              pinv.getItemInHand().setAmount(amount - 1);
              e.getBlock().setType(Material.CROPS);
            }
          }
          , 1L); } else {
          p.sendMessage("沒有種子可栽種");
        } } else {p.sendMessage("你必須手裡拿著種子才可以自動栽種!");}
    }
    //else if (e.getBlock()!=null&&main.mode.get(e.getPlayer().getName())) {e.setCancelled(true);} 
  }

  @EventHandler
  public void click(PlayerInteractEvent e) { 
	  if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == Material.WALL_SIGN)) {
      Player p = e.getPlayer();
      Sign sign = (Sign)e.getClickedBlock().getState();
      if (sign.getLine(0).equals("[pjlike]")) {
        if (!this.instance.getConfig().getBoolean(sign.getLine(1) + ".like." + p.getName())) {
          this.instance.getConfig().set(sign.getLine(1) + ".like." + p.getName(), Boolean.valueOf(true));
          this.instance.saveConfig();
          sign.setLine(2, Integer.toString(Integer.valueOf(sign.getLine(2)).intValue() + 1));
          sign.update();
          p.sendMessage("給了" + sign.getLine(1) + "一個讚 總共有" + sign.getLine(2));
        } else {
          p.sendMessage("你給過讚了！" + ChatColor.RED + "♥");
        } } else if ((sign.getLine(0).equals("[pjhelp]")) && 
        (main.config.getBoolean("helpop限制"))) {
        String pname = e.getPlayer().getDisplayName();
        e.getPlayer().sendMessage("求助成功!");
        Player[] list = Bukkit.getServer().getOnlinePlayers();
        for (int i = 0; i < list.length; i++)
          if (list[i].isOp()) list[i].sendMessage("玩家:" + pname + ChatColor.DARK_PURPLE + "提出了求助訊號!!");
      }
    }
  }

  @EventHandler
  public void spawn(CreatureSpawnEvent e)
  {
    if (main.config.getBoolean("凋零限制")) {
      e.getSpawnReason(); if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
        Double locX = Double.valueOf(e.getLocation().getX());
        Double locY = Double.valueOf(e.getLocation().getY());
        Double locZ = Double.valueOf(e.getLocation().getZ());
        String world = e.getLocation().getWorld().getName();
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "有人在" + world + " " + locX + " " + locY + " " + locZ + "建造了凋靈怪! 不過他失敗了 哈哈哈哈");
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void cm(PlayerCommandPreprocessEvent e) { Player p = e.getPlayer();
    if ((main.mode.containsKey(p.getName())) && (main.mode.containsValue(Boolean.valueOf(true)))) {
      String command = e.getMessage();
      if (main.config.getStringList("dontallowcommand").contains(command)) {
        p.sendMessage("目前不能行使這項指令!");
        e.setCancelled(true);
      }
    }
  }

  

  @EventHandler
  public void respawn(PlayerRespawnEvent e)
  {
    Player p = e.getPlayer();
    if ((main.mode.get(p.getName()))){
      int iChance = (int)(Math.random()*10000+1);
      if(iChance<=50*100){
    	e.setRespawnLocation(
        new Location(
        Bukkit.getServer().getWorld(
        main.mobconfig.get((String)main.zone.get(p.getName()) + ".World").toString()), 
        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationX"), 
        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationY"), 
        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationZ")));
      if (main.mobconfig.getBoolean((String)main.zone.get(p.getName()) + ".GUNS")) {
        p.getInventory().addItem(new ItemStack[] { main.AK47 });
        p.getInventory().addItem(new ItemStack[] { main.SHIELD });
        p.getInventory().addItem(new ItemStack[] { main.PYTHON });
        p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1000) });
        p.setGameMode(GameMode.SURVIVAL);
      }
      }else{
    	  e.setRespawnLocation(
    		        new Location(
    		        Bukkit.getServer().getWorld(
    		        main.mobconfig.get((String)main.zone.get(p.getName()) + ".World1").toString()), 
    		        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationX1"), 
    		        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationY1"), 
    		        main.mobconfig.getDouble((String)main.zone.get(p.getName()) + ".LocationZ1")));
    		      if (main.mobconfig.getBoolean((String)main.zone.get(p.getName()) + ".GUNS")) {
    		        p.getInventory().addItem(new ItemStack[] { main.AK47 });
    		        p.getInventory().addItem(new ItemStack[] { main.SHIELD });
    		        p.getInventory().addItem(new ItemStack[] { main.PYTHON });
    		        p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1000) });
    		        p.setGameMode(GameMode.SURVIVAL);
    		      }
      }
    } else {
    	p.teleport(new Location(Bukkit.getServer().getWorld(main.config.getString("重生點W"))
    			,main.config.getDouble("重生點X")
    			,main.config.getDouble("重生點Y")
    			,main.config.getDouble("重生點Z")
    			,(float)main.config.get("重生點yaw")
    			,(float)main.config.getDouble("重生點pitch")));
    	p.sendMessage("傳送完畢!");
    }    
  }
@EventHandler
  public void login(PlayerJoinEvent e) { 
	  Player p = e.getPlayer();
      main.mode.put(p.getName(), Boolean.valueOf(false));
      p.sendMessage("以清除遊戲紀錄(勿擔心)");
      p.teleport(new Location(Bukkit.getServer().getWorld(main.config.getString("重生點W"))
  			,main.config.getDouble("重生點X")
  			,main.config.getDouble("重生點Y")
  			,main.config.getDouble("重生點Z")
  			,(float)main.config.get("重生點yaw")
  			,(float)main.config.getDouble("重生點pitch")));
  	p.sendMessage("傳送完畢!");
  }

  /*
@SuppressWarnings("deprecation")
@EventHandler
public void death(PlayerDeathEvent e) {
  if ((e.getEntity() instanceof Player)) {
    Player p = e.getEntity();
	LivingEntity z = p.getWorld().spawnCreature(p.getLocation(), EntityType.ZOMBIE);
    z.setCustomName(e.getEntity().getName() + "的屍體");
    z.setMaxHealth(100.0D);
    z.setHealth(100.0D);
    ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta ismeta = (SkullMeta)is.getItemMeta();
    ismeta.setOwner(p.getName());
    is.setItemMeta(ismeta);
    z.getEquipment().setHelmet(is);
    p.sendMessage("警告!若惡意自殺 將會被BAN!");
    //RemoteConsoleCommandSender sse = null;
  }
}
@EventHandler
public void damage(EntityDamageByEntityEvent e){
	  if(e.getDamager() instanceof Player){
		  Player Dp = (Player)e.getDamager();
		  ItemMeta It = Dp.getItemInHand().getItemMeta();
		  ItemStack Is = Dp.getItemInHand();
		  if(Is.getType()!=null&&Is.getType()==Material.DIAMOND_SWORD&&It.getEnchantLevel(Enchantment.KNOCKBACK)==1000){
			  Dp.sendMessage("檢測到惡意物品 自動移除");
			  Dp.getInventory().remove(Is);
			  e.setCancelled(true);
		  }
	  }
}
*/
}