package src.entity;

import src.main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import src.Chunk.Chunk;
import src.Chunk.ChunkGrid;
import src.item.Item;
import src.item.storageInventory;
import src.main.CodeUtilities;
import src.main.GamePanel;
import src.main.MouseHandler;
import src.main.UI.progressBar;

public class Player extends entity{

	public GamePanel gp;
	KeyHandler keyH;

	public int screenX;
	public int screenY;
	public int shadowX;
	public int shadowY;

	public int XCoords;
	public int YCoords;
	

	public final int spawnCoords;

	public storageInventory inventory;
	public Item[] hotbar;

	public progressBar pBar;
	public int pBarProgress;

	public Player(GamePanel gp, KeyHandler keyH){
		
	
		this.gp = gp;
		this.keyH = keyH;


		
		inventory = new storageInventory(90, gp);
		hotbar = new Item[22];

		screenX = (gp.screenWidth/2) - entityTextureWidth;
		screenY = (gp.screenHeight/2) - entityTextureHeight;
		shadowX = screenX + 20;
		shadowY = screenY + 65;
		
		spawnCoords = ((Chunk.chunkSize * ChunkGrid.gridSize * GamePanel.tileSize)/2 + (GamePanel.tileSize * Chunk.chunkSize)/2);
		// spawnCoords = (Chunk.chunkSize * ChunkGrid.gridSize * GamePanel.tileSize)/2 + GamePanel.tileSize;
		

		this.idleArray = new BufferedImage[22][8];
		this.runningArray = new BufferedImage[22][8];
		this.miningArray = new BufferedImage[26][8];

		getPlayerImages();
		setDefaultValues();
		
		
	}

	


	public void setDefaultValues() {

		speed = 8;
		worldX = spawnCoords;
		worldY = spawnCoords;
		XCoords = spawnCoords;
		YCoords = spawnCoords;
		entityTextureWidth = 92;
		entityTextureHeight = 116;
		shadowTextureWidth = 164;
		shadowTextureHeight = 78;
		direction = 4;
		animationFrame = 0;
		selectedEntityImage = idleArray;
		selectedShadowImage = idleShadowImage;
		moving = false;
		totalFramesInAnimation = 22;
		
		pBar = gp.ui.new progressBar(screenX - 300, gp.screenHeight - 100, 732, 25, Color.yellow, Color.black);

	}

	public void getPlayerImages() {

		//Player images
		idleImage = CodeUtilities.loadImage("entity/player/hr-level1_idle");
		runningImage = CodeUtilities.loadImage("entity/player/hr-level1_running");
		miningImage = CodeUtilities.loadImage("entity/player/hr-level1_mining_tool-1");

		//Shadow images
		idleShadowImage = CodeUtilities.loadImage("entity/player/hr-level1_idle_shadow");
		runningShadowImage = CodeUtilities.loadImage("entity/player/hr-level1_running_shadow");
		miningShadowImage = CodeUtilities.loadImage("entity/player/hr-level1_mining_tool_shadow");

		playerImagesToArrays();


	}

	public void playerImagesToArrays() {

		//This will need to be updated if more animations are ever added
		//Idle animations
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < idleArray.length; j++){
			idleArray[j][i] = idleImage.getSubimage((j * 92), (i * 116), 92, 116);

			}
		}
		//Running animations
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < runningArray.length; j++){
				runningArray[j][i] = runningImage.getSubimage((j * 88), (i * 132), 88, 132);

			}
		}


		
		//Mining animations
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < idleArray.length; j++){
				miningArray[j][i] = miningImage.getSubimage((j * 196), (i * 194), 196, 194);

			}
		}



	}



	public void turnToMining(MouseHandler mouseH){
		if(mouseH.mouseX > screenX){
			if(mouseH.mouseY > screenY){
				direction = 3;
			}else{
				direction = 1;
			}
		}else{
			if(mouseH.mouseY > screenY){
				direction = 5;
			}else{
				direction = 7;
			}
		}
	}




	public void update() {
		
		
		// 
		// 
		//MAYBE INEFFICIENT CHANGE LATER
		//
		// 

	
		// mining = keyH.testable ? true : false;
		// if(keyH.testable){
		// 	// DEBUG
		// 	inventory.addItemToInventory(new Item("stone-furnace", 30));
		// 	inventory.addItemToInventory(new Item("assembling-machine-1", 30));
		// 	inventory.addItemToInventory(new Item("steel-chest", 30));
		// 	inventory.addItemToInventory(new Item("iron-ore", 124));
		// 	keyH.testable = false;
		// }
		moving = (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) ? true : false;


		if(!mining){
			if (keyH.upPressed){

				if(keyH.rightPressed){
					worldY -= speed;
					worldX += speed;
					direction = 1;
				}
				else if(keyH.leftPressed){
					worldY -= speed;
					worldX -= speed;
					direction = 7;
				}
				else{
					worldY -= speed;
					direction = 0;
				}

			}else if (keyH.downPressed){
				if(keyH.rightPressed){
					worldY += speed;
					worldX += speed;
					direction = 3;
				}
				else if(keyH.leftPressed){
					worldY += speed;
					worldX -= speed;
					direction = 5;
				}
				else{
					worldY += speed;
					direction = 4;
				}

			}else if(keyH.leftPressed){
				worldX -= speed;
				direction = 6;
			}else if(keyH.rightPressed){
				worldX += speed;
				direction = 2;
			}
		}

		nextAnimationFrame();



		XCoords = (worldX - spawnCoords) / GamePanel.tileSize;
		YCoords = (worldY - spawnCoords) / GamePanel.tileSize;




	}

	public void nextAnimationFrame(){
		if(mining){
			selectedEntityImage = miningArray;
			selectedShadowImage = miningShadowImage;
			entityTextureWidth = 196;
			entityTextureHeight = 194;
			shadowTextureWidth = 292;
			shadowTextureHeight = 142;

		}else if (moving){
			selectedEntityImage = runningArray;
			selectedShadowImage = runningShadowImage;
			entityTextureWidth = 88;
			entityTextureHeight = 132;
			shadowTextureWidth = 190;
			shadowTextureHeight = 68;

		}else {
			selectedEntityImage = idleArray;
			selectedShadowImage = idleShadowImage;
			entityTextureWidth = 92;
			entityTextureHeight = 116;
			shadowTextureWidth = 164;
			shadowTextureHeight = 78;
		}
		animationTick++;
		if (animationTick > 4 || moving || mining) {
			animationTick = 0;
			animationFrame++;
		if (animationFrame >= totalFramesInAnimation){
			animationFrame = 0;
		}
		
		}
		

	}
	
	public void draw(Graphics2D g2) {


		if(mining){
			pBar.draw(g2, pBarProgress);
		}

		// BufferedImage playerImage = selectedEntityImage.getSubimage((animationFrame * entityTextureWidth), (direction * entityTextureHeight), entityTextureWidth, entityTextureHeight);
		BufferedImage shadowImage = selectedShadowImage.getSubimage((animationFrame * shadowTextureWidth), (direction * shadowTextureHeight), shadowTextureWidth, shadowTextureHeight);
		BufferedImage playerImage = selectedEntityImage[animationFrame][direction];

		g2.drawImage(shadowImage, shadowX, shadowY, shadowTextureWidth, shadowTextureHeight, null);
		g2.drawImage(playerImage, screenX, screenY, entityTextureWidth, entityTextureHeight, null);

	
	
	}
	
}
