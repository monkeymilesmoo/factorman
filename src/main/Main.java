package src.main;

import java.awt.Dimension;

import javax.swing.JFrame;

import src.item.ItemProperties;
import src.tileEntity.EntityImage;


public class Main {

	public static void main(String[] args) {
		
		System.setProperty("sun.java2d.opengl", "true");

		
		ItemProperties.InitializeItemProperties();
		EntityImage.loadEntityImages();
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

