package com.program.pc_cafe.image.ui.user;


import javax.swing.*;
import java.awt.*;


public class GameView extends JFrame {
	public GameView(String name) {// 740x380 1280x720
		Container c = getContentPane();
		if (name.equals("오버워치")) {
			c.add(new JLabel(new ImageIcon("/image/game_image/overwatch.png")), "Center");
			setSize(740, 380);
			setVisible(true);
		}
		
		if (name.equals("LOL")) {
			c.add(new JLabel(new ImageIcon("/image/game_image/lol.jpg")), "Center");
			setSize(1280, 720);
			setVisible(true);
		}
		
		if (name.equals("인터넷")) {
			c.add(new JLabel(new ImageIcon("/image/game_image/internet.png")), "Center");
			setSize(810, 377);
			setVisible(true);
		}
		if (name.equals("배틀그라운드")) {
			c.add(new JLabel(new ImageIcon("/image/game_image/bettleground.png")), "Center");
			setSize(1920,1080);
			setVisible(true);
		}
	}
}