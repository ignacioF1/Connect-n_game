package com.darwinex.connectn;

/**
 * Describes the position for a chip, just indicating the row and column coordinates
 */
public class ChipPosition {
	private final int row;
	private final int column;

	public ChipPosition(final int row, final int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ChipPosition)) {
			return false;
		}

		final ChipPosition that = (ChipPosition) o;

		if (row != that.row) {
			return false;
		}
		return column == that.column;

	}

	@Override
	public int hashCode() {
		int result = row;
		result = 31 * result + column;
		return result;
	}

	@Override
	public String toString() {
		return "[" + row + ',' + column + ']';
	}
}
