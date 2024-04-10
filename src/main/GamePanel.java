package src.main;

import javax.swing.JPanel;

import src.background.BackgroundManager;
import src.entity.Player;
import src.item.Item;
import src.item.ItemProperties;
import src.tileEntity.EntityImage;
import src.tileEntity.TileEntity;
import src.Chunk.Chunk;
import src.Chunk.ChunkGrid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{

	//Screen Settings
	final static int originalTileSize = 64; //64x64 tiles
	public static int zoom = 1; //not sure what to make this zoom to make it look okay but i'll try this

	public static int tileSize = originalTileSize * zoom; //64x64 tiles
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

	public int frameNumber = 0;



	boolean loading = false;





	//FPS
	int FPS = 60;

	public KeyHandler keyH = new KeyHandler();
	public MouseHandler mouseH = new MouseHandler(this);
	ResizeListener resizeL = new ResizeListener();
	Thread gameThread;
	public UI ui = new UI(this); 
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

		ItemProperties.InitializeItemProperties();
		EntityImage.loadEntityImages();




		player.inventory.addItemToInventory(new Item("assembling-machine-1", 1000));
		player.inventory.addItemToInventory(new Item("steel-chest", 30));
		player.inventory.addItemToInventory(new Item("stone-furnace", 30));


		

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
		frameNumber = 0;


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
					// System.out.println(longestTime/1000000 + " miliseconds per frame");
					
				}
				//DEBUG


				mouseH.calculateMousePos();
				
				delta--;
				drawCount++;


				if (keyH.Epressed){
					keyH.Epressed = false;
					if(ui.openVisibility == false){
						ui.openUI = "playerInv";
						ui.SIUI.resizeWindow();
					}
					ui.openVisibility = !ui.openVisibility;
					
				}


				//TODO This should probably go somewhere else eventually
				if(mouseH.notOverGUI){
					if(mouseH.leftMouseClicked){
						if(ui.SIUI.inv.selectedSlot != ui.SIUI.inv.slotCount){
							if(player.inventory.invContents[ui.SIUI.inv.selectedSlot] != null){
								if(ItemProperties.itemPropertyMap.get(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID).placeable){
									chunkGrid.chunks[mouseH.mouseCol][mouseH.mouseRow].setTileEntity(mouseH.mouseTileX, mouseH.mouseTileY, EntityImage.entityImages.get(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID).tileWidth , EntityImage.entityImages.get(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID).tileHeight, player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID, this);
									if(chunkGrid.chunks[mouseH.mouseCol][mouseH.mouseRow].canDo){
										player.inventory.removeItemFromInventory(new Item(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID, 1));
										Chunk.onTextCooldown = true;
										// mouseH.leftMouseClicked = false;
									}
								}
							}
						}else if(ui.hotbar.selectedSlot != ui.hotbar.slotCount){
							if(player.hotbar[ui.hotbar.selectedSlot] != null){
								if(ItemProperties.itemPropertyMap.get(player.hotbar[ui.hotbar.selectedSlot].itemID).placeable){
									if(player.hotbar[ui.hotbar.selectedSlot].quantity > 0){
										chunkGrid.chunks[mouseH.mouseCol][mouseH.mouseRow].setTileEntity(mouseH.mouseTileX, mouseH.mouseTileY, EntityImage.entityImages.get(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID).tileWidth, EntityImage.entityImages.get(player.inventory.invContents[ui.SIUI.inv.selectedSlot].itemID).tileHeight, player.hotbar[ui.hotbar.selectedSlot].itemID, this);
										if(chunkGrid.chunks[mouseH.mouseCol][mouseH.mouseRow].canDo){
											player.inventory.removeItemFromInventory(new Item(player.hotbar[ui.hotbar.selectedSlot].itemID, 1));
											Chunk.onTextCooldown = true;

											// mouseH.leftMouseClicked = false;
										}
									}
								}
							}
						}
					}
					if(mouseH.rightMouseClicked){
						TileEntity minedTE = chunkGrid.chunks[mouseH.mouseCol][mouseH.mouseRow].getTileEntity(mouseH.mouseTileX, mouseH.mouseTileY);
						if(minedTE != null){
							minedTE.beingMined(chunkGrid, player);
							player.mining = true;
							System.out.println(minedTE.miningDurability);
							player.pBarProgress = (((int) minedTE.miningDurability) * 100) / ((int) minedTE.maxMiningDurability);
							player.turnToMining(mouseH);






						}else{
							player.mining = false;
						}
					}
					else{
						player.mining = false;
					}
				}



			}

			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
				longestTime = 0;
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

		chunkGrid.update();

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
				ui.resizeWindow();
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
		// tileEM.draw(g2);
		player.draw(g2);
		if(mouseH.notOverGUI){
			mouseH.hoveredTileEntity(g2);
		}

		ui.draw(g2);

		g2.setColor(Color.white);
		g2.drawString("draw time: " + timePassed/1000000, 10, screenHeight - 30);

		//DEBUG
		long drawEnd = 0;
		drawEnd = System.nanoTime();
		timePassed = drawEnd-drawStart;
		//DEBUG




		g2.dispose();
	}


}