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
	final int originalTileSize = 64; //64x64 tiles
	public int zoom = 1; //not sure what to make this zoom to make it look okay but i'll try this

	public int tileSize = originalTileSize * zoom; //96x96 tiles
	public int maxScreenCol = 20;
	public int maxScreenRow = 20;
	public int screenWidth = 1000;
	public int screenHeight = 1000;


	// public int screenWidth = originalTileSize * maxScreenCol; 
	// public int screenHeight = originalTileSize * maxScreenRow;

	public int chunkSize = 31;


	//FPS
	int FPS = 60;

	BackgroundManager backgroundM = new BackgroundManager(this);
	KeyHandler keyH = new KeyHandler();
	ResizeListener resizeL = new ResizeListener();
	Thread gameThread;
	public Player player = new Player(this, keyH);


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
		if(resizeL.resized){
			try{
				// System.out.println(resizeL.newSize.width);
				screenWidth = resizeL.newSize.width;
				screenHeight = resizeL.newSize.height;
				maxScreenCol = (screenWidth/originalTileSize) + 1;
				maxScreenRow = (screenHeight/originalTileSize) + 1;
				resizeL.resized = false;
				player.screenY = (screenHeight/2) - player.entityTextureHeight;
				player.screenX = (screenWidth/2) - player.entityTextureWidth;
				player.shadowX = player.screenX + 20;
				player.shadowY = player.screenY + 65;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
	}


	//Built in Java class that is used by gamepanel
	public void paintComponent(Graphics g) {

		super.paintComponent(g);


		//DEBUG
		// long drawStart = 0;
		// drawStart = System.nanoTime();
		//DEBUG



		Graphics2D g2 = (Graphics2D)g;
		
		backgroundM.draw(g2);
		player.draw(g2);



		//DEBUG
		// long drawEnd = 0;
		// drawEnd = System.nanoTime();
		// long timePassed = drawEnd-drawStart;
		// System.out.println(timePassed);
		//DEBUG




		g2.dispose();
	}


}