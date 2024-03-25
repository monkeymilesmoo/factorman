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
	static Color middleLayerBody, middleLayerEdge;
	BufferedImage behindItem, hotbarButton;
	HashMap <String, BufferedImage> icons = new HashMap<String, BufferedImage>();;
	static Color[][] outerLayer;
	BufferedImage closeWhite, closeBlack;
	String selectedUI;


	public hotbarUI hotbar = new hotbarUI();
	public inventoryUI SIUI = new inventoryUI();
	

	public UI (GamePanel gp){
		this.gp = gp;

		try{
			titilliumRegular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Regular.ttf"));
			titilliumBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Bold.ttf"));

			guiElements = ImageIO.read(getClass().getResourceAsStream("/res/core/gui/gui-new.png"));

			middleLayerBody = new Color(guiElements.getRGB(77, 9));
			middleLayerEdge = new Color(guiElements.getRGB(77, 1));

			behindItem = guiElements.getSubimage(3, 427, 76, 76);
			hotbarButton = guiElements.getSubimage(3, 739, 76, 76);

			closeWhite = ImageIO.read(getClass().getResourceAsStream("/res/core/gui/close-white.png"));
			closeBlack = ImageIO.read(getClass().getResourceAsStream("/res/core/gui/close-black.png"));


			//Outer edge for outermost windows
			outerLayer = new Color[17][17];

			for (int i = 0; i < 17; i++){
				outerLayer[i][8] = new Color(guiElements.getRGB(i, 9));
				outerLayer[8][i] = new Color(guiElements.getRGB(9, i));
			}








		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public void draw(Graphics2D g2){
		this.g2 = g2;

		//Add for each new window
		hotbar.draw();
		if(SIUI.visible){
			SIUI.draw();
		}

		g2.setFont(titilliumBold.deriveFont(32.0F));
	}


	public boolean checkMouseWindow(int mouseX, int mouseY){

		if(hotbar.checkMouseWindow(mouseX, mouseY)){
			selectedUI = "hotbar";
			return false;
		}
		if(SIUI.visible){
			if(SIUI.checkMouseWindow(mouseX, mouseY)){
				selectedUI = "playerInv";
				return false;
			}
		}

		return true;
	}


	public static void drawOuterEdge(int x, int y, int width, int height, Graphics2D g2){


		//draw edges around main body

		//Top edge
		for(int i = 0; i< 4; i++){
			g2.setColor(outerLayer[8][(2 * i)]);
			g2.fillRect(x - 4 + i, y - 4 + i, width + 8 - (2 * i), 1);
		}
		
		//Left edge
		for(int i = 0; i< 4; i++){
			g2.setColor(outerLayer[(2 * i)][8]);
			g2.fillRect(x - 4 + i, y - 4 + i, 1, height + 8 - (2 * i));
		}
		
		// //Right edge
		for(int i = 0; i< 4; i++){
			g2.setColor(outerLayer[15 - (2 * i)][8]);
			g2.fillRect(x + width + i, y - i, 1, height + (2 * i));
		}
		
		//Bottom edge
		for(int i = 0; i< 4; i++){
			g2.setColor(outerLayer[8][(2 * -i) + 15]);
			g2.fillRect(x - i, y + height + i, width + (2 * i) + 1, 1);
		}

		g2.setColor(outerLayer[8][8]);
		g2.fillRect(x, y, width, height);


	}












	public void resizeWindow(){
		//Add for each new window
		hotbar.resizeWindow();
		SIUI.resizeWindow();


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
			

			UI.drawOuterEdge(topleftX, topleftY, width, height, g2);
			
	
			for(int i = 0; i< 11; i++){
				for(int j = 0; j < 2; j++){
					g2.drawImage(hotbarButton, 60 + topleftX + (40 * i), topleftY + 8 + (45* j), 40, 40, null);
					
					g2.drawImage(EntityImage.entityImages.get("assembling-machine-1").icon, 60 + topleftX + (40 * i) + 5, topleftY + 8 + (45 * j) + 5, 30, 30, null);
				}
			}

		}

		public boolean checkMouseWindow(int mouseX, int mouseY){

			if(mouseX > topleftX && mouseX < topleftX + width && mouseY > topleftY && mouseY < topleftY + height){
				return true;
			}
			return false;
		}


	}

	public class inventoryUI{

		private int topleftX;
		private int topleftY;
		final private int largerWidth = 1320;
		final private int largerHeight = 500;
		final private int width = 424;
		final private int height = 448;
		public boolean visible = false;


		public void resizeWindow(){
			this.topleftX = gp.screenWidth/ 5 - 150;
			this.topleftY = gp.screenHeight / 4;
		} 

		public boolean checkMouseWindow(int mouseX, int mouseY){
			if(mouseX > topleftX && mouseX < topleftX + largerWidth && mouseY > topleftY && mouseY < topleftY + largerHeight){
				return true;
			}
			return false;
		}


		public void draw(){
			
			//Draw outer rectangle
			UI.drawOuterEdge(topleftX, topleftY, largerWidth, largerHeight, g2);

			//Draw close x top right
			UI.drawOuterEdge(topleftX + largerWidth - 36, topleftY + 7, 24, 24, g2);
			g2.drawImage(closeWhite, topleftX + largerWidth - 34, topleftY + 10, 20, 20, null);


			//Draw inner rectangles
			g2.setColor(middleLayerBody);
			for (int i = 0; i < 3; i++){
				g2.fillRect(topleftX + 11 + ((width + 13) * i), topleftY + 41, width, height);
			}

			g2.setFont(titilliumBold.deriveFont(18.0F));
			g2.setColor(Color.white);
			g2.drawString("Character", topleftX + 14, topleftY + 25);

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
