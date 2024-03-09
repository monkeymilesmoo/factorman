package src.entity;

import src.main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class Player extends entity{

	GamePanel gp;
	KeyHandler keyH;

	public int screenX;
	public int screenY;
	public int shadowX;
	public int shadowY;


	public Player(GamePanel gp, KeyHandler keyH){
		
		this.gp = gp;
		this.keyH = keyH;

		getPlayerImages();
		setDefaultValues();
	}

	public void setDefaultValues() {

		speed = 8;
		worldX = 400;
		worldY = 400;
		entityTextureWidth = 92;
		entityTextureHeight = 116;
		shadowTextureWidth = 164;
		shadowTextureHeight = 78;
		direction = 4;
		selectedCol = 0;
		selectedEntityImage = idleImage;
		selectedShadowImage = idleShadowImage;
		moving = false;
		totalFramesInAnimation = 22;

	}

	public void getPlayerImages() {

		try {
			//Player images
			idleImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_idle.png"));
			runningImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_running.png"));
			miningImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_mining_tool-1.png"));

			//Shadow images
			idleShadowImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_idle_shadow.png"));
			runningShadowImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_running_shadow.png"));
			miningShadowImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_mining_tool_shadow.png"));



			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

	public void update() {
		
		
		// 
		// 
		//MAYBE INEFFICIENT CHANGE LATER
		//
		// 

	
		mining = keyH.testable ? true : false;
		if(keyH.testable){
			selectedEntityImage = miningImage;
		}
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









	}

	public void nextAnimationFrame(){
		if(mining){
			selectedEntityImage = miningImage;
			selectedShadowImage = miningShadowImage;
			entityTextureWidth = 196;
			entityTextureHeight = 194;
			shadowTextureWidth = 292;
			shadowTextureHeight = 142;
			totalFramesInAnimation = 26;

		}else if (moving){
			selectedEntityImage = runningImage;
			selectedShadowImage = runningShadowImage;
			entityTextureWidth = 88;
			entityTextureHeight = 132;
			shadowTextureWidth = 190;
			shadowTextureHeight = 68;
			totalFramesInAnimation = 22;

		}else {
			selectedEntityImage = idleImage;
			selectedShadowImage = idleShadowImage;
			entityTextureWidth = 92;
			entityTextureHeight = 116;
			shadowTextureWidth = 164;
			shadowTextureHeight = 78;
			totalFramesInAnimation = 22;
		}
		animationTick++;
		if (animationTick > 4 || moving || mining) {
			animationTick = 0;
			selectedCol++;
		if (selectedCol >= totalFramesInAnimation){
			selectedCol = 0;
		}
		
		}
		

	}
	
	public void draw(Graphics2D g2) {




		BufferedImage playerImage = selectedEntityImage.getSubimage((selectedCol * entityTextureWidth), (direction * entityTextureHeight), entityTextureWidth, entityTextureHeight);
		BufferedImage shadowImage = selectedShadowImage.getSubimage((selectedCol * shadowTextureWidth), (direction * shadowTextureHeight), shadowTextureWidth, shadowTextureHeight);


		g2.drawImage(shadowImage, shadowX, shadowY, shadowTextureWidth, shadowTextureHeight, null);
		g2.drawImage(playerImage, screenX, screenY, entityTextureWidth, entityTextureHeight, null);

	
	
	
	}
	
}
