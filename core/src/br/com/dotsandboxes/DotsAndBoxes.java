package br.com.dotsandboxes;

import com.badlogic.gdx.ApplicationAdapter;

//Classe DotsAndBoxes e Main
public class DotsAndBoxes extends ApplicationAdapter {
	private Screen screen;
	private Board board;
	
	//Inicializa
	@Override
	public void create () {
		screen = new Screen();
		board = new Board(7,6);
		board.resetBoard(screen.reset);
	}

	//Game loop
	@Override
	public void render () {
		screen.gameScreen(board);
	}
	
	//Finaliza
	@Override
	public void dispose () {
		screen.disposeScreen();
		board.disposeBoard();
	}
}
