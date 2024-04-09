package src.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, rightPressed, leftPressed, Lpressed, Epressed, Rpressed, testable;


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
			case(KeyEvent.VK_L):
				Lpressed = true;
				break;
			case(KeyEvent.VK_E):
				Epressed = true;
				break;
			case(KeyEvent.VK_R):
				Rpressed = true;
				break;
			case(KeyEvent.VK_SPACE):
				testable = true;
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
			case(KeyEvent.VK_E):
				Epressed = false;
				break;
			case(KeyEvent.VK_R):
				Rpressed =false;
				break;
			case(KeyEvent.VK_SPACE):
				testable = false;
				break;
			
		}
	}



}