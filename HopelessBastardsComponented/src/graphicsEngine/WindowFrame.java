package graphicsEngine;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import bufferedImageImplementation.Canvas;
import config.ConfigDataProvider;

import javax.swing.JButton;

public class WindowFrame extends JFrame implements IWindowFrame{

	private static final long serialVersionUID = 1L;
	private int BoundX = 0;
	private int BoundY = 0;
	
	private int width;
	private int height;
	
	private ConfigDataProvider data;
	
	public WindowFrame() {
		data = new ConfigDataProvider();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*Saját egérkurzor beállítása a framehez.*/
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""),new Point(0,0), "KissViktorCursor");
		setUndecorated(true);
		setCursor(cursor);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();/*A képernyõ méretét kérem le*/
		//width = dim.width;
		//height = dim.height;
		width = data.getScreenWidth();
		height = data.getScreenHeight();
		/*width = 700;
		height = 700;*/
		
		BoundX = data.getScreenX();
		BoundY = data.getScreenY();
		
		setBounds(BoundX, BoundY, width, height);
		
		//pack();
		setResizable(true);
		setVisible(true);
	}

	@Override
	public void addCanvas(ICanvas canvas) {
		getContentPane().add((Canvas)canvas);
	}	
}