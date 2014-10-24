package pj.explotionmine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	HashMap<String,Boolean> onoff = new HashMap<String,Boolean>();
	File bin = new File("./plugins/explotionmine/onoff.bin");
	File bin2 = new File("./plugins/explotionmine");
	public File Mainfile = new File("./plugins/explotionmine/Config.yml");
	public YamlConfiguration Mainconfig = new YamlConfiguration();
	public void onEnable()
	{
		levelmob levelmob = new levelmob(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(levelmob, this);
		if(bin.exists()){
		onoff=loadHashmap("./plugins/explotionmine/onoff.bin");
		}
		else if(!bin.exists()){
			try {
				bin2.mkdirs();
				bin.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		Bukkit.getServer().getLogger().info("explotion!");
		
		if(!Mainfile.exists())
		{
			try {
				Mainfile.createNewFile();
				Mainconfig.save(Mainfile);
			} catch (IOException e) {e.printStackTrace();}
		}else{try {
			Mainconfig.load(Mainfile);
		} catch (FileNotFoundException e1) {e1.printStackTrace();
		} catch (IOException e1) {e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {e1.printStackTrace();}
		try {
			Mainconfig.save(Mainfile);
		} catch (IOException e) {e.printStackTrace();}}
	}
	public void onDisable(){
		saveHashmap(onoff,"./plugins/explotionmine/onoff.bin");
		try {
			Mainconfig.load(Mainfile);
		} catch (FileNotFoundException e1) {e1.printStackTrace();
		} catch (IOException e1) {e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {e1.printStackTrace();}
		try {
			Mainconfig.save(Mainfile);
		} catch (IOException e) {e.printStackTrace();}
		Bukkit.getServer().getLogger().info("explotion!");
	}
	@EventHandler
	public void bbreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if(	player.getItemInHand().getType()==Material.DIAMOND_PICKAXE){
		Location loc = event.getPlayer().getLocation();
		if(onoff.get(player.getName())){
		loc.getWorld().createExplosion(loc, 4F);
		}else{}
		}
		}
	@EventHandler 
	public void onExplosion(EntityExplodeEvent event)
	{
		List<Block> blocklist = event.blockList();
		if(check(blocklist)==false){
			event.setCancelled(true);
			}
	}	
	@EventHandler
	public void Login(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		onoff.put(player.getName(), true);
		}
	@EventHandler
	public void Damage(EntityDamageEvent event)
	{
		if(event.getCause()==DamageCause.BLOCK_EXPLOSION)
		{
			event.setCancelled(true);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(sender instanceof Player&&sender.isOp()){
			Player player = (Player)sender;
		if(label.equalsIgnoreCase("explosionmine")||label.equalsIgnoreCase("exmine"))
		{
			if(onoff.get(player.getName()))
			{
				onoff.put(player.getName(), false);
			}
			else
			{
				onoff.put(player.getName(), true);
			}
		}
		else if(label.equalsIgnoreCase("levelmob")||label.equalsIgnoreCase("lmb"))
		{
			if(!Mainconfig.getBoolean("enable"+player.getLocation().getWorld())
					||!Mainconfig.contains("enable"+player.getLocation().getWorld())){
			Location loc = player.getLocation();
			Mainconfig.set("enable"+player.getLocation().getWorld(), true);
			Mainconfig.set("Location"+player.getLocation().getWorld()+"X", loc.getX());
			Mainconfig.set("Location"+player.getLocation().getWorld()+"Y", loc.getY());
			Mainconfig.set("Location"+player.getLocation().getWorld()+"Z", loc.getZ());
			player.sendMessage("levelmob enable!");
			}
			else
			{
				Mainconfig.set("enable"+player.getLocation().getWorld(), false);
				player.sendMessage("levelmob disable!");
			}
		}
		}
		return false;
		}
	/**
	 * @author petjelinux
	 * @param hash
	 * @param path
	 * @param blist 
	 */
	public void saveHashmap(HashMap<String,Boolean> hash,String path)
	{
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(hash);
			oos.close();
			oos.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	public HashMap<String,Boolean> loadHashmap(String path)
	{
		try{
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path));
			Object newhash=oos.readObject();
			return (HashMap<String,Boolean>)newhash;
		}
		catch(IOException e1){e1.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public boolean check(List<Block> blist){
		for(int j=1;j<blist.size();j++){
			if(	blist.get(j).getType()!=Material.STONE&&
					blist.get(j).getType()!=Material.NETHERRACK&&
					blist.get(j).getType()!=Material.SOUL_SAND&&
					blist.get(j).getType()!=Material.DIRT)
			{
				return false;
				}
		}
		return true;
	}
}
