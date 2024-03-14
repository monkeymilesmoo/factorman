package src.main;

import javax.swing.JPanel;

import src.background.BackgroundManager;
import src.entity.Player;
import src.Chunk.ChunkGrid;

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

	//DEBUG
	long longestTime = 0;
	public long timePassed = 0;
	//DEBUG



	boolean loading = false;





	//FPS
	int FPS = 60;

	KeyHandler keyH = new KeyHandler();
	MouseHandler mouseH = new MouseHandler(this);
	ResizeListener resizeL = new ResizeListener();
	Thread gameThread;
	public Player player = new Player(this, keyH);

	public ChunkGrid chunkGrid;

	public BackgroundManager backgroundM = new BackgroundManager(this);

	public GamePanel() {

		// 
		//NEEDS TO BE FINISHED - LOADING AND SAVING MAP
		// 
		// 
		// MAKE LOAD OR NEW PROMPT
		long beforeOpening = System.nanoTime();

		if(!loading){
			chunkGrid = new ChunkGrid(this, loading);
		}else{
			chunkGrid = ChunkGrid.loadFromFile("chunkGrid.dat");
		}

		System.out.println("loadtime: " + (System.nanoTime() - beforeOpening));




		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
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
		int frameNumber = 0;


		while(gameThread != null){


			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1){
				
				frameNumber++; //Step 0.5: update frame number
				update();	//STEP ONE: Update information like character pos
				repaint();	//STEP TWO: Calls paintComponent to draw screen
				

				//DEBUG
				if (timePassed > longestTime){
					longestTime = timePassed;
					if (longestTime > 16600000){
						System.out.println(longestTime);
						longestTime = 0;
					}
				}
				//DEBUG
				
				delta--;
				drawCount++;

			}

			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
				if(keyH.Lpressed == true){
					chunkGrid.saveToFile("chunkGrid.dat");
					keyH.Lpressed = false;
				}
				
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
		long drawStart = 0;
		drawStart = System.nanoTime();
		//DEBUG



		Graphics2D g2 = (Graphics2D)g;
		
		backgroundM.draw(g2);
		player.draw(g2);



		//DEBUG
		long drawEnd = 0;
		drawEnd = System.nanoTime();
		timePassed = drawEnd-drawStart;
		//DEBUG




		g2.dispose();
	}


}