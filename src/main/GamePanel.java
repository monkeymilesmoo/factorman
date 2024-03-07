package src.main;

import javax.swing.JPanel;

import src.entity.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{

	//Screen Settings
	final int originalTileSize = 32; //32x32 tiles
	public int zoom = 1; //not sure what to make this zoom to make it look okay but i'll try this

	public int tileSize = originalTileSize * zoom; //96x96 tiles
	final int maxScreenCol = 40;
	final int maxScreenRow = 24;
	final int screenWidth = originalTileSize * maxScreenCol; 
	final int screenHeight = originalTileSize * maxScreenRow;


	//FPS
	int FPS = 60;


	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);


	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}


	@Override
	public void run() {

		double drawInterval = 1000000000/FPS; //.016666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;


		while(gameThread != null){


			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;

			if (delta >= 1){
				
				update();	//STEP ONE: Update information like character pos
				repaint();	//STEP TWO: Calls paintComponent to draw screen
				delta--;

			}



			

		}
	}

	public void update() {

		// System.out.println(keyH.upPressed);

		player.update();

		
	}


	//Built in Java class that is used by gamepanel
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);

		g2.dispose();
	}


}