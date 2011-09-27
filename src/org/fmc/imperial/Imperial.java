package org.fmc.imperial;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.fmc.imperial.service.LoadData;
import org.fmc.imperial.ui.Board;


public class Imperial {

	static Game game;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean headless = false;
		
		if (args.length > 0) {
			for (int i=0; i<args.length; i++) {
				if (args[i].equalsIgnoreCase("-headless")) {
					headless = true;
				}
			}
		}
		
		// Load map (default = 2030)
		game = new Game(LoadData.loadCountriesHardcoded(), headless);
		
		// Create and show GUI
		if (!headless) {
			createAndShowGUI(game);
		}
		
		// Launch the game
		game.getEngine().start();
 	}
	
	private static void createAndShowGUI(Game g) {
		JFrame f = new JFrame("Imperial 2030");
		final Board board = new Board(f, g);
		f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		f.getContentPane().add("Center", board);
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 1024;
		int h = 768;
		f.pack();
		f.setLocation(screenSize.width/2 - w/2, 0);
		f.setVisible(true);
	}

}
