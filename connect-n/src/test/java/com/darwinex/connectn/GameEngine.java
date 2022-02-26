package com.darwinex.connectn;

import com.darwinex.connectn.game.Game;

public class GameEngine {

	public GameResult play(final Game game, final Player playerA, final Player playerB) {
		GameResult gameResult;
		while (playerA.hasMoreMovements() && playerB.hasMoreMovements()) {
			gameResult = play(game, playerA);
			if (gameResult != null) {
				return gameResult;
			}

			gameResult = play(game, playerB);
			if (gameResult != null) {
				return gameResult;
			}
		}
		return null;
	}

	private GameResult play(final Game game, final Player player) {
		game.putChip(player.getChip(), player.nextMovement());
		return game.getGameResult();
	}

}
