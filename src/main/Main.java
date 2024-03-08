package src.main;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		GamePanel gp = new GamePanel();


		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// window.setResizable(false);
		window.setTitle("test");
		window.add(gp);
		window.pack();
		window.setVisible(true);


		gp.startGameThread();

	}


}

