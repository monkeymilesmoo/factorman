package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import src.item.Item;
import src.item.ItemGroups;
import src.item.Recipe;
import src.tileEntity.EntityImage;
import src.tileEntity.TileEntity;

public class UI {
	
	Graphics2D g2;
	static Font titilliumBold;
	Font titilliumRegular;
	GamePanel gp;
	BufferedImage guiElements;
	// static Color middleLayerBody, middleLayerEdge;
	BufferedImage[] behindItem, hotbarButton;
	HashMap <String, BufferedImage> icons = new HashMap<String, BufferedImage>();;
	static Color[][] outerLayer, middleLayer;
	static BufferedImage closeWhite, closeBlack;
	static BufferedImage inHandIcon;
	static BufferedImage[] bigButtons;
	static Color[] bigButtonColor;
	String selectedUI;
	ArrayList<disappearingText> disappearingTextList = new ArrayList<disappearingText>();
	public boolean openVisibility = false;
	public String openUI = "";
	public TileEntity openTE;


	public hotbarUI hotbar = new hotbarUI();
	public playerInventoryUI SIUI = new playerInventoryUI();
	public buildingUI BUI = new buildingUI();
	

	public UI (GamePanel gp){
		this.gp = gp;

		try{
			titilliumRegular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Regular.ttf"));
			titilliumBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Bold.ttf"));

			guiElements = CodeUtilities.loadImage("core/gui/gui-new");

			// middleLayerBody = new Color(guiElements.getRGB(77, 9));
			// middleLayerEdge = new Color(guiElements.getRGB(77, 1));



			//0 = idle; 1 = hovered; 2 = selected;
			behindItem = new BufferedImage[3];
			hotbarButton = new BufferedImage[3];
			behindItem[0] = guiElements.getSubimage(3, 427, 76, 76);
			behindItem[1] = guiElements.getSubimage(84, 427, 76, 76);
			behindItem[2] = guiElements.getSubimage(165, 427, 76, 76);
			hotbarButton[0] = guiElements.getSubimage(3, 739, 76, 76);
			hotbarButton[1] = guiElements.getSubimage(84, 739, 76, 76);
			hotbarButton[2] = guiElements.getSubimage(165, 739, 76, 76);

			closeWhite = CodeUtilities.loadImage("core/gui/close-white");
			closeBlack = CodeUtilities.loadImage("core/gui/close-black");
			
			


			//Outer edge for outermost windows
			outerLayer = new Color[17][17];

			for (int i = 0; i < 17; i++){
				outerLayer[i][8] = new Color(guiElements.getRGB(i, 9));
				outerLayer[8][i] = new Color(guiElements.getRGB(9, i));
			}

			//Middle layer edge for outermost windows
			middleLayer = new Color[17][17];

			for (int i = 0; i < 17; i++){
				middleLayer[i][8] = new Color(guiElements.getRGB(i + 69, 9));
				middleLayer[8][i] = new Color(guiElements.getRGB(77, i));
			}

			//Load recipeMenu header buttons
			bigButtons = new BufferedImage[4];

			bigButtons[0] = CodeUtilities.loadImage("item-group/logistics").getSubimage(0, 0, 128, 128);
			bigButtons[1] = CodeUtilities.loadImage("item-group/production").getSubimage(0, 0, 128, 128);
			bigButtons[2] = CodeUtilities.loadImage("item-group/intermediate-products").getSubimage(0, 0, 128, 128);
			bigButtons[3] = CodeUtilities.loadImage("item-group/military").getSubimage(0, 0, 128, 128);
			
			bigButtonColor = new Color[3];

			bigButtonColor[0] = new Color(guiElements.getRGB(9, 26));
			bigButtonColor[1] = new Color(guiElements.getRGB(84, 9));
			bigButtonColor[2] = new Color(guiElements.getRGB(77, 9));


			inHandIcon = CodeUtilities.loadImage("core/gui/slot-item-in-hand-black");
			







		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void drawNumber(int x, int y, int number, Graphics2D g2){

		
		g2.setFont(titilliumBold.deriveFont(16.0F));
		g2.setColor(Color.white);
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

		if(openVisibility){
			if(openUI == "playerInv"){
				SIUI.draw();
			}
			if(openUI == "buildingUI"){
				BUI.draw();
			}
		}

		if(SIUI.inv.selectedSlot != SIUI.inv.slotCount){
			if(gp.player.inventory.invContents[SIUI.inv.selectedSlot] != null){
				g2.drawImage(EntityImage.entityImages.get(gp.player.inventory.invContents[SIUI.inv.selectedSlot].itemID).icon, gp.mouseH.mouseX + 10, gp.mouseH.mouseY + 10, 30, 30, null);
				UI.drawNumber(gp.mouseH.mouseX + 10, gp.mouseH.mouseY + 40, gp.player.inventory.invContents[SIUI.inv.selectedSlot].quantity, g2);
			}
			
		}else if(hotbar.selectedSlot != hotbar.slotCount){
			if(gp.player.hotbar[hotbar.selectedSlot] != null){
				g2.drawImage(EntityImage.entityImages.get(gp.player.hotbar[hotbar.selectedSlot].itemID).icon, gp.mouseH.mouseX + 10, gp.mouseH.mouseY + 10, 30, 30, null);
				UI.drawNumber(gp.mouseH.mouseX + 10, gp.mouseH.mouseY + 40, gp.player.hotbar[hotbar.selectedSlot].quantity, g2);
			}
		}

		Iterator<disappearingText> iterator = disappearingTextList.iterator();

		while(iterator.hasNext()){
			disappearingText nextText = iterator.next();
			nextText.updateText(iterator);
		}



		
		


		g2.setFont(titilliumBold.deriveFont(32.0F));
	}


	public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
		if (selectedUI == "hotbar"){
			//Check slots first
			if (mouseX - hotbar.topleftX - hotbar.startSlotsX > 0 && mouseY - hotbar.topleftY - hotbar.startSlotsY > 0 && mouseX - hotbar.topleftX - hotbar.startSlotsX < hotbar.endSlotsX && mouseY - hotbar.topleftY - hotbar.startSlotsY < hotbar.endSlotsY){
				hotbar.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
				return;
			}else{
				if(hotbar.slotSelection[hotbar.lastHovered] != 2){
					hotbar.slotSelection[hotbar.lastHovered] = 0;
				}
				hotbar.lastHovered = hotbar.slotCount;
			}
			
			
			return;
		}
		if (selectedUI == "playerInv"){
			//Check slots first
			if (mouseX - SIUI.topleftX - SIUI.inv.startSlotsX > 0 && mouseY - SIUI.topleftY - SIUI.inv.startSlotsY > 0 && mouseX - SIUI.topleftX - SIUI.inv.startSlotsX < SIUI.endSlotsX && mouseY - SIUI.topleftY - SIUI.inv.startSlotsY < SIUI.endSlotsY){
				SIUI.inv.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
			}else{
				if(SIUI.inv.slotSelection[SIUI.inv.lastHovered] != 2){
					SIUI.inv.slotSelection[SIUI.inv.lastHovered] = 0;
				}
				SIUI.inv.lastHovered = SIUI.inv.slotCount;
			}

			if (mouseX - SIUI.topleftX - SIUI.rMenu.windowNum - SIUI.rMenu.startSlotsX > 0 && mouseY - SIUI.topleftY - SIUI.rMenu.startSlotsY > 0 && mouseX - SIUI.topleftX - SIUI.rMenu.windowNum - SIUI.rMenu.startSlotsX < SIUI.endSlotsX && mouseY - SIUI.topleftY - SIUI.rMenu.startSlotsY < SIUI.endSlotsY){
				SIUI.rMenu.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
				return;
			}else{
				if(SIUI.rMenu.slotSelection[SIUI.rMenu.lastHovered] != 2){
					SIUI.rMenu.slotSelection[SIUI.rMenu.lastHovered] = 0;
				}
				
				// System.out.println(mouseY);
				// System.out.println(mouseY - SIUI.topleftY - SIUI.rMenu.startSlotsY);
				SIUI.rMenu.lastHovered = SIUI.rMenu.slotCount;
			}

			if(mouseX - SIUI.topleftX > 0 && mouseX < SIUI.topleftX + SIUI.largerWidth && mouseY > 0 && mouseY < SIUI.topleftY + 50){
				SIUI.movingWindowCheck(mouseX, mouseY, leftClicking);
				return;
			}


			return;
			
		}

		if (selectedUI == "buildingUI"){
			//Check slots first
			if (mouseX - BUI.topleftX - BUI.inv.startSlotsX > 0 && mouseY - BUI.topleftY - BUI.inv.startSlotsY > 0 && mouseX - BUI.topleftX - BUI.inv.startSlotsX < BUI.endSlotsX && mouseY - BUI.topleftY - BUI.inv.startSlotsY < BUI.endSlotsY){
				BUI.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
				return;
			}else{
				if(BUI.inv.slotSelection[BUI.inv.lastHovered] != 2){
					BUI.inv.slotSelection[BUI.inv.lastHovered] = 0;
				}
				BUI.inv.lastHovered = BUI.inv.slotCount;
			}

			if(mouseX - BUI.topleftX > 0 && mouseX < BUI.topleftX + BUI.largerWidth && mouseY > 0 && mouseY < BUI.topleftY + 50){
				BUI.movingWindowCheck(mouseX, mouseY, leftClicking);
				return;
			}


			return;
			
		}
		


		
	} 

	public boolean checkMouseWindow(int mouseX, int mouseY){

		if(hotbar.checkMouseWindow(mouseX, mouseY)){
			selectedUI = "hotbar";
			return false;
		}
		if(openVisibility){
			if(openUI == "playerInv"){
				if(SIUI.checkMouseWindow(mouseX, mouseY)){
					selectedUI = "playerInv";
					return false;
				}
			}
			if(openUI == "buildingUI"){
				if(BUI.checkMouseWindow(mouseX, mouseY)){
					selectedUI = "buildingUI";
					return false;
				}
			}
		}

		selectedUI = null;
		return true;
	}

	public void addNewDisspearingText(int worldX, int worldY, String text){
		disappearingTextList.add(new disappearingText(worldX, worldY, text));
	}


	public static void drawMiddleLayer(int x, int y, int width, int height, Graphics2D g2){

		for(int i = 0; i< 4; i++){
			g2.setColor(middleLayer[8][(2 * i)]);
			g2.fillRect(x - 4 + i, y - 4 + i, width + 8 - (2 * i), 1);
		}
		
		//Left edge
		for(int i = 0; i< 4; i++){
			g2.setColor(middleLayer[(2 * i)][8]);
			g2.fillRect(x - 4 + i, y - 4 + i, 1, height + 8 - (2 * i));
		}
		
		// //Right edge
		for(int i = 0; i< 4; i++){
			g2.setColor(middleLayer[15 - (2 * i)][8]);
			g2.fillRect(x + width + i, y - i, 1, height + (2 * i));
		}
		
		//Bottom edge
		for(int i = 0; i< 4; i++){
			g2.setColor(middleLayer[8][(2 * -i) + 15]);
			g2.fillRect(x - i, y + height + i, width + (2 * i) + 1, 1);
		}

		g2.setColor(middleLayer[8][8]);
		g2.fillRect(x, y, width, height);


		// g2.setColor(middleLayerBody);
		// g2.fillRect(x, y, width, height);
	}

	public static void drawOuterLayer(int x, int y, int width, int height, Graphics2D g2, boolean drawingCloseButton){


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
			UI.drawOuterLayer(x + width - 36, y + 7, 24, 24, g2, false);
			g2.drawImage(closeWhite, x + width - 34, y + 10, 20, 20, null);
		}

	}












