package IRCbot;

import IRCbot.IRCbot;

import java.util.*;
import java.awt.Robot;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
class IRCbot_say implements Runnable{
	Socket socket;
	String server;
	String channel;
	IRCbot instance;
	public  IRCbot_say(String server, String channel,IRCbot instance){ //Get the arguments & get class 
									   //IRCbots
		this.server=server;
		this.channel=channel;
		this.instance=instance;
	}
	@Override
	public void run(){ //input Lines
	try{
		Scanner scanner=new Scanner(System.in);
        		String say=scanner.nextLine();
        		//the "bw" in file IRCbot.java which has set the varible "bw"
							  //BUT it only was set IN THAT BLOCK
			instance.bw.write("PRIVMSG "+channel+" : "+say+"\r\n");
        		instance.bw.flush();
		}catch(IOException e1){}
		  catch(Exception e2){}
		}
	}
