package negc.sftl;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events implements Listener
{
	Main instance;
	public Events(Main instance)
	{
		this.instance = instance;
	}
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e)
	{
		if(instance.getServer().getOnlinePlayers().length>=10||instance.GameStart)
		{
			e.disallow(e.getResult().KICK_WHITELIST, "遊戲以開始,請稍待");
		}		
		else
		{
			instance.isGaming.put(e.getPlayer().getName(), false);
		}
		if(instance.getServer().getOnlinePlayers().length==0)
		{
			start();
			instance.getServer().broadcastMessage("人數齊全,開始遊戲!");
		}		
	}
	
@EventHandler
public void RS(final PlayerRespawnEvent e)
{
	instance.getServer().getScheduler().runTaskLater(instance, new Runnable(){
		@Override
		public void run() 
		{
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1000000), true);
			e.getPlayer().setGameMode(GameMode.CREATIVE);
			instance.isalive.put(e.getPlayer().getName(), false);
		}}, 50);	
	System.out.println("隱形");
}

@EventHandler
public void DE(PlayerDeathEvent e)
{
	e.getEntity().getInventory().clear();
	instance.score.setScore(instance.score.getScore()-1);
	do
	{
		for(int j=1;j<=instance.getServer().getOnlinePlayers().length;j++)
		{
			Player[] i = instance.getServer().getOnlinePlayers();
			if(instance.isalive.get(i[j-1].getName()))
			{
				instance.amount+=1;
			}
		}
	}
	while(instance.amount<=1);
	{
		instance.getServer().broadcastMessage("遊戲結束! Game Over!");
		for(int j=1;j<=instance.getServer().getOnlinePlayers().length;j++)
		{
			Player[] i = instance.getServer().getOnlinePlayers();
			i[j-1].kickPlayer("重新開始遊戲~");
			instance.score.setScore(0);
			instance.getServer().reload();
		}
	}
}

@EventHandler
public void Quit(PlayerQuitEvent e)
{
	//Code here
}

/**
 * Timer
 */
public void start()
{
	final Server server = instance.getServer();
	instance.getServer().getScheduler().runTaskLater(instance, new Runnable(){
		@Override
		public void run() 
		{
			server.broadcastMessage(ChatColor.AQUA+"遊戲開始! GameStart!");
			/*
			 for(int j=1;j<=instance.getServer().getOnlinePlayers().length;j++)
			{
				Player[] i = instance.getServer().getOnlinePlayers();
				instance.isGaming.put(i[j].getName(), true);
				for(int g=1;g<=instance.ItemsConfig.getIntegerList("KITS").size();g++)
				{
				ItemStack itsk = new ItemStack(1);
				itsk.setTypeId(instance.ItemsConfig.getIntegerList("KITS").get(g));
				i[j].getInventory().addItem(itsk);
				i[j].sendMessage(""+itsk);
				}
				}
			*/
			for(int j=1;j<=instance.getServer().getOnlinePlayers().length;j++)
			{
				for(int g=1;g<=instance.ItemsConfig.getIntegerList("KITS").size();g++)
				{
				Player[] i = instance.getServer().getOnlinePlayers();
			server.dispatchCommand(server.getConsoleSender(), "give "+i[j-1].getName()+" "+instance.ItemsConfig.getIntegerList("KITS")
					.get(g-1));
			i[j-1].setGameMode(GameMode.ADVENTURE);
			instance.isalive.put(i[j-1].getName(), true);
		}
		}
			for(int j=1;j<=instance.getServer().getOnlinePlayers().length;j++)
			{
				Player[] i = instance.getServer().getOnlinePlayers();
				i[j-1].removePotionEffect(PotionEffectType.INVISIBILITY);
				instance.score.setScore(instance.score.getScore()+1);
				i[j-1].setScoreboard(instance.board);
			}
		}
		}, 180);
	instance.GameStart = true;
}
}