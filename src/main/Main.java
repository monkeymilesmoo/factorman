package src.main;

import java.awt.Dimension;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		
		System.setProperty("sun.java2d.opengl", "true");

		//test
		JFrame window = new JFrame();
		GamePanel gp = new GamePanel();


		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// window.setResizable(false);
		window.setTitle("test");
		window.add(gp);
		window.pack();
		window.setVisible(true);
		window.setMinimumSize(new Dimension(1000,1000));


		gp.startGameThread();

	}


}

