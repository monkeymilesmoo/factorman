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
import java.util.ArrayList;

import src.main.GamePanel;
import src.tileEntity.Ore;
import src.tileEntity.TileEntity;

public class ChunkGrid implements Serializable{
	public static final int gridSize = 100;
	public transient Chunk[][] chunks;

	public final static int middleChunk = gridSize/2;
	public int leftmostChunk = middleChunk;
	public int topmostChunk = middleChunk;
	public int rightmostChunk = middleChunk;
	public int bottommostChunk = middleChunk;
	

	public Chunk[][] generatedChunks;

	public ArrayList<byte[]> chunksWithTE = new ArrayList<byte[]>();
	//One int, first 4 bytes correspond to x, second 4 bytes are y


	//REMOVE GAMEPANEL LATER MAYBE
	public ChunkGrid(GamePanel gp, boolean loading) {



		chunks = new Chunk[gridSize][gridSize];
		
		if (!loading){
			// newEmptyChunks();
			chunks[middleChunk][middleChunk] = new Chunk(middleChunk, middleChunk, this);
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

					chunks[middleChunk][middleChunk].setTile(col, row, num);
					col++;
				}

					col = 0;
					row++;
				

			
				}
			
			br.close();
			
			

		}catch(Exception e){
			e.printStackTrace();
		}
		}
		
	}

	public void newEmptyChunks() {
		for(int x = 0; x < gridSize; x++){
			for (int y = 0; y < gridSize; y++){
				chunks[x][y] = new Chunk(x, y, this);
				

			}
		}
	}

	public void generateNewChunk(int chunkX, int chunkY) {
		//FOR NOW IT JUST MAKES AN EMPTY CHUNK
		//
		//
		// CHANGE LATER
		//
		//
		chunks[chunkX][chunkY] = new Chunk(chunkX, chunkY, this);
		for(int x = 0; x < Chunk.chunkSize; x++){
			for (int y = 0; y < Chunk.chunkSize; y++){
				chunks[chunkX][chunkY].setTile(x, y, (byte) 0);
			}
		}
		
		//TODO DEBUG makes ore in bottom right of chunk
		Ore DEMOORE = new Ore(30, 30, chunkX, chunkY, 1, 1, "iron-ore", (byte) 25);
		getChunk(chunkX + (Math.floorDiv(30, Chunk.chunkSize)), chunkY + (Math.floorDiv(30, Chunk.chunkSize))).tileEntities[30 % Chunk.chunkSize][30 % Chunk.chunkSize] = DEMOORE;
		chunks[chunkX][chunkY].tileEntityList.add(DEMOORE);




		
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

		generatedChunks = new Chunk[rightmostChunk-leftmostChunk][bottommostChunk-topmostChunk];
		for (int i=leftmostChunk; i<rightmostChunk; i++){
			for (int j=topmostChunk; j<bottommostChunk; j++){
				generatedChunks[i - leftmostChunk][j - topmostChunk] = getChunk(i,j);
			}
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			System.out.println("Map saved successfully to: " + filename);
			
			objectOut.close();
			fileOut.close();

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
			grid.chunks = new Chunk[gridSize][gridSize];


			for(int i = 0; i < grid.generatedChunks.length; i++){
				int j = 0;
				while(j < grid.generatedChunks[0].length){
					grid.setChunk(i + grid.leftmostChunk, j + grid.topmostChunk, grid.generatedChunks[i][j]);
					j++;
				}
			}
			System.out.println("Map successfully loaded from: " + filename);

			objectIn.close();
		} catch (IOException e){
			System.err.println("Error loading chunkGrid from file: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return grid;
	}

	public static byte[] createCoordinateBytes(int x, int y) {
        byte[] bytes = new byte[8]; // 4 bytes for x, 4 bytes for y
        writeIntToBytes(x, bytes, 0); // Write x to the first 4 bytes
        writeIntToBytes(y, bytes, 4); // Write y to the next 4 bytes
        return bytes;
    }

    // Method to read an integer from a byte array starting from the specified index
    public static int readIntFromBytes(byte[] bytes, int startIndex) {
        return ((bytes[startIndex] & 0xFF) << 24) |
                ((bytes[startIndex + 1] & 0xFF) << 16) |
                ((bytes[startIndex + 2] & 0xFF) << 8) |
                (bytes[startIndex + 3] & 0xFF);
    }

    // Method to write an integer to a byte array starting from the specified index
    public static void writeIntToBytes(int value, byte[] bytes, int startIndex) {
        bytes[startIndex] = (byte) (value >> 24);
        bytes[startIndex + 1] = (byte) (value >> 16);
        bytes[startIndex + 2] = (byte) (value >> 8);
        bytes[startIndex + 3] = (byte) value;
    }



	public void update(){
		for (byte[] coordinate : chunksWithTE){
			int x = readIntFromBytes(coordinate, 0);
			int y = readIntFromBytes(coordinate, 4);

			Chunk selectedChunk =	chunks[x][y];

			for (TileEntity TEinChunk : selectedChunk.tileEntityList){
				TEinChunk.doWork();
			}
		}
	}




}

