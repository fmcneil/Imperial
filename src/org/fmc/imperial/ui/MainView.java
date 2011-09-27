package org.fmc.imperial.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JPanel;

public class MainView extends JPanel {

    private Graphics gbuf;
    private Image im;
    
    Image boardImg;
    JPanel masterP; 
    
    Dimension boardSize = new Dimension(1015,605);

    public MainView(Image boardImg) {
    	super();
    	
    	this.boardImg = boardImg;
    	
		masterP = new JPanel();
		masterP.setPreferredSize(boardSize);
		this.add(masterP);
		
		this.repaint();
    }
	
	public void paint(Graphics g) {
		// offscreen
	    if (im == null) {
	    	//System.out.println("[GoFrame.paint()] Creating offscreen image.");
	    	im = masterP.createImage(boardSize.width, boardSize.height);
		    if (im != null) {
		    	gbuf = im.getGraphics();
		    } else {
		    	System.out.println("[FATAL] [GoFrame()] offscreen image is null.");
		    	System.out.println("------- isDisplayable(): "+isDisplayable());
		    	System.out.println("------- isHeadless()  : "+GraphicsEnvironment.isHeadless());
		    	System.exit(1);
		    }
	    }
    	
	    gbuf.setColor(Color.white);
	    gbuf.fillRect(0, 0, boardSize.width, boardSize.height);

    	// draw empty goban
    	gbuf.drawImage(boardImg, 0, 0, masterP);
    	
    	// render offscreen image
        g.drawImage(im,0,0,masterP);

	}
}
