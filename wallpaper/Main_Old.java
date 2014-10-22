import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
class Wallpaper_2 implements ActionListener{
	Button[] button;
	static int Timer;
	static JFrame f;
	static String[] Filename;
	public static void main(String args[])
{
	Timer=Integer.parseInt(args[0]);
	Filename=new String[Timer];	
	Show();
}
	//
public static void Show()
	 {
		 Wallpaper_2 wall=new Wallpaper_2();
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
	int random = (int)(Math.random()*100000);
	FileWriter fw = new FileWriter("./"+random+"wallpaper.xml",true);
        File f = new File("./"+random+"wallpaper.xml");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write("<background><starttime><year>2014</year><month>10</month><day>6</day><hour>00</hour><minute>00</minute><second>00</second></starttime>");
        if(!f.exists()){f.createNewFile();}    
        for(int i=0;i<Timer;i++)
{if(i!=Timer-1){
	bw.write("<static><duration>30.0</duration><file>"+Filename[i]+"</file></static><transition><duration>5.0</duration><from>"+Filename[i]+"</from><to>"+Filename[i+1]+"</to></transition>");
}else{"<static><duration>30.0</duration><file>"+Filename[i]+"</file></static>"}
}
bw.write("</background>");
bw.close();
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
