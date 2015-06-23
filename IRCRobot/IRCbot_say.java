package IRCbot;

import java.util.*;
import java.io.*;
class IRCbot_say extends IRCbot implements Runnable{
	
	public  IRCbot_say(){}
	@Override
	public void run(){ //input Lines
	for(;;){
	try{
		Scanner scanner=new Scanner(System.in);
        		String say=scanner.nextLine();
        		//the "bw" in file IRCbot.java which has set the variable "bw"
							  //BUT it only was set IN THAT BLOCK
        		if(!say.equals("exit")){
			    bw.write("PRIVMSG "+channel+" : "+say+"\r\n");
        		bw.flush();
        		}
        		else
        		{
        			System.exit(0);
        		}
		}catch(IOException e1){}
		  catch(Exception e2){}
	}
		}
	}