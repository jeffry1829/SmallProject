package pj.imagehandle;

import javax.swing.JFrame;
import javax.swing.JLabel;

class Main
{
	static JFrame frame;
	static JLabel label;
	public static void main(String[] args)
{
	  MouseEvents mouse = new MouseEvents(frame,label,"imagepath");
	  mouse.startx();
}
}