	public void resizeWindow(){
		//Add for each new window
		hotbar.resizeWindow();
		SIUI.resizeWindow();
		BUI.resizeWindow();


	}


	public class disappearingText{
		private int worldX;
		private int worldY;

		private int originalY;

		private int screenX;
		private int screenY;

		private String text;



		public disappearingText(int worldX, int worldY, String text){

			this.worldX = worldX;
			this.worldY = worldY; 
			this.text = text;
			originalY = worldY;


		}

		public void updateText(Iterator<disappearingText> iterator){
			
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;

			g2.setFont(titilliumBold.deriveFont(18.0F));
			g2.setColor(Color.white);
			
			g2.drawString(text, screenX, screenY);
			worldY--;
			if(originalY - worldY > 25){
				iterator.remove();
			}
		}

	}

	public class recipeMenu{
		final private int width = 424;
		final private int height = 448;
		final public int slotCount = 90;
		public int[][] slotSelection =  new int[9][10];
		public final int[] buttonSelection =  new int[4];
		private int lastHovered = slotCount;
		public int selectedButton = 0;
		final private int slotNumWidth = 10;
		final private int startButtonsX = 26;
		final private int startButtonsY = 78;
		final private int startSlotsX = 26;
		final private int startSlotsY = 78;
		private int topleftX, topleftY;
		private int windowNum;
		private BufferedImage[][] recipeImageRows = new BufferedImage[9][];
		private String[][] recipeArray;
		public String selectedRecipe;




