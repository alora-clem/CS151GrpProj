package groupProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Group Project
 * author @AloraClem
 * Version 1
 * Buttons A-F so that players may select the pit to play
 * Most likely will be cut and pasted into a larger class
 */

public class BoardButtons {
	public static void main(String[] args){
		BoardModelData model = new BoardModelData();
		boolean playerOneActive = true;
		//Data of arraylist corresponds to:
		// Pit Letter; Player 1 index of ArrayList ; Player 2 index of ArrayList
		// A ; 6; 8
		// B ; 5; 9
		// C ; 4; 10
		// D ; 3; 11
		// E ; 2; 12
		// F ; 2; 13
		// Home ; 0; 7
		JFrame frame = new JFrame();
		JPanel pitButtons = new JPanel();
		pitButtons.setLayout(new FlowLayout());
		//Create buttons for A-F
		JButton aButton = new JButton("A");
		aButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 6 or 8
				if(playerOneActive){
					model.updateData(6);
				}
				else{
					model.updateData(8);
				}
			}
		});
		JButton bButton = new JButton("B");
		bButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 5 or 9
				if(playerOneActive){
					model.updateData(5);
				}
				else{
					model.updateData(9);
				}
			}
		});
		JButton cButton = new JButton("C");
		cButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 4 or 10
				if(playerOneActive){
					model.updateData(4);
				}
				else{
					model.updateData(10);
				}
			}
		});
		JButton dButton = new JButton("D");
		dButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 3 or 11
				if(playerOneActive){
					model.updateData(3);
				}
				else{
					model.updateData(11);
				}
			}
		});
		JButton eButton = new JButton("E");
		eButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 2 or 12
				if(playerOneActive){
					model.updateData(2);
				}
				else{
					model.updateData(12);
				}
			}
		});
		JButton fButton = new JButton("F");
		fButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//either pit 1 or 13
				if(playerOneActive){
					model.updateData(1);
				}
				else{
					model.updateData(13);
				}
			}
		});
		pitButtons.add(aButton);
		pitButtons.add(bButton);
		pitButtons.add(cButton);
		pitButtons.add(dButton);
		pitButtons.add(eButton);
		pitButtons.add(fButton);
		frame.add(pitButtons);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//attach listeners to them for clicking
	}
}
