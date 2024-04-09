package src.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import src.Chunk.Chunk;
import src.item.ItemProperties;
import src.main.GamePanel;
import src.tileEntity.EntityImage;
import src.tileEntity.TileEntity;

public class BackgroundManager {


	GamePanel gp;
	Background[] terrain;

	Random terrainSeed;

	EntityImage entityImage;


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

			if (chunk == null){
				gp.chunkGrid.generateNewChunk(worldCol, worldRow);
			}
			if (chunk != null){
				for(chunkCol = 0; chunkCol < Chunk.chunkSize; chunkCol++){
					for(chunkRow = 0; chunkRow < Chunk.chunkSize; chunkRow++){
						
						int tileNum = chunk.getTile(chunkCol, chunkRow);




						int worldX = (worldCol * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkCol);
						int worldY = (worldRow * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * chunkRow);
						int screenX = worldX - gp.player.worldX + gp.player.screenX;
						int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
						g2.drawImage(terrain[tileNum].drawingImage[terrainSeed.nextInt(8)], screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);


						
					}
				}
				
			}
			this.terrainSeed.setSeed(44);
			// if(chunkCol >= Chunk.chunkSize){
			// 	chunkCol = 0;
			// 	chunkRow++;
			// }

			if(chunkRow >= Chunk.chunkSize){
				worldCol++;
				chunkRow = 0;
				// this.terrainSeed.setSeed(44);
			}
			if (worldCol >= endWorldCol){
				worldCol = leftCol - 1;
				worldRow++;
			}
			

		}

		worldCol = leftCol;
		worldRow = topRow;
		chunkCol = 0;
		chunkRow = 0;
		

		for (worldRow = topRow; worldRow < endWorldRow; worldRow++){
			for(worldCol = leftCol; worldCol < endWorldCol; worldCol++){

				
				Chunk chunk = gp.chunkGrid.getChunk(worldCol, worldRow);
				// Makes sure going to edge of the world won't crash the game
	
	
				if (chunk != null){
					for(TileEntity entity : chunk.tileEntityList){
							
			
						TileEntity tileEntity = chunk.getTileEntity(entity.x, entity.y);
		
		
		
						int worldX = (worldCol * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * entity.x);
						int worldY = (worldRow * GamePanel.tileSize * (Chunk.chunkSize)) + (GamePanel.tileSize * entity.y);
						int screenX = worldX - gp.player.worldX + gp.player.screenX;
						int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
		
						if (tileEntity != null){
							entityImage = EntityImage.entityImages.get(tileEntity.tileEntityID);
							// g2.drawImage(EntityImage.entityImages.get("assembling-machine-1-shadow").imageArr[0][0], screenX + 13 , screenY + 13, GamePanel.tileSize * EntityImage.entityImages.get("assembling-machine-1-shadow").tileWidth - 2, GamePanel.tileSize * EntityImage.entityImages.get("assembling-machine-1-shadow").tileHeight - 26, null);
							// g2.drawImage(entityImage.imageArr[0][0], screenX - entityImage.shiftX, screenY - entityImage.shiftY, GamePanel.tileSize * entityImage.tileWidth + 24, GamePanel.tileSize * entityImage.tileHeight + 36, null);

							
							g2.drawImage(entityImage.shadowArr[0][0], screenX - entityImage.shiftX, screenY - entityImage.shiftY, GamePanel.tileSize * entityImage.tileWidth + (2 * entityImage.shiftX) + entityImage.shadowOffsetRight, GamePanel.tileSize * entityImage.tileHeight + (2 * entityImage.shiftY) + entityImage.shadowOffsetDown, null);
							
							// g2.drawImage(entityImage.imageArr[0][0], screenX - entityImage.shiftX, screenY - entityImage.shiftY, GamePanel.tileSize * entityImage.tileWidth + (2 * entityImage.shiftX), GamePanel.tileSize * entityImage.tileHeight + (2 * entityImage.shiftY), Color.GREEN, gp);
							
						}
						
					}
	
				}
	
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

		if(gp.mouseH.notOverGUI){
			if(gp.ui.hotbar.selectedSlot != gp.ui.hotbar.slotCount){
				if(gp.player.hotbar[gp.ui.hotbar.selectedSlot].quantity > 0){
					if(ItemProperties.itemPropertyMap.get(gp.player.hotbar[gp.ui.hotbar.selectedSlot].itemID).placeable){
						entityImage = EntityImage.entityImages.get(gp.player.hotbar[gp.ui.hotbar.selectedSlot].itemID);
						int screenX = (gp.mouseH.mouseTileX * GamePanel.tileSize) + (gp.mouseH.mouseCol * Chunk.chunkSize * GamePanel.tileSize) - gp.player.worldX + gp.player.screenX;
						int screenY = (gp.mouseH.mouseTileY * GamePanel.tileSize) + (gp.mouseH.mouseRow * Chunk.chunkSize * GamePanel.tileSize) - gp.player.worldY + gp.player.screenY;

						g2.drawImage(entityImage.imageArr[0][0], screenX - entityImage.shiftX, screenY - entityImage.shiftY, GamePanel.tileSize * entityImage.tileWidth + (2 * entityImage.shiftX), GamePanel.tileSize * entityImage.tileHeight + (2 * entityImage.shiftY), null);
					}
				}
			}else if(gp.ui.SIUI.inv.selectedSlot != gp.ui.SIUI.inv.slotCount){
				if(gp.player.inventory.invContents[gp.ui.SIUI.inv.selectedSlot].quantity > 0){
					if(ItemProperties.itemPropertyMap.get(gp.player.inventory.invContents[gp.ui.SIUI.inv.selectedSlot].itemID).placeable){
						entityImage = EntityImage.entityImages.get(gp.player.inventory.invContents[gp.ui.SIUI.inv.selectedSlot].itemID);
						int screenX = (gp.mouseH.mouseTileX * GamePanel.tileSize) + (gp.mouseH.mouseCol * Chunk.chunkSize * GamePanel.tileSize) - gp.player.worldX + gp.player.screenX;
						int screenY = (gp.mouseH.mouseTileY * GamePanel.tileSize) + (gp.mouseH.mouseRow * Chunk.chunkSize * GamePanel.tileSize) - gp.player.worldY + gp.player.screenY;

						g2.drawImage(entityImage.imageArr[0][0], screenX - entityImage.shiftX, screenY - entityImage.shiftY, GamePanel.tileSize * entityImage.tileWidth + (2 * entityImage.shiftX), GamePanel.tileSize * entityImage.tileHeight + (2 * entityImage.shiftY), null);
					}
				}
			}
		}







	
		
	}


	
}
