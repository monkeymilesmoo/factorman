package src.main;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import src.Chunk.Chunk;
import src.tileEntity.TileEntity;


public class MouseHandler extends MouseAdapter{

	public boolean leftMouseClicked, rightMouseClicked;
	public int mouseX, mouseY;
	public int mouseWorldX, mouseWorldY;
	public int mouseCol, mouseRow;
	public int mouseTileX, mouseTileY;
	public boolean notOverGUI;
	private int oldMouseX, oldMouseY;

	BufferedImage selectionYellow;

	GamePanel gp;

	public MouseHandler(GamePanel gp){
		this.gp = gp;
		try{
			selectionYellow = ImageIO.read(getClass().getResourceAsStream("/res/core/cursor-boxes-32x32.png")).getSubimage(0, 0, 64, 64);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			leftMouseClicked = true;
			
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightMouseClicked = true;
			
		}
		if(notOverGUI == false){
			gp.ui.hoveredSlotCheck(mouseX, mouseY, leftMouseClicked, rightMouseClicked);
			
		}
	}

	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			leftMouseClicked = false;	
			
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightMouseClicked = false;

		}
		
		if(notOverGUI == false){
			gp.ui.hoveredSlotCheck(mouseX, mouseY, leftMouseClicked, rightMouseClicked);

		}
	}

	public void mouseDragged(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();


		

		

	}

	public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();

		


		
		if(notOverGUI == false){
			gp.ui.hoveredSlotCheck(mouseX, mouseY, false, false);
		}
	}

	public void calculateMousePos(){

		// if(mouseX != oldMouseX || mouseY != oldMouseY){
		mouseWorldX = mouseX + gp.player.worldX - gp.player.screenX;
		mouseWorldY = mouseY + gp.player.worldY - gp.player.screenY;
		mouseTileX = (mouseWorldX % (Chunk.chunkSize * GamePanel.tileSize)) / GamePanel.tileSize;
		mouseTileY = (mouseWorldY % (Chunk.chunkSize * GamePanel.tileSize)) / GamePanel.tileSize;
		mouseCol = (mouseWorldX - mouseTileX) / (Chunk.chunkSize * GamePanel.tileSize);
		mouseRow = (mouseWorldY - mouseTileY) / (Chunk.chunkSize * GamePanel.tileSize);

		
		notOverGUI = gp.ui.checkMouseWindow(mouseX, mouseY);


		// oldMouseX = mouseX;
		// oldMouseY = mouseY;
		// }
	}

	public void hoveredTileEntity(Graphics2D g2){

	
		
		Chunk hoveredChunk = gp.chunkGrid.getChunk(mouseCol, mouseRow);
		if(hoveredChunk != null){
			TileEntity hovered = hoveredChunk.getTileEntity(mouseTileX, mouseTileY);

			if (hovered != null){

				int hoveredWorldX = (hovered.chunkX * Chunk.chunkSize * GamePanel.tileSize) + (hovered.x * GamePanel.tileSize) - gp.player.worldX + gp.player.screenX;
				int hoveredWorldY = (hovered.chunkY * Chunk.chunkSize * GamePanel.tileSize) + (hovered.y * GamePanel.tileSize) - gp.player.worldY + gp.player.screenY;
				
				g2.drawImage(selectionYellow, hoveredWorldX, hoveredWorldY, GamePanel.tileSize * hovered.tileWidth, GamePanel.tileSize* hovered.tileHeight, null);

			}
		}

	}
	
}

