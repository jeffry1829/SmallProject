package pj.pjshotter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CoolTime {
	public Set<Cool> list = new HashSet<Cool>();
	//秒數*1000
public void addCoolTime(String playername,int tasknumber,long time)
{
	Cool cool = new Cool(playername,tasknumber,time);
	Iterator<Cool> it = list.iterator();
	while(it.hasNext())
	{
		Cool iterator = it.next();
		if(iterator.getPlayerName().equals(playername)&&
				iterator.getTask()==tasknumber)
		{
			it.remove();
		}
	}
	list.add(cool);
}
public Cool getCool(String playername,Integer Tasks)
{
	Iterator<Cool> it = list.iterator();
	while(it.hasNext())
	{
		Cool cool = it.next();
		if(cool.getPlayerName().equals(playername)&&
				cool.getTask()==Tasks)
		{
			return cool;
		}
	}
	return null;
} 
}
