package group;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * author @sidqdeep
 * 
 */
public class Board {

	private Player p;
	private boolean extraTurn;
	private ArrayList<ChangeListener> listeners;
	private int countUndo;
	private int[] previousState;
	private ArrayList<PitShape> pitShapes;
	private boolean again;

	/**
	 * Board class constructor - creates a starting board initializing empty "pits"
	 * or containers with no marbles.
	 * 
	 * @param aBoardStyle
	 *            - a concrete implementation of BoardStyle determining the shape of
	 *            the pits to be used in the game
	 */
	public Board(BoardStyle aBoardStyle)

	{
		countUndo = 0;
		previousState = new int[14];

		p = Player.ONE;
		extraTurn = false;
		again = true;
		pitShapes = new ArrayList<PitShape>();
		for (int i = 0; i < 6; i++) {
			pitShapes.add(new PitShape(0, i, Player.ONE, aBoardStyle));
		}
		pitShapes.add(new Mancala(0, 6, Player.ONE, aBoardStyle));

		for (int i = 7; i < 13; i++) {
			pitShapes.add(new PitShape(0, i, Player.TWO, aBoardStyle));
		}
		pitShapes.add(new Mancala(0, 13, Player.TWO, aBoardStyle));

		listeners = new ArrayList<ChangeListener>();
	}

	/**
	 * Populates board with given marbles for new game
	 * 
	 * @param marbles
	 *            - the number of marbles per pit.
	 */

