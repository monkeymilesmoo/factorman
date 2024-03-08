package src.main;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeListener implements ComponentListener{


	public Dimension newSize;

	@Override
	public void componentResized(ComponentEvent e) {
		
		newSize = e.getComponent().getBounds().getSize();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	
	
}
