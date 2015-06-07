package pj.pjlike;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
  implements Listener
{
  public static ItemStack AK47 = new ItemStack(Material.STONE_HOE, 1);
  public static ItemStack SHIELD = new ItemStack(Material.IRON_SWORD, 1);
  public static ItemStack PYTHON = new ItemStack(Material.GOLD_PICKAXE, 1);
  public ItemMeta AK47meta;
  public ItemMeta SHIELDmeta;
  public ItemMeta PYTHONmeta;
  public static HashMap<String, Boolean> mode = new HashMap<String, Boolean>();
  public static HashMap<String, Location> loc = new HashMap<String, Location>();
  public static HashMap<String, String> zone = new HashMap<String, String>();
  public static File configFile = new File("./plugins/pjlike/MainConfig.yml");
  public static YamlConfiguration config = new YamlConfiguration();
  public static File mobFile = new File("./plugins/pjlike/mobConfig.yml");
  public static YamlConfiguration mobconfig = new YamlConfiguration();

  public void onEnable()
  {
    this.AK47meta = AK47.getItemMeta();
    this.AK47meta.setDisplayName("§eAK-47§e ▪ «30»");
    AK47.setItemMeta(this.AK47meta);
    this.SHIELDmeta = SHIELD.getItemMeta();
    this.SHIELDmeta.setDisplayName("§eRiot Shield§e");
    SHIELD.setItemMeta(this.SHIELDmeta);
    this.PYTHONmeta = PYTHON.getItemMeta();
    this.PYTHONmeta.setDisplayName("§ePython §l/§e Knife§e ▪ «6»");
    PYTHON.setItemMeta(this.PYTHONmeta);

    customEvent cuev = new customEvent(this);
    getLogger().info("pjlike開啟中");
    getLogger().info("Hello!");
    getConfig().options().copyDefaults(true);
    getServer().getPluginManager().registerEvents(this, this);
    getServer().getPluginManager().registerEvents(cuev, this);

    commands c = new commands();
    getCommand("pj").setExecutor(c);
    if (!configFile.exists()) {
      List<String> data = new ArrayList<String>();
      config.set("聊天限制", Boolean.valueOf(true));
      config.set("凋零限制", Boolean.valueOf(true));
      config.set("helpop限制", Boolean.valueOf(true));
      config.set("dontallowcommand", data);
      try {
        config.save(configFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      MainConfigloadandload();
    }
    if (mobFile.exists()) {
      try {
        mobconfig.load(mobFile);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InvalidConfigurationException e) {
        e.printStackTrace();
      }
      try {
        mobconfig.save(mobFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        mobconfig.save(mobFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    saveConfig();
  }

  public void onDisable() {
    getLogger().info("關閉中");
    saveConfig();
  }

  private void MainConfigloadandload()
  {
    try
    {
      config.load(configFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    }
    try {
      config.save(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}