package src.main;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class CodeUtilities {
	
	public static String CapitalizeFirstLetters(String str){

		String[] words = str.split(" ");
		StringBuilder result = new StringBuilder();

		for (String word : words){
			if(!word.isEmpty()){
				result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
			}
		}

		str = result.toString().trim();


		return str;

	}

	public static BufferedImage loadImage(String filePath){
		
		String imageFolderPath = "C:\\Users\\monke\\Documents\\repos\\factormanImages\\";




		try{
			String imagePath = imageFolderPath + filePath;
			File imageFile = new File(imagePath);

			BufferedImage image = ImageIO.read(imageFile);

			return image;

		}catch(Exception e){
			// e.printStackTrace();
			return null;
		}

		
		

	}

}
