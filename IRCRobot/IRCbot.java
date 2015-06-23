package IRCbot;

import java.io.*;
import java.net.*;

public class IRCbot{
	BufferedReader br;
	BufferedWriter bw;
	Socket socket;
	String server;
	String channel;
	String nickname;
	Boolean connect;
	String okmessage;
	String content;
			
		public IRCbot(){}
		
		public IRCbot(String server, String channel,String nickname){
			this.server=server;
			this.channel=channel;
			this.nickname=nickname;
		try{
			socket=new Socket(server,6667);
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write("NICK " +nickname + "\r\n");
        		bw.write("USER " +nickname + " 8 * : I am a Bot \r\n");
        		bw.write("JOIN " + channel + "\r\n");
        		bw.flush();
		}catch(IOException e1){}
			catch(Exception e2){}
		}
		
	public static void main(String args[]) throws IOException, Exception{
		Thread job_display = new Thread(new display("server","channel","testbot's name"));
		Thread job_say=new Thread(new IRCbot_say());
		job_display.start();
		job_say.start();
	}
}
