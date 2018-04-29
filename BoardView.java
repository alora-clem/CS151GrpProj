package group;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * author @sidqdeep
 * 
 */

public class BoardView implements ChangeListener

{
	private final Board board;
	private ArrayList<PitShape> pitShapes;
	final JTextField playerTurn;

	/**
	 * BoardView class constructor - creates a display based on the current board
	 * 
	 * @param b
	 *            - the board object to reference when updating this view.
	 */
	public BoardView(Board b) {
		board = b;
		pitShapes = b.getData();
		JFrame frame = new JFrame("Mancala");
		frame.setSize(1200, 600);

		final JPanel undo = new JPanel(new GridLayout(1, 0));
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.undo();
			}
		});

		final JPanel grid = new JPanel(new GridLayout(0, 8));
		grid.add(b.getMancala(Player.TWO));
		for (int i = 0; i < 6; i++) {
			JPanel ingrid = new JPanel(new GridLayout(2, 0));
			final PitShape toppit = pitShapes.get(12 - i);
			final PitShape botpit = pitShapes.get(i);
			ingrid.add(toppit);// or some other empty component
			ingrid.add(botpit);
			toppit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e); // To change body of generated methods, choose Tools | Templates.
					board.choosePit(toppit);
				}
			});
			botpit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e); // To change body of generated methods, choose Tools | Templates.
					board.choosePit(botpit);
				}
			});
			grid.add(ingrid);
		}
		grid.add(b.getMancala(Player.ONE));
		undo.add(undoButton);
		// undo button to north
		frame.add(undo, BorderLayout.NORTH);
		frame.add(grid, BorderLayout.CENTER);
		frame.pack();
		playerTurn = new JTextField(board.getPlayer().toString());
		playerTurn.setHorizontalAlignment(JTextField.CENTER);
		frame.add(playerTurn, BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Repaints when the state of the board is changed
	 * 
	 * @param e
	 *            - a change event
	 */
	public void stateChanged(ChangeEvent e) {
		pitShapes = board.getData();
		for (PitShape pitShape : pitShapes) {
			pitShape.repaint();
		}
		playerTurn.setText(board.getPlayer().toString());
		if (board.gameOver()) {
			String score = "Final score: Player 1 " + pitShapes.get(6).getMarbles();
			score += ", Player 2 " + pitShapes.get(13).getMarbles() + ". ";
			if (pitShapes.get(6).getMarbles() > pitShapes.get(13).getMarbles())
				playerTurn.setText(score + "Player 1 Wins!");
			else if (pitShapes.get(6).getMarbles() < pitShapes.get(13).getMarbles())
				playerTurn.setText(score + "Player 2 Wins!");
			else
				playerTurn.setText(score + "Draw!");
		}
	}
}
