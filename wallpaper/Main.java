import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
class Wallpaper implements ActionListener{
	static int random;
	Button[] button;
	static int Timer;
	static JFrame f;
	static String[] Filename;
	public static void main(String args[])
{
	random = (int)(Math.random()*100000);
	Timer=Integer.parseInt(args[0]);
	Filename=new String[Timer];	
	Show();
}
	//
public static void Show()
	 {
		 Wallpaper wall=new Wallpaper();
		 f = new JFrame("請選擇"); 		
		 
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		 
		 f.setLayout(new FlowLayout());
		 // Step 4: 設定視窗顯示在螢幕中央 
		 f.setLocationRelativeTo(null);
		 
		wall.button=new Button[Timer];
		for(int i=0;i<Timer;i++)
		{
			wall.button[i]=new Button(""+i);
			wall.button[i].addActionListener(new Wallpaper());
			f.add(wall.button[i]);	
		}
		Button RUN=new Button("RUN!");
		RUN.addActionListener(new Wallpaper());
		f.add(RUN);
		f.pack();
		f.setVisible(true); 
	 }
public void RUN() throws Exception
{
	String path="/usr/share/backgrounds/contest/"+random+"wallpaper.xml";
	FileWriter fw = new FileWriter(path,true);
        File f = new File(path);
	BufferedWriter bw=new BufferedWriter(fw);
        if(!f.exists()){f.createNewFile();}
	bw.write("<background><starttime><year>2003</year><month>6</month><day>14</day><hour>05</hour><minute>30</minute><second>00</second></starttime>");
        for(int i=0;i<Timer;i++)
{if(i!=Timer-1){
bw.write("<static><duration>60.0</duration><file>"+Filename[i]+"</file></static><transition><duration>5.0</duration><from>"+Filename[i]+"</from><to>"+Filename[i+1]+"</to></transition>");}
else{bw.write("<static><duration>60.0</duration><file>"+Filename[i]+"</file></static>");
}
}
bw.write("</background>");
bw.close();
////////////////////////////Writer2/////////////////////////////////////////////
String path2 = "/usr/share/gnome-background-properties/"+random+"Costume.xml";
File f2 = new File(path2);
FileWriter fw2 = new FileWriter(path2,true);
BufferedWriter bw2 = new BufferedWriter(fw2);
if(!f2.exists()){f2.createNewFile();}
bw2.write("<wallpapers><wallpaper deleted=\"false\"><name>Another-"+random+"</name><filename>/usr/share/backgrounds/contest/"+random+"wallpaper.xml"+"</filename><options>zoom</options></wallpaper></wallpapers>");
bw2.close();
}
public void Return(int Number)
	{
		FileDialog fd = new FileDialog(f,"開啟圖檔",FileDialog.LOAD);
		fd.setVisible(true);
		Filename[Number]=fd.getDirectory() + 
				 System.getProperty("file.separator").charAt(0) 
				+ fd.getFile();
	}
public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()!="RUN!"){
		Return(Integer.parseInt(e.getActionCommand()));
	}else{
	try{
	RUN();
	}catch(Exception e1){}
	}
	}
}
