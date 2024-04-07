package src.tileEntity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.item.ItemProperties;


public class EntityImage {
	
	public BufferedImage image, shadowImage, icon; 
	public BufferedImage[][] imageArr, tempImgArr, shadowArr;
	public int tileHeight, tileWidth;
	public int shiftX, shiftY;
	private int frameWidth, frameHeight;
	public int shadowOffsetRight, shadowOffsetDown;


	public transient static HashMap<String, EntityImage> entityImages = new HashMap<String, EntityImage>();

	public EntityImage(String fileName, String shadowFileName, int frameWidth, int frameHeight, int shadowWidth, int shadowHeight, int shadowOffsetRight, int shadowOffsetDown, int tileWidth, int tileHeight, int shiftX, int shiftY){
		
		try {
			
			String itemType = ItemProperties.itemPropertyMap.get(fileName).itemType; 

			if(shadowFileName == ""){
				shadowFileName = fileName;
			}

			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + fileName + ".png")));
			shadowImage = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + itemType + "/" + fileName + "/" + shadowFileName + ".png")));

			

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

		shadowArr = bakeInShadows();
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
				int width = imageArr[i][j].getWidth();
				int height =imageArr[i][j].getHeight();
				
				BufferedImage newImage = new BufferedImage(width + shadowOffsetRight + shiftX,  height + shadowOffsetDown + shiftY, BufferedImage.TYPE_INT_ARGB);
				
				newImage.createGraphics().drawImage(imageArr[i][j],  0, 0, null);
				newImage.createGraphics().drawImage(shadowArr[i][j], shadowOffsetRight + shiftX, shadowOffsetDown + shiftY, null);
				
				arr[i][j] = newImage;
				System.out.println(width);
				
			}
		}

		return arr;
	}

	public static void loadEntityImages(){

		// entityImages = new EntityImage[3];



		try{
			
			
			//LEAVE SHADOW BLANK IF IT IS INCLUDED IN IMAGE ALREADY
			entityImages.put("assembling-machine-1", new EntityImage("assembling-machine-1", "assembling-machine-1-shadow", 32, 1, 0, 0, 15, 0, 3, 3, 12, 18));

			
			
			// entityImages.put("assembling-machine-1", new EntityImage("building/assembling-machine-1/assembling-machine-1", 32, 1, 3, 3, 12, 18, 0, 0, "assembling-machine-1"));
			// entityImages.put("assembling-machine-1-shadow", new EntityImage("building/assembling-machine-1/assembling-machine-1-shadow", 32, 1, 3, 3, 4, 13, 0, 0, null));
			// entityImages.put("steel-chest", new EntityImage("building/steel-chest/steel-chest", 1, 1, 1, 1, 0, 10, 0, 0, "steel-chest"));
			// entityImages.put("steel-chest-shadow", new EntityImage("building/steel-chest/steel-chest-shadow", 1, 1, 1, 1, 0, 10, 0, 0, null));
			// entityImages.put("iron-ore", new EntityImage("Ore/iron-ore", 8, 8, 1, 1, 0, 0, 0, 0, "iron-ore"));
			// entityImages.put("stone-furnace", new EntityImage("building/stone-furnace/stone-furnace", 1, 1, 2, 3, -10, -30, 55, 20, "stone-furnace"));

			








		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
