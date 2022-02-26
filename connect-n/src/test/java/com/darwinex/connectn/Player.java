package com.darwinex.connectn;

import java.util.Arrays;

public class Player {

	private final Chip chip;
	private final int[] movements;
	private int movementPointer;

	public Player(final Chip chip, final int... movements) {
		this.chip = chip;
		this.movements = movements;
		this.movementPointer = 0;
	}

	public Chip getChip() {
		return chip;
	}

	public int nextMovement() {
		return movements[movementPointer++];
	}

	public boolean hasMoreMovements() {
		return movementPointer < movements.length;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Player player = (Player) o;

		if (movementPointer != player.movementPointer) {
			return false;
		}
		if (chip != player.chip) {
			return false;
		}
		return Arrays.equals(movements, player.movements);

	}

	@Override
	public int hashCode() {
		int result = chip != null ? chip.hashCode() : 0;
		result = 31 * result + Arrays.hashCode(movements);
		result = 31 * result + movementPointer;
		return result;
	}

	@Override
	public String toString() {
		return "Player{" + "chip=" + chip +
				", movements=" + Arrays.toString(movements) +
				", movementPointer=" + movementPointer +
				'}';
	}
}
