

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
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
 *  author @Sidqdeep, @Alora, @Patrick
 */

public class BoardView implements ChangeListener
{
	private final Board board;
	private ArrayList<PitShape> pitShapes;
	final JTextField playerTurn;

	/**
	 * constructor creates a display based on the current board
	 * @param b reference when updating thw view.
	 */
	public BoardView(Board b) 
	{
		board = b;
		pitShapes = b.getData();
		JFrame frame = new JFrame("Mancala");
		frame.setSize(1200, 600);

		final JPanel undo 	= new JPanel(new GridLayout(1, 0));
		JButton undoButton	 = new JButton("Undo");
		
		undoButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				board.undo();
			}
			
		});

		final JPanel grid = new JPanel(new GridLayout(0, 8));
		grid.add(b.getMancala(Player.TWO));
		
		for (int i = 0; i < 6; i++) 
		{
			JPanel ingrid = new JPanel(new GridLayout(2, 0));
			final PitShape topPit = pitShapes.get(12 - i);
			final PitShape bottomPit = pitShapes.get(i);
			
			ingrid.add(topPit);// or some other empty component
			ingrid.add(bottomPit);
			
			topPit.addMouseListener(new MouseAdapter()
			{
			
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					
					super.mouseClicked(e);
					board.choosePit(topPit);
				}
				
			});
			
			
			bottomPit.addMouseListener(new MouseAdapter() 
			{
				
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					
					super.mouseClicked(e);
					board.choosePit(bottomPit);
				}
			});
			
			grid.add(ingrid);
		}
		
		grid.add(b.getMancala(Player.ONE));
		
		// undo button to north
		undo.add(undoButton);
		frame.add(undo, BorderLayout.NORTH);
		
		//pit info
		JTextArea aPits= new JTextArea("                                                A1               "
				+ "           A2                          A3                          A4   "
				+ "                       A5                          A6 \n Player A");
		JTextArea bPits= new JTextArea("Player B\n                                                 B6               "
				+ "           B5                          B4                          B3   "
				+ "                       B2                          B1 ");
		JTextArea mancalaA= new JTextArea("\n\n\n\nM \na \nn \nc \na \nl \na \n \nA");
		JTextArea mancalaB= new JTextArea("\n\n\n\nM \na \nn \nc \na \nl \na \n \nB");
		
		JPanel fullBoard = new JPanel(new BorderLayout());
		fullBoard.add(grid, BorderLayout.CENTER);
		fullBoard.add(aPits, BorderLayout.SOUTH);
		fullBoard.add(bPits, BorderLayout.NORTH);
		fullBoard.add(mancalaA, BorderLayout.EAST);
		fullBoard.add(mancalaB, BorderLayout.WEST);
		
		frame.add(fullBoard, BorderLayout.CENTER);
		
		playerTurn = 	new JTextField(board.getPlayer().toString());
		playerTurn.setHorizontalAlignment(JTextField.CENTER);
		
		frame.add(playerTurn, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	
	/**
	 * Repaints when the state of the board is changed
	 * @param e an change event
	 */
	public void stateChanged(ChangeEvent e) 
	{
		
		pitShapes = board.getData();
		
		for (PitShape pitShape : pitShapes) 
		{
			
				pitShape.repaint();
		}
		
		playerTurn.setText(board.getPlayer().toString());
		
		if (board.gameOver()) 
		{
			
			String score = "Final score: Player 1 " + pitShapes.get(6).getMarbles();
			score += ", Player 2 " + pitShapes.get(13).getMarbles() + ". ";
			
			if (pitShapes.get(6).getMarbles() > pitShapes.get(13).getMarbles())
			{
					playerTurn.setText(score + "Player 1 Wins!");
			}
			else if (pitShapes.get(6).getMarbles() < pitShapes.get(13).getMarbles())
			{
				
				playerTurn.setText(score + "Player 2 Wins!");
			}
			else
				{
						
						playerTurn.setText(score + "Draw!");
				}
		}
		
		
	}
	
	
}
