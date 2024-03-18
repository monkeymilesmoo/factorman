package src.Chunk;

import java.io.Serializable;
import java.util.ArrayList;

import src.tileEntity.TileEntity;

public class Chunk implements Serializable{
	byte[][] tiles;
	TileEntity[][] tileEntities;
	public final static int chunkSize = 32;

	ArrayList<TileEntity> tileEntityList = new ArrayList<TileEntity>();

	public Chunk() {
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

	public void setTileEntity(int x, int y, byte tileWidth, byte tileHeight, short tileEntityID){

		TileEntity insertingTE = new TileEntity(tileWidth, tileHeight, tileEntityID);
		tileEntities[x][y] = insertingTE;
		for (int i = x; i < (x + tileWidth); i++){
			for(int j = y; j < (y + tileHeight); j++){
				tileEntities[i][j] = tileEntities[x][y];
			}
		}

		tileEntityList.add(insertingTE);
	}
	
}
