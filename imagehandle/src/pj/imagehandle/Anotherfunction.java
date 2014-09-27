package pj.imagehandle;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class Anotherfunction implements ActionListener{
	String Filename; // 目前的檔名 
	 BufferedImage image; // 目前的影像 
	 JFrame f; // 目前的視窗 
	 public Anotherfunction(String aFilename){ 
	 Filename=aFilename; 
	 image=LoadImage(Filename); 
	 } 
	 // 載入一張影像 
	 public static BufferedImage LoadImage(String Filename){
	 BufferedImage image; 
	 try{ 
	 image=ImageIO.read(new File(Filename)); 
	 }catch(Exception e){ 
	 javax.swing.JOptionPane.showMessageDialog(null, 
	"載入圖檔錯誤: "+Filename); 
	 image=null; 
	 } 
	 return image; 
}
	 public void Show()
	 {
		 f = new JFrame(""); 		
		 
		 // Step 1: 若影像超過螢幕， 則加入捲軸 
		 JScrollPane scrollPane = new JScrollPane( 
		new JLabel(new ImageIcon(image))); 
		 f.getContentPane().add(scrollPane); 
		 f.pack(); 
		 
		 // Step 2: 設定點選 x 表示關閉視窗 
		 //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		 
		 // Step 3: 加入視窗標題 
		 f.setTitle(Filename+" "+image.getWidth()+ 
		" x "+image.getHeight()); 
		 
		 // Step 4: 設定視窗顯示在螢幕中央 
		 f.setLocationRelativeTo(null);
		 
		 // Step 5:Another 
		 JMenuBar jmb=new JMenuBar();
		 JMenu jm=new JMenu("Select a File");
		 JMenuItem jmi=new JMenuItem("Select~");
		 // 將JMenuItem 加上ActionListener註冊 按到會跑到actionPerformed
		 jmi.addActionListener(this);
		 jm.add(jmi);
		 jmb.add(jm);		 
		 f.setJMenuBar(jmb);		 
		 // Step X: 顯示出視窗 
		 f.setVisible(true); 
	 }
	public void actionPerformed(ActionEvent e) {
		Open();
	}
	public void Open()
	{
		FileDialog fd = new FileDialog(f,"開啟圖檔",FileDialog.LOAD);
		fd.setVisible(true);
		Filename=fd.getDirectory() + 
				 System.getProperty("file.separator").charAt(0) 
				+ fd.getFile();
		System.out.println(Filename);
		image=LoadImage(Filename);
		Show();
	}
}
