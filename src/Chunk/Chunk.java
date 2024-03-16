package src.Chunk;

import java.io.Serializable;

import src.tileEntity.TileEntity;
import src.tileEntity.Building;

public class Chunk implements Serializable{
	byte[][] tiles;
	TileEntity[][] tileEntities;
	Building test = new Building();
	public final static int chunkSize = 32;

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

	public void setTileEntity(int x, int y, TileEntity tileEntityID){
		tileEntities[x][y] = tileEntityID;
	}
	
}
