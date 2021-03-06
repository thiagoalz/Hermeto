package net.thiagoalz.hermeto.panel;

import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.player.IPlayer;


/**
 * Contain all the states of the game, so all the components can easily 
 * access the state of the game.
 * 
 */
public interface IGameContext {
	/**
	 * Retrieve the dimension of the game context, it will be returned 
	 * as an {@code} array where the first {@code int} is the columns 
	 * and the second is the lines.
	 *  
	 * @return An {@code int[]} like [columns, lines]. 
	 */
	public int[] getDimensions();
	
	/**
	 * Retrieve all the players of the game. The collection will never be empty, 
	 * because the Master DJ player will always be playing.
	 * 
	 * @return A {@code Map} collection with all the player in the game.
	 */
	public Map<String, IPlayer> getAllPlayers();
	
	
	/**
	 * Retrieve the Master DJ player.
	 * 
	 * @return A {@code Player} instance that is the Master DJ.
	 */
	public IPlayer getMasterDJ();
	
	/**
	 * Return {@code TRUE} if the game is executing the music. 
	 * Otherwise return {@code FALSE}. 
	 * 
	 * @return
	 */
	public boolean isPlaying();
	
	/**
	 * Return the current instrument selected.
	 * 
	 * @return The current instrument selected.
	 */
	public InstrumentType getCurrentInstrumentType();
	
	/**
	 * Retrieve all the squares that are marked to execute in the panel.
	 * @return A set with all the positions that are marked.
	 */
	//public Set<Position> getMarkedSquares();
	
	/**
	 * Retrieve all the square that are marked in a column
	 * 
	 * @param The column number that will be search. 
	 * @return A set with all the positions that are marked. 
	 */
	//public List<Position> getColumnMarkedSquares(int column);
	
	/**
	 * Retrieve all the square that are marked in a column
	 * 
	 * @param The column number that will be search.
	 * @return A set with all the positions that are marked. 
	 */
	//public List<Position> getRowMarkedSquares(int row);
	
}
