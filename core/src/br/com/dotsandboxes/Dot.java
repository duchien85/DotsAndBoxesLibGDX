package br.com.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Classe marcador
class Dot {

	private int x;
	private int y;

	private Texture dotActual;
	private Texture dotNormal;
	private Texture dotMoved;
	private Texture dotMarked;

	private int estado; // Define o estado, marcado, livre ou quadrado

	//Seta o estado
	public void setEstado(int estado) {
		this.estado = estado;
		switch (this.estado) {
		case 0:
			dotActual = dotNormal;
			break;
		case 1:
			dotActual = dotMarked;
			break;
		case 2:
			dotActual = dotMoved;
			break;
		}
		
	}

	//Pega o estado
	public int getEstado() {
		return estado;
	}

	// Construtor
	public Dot(int x, int y, int position) {
		this.x = x;
		this.y = y;
		estado = 0;

		if (position == 0) {
			dotNormal = new Texture(Gdx.files.internal("Dot/DotH.png"));
			dotMoved = new Texture(Gdx.files.internal("Dot/DotMoveH.png"));
			dotMarked = new Texture(Gdx.files.internal("Dot/DotMarkH.png"));
		}else{
			dotNormal = new Texture(Gdx.files.internal("Dot/DotV.png"));
			dotMoved = new Texture(Gdx.files.internal("Dot/DotMoveV.png"));
			dotMarked = new Texture(Gdx.files.internal("Dot/DotMarkV.png"));
		}

		dotActual = dotNormal;
	}

	//Desenha Dot
	public void drawDot(SpriteBatch batch) {
		batch.draw(dotActual, x, y);
	}
	
	//Destroi elementos
	public void diposeDot(){
		dotActual.dispose();
		dotMarked.dispose();
		dotMoved.dispose();
		dotNormal.dispose();
	}

}
