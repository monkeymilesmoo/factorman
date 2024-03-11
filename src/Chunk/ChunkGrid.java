package src.Chunk;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import src.main.GamePanel;

public class ChunkGrid implements Serializable{
	public static final int gridSize = 100;
	public Chunk[][] chunks;

	public int leftmostChunk;
	public int topmostChunk;


	//REMOVE GAMEPANEL LATER MAYBE
	public ChunkGrid(GamePanel gp, boolean loading) {
		chunks = new Chunk[gridSize][gridSize];

		if (!loading){
			newEmptyChunk();
			try {
			
			//FOR NOW JUST READING ONE TXT FILE
			InputStream is = getClass().getResourceAsStream("/data/map/map.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			// double beforeTime = System.nanoTime();
			// double timeBtwn = 0;
			// double afterTime = 0;
			// double biggestTime = 0;
			// double totalTime = 0;

			while (col < Chunk.chunkSize && row < Chunk.chunkSize){

				String line = br.readLine();

				// beforeTime = System.nanoTime();

				while(col < Chunk.chunkSize){

					String numbers[] = line.split(" ");

					byte num = Byte.parseByte(numbers[col]);

					// System.out.print(num + " ");

					chunks[50][50].setTile(col, row, num);
					col++;
				}

					col = 0;
					row++;
				

			
				}
			
			// System.out.println(biggestTime);
			// System.out.println(totalTime);
			br.close();
			
			

		}catch(Exception e){
			e.printStackTrace();
		}
		}
		
	}

	public void newEmptyChunk() {
		for(int x = 0; x < gridSize; x++){
			for (int y = 0; y < gridSize; y++){
				chunks[x][y] = new Chunk();
			}
		}
	}

	public Chunk getChunk(int chunkX, int chunkY) {
		try {
			return chunks[chunkX][chunkY];
		} catch(Exception e){
			return null;
		}
	}
	
	public void setChunk(int chunkX, int chunkY, Chunk chunk){
		chunks[chunkX][chunkY] = chunk;
	}

	public void saveToFile(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			System.out.println("Map saved successfully to: " + filename);
			
			objectOut.close();

		} catch (IOException e){
			System.err.println("Error saving chunkGrid to file: " + e.getMessage());
		}
		
	}

	public static ChunkGrid loadFromFile(String filename) {
		ChunkGrid grid = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(fileIn));
			// double beforeLoadTime = System.nanoTime();
			grid = (ChunkGrid) objectIn.readObject();
			System.out.println("Map successfully loaded from: " + filename);
			// double afterLoadTime = System.nanoTime();
			// System.out.println("Done in: " + (afterLoadTime-beforeLoadTime) + "nanoseconds");

			objectIn.close();
		} catch (IOException e){
			System.err.println("Error loading chunkGrid from file: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}
}

