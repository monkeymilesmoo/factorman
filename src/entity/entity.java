package src.entity;

import java.awt.image.BufferedImage;

public class entity {

	public int x, y;
	public int speed;
	public int textureWidth, textureHeight;

	public BufferedImage idleImage, runningImage, miningImage;
	public int direction; //cardinal directions going clockwise 0 -> 7
	public int selectedCol, selectedRow;
	public int totalFramesInAnimation;
	public BufferedImage selectedImage;

	public BufferedImage testImage;
	public boolean moving, mining;
	public int animationTick;
	
}
