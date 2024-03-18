package src.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import src.Chunk.Chunk;
import src.main.GamePanel;
import src.tileEntity.Building;
import src.tileEntity.TileEntity;

public class BackgroundManager {


	GamePanel gp;
	Background[] terrain;

	Random terrainSeed;



	public BackgroundManager(GamePanel gp){

		this.gp = gp;

		this.terrainSeed = new Random();

		terrain = new Background[3]; // CHANGE TO NUMBER OF TERRAINS
		
		getBackgroundImage();

	}

	public void getBackgroundImage() {

		try{
			
			terrain[0] = new Background();
			terrain[0].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/dirt-1.png"));
			terrain[0].width = 64;
			terrain[0].howMany = 8;
			
			terrain[1] = new Background();
			terrain[1].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/sand-1.png"));
			terrain[1].width = 128;
			terrain[1].howMany = 8;
			
			terrain[2] = new Background();
			terrain[2].image = ImageIO.read(getClass().getResourceAsStream("/res/background/terrain/grass-1.png"));
			terrain[2].width = 128;
			terrain[2].howMany = 16;


			int renderingTerrain = 0;
			int gettingSub = 0;

			while (renderingTerrain < terrain.length){
				
				terrain[renderingTerrain].drawingImage = new BufferedImage[terrain[renderingTerrain].howMany];

				while (gettingSub < terrain[renderingTerrain].howMany){
					terrain[renderingTerrain].drawingImage[gettingSub] = new BufferedImage(GamePanel.tileSize, GamePanel.tileSize, BufferedImage.TYPE_INT_RGB);
					terrain[renderingTerrain].drawingImage[gettingSub].createGraphics().drawImage(terrain[renderingTerrain].image.getSubimage(terrain[renderingTerrain].width * terrainSeed.nextInt(8), 160, terrain[renderingTerrain].width,  terrain[renderingTerrain].width), 0, 0, GamePanel.tileSize, GamePanel.tileSize, null);
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


		



		//Which tile in each chunk is selected
		int chunkCol = 0;
		int chunkRow = 0;



		int leftCol = ((gp.player.worldX - gp.player.screenX) / (Chunk.chunkSize * GamePanel.tileSize));
		int topRow = ((gp.player.worldY - gp.player.screenY) / (Chunk.chunkSize * GamePanel.tileSize));


		if(leftCol - 1< gp.chunkGrid.leftmostChunk){
			gp.chunkGrid.leftmostChunk = leftCol - 1;
		}
		if (topRow - 1< gp.chunkGrid.topmostChunk){
			gp.chunkGrid.topmostChunk = topRow - 1;
		}


		//Which chunk is selected
		int worldCol = leftCol;
		int worldRow = topRow;

		int endWorldCol = leftCol + 2;
		int endWorldRow = topRow + 2; 

		if(endWorldCol + 1 > gp.chunkGrid.rightmostChunk){
			gp.chunkGrid.rightmostChunk = endWorldCol;
		}
		if (endWorldRow + 1> gp.chunkGrid.bottommostChunk){
			gp.chunkGrid.bottommostChunk = endWorldRow;
		}




		while ((worldCol < endWorldCol) && (worldRow < endWorldRow)){
			

			Chunk chunk = gp.chunkGrid.getChunk(worldCol, worldRow);
			// Makes sure going to edge of the world won't crash the game

			// if (chunk == null){
			// 	gp.chunkGrid.generateNewChunk(worldCol, worldRow);
			// }
			if (chunk != null){
				
				int tileNum = chunk.getTile(chunkCol, chunkRow);




				int worldX = (worldCol * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkCol);
				int worldY = (worldRow * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkRow);
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
				// long firstTime = System.nanoTime();
				g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
				// long secondTime = System.nanoTime();

				//For now just draw a building on every possible tile

				// if (tileEntity != null){
				// 	g2.drawImage(Building.buildingImages[0].imageArr[gp.frameNumber % 32][0], screenX, screenY, GamePanel.tileSize * 3, GamePanel.tileSize * 3, null);
				// }

				// if(secondTime - firstTime > 50000){
					// System.out.println("" + (secondTime - firstTime) + " " + chunkCol + " " + chunkRow + " " + tileNum);
					// System.out.println(tileNum);
				// }
			}
			chunkCol++;

			if(chunkCol >= Chunk.chunkSize){
				chunkCol = 0;
				chunkRow++;
			}

			if(chunkRow >= Chunk.chunkSize){
				worldCol++;
				chunkRow = 0;
				this.terrainSeed.setSeed(44);
			}
			if (worldCol >= endWorldCol){
				worldCol = leftCol - 1;
				worldRow++;
			}
			

		}

		worldCol = leftCol;
		worldRow = topRow;
		// System.out.println(drawNum);
		while ((worldCol < endWorldCol) && (worldRow < endWorldRow)){
			

			Chunk chunk = gp.chunkGrid.getChunk(worldCol, worldRow);
			// Makes sure going to edge of the world won't crash the game

			if (chunk == null){
				gp.chunkGrid.generateNewChunk(worldCol, worldRow);
			}
			if (chunk != null){
				

				TileEntity tileEntity = chunk.getTileEntity(chunkCol, chunkRow);



				int worldX = (worldCol * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkCol);
				int worldY = (worldRow * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkRow);
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
				// g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
				
				//For now just draw a building on every possible tile

				if (tileEntity != null){
					g2.drawImage(Building.buildingImages[1].imageArr[gp.frameNumber % 256 / 8][0], screenX + 13 , screenY + 13, GamePanel.tileSize * Building.buildingImages[1].tileWidth - 2, GamePanel.tileSize * Building.buildingImages[1].tileHeight - 26, null);
					g2.drawImage(Building.buildingImages[0].imageArr[gp.frameNumber % 256 / 8][0], screenX - 12, screenY - 18, GamePanel.tileSize * Building.buildingImages[0].tileWidth + 24, GamePanel.tileSize * Building.buildingImages[0].tileHeight + 36, null);
				}

				

			}
			chunkCol++;

			if(chunkCol >= Chunk.chunkSize){
				chunkCol = 0;
				chunkRow++;
			}

			if(chunkRow >= Chunk.chunkSize){
				worldCol++;
				chunkRow = 0;
				this.terrainSeed.setSeed(44);
			}
			if (worldCol >= endWorldCol){
				worldCol = leftCol - 1;
				worldRow++;
			}
			

		}

		
	}


	
}