	public void fillBoard(int marbles) {
		int counter = 0;
		for (PitShape p : pitShapes) {
			if (!(p instanceof Mancala))

			{
				p.setMarbles(marbles);
				previousState[counter] = marbles;
			} else {
				previousState[counter] = 0;
				counter++;
			}
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Attaches listeners to the collection of listeners for this model
	 * 
	 * @param listener
	 *            - ChangeListeners to be notified of changes to the model
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Returns the current player.
	 * 
	 * @return - a player, Player.ONE or Player.TWO
	 */
	public Player getPlayer() {
		return p;
	}

	/**
	 * Evaluates and returns the game status
	 * 
	 * @return - a boolean to signal the end of the game.
	 */
	public boolean getGameStatus() {

		return gameOver();
	}

	/**
	 * Determines the position of the pit of the last marble dropped on the board
	 * for the selected pit.
	 * 
	 * @param pitShape
	 *            - a pit object containing the count of marbles to distribute
	 * @return the pit index in the board's array of pits that the last marble lands
	 *         in
	 */
	public int getLastMarble(PitShape pitShape) {
		int numberOfMarbles = pitShape.getMarbles();
		int currentIndex = pitShape.getIndex();

		while (numberOfMarbles > 0) {
			if (currentIndex == 5 && p == Player.TWO) {
				currentIndex += 2;
			}

			else if (currentIndex == 12 && p == Player.ONE) {
				currentIndex += 2;
			}

			else {
				currentIndex++;
			}

			if (currentIndex == 14) {
				currentIndex = 0;
			}
			numberOfMarbles--;
		}
		return currentIndex;
	}

	/**
	 * Distributes marbles of the pit chosen by the player around the pits on the
	 * board beginning with the pit after the chosen pit.
	 * 
	 * @param startIndex
	 *            - the starting index to begin distributing marbles
	 * @param marbles
	 *            - the number of marbles to distribute
	 */
	public void distributeMarbles(int startIndex, int marbles) {
		int numberOfMarbles = marbles;
		int currentIndex = startIndex;

		while (numberOfMarbles > 0) {
			if (currentIndex == 5 && p == Player.TWO) {
				currentIndex += 2;
			}

			else if (currentIndex == 12 && p == Player.ONE) {
				currentIndex = 0;
			}

			else {
				currentIndex++;
			}

			if (currentIndex == 14) {
				currentIndex = 0;
			}
			pitShapes.get(currentIndex).setMarbles(pitShapes.get(currentIndex).getMarbles() + 1);
			numberOfMarbles--;
		}
	}

	/**
	 * Checks to see if conditions have been met where the player wins marbles from
	 * the other player depending on the location where the last pit dropped on the
	 * board. The last pit must be empty and on the current player's side.
	 * 
	 * @param lastMarbleDropped
	 *            - the index of the pit where the last marble dropped.
	 */
	private void wonOpponentMarbles(int lastMarbleDropped) {
		int mancala = 6;
		if (lastMarbleDropped == 6 || lastMarbleDropped == 13) {
			return;
		} else if (pitShapes.get(lastMarbleDropped).getMarbles() == 1
				&& pitShapes.get(lastMarbleDropped).getPlayer() == p) {
			if (pitShapes.get(12 - lastMarbleDropped).getMarbles() == 0) {
				return;
			}
			if (p == Player.ONE) {
				mancala = 6;
			} else {
				mancala = 13;
			}

			pitShapes.get(mancala).setMarbles(
					pitShapes.get(mancala).getMarbles() + pitShapes.get(12 - lastMarbleDropped).getMarbles());
			pitShapes.get(mancala)
					.setMarbles(pitShapes.get(mancala).getMarbles() + pitShapes.get(lastMarbleDropped).getMarbles());
			pitShapes.get(12 - lastMarbleDropped).setMarbles(0);
			pitShapes.get(lastMarbleDropped).setMarbles(0);
		}
	}

	/**
	 * Begins the sequence of actions after a player chooses a particular pit on the
	 * board.
	 * 
	 * @param pitShape
	 *            - the chosen pit by the player.
	 */
	public void choosePit(PitShape pitShape) {
		countUndo = 0;
		if (pitShape.getPlayer() != p)
			return;
		if (pitShape.getMarbles() == 0)
			return;
		for (PitShape pitt : pitShapes) {
			previousState[pitt.getIndex()] = pitt.getMarbles();
		}

		extraTurn = getExtraTurn(pitShape);
		int lastMarbleDropped = getLastMarble(pitShape);
		int numberOfMarbles = pitShape.getMarbles();
		pitShape.setMarbles(0);

		distributeMarbles(pitShape.getIndex(), numberOfMarbles);
		wonOpponentMarbles(lastMarbleDropped);
		if (gameOver()) {
			clearBoard();
		}
		if (!extraTurn) {
			switchPlayer();
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Executes a check to see if the current player gets an extra turn depending on
	 * the condition where the last marble dropped from the chosen pit by the player
	 * is the player's mancala.
	 * 
	 * @param pitShape
	 *            - the pit chosen by the player
	 * @return - a boolean determining whether or not the player receives an extra
	 *         turn
	 */
	private boolean getExtraTurn(PitShape pitShape) {
		int totalMoves = pitShape.getIndex() + pitShape.getMarbles();
		if ((totalMoves - 6) % 13 == 0 && p == Player.ONE) {
			return true;
		}
		if ((totalMoves - 13) % 13 == 0 && p == Player.TWO) {
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if conditions have been met to end the game -- the game ends if
	 * any player's set of pits are empty.
	 * 
	 * @return a boolean determining if game end conditions are satisfied
	 */
	public boolean gameOver() {
		boolean emptyRow = true;
		for (int i = 0; i < 6; i++) {
			if (pitShapes.get(i).getMarbles() != 0) {
				emptyRow = false;
			}
		}
		if (emptyRow) {
			return emptyRow;
		}

		emptyRow = true;

		for (int i = 7; i < 13; i++) {
			if (pitShapes.get(i).getMarbles() != 0) {
				emptyRow = false;
			}
		}

		return emptyRow;
	}

	/**
	 * Checks if an undo action is valid and performs an undo function where the
	 * previous state of the gameplay is restored.
	 */
	public void undo() {
		if (prevState() && canUndo()) {
			again = true;
			int counter = 0;
			for (PitShape p : pitShapes) {
				p.setMarbles(previousState[counter]);
				counter++;
			}
			countUndo = 1;
			if (!extraTurn && again) {
				again = false;
				switchPlayer();
			}
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Gets the ArrayList containing the pits.
	 * 
	 * @return an arraylist of Pits.
	 */
	public ArrayList<PitShape> getData() {
		return pitShapes;
	}

	/**
	 * Gets the mancala of a given player
	 * 
	 * @param player
	 *            - the player to whom the mancala belongs
	 * @return - the mancala object belonging to the player.
	 */
	public Mancala getMancala(Player player) {
		for (PitShape pat : pitShapes) {
			if (pat.getPlayer() == player && pat instanceof Mancala) {
				return (Mancala) pat;
			}
		}
		return null;
	}

	/**
	 * Switches the player status between Player.ONE and Player.TWO
	 */
	private void switchPlayer() {
		if (p == Player.ONE) {
			p = Player.TWO;
		}

		else {
			p = Player.ONE;
		}
		extraTurn = false;
	}

	/**
	 * Checks to see if a previous state exists for the current snapshot of the
	 * distribution of marbles
	 * 
	 * @return a boolean determining if the previous state exists.
	 */
	private boolean prevState() {
		boolean hasPrev = false;
		for (int i : previousState) {
			if (previousState[i] == 0)
				hasPrev = false;
			else {
				hasPrev = true;
				break;
			}
		}
		if (hasPrev)
			return true;
		else {
			return false;
		}
	}

	/**
	 * Checks the allowable conditions for a player to undo an action.
	 * 
	 * @return a boolean, true if the player can undo, false if not.
	 */
	private boolean canUndo() {
		if (countUndo == 1)
			return false;
		return true;
	}

	/**
	 * Clears the pits on the board to be zero
	 */
	private void clearBoard() {
		for (int i = 0; i < 6; i++) {
			pitShapes.get(6).setMarbles(pitShapes.get(6).getMarbles() + pitShapes.get(i).getMarbles());
			pitShapes.get(i).setMarbles(0);
		}
		for (int i = 7; i < 13; i++) {
			pitShapes.get(13).setMarbles(pitShapes.get(13).getMarbles() + pitShapes.get(i).getMarbles());
			pitShapes.get(i).setMarbles(0);
		}
	}
}
