package src.background;

import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class BackgroundManager {


	GamePanel gp;
	Background[] background;


	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		background = new Background[10];


	}

	public void getBackgroundImage() {

		try{
			
			background[0] = new Background();
			background[0].image = ImageIO.read(getClass().getResourceAsStream("/res/background/32x32_guide.png"));

		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	public void draw(Graphics2D g2){

		
	}


	
}
