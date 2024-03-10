package src.Chunk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ChunkGrid implements Serializable{
	private static final int gridSize = 10;
	private Chunk[][] chunks;

	public ChunkGrid() {
		chunks = new Chunk[gridSize][gridSize];

		

		
	}

	public void newEmptyChunk() {
		for(int x = 0; x < gridSize; x++){
			for (int y = 0; y < gridSize; y++){
				chunks[x][y] = new Chunk();
			}
		}
	}

	public Chunk getChunk(int chunkX, int chunkY) {
		return chunks[chunkX][chunkY];
	}
	
	public void setChunk(int chunkX, int chunkY, Chunk chunk){
		chunks[chunkX][chunkY] = chunk;
	}

	public void saveToFile(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			System.out.println("Map saved successfully to file");
			
			objectOut.close();

		} catch (IOException e){
			System.err.println("Error saving chunkGrid to file: " + e.getMessage());
		}
		
	}

	public static ChunkGrid loadFromFile(String filename) throws ClassNotFoundException {
		ChunkGrid grid = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			grid = (ChunkGrid) objectIn.readObject();
			System.out.println("Map successfully loaded from: " + filename);

			objectIn.close();
		} catch (IOException e){
			System.err.println("Error loading chunkGrid from file: " + e.getMessage());
		}
		return grid;
	}
}

