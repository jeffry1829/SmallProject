package IRCbot;

import IRCbot.IRCbot;

import java.util.*;
import java.net.*;
import java.io.*;

class display extends IRCbot implements Runnable{
	public display(String server , String channel , String nickname){
		super(server , channel , nickname);
}
	public void run(){
		try{
        		while(socket.isConnected()){
				content = br.readLine();
        			System.out.println(content);
				if(content.startsWith("PING")){
	bw.write("PONG "+content.substring(6) + "\r\n");
	bw.flush();	
}
				else if(content.contains("PRIVMSG") && !content.contains("005")){
					okmessage = (content.substring(content.indexOf(':',2))).replaceAll("[ \\/]" , "");
					new Gvoice(okmessage,"zh_TW");
				}
        			}
        		}catch(IOException e1){}
        		 catch(Exception e2){}	
        		}        	
}
