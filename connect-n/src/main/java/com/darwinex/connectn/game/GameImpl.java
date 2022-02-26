package com.darwinex.connectn.game;

import com.darwinex.connectn.ChipPosition;
import com.darwinex.connectn.GameResult;
import com.darwinex.connectn.Chip;
import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {
	//class-global variables
	int rows;
	int columns;
	int n;
	int[][] matrix;
	List<ChipPosition> chipPositionRed;
	List<ChipPosition> chipPositionYellow;

	/**
	 * @param rows    the number of rows in the board
	 * @param columns the number of columns in the board
	 * @param n       the number of connected chips required to win the game
	 */
	public GameImpl(final int rows, final int columns, final int n) {
		try {
			this.rows = rows;
			this.columns = columns;
			this.n = n;
			matrix = new int[rows][columns];
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Parameters 'rows', 'columns' or 'n' out of boundaries!");
		}
	}

	/**
	 * Receives and stores each player´s next move
	 * Matrix filled with chip color: 0-empty, 1-red, 2-yellow
	 * @param chip, chip color
	 * @param column, column where the chip was placed
	*/
	@Override
	public void putChip(final Chip chip, final int column) {
		try {
			for (int i = 0; i < rows + 1; i++) {
				if (matrix[i][column] == 0) {
					matrix[i][column] = chip.ordinal() + 1;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Parameters 'chip' or 'column' out of boundaries!");
		}
	}

	/**
	 * Calculates the game result
	 * @throws GameResult, contains (if exists) the winner chip colour and their positions
	 */
	@Override
	public GameResult getGameResult() {
		GameResult gameResult = null;
		try {
			//Test rows
			for (int i = 0; i < rows; i++) {
				chipPositionRed = new ArrayList<ChipPosition>();
				chipPositionYellow = new ArrayList<ChipPosition>();
				for (int j = 0; j < columns; j++) {
					if (matrix[i][j] == 1) {
						chipPositionRed.add(new ChipPosition(i, j));
					} else if (matrix[i][j] == 2) {
						chipPositionYellow.add(new ChipPosition(i, j));
					}
				}
				gameResult = findSequence();
				if (gameResult != null) {
					return gameResult;
				}
			}
			//Test columns
			for (int j = 0; j < columns; j++) {
				chipPositionRed = new ArrayList<ChipPosition>();
				chipPositionYellow = new ArrayList<ChipPosition>();
				for (int i = 0; i < rows; i++) {
					if (matrix[i][j] == 1) {
						chipPositionRed.add(new ChipPosition(i, j));
					} else if (matrix[i][j] == 2) {
						chipPositionYellow.add(new ChipPosition(i, j));
					}
				}
				gameResult = findSequence();
				if (gameResult != null) {
					return gameResult;
				}
			}
			//Test diagonals
			//if the game matrix has equal or more than n rows and columns, take out the diagonals and analyse them
			if (rows >= n && columns >= n) {
				int x = 0;
				int y;
				chipPositionRed = new ArrayList<ChipPosition>();
				chipPositionYellow = new ArrayList<ChipPosition>();
				//45° Top left and center diagonals
				for (int i = 0; i < rows - n; i++) {
					for (int j = 0; j < columns; j++) {
						x = i + j;
						if (matrix[x][j] == 1) {
							chipPositionRed.add(new ChipPosition(x, j));
						} else if (matrix[x][j] == 2) {
							chipPositionYellow.add(new ChipPosition(x, j));
						}
						if (x == rows - 1) {
							break;
						}
					}
					gameResult = findSequence();
					if (gameResult != null) {
						return gameResult;
					}
				}
				//45° Bottom right diagonals
				for (int i = 1; i < columns - n; i++) {
					chipPositionRed = new ArrayList<ChipPosition>();
					chipPositionYellow = new ArrayList<ChipPosition>();
					for (int j = 0; j < columns && j < rows; j++) {
						x = j + i;
						if (matrix[j][x] == 1) {
							chipPositionRed.add(new ChipPosition(j, x));
						} else if (matrix[j][x] == 2) {
							chipPositionYellow.add(new ChipPosition(j, x));
						}
						if (x == columns - 1) {
							break;
						}
					}
					gameResult = findSequence();
					if (gameResult != null) {
						return gameResult;
					}
				}
				//-45° Bottom left and center diagonals
				for (int i = rows - 1; i > n - 2; i--) {
					chipPositionRed = new ArrayList<ChipPosition>();
					chipPositionYellow = new ArrayList<ChipPosition>();
					for (int j = 0; j < columns && j < rows; j++) {
						x = i - j;
						if (matrix[x][j] == 1) {
							chipPositionRed.add(new ChipPosition(x, j));
						} else if (matrix[x][j] == 2) {
							chipPositionYellow.add(new ChipPosition(x, j));
						}
						if (x == 0) {
							break;
						}
					}
					gameResult = findSequence();
					if (gameResult != null) {
						return gameResult;
					}
				}
				//-45° Top right diagonals
				for (int j = 1; j < columns - n + 1; j++) {
					int z = rows - 1;
					chipPositionRed = new ArrayList<ChipPosition>();
					chipPositionYellow = new ArrayList<ChipPosition>();
					for (int i = 0; i < columns && i < rows; i++) {
						x = z - i;
						y = j + i;
						if (matrix[x][y] == 1) {
							chipPositionRed.add(new ChipPosition(x, y));
						} else if (matrix[x][y] == 2) {
							chipPositionYellow.add(new ChipPosition(x, y));
						}
						if (x == 0 || y == columns - 1) {
							break;
						}
					}
					gameResult = findSequence();
					if (gameResult != null) {
						return gameResult;
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Cannot calculate game result, input problem!");
		}
	}

			/**
			 * Checks for a sequence of n repetitions in the received chipPositionRed and chipPositionYellow arrays
			 * @throws GameResult (if exist) with the winning chips' color and their positions
			 */
			private GameResult findSequence () {
				Chip chip;
				GameResult gameResult = null;
				int cCol;
				int cRow;
				int countRed = 0;
				int countYellow = 0;
				//TEST rows
				for (int i = 0; i < chipPositionRed.size() - 1; i++) {
					if (chipPositionRed.get(i).getRow()+1 == chipPositionRed.get(i+1).getRow()) {
						countRed++;
					}else{
						countRed = 0;
					}
				}
				//TEST yellow
				countRed = 0;
				countYellow = 0;
				for (int i = 0; i < chipPositionYellow.size() - 1; i++) {
					if (chipPositionYellow.get(i).getRow()+1 == chipPositionYellow.get(i+1).getRow()) {
						countYellow++;
					}else{
						countYellow = 0;
					}
				}
				//TEST red
				if (countRed + 1 == n) {
					chip = Chip.RED;
					gameResult = new GameResult(chip, chipPositionRed.toArray(new ChipPosition[0]));
					return gameResult;
				} else if (countYellow + 1 == n) {
					chip = Chip.YELLOW;
					gameResult = new GameResult(chip, chipPositionYellow.toArray(new ChipPosition[0]));
					return gameResult;
				}
				//TEST columns
				countRed = 0;
				countYellow = 0;
				for (int i = 0; i < chipPositionRed.size() - 1; i++) {
					if (chipPositionRed.get(i).getColumn()+1 == chipPositionRed.get(i+1).getColumn()) {
						countRed++;
					}else{
						countRed = 0;
					}
				}
				//TEST yellow
				for (int i = 0; i < chipPositionYellow.size() - 1; i++) {
					if (chipPositionYellow.get(i).getColumn()+1 == chipPositionYellow.get(i+1).getColumn()) {
						countYellow++;
					}else{
						countYellow = 0;
					}
				}
				//TEST red
				if (countRed + 1 == n) {
					chip = Chip.RED;
					gameResult = new GameResult(chip, chipPositionRed.toArray(new ChipPosition[0]));
					return gameResult;
				} else if (countYellow + 1 == n) {
					chip = Chip.YELLOW;
					gameResult = new GameResult(chip, chipPositionYellow.toArray(new ChipPosition[0]));
					return gameResult;
				}
				return null;
			}
}
