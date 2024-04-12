package src.tileEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.item.ItemProperties;
import src.main.CodeUtilities;


public class EntityImage {
	
	public BufferedImage image, shadowImage, icon; 
	public BufferedImage[][] imageArr, tempImgArr, shadowArr;
	public int tileHeight, tileWidth;
	public int shiftX, shiftY;
	private int frameWidth, frameHeight;
	public int shadowOffsetRight, shadowOffsetDown;
	public String visibleName;
	public String shadowFileName;


	public transient static HashMap<String, EntityImage> entityImages = new HashMap<String, EntityImage>();

	public EntityImage(String fileName, boolean includesDashShadow, int frameWidth, int frameHeight, int shadowOffsetRight, int shadowOffsetDown, int tileWidth, int tileHeight, int shiftX, int shiftY){
		
		String itemType = ItemProperties.itemPropertyMap.get(fileName).itemType; 

		try{
			if(includesDashShadow){
				shadowFileName = fileName + "-shadow";
				shadowImage = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + shadowFileName + ".png")));

			}else{
				shadowImage = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + fileName + ".png")));
			}
			
			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + fileName + ".png")));

			if(image == null || shadowImage == null){
				System.out.println("missing image for " + fileName);
				return;
			}
			imageArr = makeImageArray(frameWidth, frameHeight, image, false);
			shadowArr = makeImageArray(frameWidth, frameHeight, shadowImage, true);

		}catch(Exception e){
			System.out.println("missing image for " + fileName);
		}


		visibleName = fileName.replace("-", " ");
		visibleName = CodeUtilities.CapitalizeFirstLetters(visibleName);
		

		try {
			icon = ImageIO.read(getClass().getResourceAsStream(("/res/icons/" + fileName + ".png")));
		} catch (IOException e) {
			System.out.println("missing icon for " + fileName);
			
		}
		icon = icon.getSubimage(0, 0, 64, 64);


		



		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.shadowOffsetRight = shadowOffsetRight;
		this.shadowOffsetDown =shadowOffsetDown;

		if(shadowArr != null && imageArr != null){
			if(!includesDashShadow){
				shiftX += shadowOffsetRight;
				shiftY += shadowOffsetDown;
				shadowArr = imageArr;

			}else{
				shadowArr = bakeInShadows();
			}
		}
	}

	private BufferedImage[][] makeImageArray(int frameWidth, int frameHeight, BufferedImage inputImg, boolean transparent){

		int imgWidth = inputImg.getWidth();
		int imgHeight = inputImg.getHeight();
		int width = imgWidth/frameWidth;
		int height = imgHeight/frameHeight;

		tempImgArr = new BufferedImage[frameWidth][frameHeight];

		if(transparent){
			inputImg = makeTransparent(inputImg);
		}

		for(int i = 0; i < frameWidth; i++){
			for(int j = 0; j < frameHeight; j++){
				
				tempImgArr[i][j] = inputImg.getSubimage(i * width, j * height, width, height);
			}
		}

		return tempImgArr;


	}

	private BufferedImage makeTransparent(BufferedImage image){
		int width = image.getWidth();
        int height = image.getHeight();

		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                // Extract alpha value from the original image
                int alpha = (argb >> 24) & 0xFF;

                // Modify alpha value (e.g., set to 50% transparency)
                alpha = alpha / 3; // For 50% transparency

                // Set modified alpha value back to the pixel
                argb = (alpha << 24) | (argb & 0x00FFFFFF); // Reconstruct ARGB value

                // Set pixel in transparent image
                image.setRGB(x, y, argb);
            }
        }
		return image;
	}

	private BufferedImage[][] bakeInShadows(){

		BufferedImage[][] arr = new BufferedImage[frameWidth][frameHeight];

		
		for(int i = 0; i < frameWidth; i++){
			for(int j = 0; j < frameHeight; j++){

				int width = shadowArr[i][j].getWidth();
				int height =shadowArr[i][j].getHeight();
				
				BufferedImage newImage = new BufferedImage(width + shadowOffsetRight + shiftX,  height + shadowOffsetDown + shiftY, BufferedImage.TYPE_INT_ARGB);
				
				newImage.createGraphics().drawImage(shadowArr[i][j], shiftX, shiftY, width + shadowOffsetRight, height + shadowOffsetDown, null);
				newImage.createGraphics().drawImage(imageArr[i][j],  0, 0, width + shiftX, height + shiftY, null);
				
				arr[i][j] = newImage;
				
			}
		}

		return arr;
	}

	public static void loadEntityImages(){

		// entityImages = new EntityImage[3];



		try{
			
			
			putImage("assembling-machine-1", true, 32, 1, 0, 0, 3, 3, 12, 18);
			putImage("stone-furnace", false, 1, 1, 50, 0, 2, 2, 0, 0);
			putImage("iron-ore", false, 8, 8, 0, 0, 1, 1, 0, 0);

			



			//TODO for now, just the icons are being used, but eventually fill these out for each building
			//TODO for now each dashshadow is false, change back if needed

			//Containers
			putImage("wooden-chest", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("iron-chest", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("steel-chest", true, 1, 1, 50, 0, 1, 1, 0, 10);
			putImage("storage-tank", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Belts
			//TODO NEED TO ADD EXTRA IMAGES FOR FRONT AND BACK STRUCTURES
			putImage("transport-belt", false, 1, 1, 0, 0, 0, 0, 0, 0); 
			putImage("fast-transport-belt", false, 1, 1, 0, 0, 0, 0, 0, 0); 
			putImage("express-transport-belt", false, 1, 1, 0, 0, 0, 0, 0, 0); 
			//Underground belts
			//TODO NEED TO ADD EXTRA IMAGES FOR FRONT AND BACK STRUCTURES
			putImage("underground-belt", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-underground-belt", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("express-underground-belt", false, 1, 1, 0, 0, 0, 0, 0, 0);
			//Splitters
			//TODO Somehow do splitters???? they have different images for updownleftright so maybe run them through the spritesheet manipulator
			putImage("splitter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-splitter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("express-splitter", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Inserters
			//TODO each part of an inserter should be put into a spritesheet
			putImage("burner-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("long-handed-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("filter-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("stack-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("stack-filter-inserter", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Power poles
			putImage("small-electric-pole", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("medium-electric-pole", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("big-electric-pole", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("substation", false, 1, 1, 0, 0, 0, 0, 0, 0);
			//Pipes
			putImage("pipe", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("pipe-to-ground", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("pump", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Train items
			putImage("rail", false, 1, 1, 0, 0, 0, 0, 0, 0);
			//TODO i skipped rails for now
			putImage("train-stop", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("rail-signal", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("rail-chain-signal", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("locomotive", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("cargo-wagon", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fluid-wagon", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("artillery-wagon", false, 1, 1, 0, 0, 0, 0, 0, 0);
			
			//Transport
			putImage("car", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("tank", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("spidertron", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("spidertron-remote", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Logistics
			putImage("logistic-robot", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("construction-robot", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-active-provider", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-passive-provider", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-storage", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-buffer", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-requester", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("roboport", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Circuit network
			putImage("small-lamp", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("red-wire", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("green-wire", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("arithmetic-combinator", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("decider-combinator", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("constant-combinator", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("power-switch", false, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("programmable-speaker", false, 1, 1, 0, 0, 0, 0, 0, 0);

			//Terrain isn't part of entityimages









		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void putImage(String fileName, boolean includesDashShadow, int frameWidth, int frameHeight, int shadowOffsetRight, int shadowOffsetDown, int tileWidth, int tileHeight, int shiftX, int shiftY){
		if(fileName != ""){
			entityImages.put(fileName, new EntityImage(fileName, includesDashShadow, frameWidth, frameHeight, shadowOffsetRight, shadowOffsetDown, tileWidth, tileHeight, shiftX, shiftY));
		}
	}

}
