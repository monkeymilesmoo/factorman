package src.tileEntity;


public class Building extends TileEntity{


	// public transient static EntityImage[] entityImages;

	// public transient static HashMap<String, EntityImage> entityImages = new HashMap<String, EntityImage>();

	byte rotation = 2;


	public Building(int x, int y, int chunkX, int chunkY, byte tileWidth, byte tileHeight, String tileEntityID){

		super(x, y, chunkX, chunkY, tileWidth, tileHeight, tileEntityID);

	}

	

	// public void buildingImageToSubArray(BufferedImage inputImage, int ) {



	// }
}
