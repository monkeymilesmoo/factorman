package src.main;

import javax.swing.JPanel;

import src.background.BackgroundManager;
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
	public int maxScreenCol = 40;
	public int maxScreenRow = 24;
	public int screenWidth = originalTileSize * maxScreenCol; 
	public int screenHeight = originalTileSize * maxScreenRow;


	//FPS
	int FPS = 60;

	BackgroundManager backgroundM = new BackgroundManager(this);
	KeyHandler keyH = new KeyHandler();
	ResizeListener resizeL = new ResizeListener();
	Thread gameThread;
	Player player = new Player(this, keyH);


	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addComponentListener(resizeL);
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
		int timer = 0;
		int drawCount = 0;


		while(gameThread != null){


			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1){
				
				update();	//STEP ONE: Update information like character pos
				repaint();	//STEP TWO: Calls paintComponent to draw screen
				
				delta--;
				drawCount++;

			}

			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
			}



			

		}
	}

	public void update() {

		// System.out.println(keyH.upPressed);

		player.update();

		try{
		// System.out.println(resizeL.newSize.width);
		screenWidth = resizeL.newSize.width;
		screenHeight = resizeL.newSize.height;
		maxScreenCol = screenWidth/originalTileSize;
		maxScreenRow = screenHeight/originalTileSize;
		}catch(Exception e){
			e.printStackTrace();
		}

	
	}


	//Built in Java class that is used by gamepanel
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		
		backgroundM.draw(g2);
		player.draw(g2);

		g2.dispose();
	}


}