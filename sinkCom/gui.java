import javax.swing.*;
import java.util.*;
import java.awt.*;

class gui extends setup implements ActionListener{
  JFrame f;
  JLabel result_label=new JLabel();
  ArrayList<JButton> buttonList=new ArrayList<JButton>();
  int index;
  String[] _comss_={};
  
  void setupgui(){
    JPanel back=f.getContentPane();
    JPanel center=new JPanel();
    f=new JFrame("sinkCom gui");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
      for(int i=0;i<49;i++){
        JButton b=new Jbutton();
        buttonList.add(b);
        b.addActionListener(new innerButtonEvent());
        p.add(b);
      }
    
    Box onetoseven=new Box(new JLabel({1,2,3,4,5,6,7}));
    
    back.add(BorderLayout.EAST,onetoseven);
    back.add(BorderLayout.NORTH,onetoseven);
    back.add(BorderLayout.CENTER,center);
    
    GridLayout glo=new GridLayout(7,7);
    glo.setVgap(1);
    glo.setHgap(2);
    
    center.setLayout(glo);
    
    f.setBounds(50,50,300,300);
    f.pack();
    f.setVisible(true);
  }

  class innerButtonEvent implements ActionListener{
    void actionPerformed(ActionEvent event){
      JButton obj=(JButton)event.getSource();
      index=buttonList.indexOf(obj);
    }
  } //inner class end
  
  class Submit implements ActionListener{
    void actionPerformed(ActionEvent event){
      _comss_=event.getText().split(" ");
      
    }
  } //inner class end
  
  int getIndex(){return index;}
  
  String[] getFirst_gui(){
    JDialog dialog=new JDialog(this,"dialog");
    JButton submit=new JButton("Submit");
    submit.addActionListener(new Submit());
    JTextArea textarea=new JTextArea(1,20);
    textarea.setText("First input three ships' names then split with *SPACE*");
    textarea.selectAll();
    JPanel padi=dialog.getContentPanel();
    
    padi.setLayout(new BoxLayout());
    padi.add(textarea);
    padi.add(submit);
      while(_comss_!={}){
        return _comss_;
      }
  } //getFirst_gui() end
  
  void check_gui(int guess){
    while(less){ //rerun while there're ships left
      ArrayList<Integer> shipLocc; //store ships' locations
      guessTimes++;
        for(sinkCom ship : comEle){
          int index=ship.getLoc().indexOf(guess); //check if guess==location return index or return -1
          shipLocc=ship.getLoc(); //store ship's(only one) location to shipLocc to continue check easily
          if(index>=0){ //if guess==location
              System.out.println("hit " + ship.getName());
              shipLocc.remove(index); //remove the hitted slot from the arraylist
              ship.setLoc(shipLocc); //replace [Object sinkCom]'s location
              if(ship.getLoc().isEmpty()){ //if the whole ship is sink  
                lessShip--; //left ship count minus 1
                System.out.println("sink" + ship.getName());
              }
              if(lessShip==0){less=false; Win();} //if sink all of the ships
          }
          else if(index<0&&ship.getLoc().isEmpty()){}
          else{System.out.println("miss");} //if guess!=location
        }
    }
  }
}
