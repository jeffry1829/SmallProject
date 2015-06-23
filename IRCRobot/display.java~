package IRCbot;

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
					okmessage = (content.substring(content.indexOf(':',2))).replaceAll("[ \\/\u0000-\u0019]" , "");
					System.out.print(okmessage);
					if(okmessage.length()<=30){
					new Gvoice(okmessage,"zh_TW");
					}
					if(content.contains("testt")){
						bw.write("PRIVMSG "+channel+" : pet_test requirement\r\n");
						System.out.println(bw);
						bw.flush();
					}
				}				
        			}
        		}catch(IOException e1){}
        		 catch(Exception e2){}	
        		}        	
}