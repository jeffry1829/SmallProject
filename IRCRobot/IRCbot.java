import java.util.*;
import java.awt.Robot;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
public class IRCbot implements Runnable{
	ArrayList arraylist;
	BufferedReader br;
	BufferedWriter bw;
	Socket socket;
	public IRCbot(String server,String channel,String nickname) throws IOException, Exception{
		socket=new Socket(server,6667);
		br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("NICK " +nickname + "\r\n");
        		bw.write("USER " +nickname + " 8 * : I am a Bot \r\n");
        		bw.write("JOIN " + channel + "\r\n");
        		bw.flush( );
        		String content;
        		Boolean bool=false;
        		while((content=br.readLine())!=null){
        			System.out.println(content);
        			if(content.contains("pet_test")){
        				bw.write("PRIVMSG "+channel+" :pet_test requirement \r\n");
        				bw.flush();
        			}
        			else if(content.contains("bot,call me master")){
        				bw.write("PRIVMSG "+channel+" :本、本來是不想說的..但如、如果誠心的求..求我的話.. 說、說..說說看也..也不是不能考慮啦！！ \r\n");
        				bw.flush();
        				bool=true;				
        				}
        			else if((content=br.readLine()).contains("求你了!")&&bool==true){
        						bw.write("PRIVMSG "+channel+" :主、主..主人大大? \r\n");
        						bw.flush();
        						bool=false;
        			}
        			}	
        		}
	public IRCbot(String server, String channel) throws IOException, Exception{
		Scanner scanner=new Scanner(System.in);
        		String say=scanner.next();
        		//socket=new Socket(server,6667);
        		//bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        		bw.write("PRIVMSG "+channel+" : "+say+"\r\n");
        		bw.flush();
	}
	public static void main(String args[]) throws IOException, Exception{
		Thread job_open=new Thread(new IRCbot("irc.freenode.net","#ysitd","petjelinux_bot"));
		Thread job_say=new Thread(new IRCbot("irc.freenode.net","#ysitd")); //#petjelinux_testchannel
		job_open.start();
		job_say.start();
	}
	public void PAUSE(long minisec){
		try{
		Thread.sleep(minisec);
	}catch(InterruptedException e1){}
	}
	@Override
	public void run(){
		for(;;){
			PAUSE(10L);
		}
	}
}