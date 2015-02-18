import java.util.*;
import java.io.*;
class timer extends TimerTask{
	IRCbot ircbot;
	public timer(IRCbot ircbot){
		this.ircbot=ircbot;
}
	@Override
	public void run(){
	try{
	ircbot.bw.write("PONG Cameron.freenode.net");
	ircbot.bw.flush();
	}catch(IOException e1){}
	 catch(Exception e2){}
}		
}
