package src.tileEntity;

import src.Chunk.Chunk;
import src.Chunk.ChunkGrid;
import src.entity.Player;
import src.item.Item;
import src.main.GamePanel;



public class Ore extends TileEntity{


	//Probably change this later
	// public byte miningDurability = 25;
	public int remainingOre = 1000;
	
	public Ore(int x, int y, int chunkX, int chunkY, int tileWidth, int tileHeight, String tileEntityID, byte maxMiningDurability){
		super(x, y, chunkX, chunkY, tileWidth, tileHeight, tileEntityID, maxMiningDurability);

		
	}

	
	@Override public void beingMined(ChunkGrid chunkGrid, Player player){

		miningDurability += 1;


		if (miningDurability >= maxMiningDurability){
			remainingOre -= 1;
			miningDurability = 0;
			player.inventory.addItemToInventory(new Item(this.tileEntityID, 1));
			player.gp.ui.addNewDisspearingText((x * GamePanel.tileSize) + (chunkX * Chunk.chunkSize * GamePanel.tileSize), (y * GamePanel.tileSize) + (chunkY * Chunk.chunkSize * GamePanel.tileSize), "+1 " + EntityImage.entityImages.get(tileEntityID).visibleName);
		}
		if (remainingOre <= 0){
			chunkGrid.chunks[chunkX][chunkY].removeTileEntity(x, y, chunkX, chunkY, tileWidth, tileHeight, this);
			player.mining = false;
		}


	}


}
