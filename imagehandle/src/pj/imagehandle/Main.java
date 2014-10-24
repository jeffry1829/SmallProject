package pj.imagehandle;

import javax.swing.JFrame;
import javax.swing.JLabel;

class Main
{
	static JFrame frame;
	static JLabel label;
	public static void main(String[] args)
{
  //ImageComponent ic=new ImageComponent("./mc1.jpg");
  //ic.Show();]
	  //Anotherfunction af=new Anotherfunction("./mc1.jpg");
	  //af.Show();
	  MouseEvents mouse = new MouseEvents(frame,label,"./mc1.jpg");
	  mouse.startx();
}
}