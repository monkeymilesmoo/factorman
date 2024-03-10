package src.Chunk;

import java.io.Serializable;

public class Chunk implements Serializable{
	private int[][] tiles;

	public Chunk() {
		tiles = new int[32][32];
	}

	public void setTile(int x, int y, int tileID) {

		tiles[x][y] = tileID;
	}
	
	public int getTile(int x, int y){
		return tiles[x][y];
	}
}
