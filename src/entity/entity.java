package src.entity;

import java.awt.image.BufferedImage;

public class entity {

	public int x, y;
	public int speed;
	public int entityTextureWidth, entityTextureHeight;
	public int shadowTextureWidth, shadowTextureHeight;

	public BufferedImage idleImage, runningImage, miningImage;
	public BufferedImage idleShadowImage, runningShadowImage, miningShadowImage;
	public int direction; //cardinal directions going clockwise 0 -> 7
	public int selectedCol, selectedRow;
	public int totalFramesInAnimation;
	public BufferedImage selectedEntityImage;
	public BufferedImage selectedShadowImage;

	public BufferedImage testImage;
	public boolean moving, mining;
	public int animationTick;
	
}
