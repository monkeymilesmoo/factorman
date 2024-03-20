package src.tileEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityImage {
	
	public BufferedImage image, icon; 
	public BufferedImage[][] imageArr;
	public int tileHeight, tileWidth;
	public int shiftX, shiftY;

	public EntityImage(String filePath, int frameWidth, int frameHeight, int tileWidth, int tileHeight, int shiftX, int shiftY, String hasIcon){
		try {
			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + filePath + ".png")));
			if(hasIcon != null){
				icon = ImageIO.read(getClass().getResourceAsStream(("/res/icons/" + hasIcon + ".png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		makeEntityImageArray(frameWidth, frameHeight);

		icon = icon.getSubimage(0, 0, 64, 64);

		this.shiftX = shiftX;
		this.shiftY = shiftY;
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
}
