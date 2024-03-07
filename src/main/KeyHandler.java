package src.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, rightPressed, leftPressed, testable;


	@Override
	public void keyTyped(KeyEvent e) {
		//Currently unused
	}

	@Override
	public void keyPressed(KeyEvent e) {
		

		int code = e.getKeyCode(); // returns number of key pressed

		switch(code){
			case(KeyEvent.VK_W):
				upPressed = true;
				break;
			case(KeyEvent.VK_A):
				leftPressed = true;
				break;
			
			case(KeyEvent.VK_S):
				downPressed = true;
				break;
			
			case(KeyEvent.VK_D):
				rightPressed = true;
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		
		switch(code){
			case(KeyEvent.VK_W):
				upPressed = false;
				break;
			case(KeyEvent.VK_A):
				leftPressed = false;
				break;
			
			case(KeyEvent.VK_S):
				downPressed = false;
				break;
			
			case(KeyEvent.VK_D):
				rightPressed = false;
				break;
			
		}
	}



}