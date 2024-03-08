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


	public Player(GamePanel gp, KeyHandler keyH){
		
		this.gp = gp;
		this.keyH = keyH;

		getPlayerImages();
		setDefaultValues();
	}

	public void setDefaultValues() {

		speed = 8;
		x = 400;
		y = 400;
		textureWidth = 92;
		textureHeight = 116;
		direction = 4;
		selectedCol = 0;
		selectedImage = idleImage;
		moving = false;
		totalFramesInAnimation = 21;

	}

	public void getPlayerImages() {

		try {
			idleImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_idle.png"));
			runningImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_running.png"));
			miningImage = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/hr-level1_mining_tool-1.png"));

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
			selectedImage = miningImage;
		}
		moving = (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) ? true : false;


		if(!mining){
			if (keyH.upPressed){
				if(keyH.rightPressed){
					y -= speed;
					x += speed;
					direction = 1;
				}
				else if(keyH.leftPressed){
					y -= speed;
					x -= speed;
					direction = 7;
				}
				else{
					y -= speed;
					direction = 0;
				}

			}else if (keyH.downPressed){
				if(keyH.rightPressed){
					y += speed;
					x += speed;
					direction = 3;
				}
				else if(keyH.leftPressed){
					y += speed;
					x -= speed;
					direction = 5;
				}
				else{
					y += speed;
					direction = 4;
				}

			}else if(keyH.leftPressed){
				x -= speed;
				direction = 6;
			}else if(keyH.rightPressed){
				x += speed;
				direction = 2;
			}
		}

		nextAnimationFrame();









	}

	public void nextAnimationFrame(){
		if(mining){
			selectedImage = miningImage;
			textureWidth = 196;
			textureHeight = 194;
			totalFramesInAnimation = 26;
			animationTick += 2;

		}else if (moving){
			selectedImage = runningImage;
			textureWidth = 88;
			textureHeight = 132;
			totalFramesInAnimation = 22;

		}else {
			selectedImage = idleImage;
			textureWidth = 92;
			textureHeight = 116;
			totalFramesInAnimation = 22;
		}
		animationTick++;
		if (animationTick > 4 || moving) {
			animationTick = 0;
			selectedCol++;
		if (selectedCol >= totalFramesInAnimation){
			selectedCol = 0;
		}
		
		}
		

	}
	
	public void draw(Graphics2D g2) {



		BufferedImage image = selectedImage.getSubimage((selectedCol * textureWidth), (direction * textureHeight), textureWidth, textureHeight);
	

		g2.drawImage(image, x, y, textureWidth, textureHeight, null);
	
	
	
	
	}
	
}
