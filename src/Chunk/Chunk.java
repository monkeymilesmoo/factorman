package src.Chunk;

import java.io.Serializable;

public class Chunk implements Serializable{
	byte[][] tiles;
	public final static int chunkSize = 32;

	public Chunk() {
		tiles = new byte[chunkSize][chunkSize];
	}

	public void setTile(int x, int y, byte tileID) {

		tiles[x][y] = tileID;
	}
	
	public int getTile(int x, int y){
		return tiles[x][y];
	}
}
