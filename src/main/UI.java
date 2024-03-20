package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.tileEntity.EntityImage;

public class UI {
	
	Graphics2D g2;
	Font titilliumBold, titilliumRegular;
	GamePanel gp;
	BufferedImage guiElements;
	Color outerLayerBody, outerLayerEdge;
	int hotbarX, hotbarY;
	BufferedImage behindItem, hotbarButton;
	HashMap <String, BufferedImage> icons = new HashMap<String, BufferedImage>();;


	public UI (GamePanel gp){
		this.gp = gp;

		try{
			titilliumRegular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Regular.ttf"));
			titilliumBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Bold.ttf"));

			guiElements = ImageIO.read(getClass().getResourceAsStream("/res/core/gui/gui-new.png"));
			outerLayerBody = new Color(guiElements.getRGB(9, 9));
			outerLayerEdge = new Color(guiElements.getRGB(9, 1));

			behindItem = guiElements.getSubimage(3, 427, 76, 76);
			hotbarButton = guiElements.getSubimage(3, 739, 76, 76);






		}catch(Exception e){
			e.printStackTrace();
		}

		// createUIElement(gp.player.screenX - 300, gp.screenHeight - 96, 732, 96, "hotbar");
	}

	// public void createUIElement(int x, int y, int width, int height, String ID){

		

	// }


	public void drawInventory(){
		
	}


	public void drawHotbar(){

		hotbarY = gp.screenHeight - 100;
		hotbarX = gp.player.screenX - 300;

		g2.setColor(outerLayerEdge);
		g2.fillRect(hotbarX - 2, hotbarY - 2, 736, 100);

		g2.setColor(outerLayerBody);
		g2.fillRect(hotbarX, hotbarY, 732, 96);

		for(int i = 0; i< 11; i++){
			for(int j = 0; j < 2; j++){
				g2.drawImage(hotbarButton, 60 + hotbarX + (40 * i), hotbarY + 8 + (45* j), 40, 40, null);
				
				g2.drawImage(EntityImage.entityImages.get("assembling-machine-1").icon, 60 + hotbarX + (40 * i) + 5, hotbarY + 8 + (45 * j) + 5, 30, 30, null);
			}
		}

		//For now, nothing to the left or right of hotbar items is necessary

	}




	public void draw(Graphics2D g2){
		this.g2 = g2;


		drawHotbar();
		


		g2.setFont(titilliumBold.deriveFont(32.0F));
	}









}
