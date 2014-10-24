package pj.imagehandle;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class ImageComponent implements Runnable{ 
 String Filename; // 目前的檔名 
 BufferedImage image; // 目前的影像 
 JFrame f; // 目前的視窗 
 public ImageComponent(String aFilename){ 
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
// 顯示一張影像 
 public void Show(){ 
 SwingUtilities.invokeLater(this); 
 } 
 // 實體化影像物件的方法 
 public void run(){ 
 f = new JFrame(""); 
 
 // Step 1: 若影像超過螢幕， 則加入捲軸 
 JScrollPane scrollPane = new JScrollPane( 
new JLabel(new ImageIcon(image))); 
 f.getContentPane().add(scrollPane); 
 f.pack(); 
 
 // Step 2: 設定點選 x 表示關閉視窗 
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
 
 // Step 3: 加入視窗標題 
 f.setTitle(Filename+" "+image.getWidth()+ 
" x "+image.getHeight()); 
 
 // Step 4: 設定視窗顯示在螢幕中央 
 f.setLocationRelativeTo(null); 
 // Step 5: 顯示出視窗 
 f.setVisible(true); 
 } 
} 
