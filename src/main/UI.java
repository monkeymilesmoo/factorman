package src.main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.FileInputStream;

public class UI {
	
	Graphics2D g2;
	Font titilliumBold, titilliumRegular;
	GamePanel gp;


	public UI (GamePanel gp){
		this.gp = gp;

		try{
			titilliumRegular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Regular.ttf"));
			titilliumBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/TitilliumWeb-Bold.ttf"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}








	public void draw(Graphics2D g2){
		this.g2 = g2;

		

		g2.setFont(titilliumBold.deriveFont(32.0F));
	}
}
