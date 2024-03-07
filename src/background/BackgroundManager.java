package src.background;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class BackgroundManager {


	GamePanel gp;
	Background[] background;


	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		background = new Background[10];
		
		getBackgroundImage();


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


		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow){

			g2.drawImage(background[0].image, x, y, gp.tileSize, gp.tileSize, null);
			
			col++;
			x += gp.tileSize;

			if(col == gp.maxScreenCol){
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}



		}



		
	}


	
}