		recipeMenu(int topleftX, int topLeftY){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
			buttonSelection[0] = 2;
			recipeArray = ItemGroups.Logistics.rows;
			System.out.println(recipeArray[0][0]);
			grabRecipeImages();
		}

		public void grabRecipeImages(){
			for(int i = 0; i < 9; i++){
				recipeImageRows[i] = new BufferedImage[10];
				for(int j = 0; j < 10; j++){

					if(j >= recipeArray[i].length){
						break;
					}
					String recipeID = recipeArray[i][j];
					// if(recipeID == null){
					// 	break;
					// }
					//TODO check if unlocked recipe later


					System.out.println(recipeID);
					recipeImageRows[i][j] = EntityImage.entityImages.get(recipeID).icon;

				}
			}


		}
		
		public void resizeWindow(int topleftX, int topLeftY, int winNum){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
			windowNum = ((width + 13) * winNum);
		}
		//TODO IMPLEMENT

		public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
			mouseX = mouseX - topleftX - startSlotsX - windowNum;
			mouseY = mouseY - topleftY - startSlotsY;

			int hoveredSlotX = (mouseX / 40) + (slotNumWidth * (mouseY / 40));
			int hoveredSlotY = (slotNumWidth * (mouseY / 40));
			
			if(leftClicking){
				// System.out.println("whaa");
				slotSelection[hoveredSlotX][hoveredSlotY] = 2;
				

				//TODO implement crafting









					if(gp.player.inventory.invContents[hoveredSlot] == null){
						slotSelection[selectedSlot] = 0;
						selectedSlot = slotCount;
						lastHovered = slotCount;
						return;
					}
					slotSelection[hoveredSlot] = 2;
					slotSelection[selectedSlot] = 0;
					hotbar.slotSelection[hotbar.selectedSlot] = 0;
					hotbar.selectedSlot = hotbar.slotCount;
					lastHovered = slotCount;
					selectedSlot = hoveredSlot;
				}
			}else if(slotSelection[hoveredSlot] != 2 && hoveredSlot != lastHovered){
				// System.out.println("hhh");
				slotSelection[hoveredSlot] = 1;
				if(lastHovered != hoveredSlot){
					slotSelection[lastHovered] = 0;
					lastHovered = hoveredSlot;
				}
			}

			



		}

		public void draw(Graphics2D g2){
			UI.drawMiddleLayer(topleftX + 11 + windowNum, topleftY + 41, width, height, g2);
			
			//TODO add selectability
			int sectionWidth = width / 4;
			for(int i = 0; i < 4; i++){
				
				g2.setColor(bigButtonColor[buttonSelection[i]]);
				g2.fillRect(topleftX + 11 + windowNum + (i * sectionWidth), topleftY + 41, sectionWidth, 64);

				
				g2.drawImage(bigButtons[i], topleftX + 11 + windowNum + 30 + (i * sectionWidth), topleftY + 41 + 7, 49, 49, null);
			}

			for(int i = 0; i < 10; i++){
				for(int j = 0; j < 9; j++){
					if(recipeImageRows[j][i] != null){
						g2.drawImage(hotbarButton[slotSelection[i][j]], topleftX  + windowNum + startButtonsX + (40 * i), topleftY + 41 + startButtonsY + (40 * j), 40, 40, null);
						g2.drawImage(recipeImageRows[j][i], 5 + topleftX  + windowNum + startButtonsX + (40 * i), 5 + topleftY + 41 + startButtonsY + (40 * j), 30, 30, null);
					
						//TODO also draw the possible craftable amount
					}
				}
			}

		}
	}

	public class progressBar{
		public int x;
		public int y;
		public int width;
		public int height;
		public Color color, bgColor;

		public progressBar(int x, int y, int width, int height, Color color, Color bgColor){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = color; //red for fuel, green for production
			this.bgColor = bgColor;
		}
		
		public void resizeWindow(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public void draw(Graphics2D g2, int progress){


			if(progress > 100){
				progress = 100;
			}
			if(bgColor != null){
			g2.setColor(bgColor);
			g2.fillRect(x, y, width, height);
			}else{
				drawOuterLayer(x, y, width, height, g2, false);
			}


			g2.setColor(color);
			g2.fillRect(x, y, (progress * width)/100, height);
		}
	}

	public class assemblerUI{
		final private int width = 424;
		final private int height = 448;
		final public int slotCount = 90;
		public final int[] slotSelection =  new int[slotCount + 1];
		private int lastHovered = slotCount;
		public int selectedSlot = slotCount;
		final private int slotNumWidth = 10;
		final private int startSlotsX = 26;
		final private int startSlotsY = 78;
		private int topleftX, topleftY;
		public String buildingType;
		public progressBar pBar;
		private int windowNum;
		private int inputSlotCount;
		private Recipe recipe;
		//TODO IMPLEMENT RECIPE PICKER

		assemblerUI(int topleftX, int topLeftY, String buildingType){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
			this.buildingType = buildingType;
			pBar = new progressBar(76 + topleftX + windowNum, topleftY + 258, 260, 25, Color.green, null);
			// (26 + topleftX + windowNum + 50, 170 + topleftY + 78, 400, 150, g2, false);
		}
		
		public void resizeWindow(int topleftX, int topLeftY, int winNum){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
			windowNum = ((width + 13) * winNum);
			pBar.resizeWindow(76 + topleftX + windowNum, topleftY + 258);
		}

		//163 + topleft of window is first input slot

		public void draw(Graphics2D g2){

			UI.drawMiddleLayer(topleftX + 11 + windowNum, topleftY + 41, width, height, g2);

			// g2.setColor(middleLayerBody);
			// g2.fillRect(topleftX + 11 + windowNum, topleftY + 41, width, height);


			// for(int i = 0; i < gp.player.inventory.invSize; i++){
			// 	g2.drawImage(behindItem[slotSelection[i]], startSlotsX + ((width + 13) * j) +topleftX + (40 * (i % 10)), topleftY + startSlotsY + (40* (i/10)), 40, 40, null);

			// }

			
			g2.setFont(titilliumBold.deriveFont(18.0F));
			g2.setColor(Color.white);
			UI.drawWindowTitle(topleftX + windowNum, topleftY, EntityImage.entityImages.get(buildingType).visibleName, g2);




			//inner image of building
			UI.drawOuterLayer(26 + topleftX + windowNum, topleftY + 78, 400, 150, g2, false);
			EntityImage entityImage = EntityImage.entityImages.get(buildingType);
			int width = 32 * entityImage.tileWidth + (2 * entityImage.shiftX) + entityImage.shadowOffsetRight;
			int height = 32 * entityImage.tileHeight + (2 * entityImage.shiftY) + entityImage.shadowOffsetDown;
			//TODO make it animate this:
			g2.drawImage(entityImage.shadowArr[0][0], 236 + topleftX + windowNum - entityImage.shiftX - width/2, topleftY + 173 - entityImage.shiftY - height/2, width, height, null);
							
			// g2.drawImage(EntityImage.entityImages.get(buildingType), 26 + topleftX + windowNum, topleftY + 78, );


			//Progress bar
			pBar.draw(g2, 50);

		}

	}

	public class buildingUI{
		//BUI
		private int topleftX;
		private int topleftY;
		private int originalMouseX;
		private int originalMouseY;
		final private int largerWidth = 880;
		final private int largerHeight = 500;
		final private int endSlotsX = 398;
		final private int endSlotsY = 358;
		//TODO for now, just testing with assembler
		public String buildingType;

		public inventory inv = SIUI.inv;
		public assemblerUI assUI = new assemblerUI(topleftX, topleftY, buildingType);

		public void newWindow(){
			assUI = new assemblerUI(topleftX, topleftY, buildingType);
			resizeWindow();
		}

		public void resizeWindow(){
			this.topleftX = gp.screenWidth/ 3 - 150;
			this.topleftY = gp.screenHeight / 4;
			inv.resizeWindow(topleftX, topleftY, 0);
			assUI.resizeWindow(topleftX, topleftY, 1);
		} 

		public void draw(){



			UI.drawOuterLayer(topleftX, topleftY, largerWidth, largerHeight, g2, true);

			inv.draw(g2);
			assUI.draw(g2);
		}

		public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
			inv.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
		}

		public boolean checkMouseWindow(int mouseX, int mouseY){
			if(mouseX > topleftX && mouseX < topleftX + largerWidth && mouseY > topleftY && mouseY < topleftY + largerHeight){
				return true;
			}
			return false;
		}

		public void movingWindowCheck(int mouseX, int mouseY, boolean leftClicking){
			if(leftClicking){
				topleftX += gp.mouseH.mouseX - originalMouseX;
				topleftY += gp.mouseH.mouseY - originalMouseY;
				originalMouseX = gp.mouseH.mouseX;
				originalMouseY = gp.mouseH.mouseY;
				inv.resizeWindow(topleftX, topleftY, 0);
				assUI.resizeWindow(topleftX, topleftY, 1);
			}else{
				originalMouseX = gp.mouseH.mouseX;
				originalMouseY = gp.mouseH.mouseY;
			}
		}
		//TODO implement
	}


	
	public class hotbarUI{
	

		private int topleftY;
		private int topleftX;
		final private int width = 732;
		final private int height = 96;
		final private int startSlotsX = 63;
		final private int startSlotsY = 11;
		final private int endSlotsX = 435;
		final private int endSlotsY = 77;
		final private int slotNumWidth = 11;
		final public int slotCount = 22;
		final private int[] slotSelection =  new int[slotCount + 1];
		private int lastHovered = slotCount;
		public int selectedSlot = slotCount;

		public void resizeWindow(){
			this.topleftY = gp.screenHeight - 100;
			this.topleftX = gp.player.screenX - 300;
			gp.player.pBar.resizeWindow(topleftX, topleftY - 30);
		} 

		public void setSlot(int slot, String itemID){
			gp.player.hotbar[slot] = gp.player.inventory.invItemsQuantity.get(itemID);
		}


		public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
			mouseX = mouseX - topleftX - startSlotsX;
			mouseY = mouseY - topleftY - startSlotsY;
			

			int hoveredSlot = (mouseX / 40) + (slotNumWidth * (mouseY / 40));
			
			if(rightClicking){
				if(hoveredSlot != selectedSlot){
					if(gp.player.hotbar[hoveredSlot] != null){
						gp.player.hotbar[hoveredSlot] = null;
						gp.mouseH.rightMouseClicked = false;
						selectedSlot = slotCount;
						slotSelection[hoveredSlot] = 1;
						lastHovered = slotCount;
					}
				}
			}else if(leftClicking){
				if(hoveredSlot != slotCount){
					if(gp.player.hotbar[hoveredSlot] == null){
						if(SIUI.inv.selectedSlot != SIUI.inv.slotCount){
							setSlot(hoveredSlot, gp.player.inventory.invContents[SIUI.inv.selectedSlot].itemID);	
						}else if(selectedSlot != slotCount){
							setSlot(hoveredSlot, gp.player.hotbar[selectedSlot].itemID);	
						}
					}
				}
				if(hoveredSlot == selectedSlot){
					selectedSlot = slotCount;
					lastHovered = slotCount;
					slotSelection[hoveredSlot] = 1;
				}else{
					 if(gp.player.hotbar[hoveredSlot] == null){
						slotSelection[selectedSlot] = 0;
						selectedSlot = slotCount;
						lastHovered = slotCount;
						return;
					 }
					slotSelection[hoveredSlot] = 2;
					slotSelection[selectedSlot] = 0;
					SIUI.inv.slotSelection[SIUI.inv.selectedSlot] = 0;
					SIUI.inv.selectedSlot = SIUI.inv.slotCount;
					lastHovered = slotCount;
					selectedSlot = hoveredSlot;
				}
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
			

			UI.drawOuterLayer(topleftX, topleftY, width, height, g2, false);
			
			for(int i = 0; i< slotCount; i++){

				g2.drawImage(hotbarButton[slotSelection[i]], 60 + topleftX + (40 * (i % 11)), topleftY + 8 + (45* (i / 11)), 40, 40, null);

				Item slotItem = gp.player.hotbar[i]; 
				if (slotItem == null){
					continue;
				}
				if(selectedSlot == i){
					g2.drawImage(inHandIcon, 60 + topleftX + (40 * (i % 11)) + 5, topleftY + 8 + (45 * (i / 11)) + 5, 30, 30, null);;
					continue;
				}
				g2.drawImage(EntityImage.entityImages.get(slotItem.itemID).icon, 60 + topleftX + (40 * (i % 11)) + 5, topleftY + 8 + (45 * (i / 11)) + 5, 30, 30, null);
				
				UI.drawNumber(startSlotsX + 5  + topleftX + (40 * (i % 11)), topleftY + startSlotsY + 33 + (40 * (i / 11)), slotItem.quantity, g2);
			}

			// for(int i = 0; i< 11; i++){
			// 	for(int j = 0; j < 2; j++){
			// 		g2.drawImage(hotbarButton[slotSelection[i]], 60 + topleftX + (40 * i), topleftY + 8 + (45* j), 40, 40, null);
					
			// 		g2.drawImage(EntityImage.entityImages.get("assembling-machine-1").icon, 60 + topleftX + (40 * i) + 5, topleftY + 8 + (45 * j) + 5, 30, 30, null);
			// 	}
			// }

		}

		public boolean checkMouseWindow(int mouseX, int mouseY){

			if(mouseX > topleftX && mouseX < topleftX + width && mouseY > topleftY && mouseY < topleftY + height){
				return true;
			}
			return false;
		}


	}

	public class inventory{
		final private int width = 424;
		final private int height = 448;
		final public int slotCount = 90;
		public final int[] slotSelection =  new int[slotCount + 1];
		private int lastHovered = slotCount;
		public int selectedSlot = slotCount;
		final private int slotNumWidth = 10;
		final private int startSlotsX = 26;
		final private int startSlotsY = 78;
		private int topleftX, topleftY;
		private int windowNum;

		inventory(int topleftX, int topLeftY){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
		}
		
		public void resizeWindow(int topleftX, int topLeftY, int winNum){
			this.topleftX = topleftX;
			this.topleftY = topLeftY;
			windowNum = ((width + 13) * winNum);
		}
		




		public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
			mouseX = mouseX - topleftX - startSlotsX;
			mouseY = mouseY - topleftY - startSlotsY;

			int hoveredSlot = (mouseX / 40) + (slotNumWidth * (mouseY / 40));
			
			if(leftClicking){
				// System.out.println("whaa");
				if(hoveredSlot == selectedSlot){
					slotSelection[selectedSlot] = 0;
					selectedSlot = slotCount;
					lastHovered = slotCount;
				}else{
					if(gp.player.inventory.invContents[hoveredSlot] == null){
						slotSelection[selectedSlot] = 0;
						selectedSlot = slotCount;
						lastHovered = slotCount;
						return;
					}
					slotSelection[hoveredSlot] = 2;
					slotSelection[selectedSlot] = 0;
					hotbar.slotSelection[hotbar.selectedSlot] = 0;
					hotbar.selectedSlot = hotbar.slotCount;
					lastHovered = slotCount;
					selectedSlot = hoveredSlot;
				}
			}else if(slotSelection[hoveredSlot] != 2 && hoveredSlot != lastHovered){
				// System.out.println("hhh");
				slotSelection[hoveredSlot] = 1;
				if(lastHovered != hoveredSlot){
					slotSelection[lastHovered] = 0;
					lastHovered = hoveredSlot;
				}
			}

			



		}
		
		public void draw(Graphics2D g2){


			UI.drawMiddleLayer(topleftX + 11 + windowNum, topleftY + 41, width, height, g2);


			for(int i = 0; i < gp.player.inventory.invSize; i++){
				g2.drawImage(behindItem[slotSelection[i]], startSlotsX + topleftX + (40 * (i % 10)), topleftY + startSlotsY + (40* (i/10)), 40, 40, null);

			}

			
			g2.setFont(titilliumBold.deriveFont(18.0F));
			g2.setColor(Color.white);
			UI.drawWindowTitle(topleftX, topleftY, "Character", g2);


			for (int i = 0; i < gp.player.inventory.invSize; i++){
				Item slotItem = gp.player.inventory.invContents[i]; 
				if(slotItem == null){
					break;
				} 
				
				if(selectedSlot == i){
					g2.drawImage(inHandIcon, 26 + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 30, 30, null);;
					continue;
				}
				g2.drawImage(EntityImage.entityImages.get(slotItem.itemID).icon, 26 + topleftX + (40 * (i % 10)) + 5, topleftY + 78 + (40 * (i / 10)) + 5, 30, 30, null);
				UI.drawNumber(31  + topleftX + (40 * (i % 10)), topleftY + 78 + 30 + (40 * (i / 10)) + 5, slotItem.quantity, g2);
				

			}

		}
	}


	public class playerInventoryUI{


		//Make sure to copy most of this stuff to new uis
		private int topleftX;
		private int topleftY;
		private int originalMouseX;
		private int originalMouseY;
		final private int largerWidth = 1320;
		final private int largerHeight = 500;
		final private int endSlotsX = 398;
		final private int endSlotsY = 358;
		public inventory inv;
		public recipeMenu rMenu;


		playerInventoryUI(){
			inv = new inventory(topleftX, topleftY);
			rMenu = new recipeMenu(topleftX, topleftY);
		}

		public void resizeWindow(){
			this.topleftX = gp.screenWidth/ 5 - 150;
			this.topleftY = gp.screenHeight / 4;
			inv.resizeWindow(topleftX, topleftY, 0);
			rMenu.resizeWindow(topleftX, topleftY, 2);
		} 

		public boolean checkMouseWindow(int mouseX, int mouseY){
			if(mouseX > topleftX && mouseX < topleftX + largerWidth && mouseY > topleftY && mouseY < topleftY + largerHeight){
				return true;
			}
			return false;
		}

		public void hoveredSlotCheck(int mouseX, int mouseY, boolean leftClicking, boolean rightClicking){
			inv.hoveredSlotCheck(mouseX, mouseY, leftClicking, rightClicking);
		}

		public void movingWindowCheck(int mouseX, int mouseY, boolean leftClicking){
			if(leftClicking){
				topleftX += gp.mouseH.mouseX - originalMouseX;
				topleftY += gp.mouseH.mouseY - originalMouseY;
				originalMouseX = gp.mouseH.mouseX;
				originalMouseY = gp.mouseH.mouseY;
				inv.resizeWindow(topleftX, topleftY, 0);
				rMenu.resizeWindow(topleftX, topleftY, 2);
			}else{
				originalMouseX = gp.mouseH.mouseX;
				originalMouseY = gp.mouseH.mouseY;
			}
		}

		


		public void draw(){
			
			//Draw outer rectangle
			UI.drawOuterLayer(topleftX, topleftY, largerWidth, largerHeight, g2, true);



			inv.draw(g2);
			rMenu.draw(g2);

			//need to draw logistics stuff and crafting stuff
			//make in their own classes


			//TODO for each new window inside use this. i is the position in larger window 
			// g2.setColor(middleLayerBody);
			// g2.fillRect(topleftX + 11 + ((width + 13) * i), topleftY + 41, width, height);
			


			

			



			



		}


	}









	


	




	









}
