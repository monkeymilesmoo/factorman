package src.tileEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.entity.entity;

public class EntityImage {
	
	public BufferedImage image, icon; 
	public BufferedImage[][] imageArr;
	public int tileHeight, tileWidth;
	public int shiftX, shiftY;
	public int stretchX, stretchY;


	public transient static HashMap<String, EntityImage> entityImages = new HashMap<String, EntityImage>();

	public EntityImage(String filePath, int frameWidth, int frameHeight, int tileWidth, int tileHeight, int shiftX, int shiftY, int stretchX, int stretchY, String hasIcon){
		try {
			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + filePath + ".png")));
			if(hasIcon != null){
				icon = ImageIO.read(getClass().getResourceAsStream(("/res/icons/" + hasIcon + ".png")));
				icon = icon.getSubimage(0, 0, 64, 64);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		makeEntityImageArray(frameWidth, frameHeight);


		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.stretchX = stretchX;
		this.stretchY = stretchY;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;

	}

	private void makeEntityImageArray(int frameWidth, int frameHeight){

		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		int width = imgWidth/frameWidth;
		int height = imgHeight/frameHeight;

		imageArr = new BufferedImage[frameWidth][frameHeight];

		for(int i = 0; i < frameWidth; i++){
			for(int j = 0; j < frameHeight; j++){
				imageArr[i][j] = image.getSubimage(i * width, j * height, width, height);
			}
		}



	}

	public static void loadEntityImages(){

		// entityImages = new EntityImage[3];



		//THISLL PROBABLY NEED TO BE REVISED
		try{
			
			
			// entityImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1));
			entityImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1, 3, 3, 12, 18, 0, 0, "assembling-machine-1"));
			entityImages.put("assembling-machine-1-shadow", new EntityImage("building/assembling-machine-1/assembling-machine-1-shadow", 32, 1, 3, 3, 4, 13, 0, 0, null));
			entityImages.put("steel-chest", new EntityImage("building/steel-chest/steel-chest", 1, 1, 1, 1, 0, 10, 0, 0, "steel-chest"));
			entityImages.put("steel-chest-shadow", new EntityImage("building/steel-chest/steel-chest-shadow", 1, 1, 1, 1, 0, 10, 0, 0, null));
			entityImages.put("iron-ore", new EntityImage("Ore/iron-ore", 8, 8, 1, 1, 0, 0, 0, 0, "iron-ore"));
			entityImages.put("stone-furnace", new EntityImage("building/stone-furnace/stone-furnace", 1, 1, 2, 3, -5, 0, 55, -5, "stone-furnace"));

			// entityImages[0] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/assembling-machine-1/assembling-machine-1.png"));
			// entityImages[1] = ImageIO.read(getClass().getResourceAsStream("/res/tileEntity/steel-chest/steel-chest.png"));
			








		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
