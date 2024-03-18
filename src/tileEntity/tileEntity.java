package src.tileEntity;

import java.io.Serializable;


public class TileEntity implements Serializable{
	//ALL BUILDING AND NON BUILDING VARIABLES MUST BE DECLARED HERE
	public short textureID;
	public byte tileWidth, tileHeight;
	public byte xOffset, yOffset;

	public TileEntity(byte tileWidth, byte tileHeight, short tileEntityID){

		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;

		


	}

	public void beingMined(){

	}

}
