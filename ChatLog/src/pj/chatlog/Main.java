package pj.chatlog;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin 
implements Listener
{
	File log = new File("./plugins/ChatLog/log.log");
	public void onEnable()
	{
		getLogger().info("感謝下載!");
		if(!log.exists())
		{
			log.getParentFile().mkdirs();
			try {
				log.createNewFile();
			} catch (IOException e) {}			
		}
		getServer().getPluginManager().registerEvents(this, this);		
	}
	public void onDisable(){getLogger().info("ShutDown");}
@EventHandler
public void LOG(PlayerChatEvent e) throws IOException
{
	long time = System.currentTimeMillis();
    Date date = new Date(time);    
    String name = e.getPlayer().getName();
    String message = e.getMessage();
    message = "["+date+"]"+name+":"+message;
    FileReader fr = new FileReader(log);
    FileWriter fw = new FileWriter(log, true);
    fw.write("\r\n"+message);
    fw.flush();
    fw.close();
    fr.close();
}
}


