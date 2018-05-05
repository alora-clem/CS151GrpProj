package group;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/** 
 * author @Sidqdeep, @Alora, @Patrick
 * 
 */

public class MancalaTester{

	static JComboBox marbles;
	static JComboBox styl;
	static JButton submit;
	static JFrame start;
	static JLabel choose;
	static JPanel options;


	public static void main(String[] args)
	{

		start	 = 	new JFrame("Start Mancala");
		start.setSize(400, 200);
		start.setLayout(new BorderLayout());
		
		marbles = 	new JComboBox();
		styl = 	new JComboBox();
		
		submit = 	new JButton("Submit");
		choose = 	new JLabel("Please select a style and # of marbles to begin game!");
		options = 	new JPanel();


		marbles.addItem("3");
		marbles.addItem("4");
		styl.addItem("Rectangles");
		styl.addItem("Circles");
		options.add(styl);
		options.add(marbles);

		//Register actionListeners to buttons
		submit.addActionListener(new submit_Action());

		//Add to the frame
		start.add(choose, BorderLayout.NORTH);
		start.add(options, BorderLayout.CENTER);
		start.add(submit, BorderLayout.SOUTH);
		start.setVisible(true);	
		
		
	}


	/** 
	 * This class contains the methods and actions performed int he game 
	 * 
	 */
	static class submit_Action implements ActionListener
	{

		/**
		 *sets up a new board based on the user choice
		 *@param e -> Action event which occurred
		 */
		public void actionPerformed (ActionEvent e)
		{
			
			start.setVisible(false);
			
			CircleStyle cStly  =  new CircleStyle();
			RectangleStyle rS  =  new RectangleStyle();
			
			if(styl.getSelectedItem().toString().equals("Rectangles"))
			{
				
				Board b  =   new Board(rS);
				BoardView bView   =  new BoardView(b);
				b.addChangeListener(bView);
				
				if(marbles.getSelectedItem().toString().equals("3"))
				
				{
					b.fillBoard(3);
				}
				else
				{
					b.fillBoard(4);
				}
				
				
			}
			
			else
			{
				Board b =	new Board(cStly);
				BoardView bView = new BoardView(b);
				b.addChangeListener(bView);
				
				if(marbles.getSelectedItem().toString().equals("3"))
				{
					b.fillBoard(3);
				}
				else
				{
					b.fillBoard(4);
				}
				
			}	
			
			
		}
		
		
	}
	
}

