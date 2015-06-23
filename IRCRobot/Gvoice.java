package IRCbot;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

class Gvoice{
	String path;
	Clip Clip_player;
	public Gvoice(String args0, String args1) throws IOException, Exception, FileNotFoundException{
	path = "./voice"+args0+".mp3";
        Download("http://translate.google.com/translate_tts?ie=utf-8&tl="+args1+"&q="+args0,path);
        Process p = Runtime.getRuntime().exec("mpg123 "+path);
	// Causes the current thread to wait, if necessary, until the process represented by this Process object has terminated.
	p.waitFor();
        Delfile(path);
}
	
	//	Download
	
	public void Download(String url,String distination) throws IOException, Exception, FileNotFoundException{
	// Give UA (user agent)
	// http://stackoverflow.com/questions/2529682/setting-user-agent-of-a-java-urlconnection
	//X-UA-Compatible: IE=EmulateIE7
	//X-UA-Compatible: IE=edge
	//X-UA-Compatible: Chrome=1
	System.setProperty("http.agent", "1");
	InputStream is = new URL(url).openConnection().getInputStream();
	FileOutputStream fos = new FileOutputStream(distination);
	byte[] buffer = new byte[1024];
	for(int length;(length = is.read(buffer) ) > 0;fos.write(buffer,0,length));
is.close();
fos.close();
}
	//	Delfile
	public void Delfile(String filepath) throws IOException, Exception, FileNotFoundException{
	File file = new File(filepath);
	file.delete();
}
	//	Play       Can't work in Google's MP3 File
	public void Play(String filepath) throws IOException, Exception, FileNotFoundException{
	File file_voice = new File(filepath);
	AudioInputStream sound = AudioSystem.getAudioInputStream(file_voice);
	DataLine.Info infoVoice = new DataLine.Info(Clip.class,sound.getFormat());
	Clip_player = (Clip)AudioSystem.getLine(infoVoice);
	// *LET'S PLAY*
	Clip_player.start();
}
}
