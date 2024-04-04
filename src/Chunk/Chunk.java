package src.Chunk;

import java.io.Serializable;
import java.util.ArrayList;

import src.main.GamePanel;
import src.main.UI;
import src.tileEntity.TileEntity;

public class Chunk implements Serializable{
	byte[][] tiles;
	public TileEntity[][] tileEntities;
	public final static int chunkSize = 32;
	
	public boolean canDo = true;

	public ArrayList<TileEntity> tileEntityList = new ArrayList<TileEntity>();
	private int thisChunkX;
	private int thisChunkY;
	private ChunkGrid chunkGrid;

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

	public void setTileEntity(int x, int y, byte tileWidth, byte tileHeight, String tileEntityID, GamePanel gp){
		
		canDo = true;


		for (int i = x; i < (x + tileWidth); i++){
			for(int j = y; j < (y + tileHeight); j++){
				if(chunkGrid.getChunk(thisChunkX + (Math.floorDiv(i, Chunk.chunkSize)), thisChunkY + (Math.floorDiv(j, Chunk.chunkSize))).getTileEntity(i % Chunk.chunkSize, j % Chunk.chunkSize) != null){
					canDo = false;
				}
			}
		}


		if(canDo){
			

			TileEntity insertingTE = new TileEntity( x, y, thisChunkX, thisChunkY, tileWidth, tileHeight, tileEntityID);
			
			
			
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
		}
		else{
			gp.ui.addNewDisspearingText((thisChunkX * Chunk.chunkSize * GamePanel.tileSize) + (x * GamePanel.tileSize), (thisChunkY * Chunk.chunkSize * GamePanel.tileSize) + (y * GamePanel.tileSize), "Can't place that there");
			gp.mouseH.leftMouseClicked = false;
			System.out.println("Can't place that there");
		}
	}

	public void removeTileEntity(int x, int y, int chunkX, int chunkY, byte tileWidth, byte tileHeight, TileEntity removingTE){
		

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
	}
	
}
