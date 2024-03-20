package src.tileEntity;

import java.util.HashMap;

public class Building extends TileEntity{


	// public transient static EntityImage[] buildingImages;
	public transient static HashMap<String, EntityImage> buildingImages = new HashMap<String, EntityImage>();

	// public transient static HashMap<String, EntityImage> buildingImages = new HashMap<String, EntityImage>();

	byte rotation = 2;


	public Building(int x, int y, byte tileWidth, byte tileHeight, String tileEntityID){

		super(x, y, tileWidth, tileHeight, tileEntityID);

	}

	public static void loadBuildingImages(){

		// buildingImages = new EntityImage[3];



		//THISLL PROBABLY NEED TO BE REVISED
		try{
			
			
			// buildingImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1));
			buildingImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1, 3, 3));
			buildingImages.put("assembling-machine-1-shadow", new EntityImage("building/assembling-machine-1/assembling-machine-1-shadow", 32, 1, 3, 3));
			buildingImages.put("steel-chest", new EntityImage("building/steel-chest/steel-chest", 1, 1, 1, 1));

			// buildingImages[0] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/assembling-machine-1/assembling-machine-1.png"));
			// buildingImages[1] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/steel-chest/steel-chest.png"));
			








		}catch(Exception e){
			e.printStackTrace();
		}

	}

	// public void buildingImageToSubArray(BufferedImage inputImage, int ) {



	// }
}
