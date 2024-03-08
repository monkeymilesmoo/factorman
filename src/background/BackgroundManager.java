package src.background;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class BackgroundManager {


	GamePanel gp;
	Terrain[] terrain;

	Random terrainSeed;


	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		this.terrainSeed = new Random();

		terrain = new Terrain[10];
		
		getBackgroundImage();


	}

	public void getBackgroundImage() {

		try{
			
			terrain[0] = new Terrain();
			terrain[0].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/sand-1.png"));
			terrain[0].width = 128;

		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	public void draw(Graphics2D g2){


		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		int drawNum = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow){
			
			drawNum++;
			this.terrainSeed.setSeed(drawNum);
			g2.drawImage(terrain[0].image.getSubimage(1 * terrainSeed.nextInt(1000), 160, terrain[0].width, terrain[0].width), x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			

			// g2.drawImage(background[0].image.getSubimage(0, 0, background[0].width, background[0].width), x, y, gp.tileSize, gp.tileSize, null);
			// maybe use for like stone paths or something
			


			col++;
			x += gp.tileSize * 2;

			if(col == gp.maxScreenCol){
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize * 2;
			}



		}


		// System.out.println(drawNum);


		
	}


	
}
