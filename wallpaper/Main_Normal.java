import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
class Wallpaper implements ActionListener{
	Button[] button;
	static int Timer;
	static int Timers; 
	static String[] Filename; // 目前的檔名 
	static JFrame f; // 目前的視窗
	static String[] arg;
	public static void main(String args[])
{
	Timer=Integer.parseInt(args[0]);
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
		for(int i=1;i<Timer;i++)
		{
			wall.button[i]=new Button(""+i);
			wall.button[i].addActionListener(new Wallpaper());
			f.add(wall.button[i]);	
		}
		Button RUN=new Button("RUN!");
		RUN.addActionListener(new Wallpaper());
		f.add(RUN);
		// Step 5:Another 
		// JMenuBar jmb=new JMenuBar();
		// JMenu jm=new JMenu("Select a File");
		// JMenuItem jmi=new JMenuItem("Select~");
		// 將JMenuItem 加上ActionListener註冊 按到會跑到actionPerformed
		//jmi.addActionListener(new Wallpaper());
		// jm.add(jmi);
		// jmb.add(jm);		 
		// f.setJMenuBar(jmb);		 
		// Step X: 顯示出視窗 
		f.pack();
		f.setVisible(true); 
	 }
public void RUN() throws Exception
{
	int random = (int)Math.random()*100;
	FileWriter fw = new FileWriter("./"+random+"wallpaper.xml");
        File f = new File("./"+random+"wallpaper.xml");
        f.createNewFile();    
        fw.write
(
        Filename[Timers]
);
}
public void Return()
	{
		Timers=Timers+1;
		FileDialog fd = new FileDialog(f,"開啟圖檔",FileDialog.LOAD);
		fd.setVisible(true);
		Filename[Timers]=fd.getDirectory() + 
				 System.getProperty("file.separator").charAt(0) 
				+ fd.getFile();
	}
public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()!="RUN!"){
		Return();
	}else{
	try{
	RUN();
	}catch(Exception e1){}
	}
	}
}
