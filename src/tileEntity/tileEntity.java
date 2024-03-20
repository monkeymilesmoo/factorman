package src.tileEntity;

import java.io.Serializable;

import src.Chunk.ChunkGrid;


public class TileEntity implements Serializable{
	//ALL BUILDING AND NON BUILDING VARIABLES MUST BE DECLARED HERE
	public short textureID;
	public byte tileWidth, tileHeight;
	public byte xOffset, yOffset;
	public int x, y;
	public byte miningDurability = 100;
	public String tileEntityID;

	public TileEntity(int x, int y, byte tileWidth, byte tileHeight, String tileEntityID){

		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.x = x;
		this.y = y;
		this.tileEntityID = tileEntityID;
		


	}

	//IM not sure what the best way to remove it is other than this
	public void beingMined(int chunkX, int chunkY, ChunkGrid chunkGrid){

		miningDurability -= 1;

		if (miningDurability <= 0){
			chunkGrid.chunks[chunkX][chunkY].removeTileEntity(x, y, tileWidth, tileHeight, this);
		}


	}

}
