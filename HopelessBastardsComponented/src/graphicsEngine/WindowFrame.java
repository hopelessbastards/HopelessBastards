package graphicsEngine;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import bufferedImageImplementation.Canvas;

import javax.swing.JButton;

public class WindowFrame extends JFrame implements IWindowFrame{

	private static final long serialVersionUID = 1L;
	private int BoundX = 0;
	private int BoundY = 0;
	
	private int WIDTH;
	private int HEIGHT;
	
	public WindowFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*Saját egérkurzor beállítása a framehez.*/
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""),new Point(0,0), "KissViktorCursor");
		setUndecorated(true);
		setCursor(cursor);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();/*A képernyõ méretét kérem le*/
		HEIGHT = dim.height;
		WIDTH = dim.width;
		setBounds(BoundX,BoundY, WIDTH, HEIGHT);
		
		//pack();
		setResizable(true);
		setVisible(true);
	}

	@Override
	public void addCanvas(ICanvas canvas) {
		getContentPane().add((Canvas)canvas);
	}	
}