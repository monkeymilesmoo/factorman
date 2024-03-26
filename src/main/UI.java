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
	BufferedImage[] behindItem, hotbarButton;
	HashMap <String, BufferedImage> icons = new HashMap<String, BufferedImage>();;
	static Color[][] outerLayer;
	static BufferedImage closeWhite;
	BufferedImage closeBlack;
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



			//0 = idle; 1 = hovered; 2 = selected;
			behindItem = new BufferedImage[3];
			hotbarButton = new BufferedImage[3];
			behindItem[0] = guiElements.getSubimage(3, 427, 76, 76);
			behindItem[1] = guiElements.getSubimage(84, 427, 76, 76);
			behindItem[2] = guiElements.getSubimage(165, 427, 76, 76);
			hotbarButton[0] = guiElements.getSubimage(3, 739, 76, 76);
			hotbarButton[1] = guiElements.getSubimage(84, 739, 76, 76);
			hotbarButton[2] = guiElements.getSubimage(165, 739, 76, 76);

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

	public static void drawNumber(int x, int y, int number, Graphics2D g2){

		if(number < 1000){
			g2.drawString(number + "", x, y);
		}else if(number < 100000){
			g2.drawString((number / 1000) + "." + (number % 1000) / 100 + "k", x, y);
		}
		//I dont think I need million. Maybe?
	}

	public static void drawWindowTitle(int x, int y, String string, Graphics2D g2){
		g2.drawString(string, x + 14, y + 25);
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


	public void hoveredSlotCheck(int mouseX, int mouseY, boolean clicking){
		// if (selectedUI == "hotbar"){
		// 	hotbar.hoveredSlotCheck(mouseX, mouseY, clicking);
		// 	return;
		// }
		if (selectedUI == "playerInv"){
			SIUI.hoveredSlotCheck(mouseX, mouseY, clicking);
		}
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

		selectedUI = null;
		return true;
	}


	public static void drawOuterEdge(int x, int y, int width, int height, Graphics2D g2, boolean drawingCloseButton){


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


		//Draw close x top right
		if (drawingCloseButton){
			UI.drawOuterEdge(x + width - 36, y + 7, 24, 24, g2, false);
			g2.drawImage(closeWhite, x + width - 34, y + 10, 20, 20, null);
		}

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
			

			UI.drawOuterEdge(topleftX, topleftY, width, height, g2, false);
			
	
			for(int i = 0; i< 11; i++){
				for(int j = 0; j < 2; j++){
					g2.drawImage(hotbarButton[0], 60 + topleftX + (40 * i), topleftY + 8 + (45* j), 40, 40, null);
					
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


		//Make sure to copy most of this stuff to new uis
		private int topleftX;
		private int topleftY;
		final private int largerWidth = 1320;
		final private int largerHeight = 500;
		final private int width = 424;
		final private int height = 448;
		final private int startSlotsX = 26;
		final private int startSlotsY = 78;
		final private int endSlotsX = 398;
		final private int endSlotsY = 358;
		final private int slotNumWidth = 10;
		final private int slotNumHeight = 9;
		final private int[] slotSelection =  new int[91];
		private int lastHovered = 90;
		private int selectedSlot = 0;
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

		public void hoveredSlotCheck(int mouseX, int mouseY, boolean clicking){
			mouseX = mouseX - topleftX - startSlotsX;
			mouseY = mouseY - topleftY - startSlotsY;
			if (mouseX < 0 || mouseY < 0 || mouseX > endSlotsX || mouseY > endSlotsY){
				if(slotSelection[lastHovered] != 2){
					slotSelection[lastHovered] = 0;
				}
				lastHovered = 90;
				return;
			}

			int hoveredSlot = (mouseX / 40) + (10 * (mouseY / 40));
			
			if(clicking){
				// System.out.println("whaa");
				slotSelection[hoveredSlot] = 2;
				slotSelection[selectedSlot] = 0;
				lastHovered = 90;
				selectedSlot = hoveredSlot;
			}else if(slotSelection[hoveredSlot] != 2 && hoveredSlot != lastHovered){
				// System.out.println("hhh");
				slotSelection[hoveredSlot] = 1;
				if(lastHovered != hoveredSlot){
					slotSelection[lastHovered] = 0;
					lastHovered = hoveredSlot;
				}
			}
		}


		public void draw(){
			
			//Draw outer rectangle
			UI.drawOuterEdge(topleftX, topleftY, largerWidth, largerHeight, g2, true);



			//Draw inner rectangles
			g2.setColor(middleLayerBody);
			for (int i = 0; i < 3; i++){
				g2.fillRect(topleftX + 11 + ((width + 13) * i), topleftY + 41, width, height);
			}

			g2.setFont(titilliumBold.deriveFont(18.0F));
			g2.setColor(Color.white);
			UI.drawWindowTitle(topleftX, topleftY, "Character", g2);

			g2.setFont(titilliumBold.deriveFont(16.0F));
			

			for(int i = 0; i < gp.player.inventory.invSize; i++){
				g2.drawImage(behindItem[slotSelection[i]], startSlotsX + topleftX + (40 * (i % 10)), topleftY + startSlotsY + (40* (i/10)), 40, 40, null);

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
						UI.drawNumber(31  + topleftX + (40 * (i % 10)), topleftY + 113 + (40 * (i / 10)), stackSize, g2);
						i++;
					}
				}
				
				if (slotItem != null){
					g2.drawImage(EntityImage.entityImages.get(slotItem.itemID).icon, 26 + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 30, 30, null);
					g2.setColor(Color.white);
					UI.drawNumber(31  + topleftX + (40 * (i % 10)), topleftY + 78 + 30 + (40 * (i / 10)) + 5, (slotItem.quantity - 1) % (stackSize) + 1, g2);
				}	

				if(i > (80 - gp.player.inventory.remainingSlots)){
					g2.setColor(Color.white);
					
				}


				slot++;
			}


		}


	}









	


	




	









}
