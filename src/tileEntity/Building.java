package src.tileEntity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Building extends TileEntity{

	public transient static BufferedImage[] buildingImages;


	public Building(){

	}

	public void loadBuildingImages(){


		//THISLL PROBABLY NEED TO BE REVISED
		try{
			
			
			buildingImages[0] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/assembling-machine-1/assembling-machine-1.png"));
			buildingImages[1] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/steel-chest/steel-chest.png"));
			








		}catch(Exception e){
			e.printStackTrace();
		}

	}

	// public void buildingImageToSubArray(BufferedImage inputImage, int ) {



	// }
}
