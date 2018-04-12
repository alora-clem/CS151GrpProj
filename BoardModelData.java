//package groupProject;

import java.awt.event.*;
import java.util.*;

/**
 * Group Project
 * author @AloraClem
 * Version 1
 * Represents the Model portion of MVC
 * Hold data of the Mancala board
 */

public class BoardModelData {
	//INSTANCE VARIABLES

	//Data of model starting with player 1's home pit 
	//and working counter-clockwise around the board
	private ArrayList<Integer> currentBoardData;
	//used for undo button
	private ArrayList<Integer> previousBoardData;
	//array list for action listeners
	private ArrayList<ActionListener> listenerArray;
	
	/**
    Constructor to create board model to hold data
    Postcondition: the array lists holding data are created
	 */
	public BoardModelData(){
		currentBoardData = new ArrayList<>();
		previousBoardData = new ArrayList<>();
		listenerArray = new ArrayList<>();
	}
	
	/**
    To update the array list of data after a move has been made
    @param index to change
    Precondition: index holds more than one pebble
    Postcondition: the pit will be empty and distributed to the other pits
	 */
	public void updateData(int index){
		//save the board before changing it
		previousBoardData = new ArrayList<>(currentBoardData);
		//check that the pit has marbles in it
		if(currentBoardData.get(index)>0){
			//save the amount of marbles
			int marbles = currentBoardData.get(index);
			//take all the marbles from the pit
			currentBoardData.set(index, 0);
			//distribute to the next pits until you run out of marbles
			while (marbles != 0){
				//traverse the array of pits
				index ++;
				//add a marble to the pit
				currentBoardData.set(index, (currentBoardData.get(index)+1));
				marbles--;
			}
		}
	}
	
	/**
    To return the value of marbles in a pit
    @param index to retrieve
    Precondition: index must be a valid index
    Postcondition: the number in the pit will be returned
	 */
	public int getMarblesInPit(int index){
		return currentBoardData.get(index);
	}
	
	/**
    To undo the last move
    Postcondition: the current board is set to the previous board
	 */
	public void undo(){
		currentBoardData = new ArrayList<>(previousBoardData);
	}
	
	/**
    To attach a listener to the model
    @param l ActionListener that signals a change in data
    Postcondition: the model will have the listener attached in the array list
	 */
	public void attachListener(ActionListener l){
		listenerArray.add(l);
	}
}
