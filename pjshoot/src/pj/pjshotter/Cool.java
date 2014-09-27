package pj.pjshotter;

public class Cool {
	public static String PlayerName;
	public static int Tasks;
	public long startTime;
	public long endTime;
	public long Time;
	public CoolTime ct = new CoolTime(); 
	Cool(String playername,int tasknumber,long time)
	{
		PlayerName = playername;
		Tasks = tasknumber;
		startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis()+time;
		Time = time;
	}
	public String getPlayerName()
	{
		return PlayerName;
	}
	public Integer getTask()
	{
		return Tasks;
	}
	public long TimeLeft()
	{
		return (int)(endTime-System.currentTimeMillis());
	}
	public boolean isOver()
	{
		return endTime<System.currentTimeMillis();
	}
	public void remove(String playername,Integer Tasks)
	{
		ct.list.remove(ct.getCool(playername, Tasks));
	}
}
