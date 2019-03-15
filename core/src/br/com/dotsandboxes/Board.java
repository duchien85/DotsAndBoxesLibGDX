package br.com.dotsandboxes;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

//Classe tabuleiro
class Board {
	private int tamanhoX;
	private int tamanhoY;
	private Dot[][] verticalDot;
	private Dot[][] horizontalDot;
	private ArrayList<Dot> nullDot = new ArrayList<Dot>();

	private Random rand;

	public int change;

	public Player player1;
	public Player player2;
	public Player computer;	

	// Get tamanhoY
	public int getTamanhoX() {
		return tamanhoX;
	}

	// Get tamanhoY
	public int getTamanhoY() {
		return tamanhoY;
	}

	// Construtor
	public Board(int tamanhoX, int tamanhoY) {
		this.tamanhoX = tamanhoX;
		this.tamanhoY = tamanhoY;
		rand = new Random(System.currentTimeMillis());
		verticalDot = new Dot[this.tamanhoX][this.tamanhoY];
		horizontalDot = new Dot[this.tamanhoX][this.tamanhoY];
		player1 = new Player("Dot/BoxPlayer.png", this.tamanhoX - 1, this.tamanhoY - 1, 1);
		player2 = new Player( "Dot/BoxComputer.png", this.tamanhoX - 1, this.tamanhoY - 1, 2);
		computer = new Player( "Dot/BoxComputer.png", this.tamanhoX - 1, this.tamanhoY - 1, 3);
		change = 1;
		for (int x = 0; x < this.tamanhoX; x++) {
			for (int y = 0; y < this.tamanhoY; y++) {
				verticalDot[x][y] = new Dot(100 + 80 + (x * 60), 50 + 15 + (y * 60), 1);
				horizontalDot[x][y] = new Dot(100 + 80 + (x * 60), 50 + 15 + (y * 60), 0);
			}
		}
	}

	// Destroi elementos
	public void disposeBoard() {
		for (int x = 0; x < tamanhoX; x++) {
			for (int y = 0; y < tamanhoY; y++) {
				verticalDot[x][y].diposeDot();
			}
		}
		player1.disposePlayer();
		player2.disposePlayer();
		computer.disposePlayer();
	}

	// Reinicia o tabuleiro
	public void resetBoard(boolean reset) {
		if (reset == true) {
			for (int x = 0; x < tamanhoX; x++) {
				for (int y = 0; y < tamanhoY; y++) {
					verticalDot[x][y].setEstado(0);
					horizontalDot[x][y].setEstado(0);
				}
			}
			player1.resetPlayer();
			player2.resetPlayer();
			computer.resetPlayer();
			change = 1;
			reset = false;
		}
	}

	// Desenha o tabuleiro
	public void drawBoard(SpriteBatch batch, int modoJogo) {
		if (modoJogo == 1) {
			for (int x = 0; x < tamanhoX; x++) {
				for (int y = 0; y < tamanhoY; y++) {
					verticalDot[x][y].drawDot(batch);
					horizontalDot[x][y].drawDot(batch);
					if (x < tamanhoX - 1 && y < tamanhoY - 1) {
						player1.markBox[x][y].drawBox(batch);
						player2.markBox[x][y].drawBox(batch);
					}

				}
			}

			player1.markPointPlayer();
			player2.markPointPlayer();
		}

		if (modoJogo == 0) {
			for (int x = 0; x < tamanhoX; x++) {
				for (int y = 0; y < tamanhoY; y++) {
					verticalDot[x][y].drawDot(batch);
					horizontalDot[x][y].drawDot(batch);
					if (x < tamanhoX - 1 && y < tamanhoY - 1) {
						player1.markBox[x][y].drawBox(batch);
						computer.markBox[x][y].drawBox(batch);
					}

				}
			}

			player1.markPointPlayer();
			computer.markPointPlayer();
		}

	}

	// Eventos do tabuleiro
	public void eventBoard(SpriteBatch batch, OrthographicCamera camera, Vector3 touch, int modoJogo) {
		mouseBoard();

		if (modoJogo == 1) {
			if (change == player1.getMarkPlayer()) {
				clickBoard(camera, touch, player1, player2);
				checkBoxBoard(batch, player1, player2);
			}
			if (change == player2.getMarkPlayer()) {

				clickBoard(camera, touch, player2, player1);
				checkBoxBoard(batch, player2, player1);
			}
		}
		if (modoJogo == 0) {

			if (change == player1.getMarkPlayer()) {
				clickBoard(camera, touch, player1, computer);
				checkBoxBoard(batch, player1, computer);
			}
			if (change == computer.getMarkPlayer()) {
				clickComputerBoard(computer, player1);
				checkBoxBoard(batch, computer, player1);
			}

		}
	}

