package src.tileEntity;


public class Building extends TileEntity{


	public transient static EntityImage[] buildingImages;
	// public transient static HashMap<String, EntityImage> buildingImages = new HashMap<String, EntityImage>();

	byte rotation = 2;


	public Building(){

	}

	public static void loadBuildingImages(){

		buildingImages = new EntityImage[2];



		//THISLL PROBABLY NEED TO BE REVISED
		try{
			
			
			// buildingImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1));
			buildingImages[0] = new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1, 3);
			buildingImages[1] = new EntityImage("building/assembling-machine-1/assembling-machine-1-shadow", 32, 1, 3);


			// buildingImages[0] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/assembling-machine-1/assembling-machine-1.png"));
			// buildingImages[1] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/steel-chest/steel-chest.png"));
			








		}catch(Exception e){
			e.printStackTrace();
		}

	}

	// public void buildingImageToSubArray(BufferedImage inputImage, int ) {



	// }
}
