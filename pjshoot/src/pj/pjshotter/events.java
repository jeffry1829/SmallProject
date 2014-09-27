package pj.pjshotter;




import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;




public class events
  implements Listener
{
  private Main instance;
  public events(Main instance)
  {
    this.instance = instance;
  }
  public Boolean tf = false;
  public Integer count = null;
  CoolTime ct= new CoolTime();
  /**
   * 第一次登入時給予15等基本Exp
   */
  @EventHandler
  public void FirstLogin(PlayerJoinEvent e)
  {
	  if(!instance.Mainconfig.getBoolean(e.getPlayer().getName()))
	  {
		  instance.Mainconfig.set(e.getPlayer().getName(), true);
		  instance.load();
		  instance.point.put(e.getPlayer().getName(), 0);
		  e.getPlayer().setLevel(e.getPlayer().getLevel()+15);
		  e.getPlayer().sendMessage("你有15級的基本等級,如果小於5等時,將會受到懲罰喔!!!");
	  }
	  Player[] plist = instance.getServer().getOnlinePlayers();
		for(int i = 0;i<instance.getServer().getOnlinePlayers().length;i++)
		{
			if(instance.point.containsKey(plist[i].getName()))
			{
				instance.point.put(plist[i].getName(), instance.point.get(plist[i].getName()));
			}
			else
			{
				instance.point.put(plist[i].getName(), 0);
			}
		}
  }
  /**
   * 死亡時血量設回30
   */
  @EventHandler
  public void PRsE(PlayerRespawnEvent e)
  {	  
	  Player p = e.getPlayer();
	  if(p instanceof Player)
	  {
		  if(e.getPlayer().getLevel()!=49)
		  {
			  p.sendMessage("已將你的血量設回30");
			  p.sendMessage("已幫你降級1等");
			  instance.point.put(p.getName(), instance.point.get(p.getName())-1);
			  p.setLevel(p.getLevel()-1);
			  p.setMaxHealth(30.0);
			  p.setHealthScale(30.0);
			  p.setHealth(30.0);
		  }
		  else
		  {
			  p.setLevel(40);
			  p.sendMessage("已將你的血量設回30");
			  p.setMaxHealth(30.0);
			  p.setHealthScale(30.0);
			  p.setHealth(30.0);				
		  }
	  }	  
  }
  /**
   * 升等時加3滴總血量
   * 剩5等時罰金
   */  
  @EventHandler
  public void LevelChange(PlayerLevelChangeEvent e)
  {
	  Player p = e.getPlayer();
	  int price = instance.Mainconfig.getInt("price");
	  if((e.getNewLevel()-e.getOldLevel())>0)
	  {
		p.setMaxHealth(p.getHealthScale()+3);
		p.setHealthScale(p.getHealthScale()+3);
		p.setHealth(p.getHealth()+3);		
		p.sendMessage("你生等了!以幫你加了3滴總血量");
		if(e.getNewLevel()<20)
		{
			p.sendMessage("20等後可以習得2連箭!");
		}
		instance.point.put(p.getName(), instance.point.get(p.getName())+1);
	  }
	  if(e.getNewLevel()==5)
	  {
		  if(instance.econ.has(p.getName(), price))
		  {
		  instance.econ.withdrawPlayer(p.getName(), price);
		  p.sendMessage("因為你的等級只剩下5級,懲罰你"+price+"元,請趕快練功!");
		  }
		  else
		  {
			  p.sendMessage("因為你的等級只剩下5級,懲罰你"+price+"元,請趕快練功!");
			  instance.econ.withdrawPlayer(p.getName(), instance.econ.getBalance(p.getName()));
		  }
	  }
	  if(e.getNewLevel()==49)
	  {		  
		  p.setMaxHealth(20.0);
		  e.getPlayer().setHealthScale(20.0);
		  e.getPlayer().setHealth(20.0);
		  e.getPlayer().sendMessage("49是個重要的過渡期,你只有20滴血,等你升到50時,就有200滴血囉!");
		  e.getPlayer().sendMessage("啊哈!忘了告訴你,如果死亡會回到40等喔,小心 嘿嘿...");
		  e.getPlayer().getInventory().addItem(new ItemStack(Material.BREAD,64));
	  }
	  if(e.getNewLevel()==50)
	  {
		  p.sendMessage("已將你的血量設為200");
		  p.sendMessage("已將你等級回歸15");
		  p.sendMessage("已給予100000元獎勵");
		  p.setMaxHealth(200.0);
		  p.setHealthScale(200.0);
		  p.setHealth(200.0);
		  instance.point.put(p.getName(), instance.point.get(p.getName())+30);
		  p.setLevel(15);
		  instance.econ.depositPlayer(p.getName(), 100000);
	  }
  }
  /**
   * 人傷人,攻擊力公式
   */
@EventHandler
  public void Damage(EntityDamageByEntityEvent e)
  {
	  if(e.getDamager() instanceof Player&&e.getEntity() instanceof Player)
	  {
		  Player p = (Player)e.getEntity();
		  Damageable pd = (Damageable)p;
		  Player kp = (Player)e.getDamager();
		  if(e.getDamage()>=pd.getHealth())
		  {
			  kp.sendMessage("你殺了一個人!降5級!");
			  kp.setLevel(kp.getLevel()-5);
			  if(p.getLevel()==49)
			  {
				  p.setLevel(40);
				  p.sendMessage("哈哈哈,下次小心點吧!");
			  }
		  }
	  }
	  if(e.getDamager() instanceof Player&&e.getEntity() instanceof LivingEntity)
	  {
		  Player p = (Player)e.getDamager();
		 e.getDamage();
		 /**
		  * 玩家攻擊公式ItemDamage+Level+hunger+point+6 除以2
		  */
		 e.setDamage((e.getDamage()+p.getLevel()+p.getFoodLevel()+instance.point.get(p.getName())+6)/4);	//Crash	  
	  }
  }
 /**
  *弓劍x2 
  */
@SuppressWarnings("deprecation")
@EventHandler
public void ShootArrow(EntityShootBowEvent  e) //Crash
{
	if(e.getEntity() instanceof Player)
	{
		final Player p = (Player)e.getEntity();
		if(p.getLevel()>=20){
		final float FallDistance = e.getForce();
		instance.removeInventoryItems(p.getInventory(), Material.ARROW, 1);		
		instance.getServer().getScheduler().runTaskLater(instance, new Runnable(){
			@Override
			public void run() 
			{
				Arrow ar = p.shootArrow();
				ar.setBounce(false);
				ar.setCritical(true);
				ar.setFallDistance(FallDistance);
			}}, 10);
	}
	}
}
/*
@EventHandler
public void Interact(PlayerInteractEvent e)
{
	final Player p = e.getPlayer();
	if(e.getItem().getType()==Material.BOW&&((e.getAction()==Action.LEFT_CLICK_AIR)||(e.getAction()==Action.LEFT_CLICK_BLOCK)))
	{
		if(ct.getCool(p.getName(), 1)==null)
		{
		p.sendMessage("LEFT");
		ct.addCoolTime(p.getName(), 1, 3*1000);
		}
		else
		{
			if(ct.getCool(p.getName(), 1).isOver())
			{
				p.sendMessage("LEFT");
				ct.addCoolTime(p.getName(), 1, 3*1000);
			}
			else
			{
				p.sendMessage("wait!!!");
			}
		}
	}
	if(e.getItem().getType()==Material.BOW&&((e.getAction()==Action.RIGHT_CLICK_AIR)||(e.getAction()==Action.RIGHT_CLICK_BLOCK)))
	{
		if(ct.getCool(p.getName(), 1)!=null)
		{
			if(!ct.getCool(p.getName(), 1).isOver())
			{
				p.sendMessage("SKILL");
				this.count=10;
				this.runTaskLater(p,3);				
				A: 
					while(tf)
				{
					System.out.println("It works!");
					tf=false;
					if(this.count>=0)
					{
						this.count=count-1;
						this.runTaskLater(p,3);
						System.out.println("It works2");
						continue A;
					}
				}
			}
			else
			{
				p.sendMessage("LEFT first!");
			}
			}
		else{p.sendMessage("LEFT first!");}
		}
	}
*/
  /**
   * 封鎖Chinese input
   */
  @EventHandler
  public void cm(PlayerCommandPreprocessEvent e) { 
	  Player p = e.getPlayer();
      String command = e.getMessage();
      if (instance.Mainconfig.getStringList("dontallowcommand").contains(command)||command.startsWith("/ci")&&!p.isOp()) {
        p.sendMessage("目前不能行使這項指令!");
        e.setCancelled(true);
    }
  }
  public void runTaskLater(final Player p,long time)
  {
  	instance.getServer().getScheduler().runTaskLater(instance, new Runnable(){
  		@SuppressWarnings("deprecation")
		@Override
  		public void run() {
  			p.shootArrow();
  			tf=true;
  		}}, time);
  }
}