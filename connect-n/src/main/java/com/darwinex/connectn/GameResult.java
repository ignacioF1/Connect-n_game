package com.darwinex.connectn;

import java.util.Arrays;

/**
 * Describes the result of a ConnectN game
 */
public class GameResult {

	private final Chip chip;
	private final ChipPosition[] positions;

	/**
	 * Builds a {@link GameResult} for a game that is won by one of the players
	 *
	 * @param chip the player that won the game
	 * @param positions the positions that make the winning chain of chips
	 */
	public GameResult(final Chip chip, final ChipPosition[] positions) {
		this.chip = chip;
		this.positions = positions;
	}

	public Chip getChip() {
		return chip;
	}

	public ChipPosition[] getPositions() {
		return positions;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final GameResult that = (GameResult) o;

		if (chip != that.chip) {
			return false;
		}
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(positions, that.positions);

	}

	@Override
	public int hashCode() {
		int result = chip != null ? chip.hashCode() : 0;
		result = 31 * result + Arrays.hashCode(positions);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("The winner is: ").append(chip).append("!, with positions: ");
		for (final ChipPosition winnerChipPosition : positions) {
			builder.append(winnerChipPosition);
		}
		return builder.toString();
	}
}
