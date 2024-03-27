package src.tileEntity;

import java.io.Serializable;

import src.Chunk.ChunkGrid;
import src.entity.Player;
import src.item.Item;


public class TileEntity implements Serializable{
	//ALL BUILDING AND NON BUILDING VARIABLES MUST BE DECLARED HERE
	public short textureID;
	public byte tileWidth, tileHeight;
	public int x, y;
	public byte miningDurability = 25;
	public String tileEntityID;
	public int chunkX;
	public int chunkY;

	public TileEntity(int x, int y, int chunkX, int chunkY, byte tileWidth, byte tileHeight, String tileEntityID){

		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.x = x;
		this.y = y;
		this.tileEntityID = tileEntityID;
		


	}

	//IM not sure what the best way to remove it is other than this
	public void beingMined(ChunkGrid chunkGrid, Player player){

		miningDurability -= 1;

		if (miningDurability <= 0){
			player.inventory.addItemToInventory(new Item(this.tileEntityID, 1));
			chunkGrid.chunks[chunkX][chunkY].removeTileEntity(x, y, chunkX, chunkY, tileWidth, tileHeight, this);
			player.mining = false;
		}


	}

}
