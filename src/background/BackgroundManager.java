package src.background;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class BackgroundManager {


	GamePanel gp;
	Terrain[] terrain;
	int mapTileNum[][];

	Random terrainSeed;


	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		this.terrainSeed = new Random();

		terrain = new Terrain[2]; // CHANGE TO NUMBER OF TERRAINS
		mapTileNum = new int[gp.chunkSize + 1][gp.chunkSize + 1];
		
		getBackgroundImage();
		loadChunk();

	}

	public void getBackgroundImage() {

		try{
			
			terrain[0] = new Terrain();
			terrain[0].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/dirt-1.png"));
			terrain[0].width = 65;
			
			terrain[1] = new Terrain();
			terrain[1].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/sand-1.png"));
			terrain[1].width = 128;


			int renderingTerrain = 0;
			int gettingSub = 0;

			while (renderingTerrain < 2){


				while (gettingSub < 8){
					terrain[renderingTerrain].drawingImage[gettingSub] = terrain[renderingTerrain].image.getSubimage(terrain[renderingTerrain].width * terrainSeed.nextInt(8), 160, gp.tileSize, gp.tileSize);
					gettingSub++;
				}
				renderingTerrain++;
				gettingSub = 0;
			}
		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	public void loadChunk(){

		try {
			
			//FOR NOW JUST READING ONE TXT FILE
			InputStream is = getClass().getResourceAsStream("/data/map/map.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;



			while (col <= gp.chunkSize && row <= gp.chunkSize){

				String line = br.readLine();

				while(col <= gp.chunkSize){

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}

				if (col > gp.chunkSize){
					col = 0;
					row++;
				}

			}
			br.close();
			
			

		}catch(Exception e){

		}
	}

	public void draw(Graphics2D g2){


		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		// int drawNum = 0;


		this.terrainSeed.setSeed(44);

		while (col < gp.chunkSize && row < gp.chunkSize){
			

			int tileNum = mapTileNum[col][row];



			// drawNum++;
			g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], x, y, gp.tileSize, gp.tileSize, null);
			

			// g2.drawImage(background[0].image.getSubimage(0, 0, background[0].width, background[0].width), x, y, gp.tileSize, gp.tileSize, null);
			// maybe use for like stone paths or something
			


			col++;
			x += gp.tileSize;

			if(col == gp.chunkSize){
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}



		}


		// System.out.println(drawNum);


		
	}


	
}
