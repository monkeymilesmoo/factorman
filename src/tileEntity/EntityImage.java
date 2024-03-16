package src.tileEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityImage {
	
	public BufferedImage image; 
	public BufferedImage[][] imageArr;

	public EntityImage(String filePath, int frameWidth, int frameHeight){
		try {
			image = ImageIO.read(getClass().getResourceAsStream(("/res/tileEntity/" + filePath + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (frameWidth > 1 || frameHeight > 1){
			makeEntityImageArray(frameWidth, frameHeight);
		}


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
