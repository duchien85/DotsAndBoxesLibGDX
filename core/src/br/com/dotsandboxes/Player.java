package br.com.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Classe jogador
class Player {
	private int tamX, tamY;
	private String scorePlayer;
	public int numScorePlayer;
	public Box[][] markBox;

	private int markPlayer;

	// Construtor Player
	public Player( String path, int tamX, int tamY, int marcador) {
		numScorePlayer = 0;
		scorePlayer = "" + numScorePlayer;
		markPlayer = marcador;
		this.tamX = tamX;
		this.tamY = tamY;

		markBox = new Box[this.tamX][this.tamY];
		for (int x = 0; x < tamX; x++) {
			for (int y = 0; y < tamY; y++) {
				markBox[x][y] = new Box(path, 194 + ((x * 46) + (14 * x)), (79 + (y * 46) + (14 * y)));
			}
		}
	}

	// Get marcação PLayer
	public int getMarkPlayer() {
		return markPlayer;
	}

	// Resetar player
	public void resetPlayer() {
		numScorePlayer = 0;
		scorePlayer = "" + numScorePlayer;
		for (int x = 0; x < tamX; x++) {
			for (int y = 0; y < tamY; y++) {
				markBox[x][y].setVisible(false);
			}
		}
	}

	// Marcar pontuação
	public void markPointPlayer() {
		int aux = 0;
		for (int x = 0; x < tamX; x++) {
			for (int y = 0; y < tamY; y++) {
				if (markBox[x][y].isVisible() == true) {
					aux++;
				}
			}
		}
		numScorePlayer = aux;
		scorePlayer = "" + numScorePlayer;

	}

	// Destroi elementos
	public void disposePlayer() {
		for (int x = 0; x < tamX; x++) {
			for (int y = 0; y < tamY; y++) {
				markBox[x][y].diposeBox();
			}
		}
	}

	// Desenha os Boxes do player
	public void drawBoxPlayer(SpriteBatch batch) {
		for (int x = 0; x < tamX; x++) {
			for (int y = 0; y < tamY; y++) {
				markBox[x][y].drawBox(batch);
			}
		}
	}

	// Get score
	public String getScorePlayer() {
		return scorePlayer;
	}

	// Set score
	public void setScorePlayer(int scorePlayer) {
		this.scorePlayer = "" + scorePlayer;
	}

}

// Classe marcação de caixas
class Box {
	private Texture box;
	private boolean visible;
	private int x, y;

	// Construtor
	public Box(String path, int x, int y) {
		visible = false;
		this.x = x;
		this.y = y;
		box = new Texture(Gdx.files.internal(path));
	}

	// Desenha a caixa
	public void drawBox(SpriteBatch batch) {
		if (visible == true) {
			batch.draw(box, x, y);
		}
	}

	// Get visible
	public boolean isVisible() {
		return visible;
	}

	// Set visible
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	// Destroi elementos
	public void diposeBox() {
		box.dispose();
	}

}
