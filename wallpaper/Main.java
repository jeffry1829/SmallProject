import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
class Wallpaper implements ActionListener{
    int random=(int)(Math.random()*100000);
    Button[] button;
    int Timer;
    JFrame f;
    String[] Filename;

  public static void main(String args[]){
    Wallpaper wall=new Wallpaper();
    wall.Timer=Integer.parseInt(args[0]);
    wall.Filename=new String[Timer]; 
    wall.Show();
  }
 
  public void Show(){
    f = new JFrame("請選擇");   
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    f.setLayout(new FlowLayout());
    f.setLocationRelativeTo(null);
    
    button=new Button[Timer];
    for(int i=0;i<Timer;i++){
      button[i]=new Button(""+i);
      button[i].addActionListener(this);
      f.add(button[i]); 
    }
    Button RUN=new Button("RUN!");
    RUN.addActionListener(this);
    f.add(RUN);
    f.pack();
    f.setVisible(true); 
  }

  public void RUN() throws Exception{
    String path="/usr/share/backgrounds/contest/"+random+"wallpaper.xml";
    FileWriter fw = new FileWriter(path,true);
    File f = new File(path);
    BufferedWriter bw=new BufferedWriter(fw);
    if(!f.exists()){f.createNewFile();}
      bw.write("<background><starttime><year>2003</year><month>6</month><day>14</day><hour>05</hour><minute>30</minute><second>00</second></starttime>");
    for(int i=0;i<Timer;i++){
      if(i != Timer-1){
        bw.write("<static><duration>60.0</duration><file>"+Filename[i]+"</file></static><transition><duration>5.0</duration><from>"
          +Filename[i]+"</from><to>"+Filename[i+1]+"</to></transition>");
      }
      else{bw.write("<static><duration>60.0</duration><file>"+Filename[i]+"</file></static>");
      }
    }
    bw.write("</background>");
    bw.close();
      ////////////////////////////Writer2///////////////////////////////
    String path2 = "/usr/share/gnome-background-properties/"+random+"Costume.xml";
    File f2 = new File(path2);
    FileWriter fw2 = new FileWriter(path2,true);
    BufferedWriter bw2 = new BufferedWriter(fw2);
    if(!f2.exists()){f2.createNewFile();} //only use 1 line
    bw2.write("<wallpapers><wallpaper deleted=\"false\"><name>Another-"+random+"</name><filename>/usr/share/backgrounds/contest/"
      +random+"wallpaper.xml"+"</filename><options>zoom</options></wallpaper></wallpapers>");
    bw2.close();
  }

  public void Return(int Number){
    FileDialog fd = new FileDialog(f,"開啟圖檔",FileDialog.LOAD);
    fd.setVisible(true);
    Filename[Number]=fd.getDirectory() + 
    System.getProperty("file.separator").charAt(0) + fd.getFile(); 
  }

  public void actionPerformed(ActionEvent e){
    if(e.getActionCommand()!="RUN!"){
    Return(Integer.parseInt(e.getActionCommand()));
    }
    else{
      try{
        RUN();
      }catch(Exception e1){}
    }
  }
}
