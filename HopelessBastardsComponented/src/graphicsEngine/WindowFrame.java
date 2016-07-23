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
	
	private int width;
	private int height;
	
	public WindowFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*Saj�t eg�rkurzor be�ll�t�sa a framehez.*/
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""),new Point(0,0), "KissViktorCursor");
		setUndecorated(true);
		setCursor(cursor);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();/*A k�perny� m�ret�t k�rem le*/
		height = dim.height;
		width = dim.width;
		/*height = 500;
		width = 500;*/
		
		setBounds(BoundX,BoundY, width, height);
		
		//pack();
		setResizable(true);
		setVisible(true);
	}

	@Override
	public void addCanvas(ICanvas canvas) {
		getContentPane().add((Canvas)canvas);
	}	
}