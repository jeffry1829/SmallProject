import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
public class IRCbot extends Thread implements Runnable{
	ArrayList arraylist;
	BufferedReader br;
	static BufferedWriter bw;
	Socket socket;
	String server;
	String channel;
	String nickname;
	Boolean connect;
	public IRCbot(long sec1, long sec2){
		java.util.Timer timer=new java.util.Timer();
		timer.schedule(new timer(this),sec1*1000,sec2*1000);		
	}
	public void run(){ //connect IRC server
		try{
		socket=new Socket(server,6667);
		br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write("NICK " +nickname + "\r\n");
        		bw.write("USER " +nickname + " 8 * : I am a Bot \r\n");
        		bw.write("JOIN " + channel + "\r\n");
        		bw.flush( );
        		String content;
        		Boolean bool=false;
        		while((content=br.readLine())!=null){ //read lines from irc
        			System.out.println(content);
        			if(content.contains("pet_test")){
        				bw.write("PRIVMSG "+channel+" :pet_test requirement \r\n");
        				bw.flush();
        			}
        			else if(content.contains("bot,call me master")){
        				bw.write("PRIVMSG "+channel+" :xxx \r\n");
        				bw.flush();
        				bool=true;				
        				}
        			else if((content=br.readLine()).contains("求你了!")&&bool==true){
        						bw.write("PRIVMSG "+channel+" :xxx \r\n");
        						bw.flush();
        						bool=false;
        			}
				else if((content=br.readLine()).startsWith("PING")){
	bw.write("PONG "+(content=br.readLine()).substring(5)+" \r\n");
	bw.flush();
}
        			}
        		}catch(IOException e1){}
        		 catch(Exception e2){}	
        		}        	
        	public IRCbot(){} //For new IRCbot()
		public IRCbot(String server, String channel,String nickname){
			this.server=server;
			this.channel=channel;
			this.nickname=nickname;
}
	public static void main(String args[]) throws IOException, Exception{
		Thread job_connect = new Thread(new IRCbot("irc.freenode.net","#testchannel","petjelinux_bot"));
		Thread job_say=new Thread(new IRCbot_say("irc.freenode.net","#testchannel",new IRCbot()));
		job_connect.start();
		job_say.start();
		new IRCbot(20,60);
	}
}
