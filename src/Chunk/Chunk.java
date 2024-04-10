package src.Chunk;

import java.io.Serializable;
import java.util.ArrayList;

import src.main.GamePanel;
import src.tileEntity.Building;
import src.tileEntity.EntityImage;
import src.tileEntity.TileEntity;

public class Chunk implements Serializable{
	byte[][] tiles;
	public TileEntity[][] tileEntities;
	public final static int chunkSize = 32;
	
	public transient boolean canDo = true;
	public static transient boolean onTextCooldown = false;

	public ArrayList<TileEntity> tileEntityList = new ArrayList<TileEntity>();
	private int thisChunkX;
	private int thisChunkY;
	private ChunkGrid chunkGrid;
	private int containsTE = -1; //index in arraylist

	public Chunk(int thisChunkX, int thisChunkY, ChunkGrid chunkGrid) {
		this.thisChunkX = thisChunkX;
		this.thisChunkY = thisChunkY;
		this.chunkGrid = chunkGrid;

		tiles = new byte[chunkSize][chunkSize];
		tileEntities = new TileEntity[chunkSize][chunkSize];
		// setTileEntity(17, 18, test);



		
	}

	public void setTile(int x, int y, byte tileID) {

		tiles[x][y] = tileID;
	}
	
	public int getTile(int x, int y){
		return tiles[x][y];
	}

	public TileEntity getTileEntity(int x, int y){
		return tileEntities[x][y];
	}

	public void setTileEntity(int x, int y, int tileWidth, int tileHeight, String tileEntityID, GamePanel gp){
		
		canDo = true;


		for (int i = x; i < (x + tileWidth); i++){
			for(int j = y; j < (y + tileHeight); j++){
				if(chunkGrid.getChunk(thisChunkX + (Math.floorDiv(i, Chunk.chunkSize)), thisChunkY + (Math.floorDiv(j, Chunk.chunkSize))).getTileEntity(i % Chunk.chunkSize, j % Chunk.chunkSize) != null){
					canDo = false;
				}
			}
		}


		if(canDo){
			

			Building insertingTE = new Building( x, y, thisChunkX, thisChunkY, tileWidth, tileHeight, tileEntityID, (byte) 5);

			
			
			
			for (int i = x; i < (x + tileWidth); i++){
				for(int j = y; j < (y + tileHeight); j++){
					chunkGrid.getChunk(thisChunkX + (Math.floorDiv(i, Chunk.chunkSize)), thisChunkY + (Math.floorDiv(j, Chunk.chunkSize))).tileEntities[i % Chunk.chunkSize][j % Chunk.chunkSize] = insertingTE;
				}
			}
			
			
			
			
			// for (int i = x; i < (x + tileWidth); i++){
			// 	for(int j = y; j < (y + tileHeight); j++){
			// 		tileEntities[i][j] = tileEntities[x][y];
			// 	}
			// }

			tileEntityList.add(insertingTE);

			if(containsTE == -1){
				chunkGrid.chunksWithTE.add(ChunkGrid.createCoordinateBytes(thisChunkX, thisChunkY));
				containsTE = chunkGrid.chunksWithTE.size() - 1;
			}

		}
		else{
			if(!onTextCooldown){
				gp.ui.addNewDisspearingText((thisChunkX * Chunk.chunkSize * GamePanel.tileSize) + (x * GamePanel.tileSize), (thisChunkY * Chunk.chunkSize * GamePanel.tileSize) + (y * GamePanel.tileSize), "Can't place " + EntityImage.entityImages.get(tileEntityID).visibleName +  " there");
				onTextCooldown = true;
			}
		}
	}

	public void removeTileEntity(int x, int y, int chunkX, int chunkY, int tileWidth, int tileHeight, TileEntity removingTE){
		

		for (int i = x; i < (x + tileWidth); i++){
			for(int j = y; j < (y + tileHeight); j++){
				chunkGrid.getChunk(chunkX + (Math.floorDiv(i, Chunk.chunkSize)), chunkY + (Math.floorDiv(j, Chunk.chunkSize))).tileEntities[i % Chunk.chunkSize][j % Chunk.chunkSize] = null;
			}
		}


		// for (int i = x; i < (x + tileWidth); i++){
		// 	for(int j = y; j < (y + tileHeight); j++){
		// 		tileEntities[i][j] = null;
		// 	}
		// }

		tileEntityList.remove(removingTE);

		if(tileEntityList.size() == 0){
			chunkGrid.chunksWithTE.remove(containsTE);
			containsTE = -1;
		}
	}
	
}
