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

			// double beforeTime = System.nanoTime();
			// double timeBtwn = 0;
			// double afterTime = 0;
			// double biggestTime = 0;
			// double totalTime = 0;

			while (col <= gp.chunkSize && row <= gp.chunkSize){

				String line = br.readLine();

				// beforeTime = System.nanoTime();

				while(col <= gp.chunkSize){

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}

				// afterTime = System.nanoTime();
				// timeBtwn = (afterTime - beforeTime);
				// // System.out.println(timeBtwn);
				// if (timeBtwn > biggestTime){
				// 	biggestTime = timeBtwn;
				// }
				// totalTime = totalTime + timeBtwn;


					col = 0;
					row++;
				

			}
			
			// System.out.println(biggestTime);
			// System.out.println(totalTime);
			br.close();
			
			

		}catch(Exception e){

		}
	}

	public void draw(Graphics2D g2){


		//NEEDS TO BE REDONE PER CHUNK
		int worldCol = 0;
		int worldRow = 0;


		this.terrainSeed.setSeed(44);

		while (worldCol < gp.chunkSize && worldRow < gp.chunkSize){
			

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;



			// drawNum++;
			g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			


			worldCol++;

			if(worldCol == gp.chunkSize){
				worldCol = 0;
				worldRow++;
			}



		}


		// System.out.println(drawNum);


		
	}


	
}
