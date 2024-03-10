package src.background;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import src.Chunk.Chunk;
import src.main.GamePanel;

public class BackgroundManager {


	GamePanel gp;
	Terrain[] terrain;

	Random terrainSeed;



	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		this.terrainSeed = new Random();

		terrain = new Terrain[2]; // CHANGE TO NUMBER OF TERRAINS
		
		getBackgroundImage();

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


		

	public void draw(Graphics2D g2){


		


		this.terrainSeed.setSeed(44);

		//Which tile in each chunk is selected
		int chunkCol = 0;
		int chunkRow = 0;


		// Starting chunk to render
		// int leftCol = (gp.player.worldX - (gp.player.worldY % Chunk.chunkSize) * Chunk.chunkSize);
		// int topRow = (gp.player.worldY - (gp.player.worldY % Chunk.chunkSize) * Chunk.chunkSize);

		int leftCol = (gp.player.worldX / (Chunk.chunkSize * gp.tileSize));
		int topRow = (gp.player.worldY / (Chunk.chunkSize * gp.tileSize));


		//Which chunk is selected
		int worldCol = leftCol - 1;
		int worldRow = topRow - 1;

		int endWorldCol = leftCol + 1;
		int endWorldRow = topRow + 1; 

		System.out.println(gp.player.worldY);
		System.out.println(leftCol);
		System.out.println(topRow);
		System.out.println();

		while ((worldCol < endWorldCol) && (worldRow < endWorldRow)){
			


			int tileNum = gp.chunkGrid.getChunk(worldCol, worldRow).getTile(chunkCol, chunkRow);



			int worldX = (worldCol * gp.tileSize * (Chunk.chunkSize)) + (gp.tileSize * chunkCol);
			int worldY = (worldRow * gp.tileSize * (Chunk.chunkSize)) + (gp.tileSize * chunkRow);
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			// System.out.println(worldX);
			// System.out.println(worldY);
			// System.out.println();

			// drawNum++;
			g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], screenX, screenY, gp.tileSize, gp.tileSize, null);
			

		
			chunkCol++;

			if(chunkCol == Chunk.chunkSize){
				chunkCol = 0;
				chunkRow++;
			}

			if(chunkRow == Chunk.chunkSize){
				worldCol++;
				chunkRow = 0;
			}
			if (worldCol > endWorldCol){
				worldCol = leftCol - 1;
				worldRow++;
			}



		}

		// System.out.println(drawNum);


		
	}


	
}
