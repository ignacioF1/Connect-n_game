package com.darwinex.connectn.game;

import com.darwinex.connectn.Chip;
import com.darwinex.connectn.GameResult;

public interface Game {

	/**
	 * Drops a new chip into the board
	 *
	 * @param chip the type of chip
	 * @param column the column where the player drops the chip
	 */
	void putChip(Chip chip, int column);

	/**
	 * @return the game result if there is a winner, together with the winning chips, or null otherwise
	 * <p>
	 * Note that when the winning streak is longer than the required number (for example in connect-4 when a new chip joins two lines of 2, resulting
	 * in a streak of 5) this method may return any of the smaller 4-chip streaks, or the whole 5-chip streak.
	 */
	GameResult getGameResult();

}
