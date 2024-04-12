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
		
		try {
			String itemType = ItemProperties.itemPropertyMap.get(fileName).itemType; 

			if(includesDashShadow){
				shadowFileName = fileName + "-shadow";
				shadowImage = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + shadowFileName + ".png")));

			}else{
				shadowImage = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + fileName + ".png")));
			}

			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + fileName + ".png")));

			visibleName = fileName.replace("-", " ");
			visibleName = CodeUtilities.CapitalizeFirstLetters(visibleName);
			

			icon = ImageIO.read(getClass().getResourceAsStream(("/res/icons/" + fileName + ".png")));
			icon = icon.getSubimage(0, 0, 64, 64);


		} catch (IOException e) {
			e.printStackTrace();
		}


		imageArr = makeImageArray(frameWidth, frameHeight, image, false);
		shadowArr = makeImageArray(frameWidth, frameHeight, shadowImage, true);


		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.shadowOffsetRight = shadowOffsetRight;
		this.shadowOffsetDown =shadowOffsetDown;

		if(!includesDashShadow){
			shiftX += shadowOffsetRight;
			shiftY += shadowOffsetDown;
			shadowArr = imageArr;

		}else{
			shadowArr = bakeInShadows();
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

			//Containers
			putImage("wooden-chest", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("iron-chest", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("steel-chest", true, 1, 1, 50, 0, 1, 1, 0, 10);
			putImage("storage-tank", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Belts
			putImage("transport-belt", true, 1, 1, 0, 0, 0, 0, 0, 0); 
			putImage("fast-transport-belt", true, 1, 1, 0, 0, 0, 0, 0, 0); 
			putImage("express-transport-belt", true, 1, 1, 0, 0, 0, 0, 0, 0); 
			//Underground belts
			putImage("underground-belt", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-underground-belt", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("express-underground-belt", true, 1, 1, 0, 0, 0, 0, 0, 0);
			//Splitters
			putImage("splitter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-splitter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("express-splitter", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Inserters
			putImage("burner-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("long-handed-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fast-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("filter-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("stack-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("stack-filter-inserter", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Power poles
			putImage("small-electric-pole", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("medium-electric-pole", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("big-electric-pole", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("substation", true, 1, 1, 0, 0, 0, 0, 0, 0);
			//Pipes
			putImage("pipe", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("pipe-to-ground", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("pump", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Train items
			putImage("rail", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("train-stop", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("rail-signal", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("rail-chain-signal", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("locomotive", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("cargo-wagon", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("fluid-wagon", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("artillery-wagon", true, 1, 1, 0, 0, 0, 0, 0, 0);
			
			//Transport
			putImage("car", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("tank", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("spidertron", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("spidertron-remote", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Logistics
			putImage("logistic-robot", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("construction-robot", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-active-provider", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-passive-provider", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-storage", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-buffer", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("logistic-chest-requester", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("robotport", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Circuit network
			putImage("small-lamp", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("red-wire", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("green-wire", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("arithmetic-combinator", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("decider-combinator", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("constant-combinator", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("power-switch", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("programmable-speaker", true, 1, 1, 0, 0, 0, 0, 0, 0);

			//Terrain
			putImage("stone-brick", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("concrete", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("hazard-concrete", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("refined-concrete", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("refined-hazard-concrete", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("landfill", true, 1, 1, 0, 0, 0, 0, 0, 0);
			putImage("cliff-explosives", true, 1, 1, 0, 0, 0, 0, 0, 0);









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
