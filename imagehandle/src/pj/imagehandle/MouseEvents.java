package pj.imagehandle;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


public class MouseEvents implements MouseListener{
	JFrame jframe;
	JLabel jlabel;
	JScrollPane scrollPane;	
	JLabel icon;
	BufferedImage image; // 目前的影像
	public MouseEvents(JFrame framex,JLabel labelx, String aFile)
	{
		framex=this.jframe;
		labelx=this.jlabel;
		image=LoadaImage(aFile);
	}
	public void startx()
	{	
		icon = new JLabel(new ImageIcon(image));
		scrollPane = new JScrollPane(icon);
		scrollPane.setSize(100, 50);
		jframe = new JFrame("Start!");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(600, 400);
		jframe.addMouseListener(this);
		jlabel = new JLabel("Start...");
		jframe.getContentPane().add(jlabel,BorderLayout.CENTER);
		jframe.getContentPane().add(scrollPane,BorderLayout.PAGE_END);	
		jframe.setVisible(true);
	}
@Override
public void mouseClicked(MouseEvent e) {
	image=this.LoadaImage("./31l3iPF.jpg");
	icon.setIcon(new ImageIcon(image));
	icon.setSize(300, 200);
	jlabel.setText("mouseClicked");
}

@Override
public void mouseEntered(MouseEvent e) {
	jlabel.setText("mouseEntered");
}

@Override
public void mouseExited(MouseEvent e) {
	jlabel.setText("mouseExited");
}

@Override
public void mousePressed(MouseEvent e) {
	jlabel.setText("mousePressed");
}

@Override
public void mouseReleased(MouseEvent e) {
	jlabel.setText("mouseReleased");
}
public BufferedImage LoadaImage(String Filename)
{
	BufferedImage image;
	try {
		image=ImageIO.read(new File(Filename));
	} catch (IOException e) {
		e.printStackTrace();
		jlabel.setText("File Loading in Error!");
		image=null;
	}
	return image;
}
}
