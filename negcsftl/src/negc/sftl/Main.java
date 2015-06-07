package negc.sftl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin 
implements Listener
{	
	File Itemsyml = new File("./plugins/negcsftl/Items.yml");
	YamlConfiguration ItemsConfig = new YamlConfiguration();
	long totalMemory = Runtime.getRuntime().totalMemory();
	long availableMemory = Runtime.getRuntime().freeMemory();
	long usedMemory = totalMemory-availableMemory;	
	public HashMap<String, Boolean> isGaming = new HashMap<String, Boolean>();
	Boolean GameStart = false;
	HashMap<String,Boolean> isalive = new HashMap<String, Boolean>();
	int amount=0;
	Scoreboard board;
	Objective ob;
	Score score;
	@SuppressWarnings("deprecation")
	public void onEnable()
	{
		/**
		 * 1.info
		 * 2.ConfigProcess
		 * 3.Registers
		 * 4.turn to false
		 */
		Server sv = this.getServer();
		for(Player i:sv.getOnlinePlayers())
		{
			isGaming.put(i.getName(), false);
		}
		this.getLogger().info("總記憶體:"+this.totalMemory);
		this.getLogger().info("以使用:"+usedMemory);
		this.getLogger().info("剩餘:"+availableMemory);
		this.getLogger().info("開啟本服核心插件!");
		if(!Itemsyml.exists())
		{
			try {
				ArrayList<Integer> KITS = new ArrayList<Integer>();
				KITS.add(276);
				ItemsConfig.set("KITS", KITS);
				ItemsConfig.save(Itemsyml);
			} catch (IOException e) {	}
		}
		else
		{
			try {
				ItemsConfig.load(Itemsyml);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {	} 
			catch (InvalidConfigurationException e) {}
			try {
				ItemsConfig.save(Itemsyml);
			} catch (IOException e) {}
		}
		Events eve = new Events(this); 
		this.getServer().getPluginManager().registerEvents(eve, this);
		Commands cmm = new Commands(this);
		this.getCommand("negcsftl").setExecutor(cmm);
		    board = getServer().getScoreboardManager().getNewScoreboard();
		    ob  = board.registerNewObjective("alive", "dummy");
		    score = ob.getScore(getServer().getOfflinePlayer(ChatColor.AQUA+"剩餘:"));
		    ob.setDisplaySlot(DisplaySlot.SIDEBAR);
			ob.setDisplayName(ChatColor.AQUA+"negcstfl");			
			score.setScore(0);
	}
	
	public void onDisable()
	{
		this.getLogger().info("關閉~");
	}
}
