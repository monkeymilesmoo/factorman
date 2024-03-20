package src.tileEntity;

import src.Chunk.ChunkGrid;
import src.entity.Player;



public class Ore extends TileEntity{


	//Probably change this later
	// public byte miningDurability = 25;
	public int remainingOre = 1000;
	
	public Ore(int x, int y, int chunkX, int chunkY, byte tileWidth, byte tileHeight, String tileEntityID){
		super(x, y, chunkX, chunkY, tileWidth, tileHeight, tileEntityID);

		
	}

	
	@Override public void beingMined(ChunkGrid chunkGrid, Player player){

		miningDurability -= 1;


		if (miningDurability <= 0){
			remainingOre -= 1;
			miningDurability = 25;
		}
		if (remainingOre <= 0){
			chunkGrid.chunks[chunkX][chunkY].removeTileEntity(x, y, chunkX, chunkY, tileWidth, tileHeight, this);
			player.mining = false;
		}


	}


}
