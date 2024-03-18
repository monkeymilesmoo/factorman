package src.tileEntity;

import java.io.Serializable;


public class TileEntity implements Serializable{
	//ALL BUILDING AND NON BUILDING VARIABLES MUST BE DECLARED HERE
	public short textureID;
	public byte tileWidth, tileHeight;
	public byte xOffset, yOffset;
	public int x, y;

	public TileEntity(int x, int y, byte tileWidth, byte tileHeight, short tileEntityID){

		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.x = x;
		this.y = y;
		


	}

	public void beingMined(){

	}

}
