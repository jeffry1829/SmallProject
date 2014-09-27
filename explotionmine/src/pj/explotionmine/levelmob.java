package pj.explotionmine;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class levelmob implements Listener{
Main instance;
public levelmob(Main instance)
{
this.instance = instance;
}
@EventHandler
public void Creature(CreatureSpawnEvent event)
{
	if(instance.Mainconfig.getBoolean("enable"+event.getLocation().getWorld()))
	{
		Location loc = new Location(event.getLocation().getWorld(),(double)instance.Mainconfig.get("Location"+event.getEntity().getLocation().getWorld()+"X"),
				(double)instance.Mainconfig.get("Location"+event.getEntity().getLocation().getWorld()+"Y"),
				(double)instance.Mainconfig.get("Location"+event.getEntity().getLocation().getWorld()+"Z"));
		int the = (int)event.getEntity().getLocation().distance(loc);
		event.getEntity().setMaxHealth(the/2);
		event.getEntity().setHealth(the/2);
		event.getEntity().setCustomName("LV:"+(int)(the/50));
		event.getEntity().setCustomNameVisible(true);
		event.getEntity().setCanPickupItems(true);
		event.getEntity().setRemoveWhenFarAway(true);
	}
}
@EventHandler
public void Kill(EntityDeathEvent event)
{
	if(event.getEntity().isCustomNameVisible())
	{
		if(event.getEntity().getCustomName().length()>=3&&event.getEntity().getCustomName().substring(0, 3).equalsIgnoreCase("lv:")){
		int exp = Integer.parseInt(event.getEntity().getCustomName().substring(3))*2;
		event.setDroppedExp(exp);
		}
	}
}
@EventHandler
public void DamageBy(EntityDamageByEntityEvent event)
{
	
	if(event.getDamager() instanceof LivingEntity&&event.getEntity() instanceof Player)
	{
		if(((LivingEntity) event.getDamager()).isCustomNameVisible()){
		int damage = Integer.parseInt(((LivingEntity)event.getDamager()).getCustomName().substring(3));
		event.setDamage(damage);
		}
	}
	else if(event.getDamager() instanceof Player&&event.getEntity() instanceof Player)
	{
		Player player = (Player)event.getDamager();
		player.damage(event.getDamage()*2);
	}
}
}