	// Verifica o local do mouse no tabuleiro
	public void mouseBoard() {

		for (int x = 0; x < tamanhoX; x++) {
			for (int y = 0; y < tamanhoY - 1; y++) {

				if (verticalDot[x][y].getEstado() != 1 && verticalDot[x][y].getEstado() != 3
						&& verticalDot[x][y].getEstado() != 4) {
					if ((Gdx.input.getX() > (180 + (x * 60))) && (Gdx.input.getX() < (180 + (x * 60) + 14))) {
						if ((480 - Gdx.input.getY() > (65 + ((y * 60) + 14)))
								&& (480 - Gdx.input.getY() < (65 + (y * 60) + 60))) {
							verticalDot[x][y].setEstado(2);
						} else {
							verticalDot[x][y].setEstado(0);
						}

					} else {
						verticalDot[x][y].setEstado(0);
					}
				}
			}
		}

		for (int x = 0; x < tamanhoX - 1; x++) {
			for (int y = 0; y < tamanhoY; y++) {

				if (horizontalDot[x][y].getEstado() != 1 && horizontalDot[x][y].getEstado() != 3
						&& horizontalDot[x][y].getEstado() != 4) {
					if ((Gdx.input.getX() > (180 + (x * 60) + 14)) && (Gdx.input.getX() < (180 + (x * 60) + 60))) {
						if ((480 - Gdx.input.getY() > (65 + ((y * 60))))
								&& (480 - Gdx.input.getY() < (65 + (y * 60) + 14))) {
							horizontalDot[x][y].setEstado(2);

						} else {
							horizontalDot[x][y].setEstado(0);
						}

					} else {
						horizontalDot[x][y].setEstado(0);
					}

				}

			}
		}
	}

	// Marca o tabuleiro
	public void clickBoard(OrthographicCamera auxCamera, Vector3 auxTouch, Player player1, Player player2) {
		if (Gdx.input.justTouched()) {
			auxTouch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			auxCamera.unproject(auxTouch);

			// Vertical
			for (int x = 0; x < tamanhoX; x++) {
				for (int y = 0; y < tamanhoY - 1; y++) {

					if (verticalDot[x][y].getEstado() != 1 && verticalDot[x][y].getEstado() != 3
							&& verticalDot[x][y].getEstado() != 4) {
						if ((auxTouch.x > (180 + (x * 60))) && (auxTouch.x < (180 + (x * 60) + 14))) {
							if ((auxTouch.y > (65 + ((y * 60) + 14))) && (auxTouch.y < (65 + (y * 60) + 60))) {
								verticalDot[x][y].setEstado(1);

								change = player2.getMarkPlayer();
							} else {
								verticalDot[x][y].setEstado(0);
							}

						} else {
							verticalDot[x][y].setEstado(0);
						}
					}
				}
			}

			// Horizontal
			for (int x = 0; x < tamanhoX - 1; x++) {
				for (int y = 0; y < tamanhoY; y++) {
					if (horizontalDot[x][y].getEstado() != 1 && horizontalDot[x][y].getEstado() != 3
							&& horizontalDot[x][y].getEstado() != 4) {
						if ((auxTouch.x > (180 + (x * 60) + 14)) && (auxTouch.x < (180 + (x * 60) + 60))) {
							if ((auxTouch.y > (65 + ((y * 60)))) && (auxTouch.y < (65 + (y * 60) + 14))) {
								horizontalDot[x][y].setEstado(1);

								change = player2.getMarkPlayer();
							} else {
								horizontalDot[x][y].setEstado(0);
							}

						} else {
							horizontalDot[x][y].setEstado(0);
						}

					}

				}
			}

		}

	}

	// Marca o tabuleiro pelo computador
	public void clickComputerBoard(Player player1, Player player2) {

		// Vertical
		for (int x = 0; x < tamanhoX; x++) {
			for (int y = 0; y < tamanhoY - 1; y++) {
				if (verticalDot[x][y].getEstado() != 1 && verticalDot[x][y].getEstado() != 3
						&& verticalDot[x][y].getEstado() != 4) {

					nullDot.add(verticalDot[x][y]);
				}
			}
		}

		// Horizontal
		for (int x = 0; x < tamanhoX - 1; x++) {
			for (int y = 0; y < tamanhoY; y++) {
				if (horizontalDot[x][y].getEstado() != 1 && horizontalDot[x][y].getEstado() != 3
						&& horizontalDot[x][y].getEstado() != 4) {
					nullDot.add(horizontalDot[x][y]);
				}
			}
		}

		// System.out.println(nullDot.size());
		if (nullDot.size() > 0) {

			nullDot.get(rand.nextInt(nullDot.size())).setEstado(1);

			change = player2.getMarkPlayer();
		}

		nullDot.clear();

	}

	// Desenha o quadrado no tabuleiro
	public void checkBoxBoard(SpriteBatch batch, Player player1, Player player2) {
		for (int y = 0; y < tamanhoY - 1; y++) {
			for (int x = 0; x < tamanhoX; x++) {
				if (x < (tamanhoX - 1) && y < (tamanhoY - 1)) {
					if (verticalDot[x][y].getEstado() == 1 && verticalDot[x + 1][y].getEstado() == 1
							&& horizontalDot[x][y].getEstado() == 1 && horizontalDot[x][y + 1].getEstado() == 1) {
						if (x < tamanhoX - 1 && y < tamanhoY - 1) {
							if (player1.markBox[x][y].isVisible() == false
									&& player2.markBox[x][y].isVisible() == false) {

								player1.markBox[x][y].setVisible(true);
								change = player1.getMarkPlayer();

							}
						}
					}
				}

			}
		}
	}
}
