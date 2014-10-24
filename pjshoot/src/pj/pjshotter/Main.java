package pj.pjshotter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
  implements Listener
{
  public static File Mainfile = new File("./plugins/pjshotter/MainConfig.yml");
  public YamlConfiguration Mainconfig = new YamlConfiguration();
  public Economy econ; 
  public HashMap<String,Integer> point = new HashMap<String,Integer>();
  public void onEnable() 
  {	
	events eve = new events(this);
    getLogger().info("pjExp開啟");
    getServer().getPluginManager().registerEvents(this, this);
    getServer().getPluginManager().registerEvents(eve, this);
    commands c = new commands(this);
    getCommand("pjexp").setExecutor(c);
    if(setupEconomy())
    {
    	System.out.println("Economy設定成功!");
    }
    else
    {
    	System.out.println("Economy設定失敗!");
    }
    if (!Mainfile.exists())
    {
      create();
    }
    else 
    {
    	load();  
    }
    File bin = new File("./plugins/pjshotter/point.bin");
    if(bin.exists())
    {
    	point = this.loadMap("./plugins/pjshotter/point.bin");
    	System.out.println("point.bin Load成功!");
    }
    else
    {
    	System.out.println("point.bin不存在!");
    }
    Player[] plist = getServer().getOnlinePlayers();
	for(int i = 0;i<getServer().getOnlinePlayers().length;i++)
	{
		if(point.containsKey(plist[i].getName()))
		{
			point.put(plist[i].getName(), point.get(plist[i].getName()));
		}
		else
		{
			point.put(plist[i].getName(), 0);
		}
	}
    }
  public void onDisable()
  {
    getLogger().info("pjEXP關閉!");
    this.saveMap(point, "./plugins/pjshotter/point.bin");
  }

  public void create()
  {
    try {
        List<String> data = new ArrayList<String>();
        Mainconfig.set("dontallowcommand", data);
        Mainconfig.set("price", 10000);
      Mainconfig.save(Mainfile); } catch (IOException e) { e.printStackTrace(); } 
  }

  public void load() 
  {
    try { Mainconfig.load(Mainfile); } catch (FileNotFoundException e) {
      e.printStackTrace(); } catch (IOException e) {
      e.printStackTrace(); } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    }try { Mainconfig.save(Mainfile); } catch (IOException e) { e.printStackTrace(); }
  }
  public void saveMap(HashMap<String,Integer> point, String path)
  {
		ObjectOutputStream ops;
		try {
			ops = new ObjectOutputStream(new FileOutputStream(path));
			ops.writeObject(point);
			ops.flush();
			ops.close();
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException e) {	e.printStackTrace();}		
  }
  @SuppressWarnings({ "unchecked", "resource" })
public HashMap<String, Integer> loadMap(String path)
  {
	try {
		ObjectInputStream ops = new ObjectInputStream(new FileInputStream(path));
		Object result = ops.readObject();	
		return (HashMap<String, Integer>)result;
	} catch (FileNotFoundException e) {e.printStackTrace();
	} catch (IOException e) {e.printStackTrace();}
	catch (ClassNotFoundException e) {e.printStackTrace();}
	return null;
  }
public boolean setupEconomy()
  {	  
	  RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	  if(rsp.getProvider()!=null)
	  {
		  econ = (Economy)rsp.getProvider();  
	  }
	  return econ!=null;	  
  }
public void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
    for (ItemStack is : inv.getContents()) {
        if (is != null && is.getType() == type) {
            int newamount = is.getAmount() - amount;
            if (newamount > 0) {
                is.setAmount(newamount);
                break;
            } else {
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) break;
            }
        }
    }
}
}