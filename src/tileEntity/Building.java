package src.tileEntity;


public class Building extends TileEntity{


	// public transient static EntityImage[] entityImages;

	// public transient static HashMap<String, EntityImage> entityImages = new HashMap<String, EntityImage>();

	byte rotation = 2;
	
	boolean ableToWork = false;


	public Building(int x, int y, int chunkX, int chunkY, int tileWidth, int tileHeight, String tileEntityID){

		super(x, y, chunkX, chunkY, tileWidth, tileHeight, tileEntityID);

	}

	

	// public void buildingImageToSubArray(BufferedImage inputImage, int ) {



	// }
	
	@Override 
	public void doWork(){
		if(ableToWork == false){
			return;
		}
		System.out.println("tertuerte");
	}


}

