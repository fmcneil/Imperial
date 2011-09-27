package org.fmc.imperial.ui;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import org.fmc.imperial.Game;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String VERSION = "0.1";
	
	private JFrame papa;
	private Game game;
	
	private MainView mainView;
	private JPanel bottom;
	ChatPane chatPane;
	JTextField messageText;
//	JButton clearButton;
	JScrollPane textScroller;
	
	Image boardImg;
	
	public Board(JFrame p, Game g) {
		this.papa = p;
		this.game = g;
		game.setBoard(this);
		
		boardImg = new ImageIcon("data"+File.separator+"board.jpg").getImage();
		
		setLayout(new BorderLayout(5,0));
		EmptyBorder eb = new EmptyBorder(5,5,5,5);
		setBackground(Color.white);
		setBorder(eb);

		mainView = new MainView(boardImg);
		
		createBottomPanel();
		
		add("Center", mainView);
		add("South", bottom);
		
	}
	
	public void addText(String s) {
		chatPane.addText(s);
	}
	
	private void createBottomPanel() {
		chatPane = new ChatPane(this);
		textScroller = new JScrollPane(chatPane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textScroller.setBounds(5,5,500,75);
		textScroller.setPreferredSize(new Dimension(500,75));
		
		messageText = new JTextField();
		messageText.setBounds(5,280,500,22);
		messageText.setColumns(10);
		messageText.setBackground(new Color(249, 249, 250));
		
		String opening = "Welcome to <strong>Imperial 2030</strong> ( v" + VERSION + " )<br>";		
		chatPane.addText(opening);
		
		bottom = new JPanel();
		JPanel messagePanel= new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
		messagePanel.setBackground(Color.white);
//		clearButton = new JButton("Clear");
//		clearButton.setBackground(Color.white);
//		clearButton.addActionListener(this);

		messagePanel.add(messageText);
//		messagePanel.add(clearButton);
		
		bottom.setBackground(Color.white);
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		SoftBevelBorder sbb2 = new SoftBevelBorder(BevelBorder.RAISED);
		bottom.setBorder(new LineBorder(Color.blue, 1));
		bottom.add(textScroller);
		bottom.add(messagePanel);
		
	}
}
