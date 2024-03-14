package src.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.Chunk.Chunk;


public class MouseHandler extends MouseAdapter{

	public boolean leftMouseClicked, rightMouseClicked;
	public int mouseX, mouseY;
	public int mouseCol, mouseRow;
	public int mouseTileX, mouseTileY;

	GamePanel gp;

	public MouseHandler(GamePanel gp){
		this.gp = gp;
	}

	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			leftMouseClicked = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightMouseClicked = true;
		}
	}

	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			leftMouseClicked = false;
			
			
			
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightMouseClicked = false;
		}
	}

	public void mouseDragged(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();

		mouseX = mouseX + gp.player.worldX - gp.player.screenX;
		mouseY = mouseY + gp.player.worldY - gp.player.screenY;
		mouseTileX = (mouseX % (Chunk.chunkSize * gp.tileSize)) / gp.tileSize;
		mouseTileY = (mouseY % (Chunk.chunkSize * gp.tileSize)) / gp.tileSize;
		mouseCol = (mouseX - mouseTileX) / (Chunk.chunkSize * gp.tileSize);
		mouseRow = (mouseY - mouseTileY) / (Chunk.chunkSize * gp.tileSize);
	}

	public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();

		mouseX = mouseX + gp.player.worldX - gp.player.screenX;
		mouseY = mouseY + gp.player.worldY - gp.player.screenY;
		mouseTileX = (mouseX % (Chunk.chunkSize * gp.tileSize)) / gp.tileSize;
		mouseTileY = (mouseY % (Chunk.chunkSize * gp.tileSize)) / gp.tileSize;
		mouseCol = (mouseX - mouseTileX) / (Chunk.chunkSize * gp.tileSize);
		mouseRow = (mouseY - mouseTileY) / (Chunk.chunkSize * gp.tileSize);
	}
	
}

