package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.item.Item;
import src.item.ItemProperties;
import src.tileEntity.EntityImage;

public class UI {
	
	Graphics2D g2;
	Font titilliumBold, titilliumRegular;
	GamePanel gp;
	BufferedImage guiElements;
	Color outerLayerBody, outerLayerEdge;
	Color middleLayerBody, middleLayerEdge;
	BufferedImage behindItem, hotbarButton;
	HashMap <String, BufferedImage> icons = new HashMap<String, BufferedImage>();;


	private hotbarUI hotbar = new hotbarUI();
	private inventoryUI SIUI = new inventoryUI();
	

	public UI (GamePanel gp){
		this.gp = gp;

		try{
			titilliumRegular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Regular.ttf"));
			titilliumBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Bold.ttf"));

			guiElements = ImageIO.read(getClass().getResourceAsStream("/res/core/gui/gui-new.png"));
			outerLayerBody = new Color(guiElements.getRGB(9, 9));
			outerLayerEdge = new Color(guiElements.getRGB(9, 1));

			middleLayerBody = new Color(guiElements.getRGB(77, 9));
			middleLayerEdge = new Color(guiElements.getRGB(77, 1));

			behindItem = guiElements.getSubimage(3, 427, 76, 76);
			hotbarButton = guiElements.getSubimage(3, 739, 76, 76);


		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public void draw(Graphics2D g2){
		this.g2 = g2;

		//Add for each new window
		hotbar.draw();
		SIUI.draw();


		g2.setFont(titilliumBold.deriveFont(32.0F));
	}


	public void resizeWindow(){
		//Add for each new window
		hotbar.resizeWindow();
		SIUI.resizeWindow();


	}

	public void drawBacking(int x, int y, int width, int height){




	}


	public class hotbarUI{
	

		private int topleftY;
		private int topleftX;
		final private int width = 732;
		final private int height = 96;

		public void resizeWindow(){
			this.topleftY = gp.screenHeight - 100;
			this.topleftX = gp.player.screenX - 300;
		} 

		public void draw(){
			

			g2.setColor(outerLayerEdge);
			g2.fillRect(topleftX - 2, topleftY - 2, width + 4, height + 4);
	
			g2.setColor(outerLayerBody);
			g2.fillRect(topleftX, topleftY, width, height);
	
			for(int i = 0; i< 11; i++){
				for(int j = 0; j < 2; j++){
					g2.drawImage(hotbarButton, 60 + topleftX + (40 * i), topleftY + 8 + (45* j), 40, 40, null);
					
					g2.drawImage(EntityImage.entityImages.get("assembling-machine-1").icon, 60 + topleftX + (40 * i) + 5, topleftY + 8 + (45 * j) + 5, 30, 30, null);
				}
			}

		}


	}

	public class inventoryUI{

		private int topleftX;
		private int topleftY;
		final private int largerWidth = 1320;
		final private int largerHeight = 500;
		final private int width = 424;
		final private int height = 448;


		public void resizeWindow(){
			this.topleftX = gp.screenWidth/ 5 - 150;
			this.topleftY = gp.screenHeight / 4;
		} 

		public void draw(){
			
			//Draw outer rectangle
			g2.setColor(outerLayerEdge);
			g2.fillRect(topleftX - 2, topleftY - 2, largerWidth + 4, largerHeight + 4);
	
			g2.setColor(outerLayerBody);
			g2.fillRect(topleftX, topleftY, largerWidth, largerHeight);

			//Draw inner rectangles
			g2.setColor(middleLayerBody);
			for (int i = 0; i < 3; i++){
				g2.fillRect(topleftX + 12 + ((width + 13) * i), topleftY + 41, width, height);
			}

			g2.setFont(titilliumBold.deriveFont(16.0F));
			

			for(int i = 0; i < gp.player.inventory.invSize; i++){
				g2.drawImage(behindItem, 26 + topleftX + (40 * (i % 10)), topleftY + 78 + (40* (i/10)), 40, 40, null);

			}


			int repeatedSlots;
			int slot = 0;

			for(int i = 0; i< gp.player.inventory.invSize; i++){

				Item slotItem = gp.player.inventory.invContents[slot]; 
				if (slotItem == null){
					continue;					
				}
				int stackSize = ItemProperties.itemPropertyMap.get(slotItem.itemID).stackSize;
					
				if (slotItem.quantity > stackSize){
					repeatedSlots = slotItem.quantity / stackSize;
					if((slotItem.quantity % stackSize) != 0){
						repeatedSlots++;
					}
				
					for (int j = 1; j < repeatedSlots; j++){
						g2.drawImage(EntityImage.entityImages.get(slotItem.itemID).icon, 26 + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 30, 30, null);
						g2.setColor(Color.white);
						g2.drawString(((Integer) (stackSize)).toString(), 26  + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + 30 + (40 * (i / 10)) + 5);
						i++;
					}
				}
				
				if (slotItem != null){
					g2.drawImage(EntityImage.entityImages.get(slotItem.itemID).icon, 26 + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 30, 30, null);
					g2.setColor(Color.white);
					g2.drawString(((Integer) ((slotItem.quantity - 1) % (stackSize) + 1)).toString(), 26  + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + 30 + (40 * (i / 10)) + 5);
				}	

				if(i > (80 - gp.player.inventory.remainingSlots)){
					g2.setColor(Color.white);
					// g2.fillRect( 26  + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 40, 40);
					// System.out.println(gp.player.inventory.remainingSlots);
					// System.out.println(gp.player.inventory.invContents[0].quantity);
				}


				slot++;
			}


		}


	}









	


	




	









}
