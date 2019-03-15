package br.com.dotsandboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

//Classe tela
class Screen {
	SpriteBatch batch;

	private Texture screenActual;
	private Texture screenMenu;
	private Texture screenGame;
	private Texture screenHelp;
	private Texture screenEnd;
	private Texture screenMode;
	private TextureRegion buttonPlay;
	private TextureRegion buttonSair;
	private TextureRegion buttonHelp;
	private TextureRegion scoreBoard;
	private TextureRegion buttonPvP;
	private TextureRegion buttonPvC;
	private Texture selectPlayer;
	private Vector3 touch;

	private BitmapFont scoreFontPlayer1;
	private BitmapFont scoreFontPlayer2;

	private Music musicMain;
	private Sound soundClick;
	private Sound soundEnd;

	private OrthographicCamera camera;

	private int modoJogo;

	public boolean reset;

	// Construtor
	public Screen() {
		batch = new SpriteBatch();
		screenMenu = new Texture(Gdx.files.internal("Telas/Menu.png"));
		screenGame = new Texture(Gdx.files.internal("Telas/InGame.png"));
		screenHelp = new Texture(Gdx.files.internal("Telas/Help.png"));
		screenEnd = new Texture(Gdx.files.internal("Telas/End.png"));
		screenMode = new Texture(Gdx.files.internal("Telas/Mode.png"));

		buttonPlay = new TextureRegion(new Texture(Gdx.files.internal("Telas/BotaoPlay.png")), 0, 0, 300, 150);
		buttonSair = new TextureRegion(new Texture(Gdx.files.internal("Telas/BotaoSair.png")), 0, 0, 52, 47);
		buttonHelp = new TextureRegion(new Texture(Gdx.files.internal("Telas/BotaoHelp.png")), 0, 0, 52, 47);
		buttonPvP = new TextureRegion(new Texture(Gdx.files.internal("Telas/BotaoPvP.png")), 0, 0, 500, 100);
		buttonPvC = new TextureRegion(new Texture(Gdx.files.internal("Telas/BotaoPvC.png")), 0, 0, 500, 100);

		scoreBoard = new TextureRegion(new Texture(Gdx.files.internal("Telas/Placar.png")), 0, 0, 373, 39);

		selectPlayer = new Texture(Gdx.files.internal("Telas/Time.png"));

		musicMain = Gdx.audio.newMusic(Gdx.files.internal("Sons/MainSong.mp3"));
		soundClick = Gdx.audio.newSound(Gdx.files.internal("Sons/Click.mp3"));
		soundEnd = Gdx.audio.newSound(Gdx.files.internal("Sons/End.mp3"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);

		screenActual = screenMenu;
		musicMain.setLooping(true);
		musicMain.play();

		scoreFontPlayer1 = new BitmapFont();
		scoreFontPlayer1.getData().setScale(2);
		scoreFontPlayer1.setColor(Color.DARK_GRAY);
		scoreFontPlayer2 = new BitmapFont();
		scoreFontPlayer2.getData().setScale(2);
		scoreFontPlayer2.setColor(Color.DARK_GRAY);
		reset = true;

		touch = new Vector3();
	}

	// Destruir os elementos
	public void disposeScreen() {
		batch.dispose();
		screenMenu.dispose();
		screenGame.dispose();
		screenHelp.dispose();
		screenEnd.dispose();
		screenMode.dispose();
		scoreBoard.getTexture().dispose();
		buttonPlay.getTexture().dispose();
		buttonSair.getTexture().dispose();
		buttonHelp.getTexture().dispose();
		selectPlayer.dispose();
		musicMain.dispose();
		soundClick.dispose();
		soundEnd.dispose();
		scoreFontPlayer1.dispose();
		scoreFontPlayer2.dispose();
		screenActual.dispose();
	}

	// Desenha a tela
	public void drawScreen(Board board) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(screenActual, 0, 0);

		// Menu
		if (screenActual == screenMenu) {
			board.resetBoard(reset);
			batch.draw(buttonPlay, 188, 164);
			batch.draw(buttonHelp, 8, 12);
		}

		// Help
		if (screenActual == screenHelp) {
			batch.draw(buttonSair, 6, 424);
		}

		// Mode
		if (screenActual == screenMode) {
			batch.draw(buttonPvC, 100, 480 - 420);
			batch.draw(buttonPvP, 100, 480 - 158);
			batch.draw(buttonSair, 6, 424);
		}

		// Game
		if (screenActual == screenGame) {
			
			//Player vs Player
			if (modoJogo == 1) {
				if ((board.player1.numScorePlayer
						+ board.player2.numScorePlayer <= (((board.getTamanhoX() - 1) * (board.getTamanhoY() - 1))-1))) {

					board.drawBoard(batch, modoJogo);
					batch.draw(scoreBoard, 73, 428);
					
					scoreFontPlayer1.draw(batch, board.player1.getScorePlayer(), 190, 460);
					scoreFontPlayer2.draw(batch, board.player2.getScorePlayer(), 433, 460);
					if (board.change == board.player1.getMarkPlayer()) {
						batch.draw(selectPlayer, 76, 428);
					} else {
						batch.draw(selectPlayer, 76 + 242, 428);
					}

				} else {
					soundEnd.play();
					screenActual = screenEnd;
				}
			}
			
			//Player vs Computer
			if(modoJogo == 0){
				if ((board.player1.numScorePlayer
						+ board.computer.numScorePlayer != ((board.getTamanhoX() - 1) * (board.getTamanhoY() - 1)))) {
					board.drawBoard(batch, modoJogo);
					batch.draw(scoreBoard, 73, 428);
					
					scoreFontPlayer1.draw(batch, board.computer.getScorePlayer(), 433, 460);
					scoreFontPlayer2.draw(batch, board.player1.getScorePlayer(), 190, 460);
				}else {
					soundEnd.play();
					screenActual = screenEnd;
				}
			}

			batch.draw(buttonSair, 6, 424);
			reset = true;
		}

		// End
		if (screenActual == screenEnd) {
			if(modoJogo == 1){
				scoreFontPlayer1.draw(batch, "Player 1: "+board.player1.getScorePlayer(), 253, 240);
				scoreFontPlayer2.draw(batch, "Player 2: "+board.player2.getScorePlayer(), 253, 190);
			}
			if(modoJogo == 0){
				scoreFontPlayer1.draw(batch, "Player 1: "+board.player1.getScorePlayer(), 253, 240);
				scoreFontPlayer2.draw(batch, "Computer: "+board.computer.getScorePlayer(), 253, 190);
			}
			
			batch.draw(buttonSair, 6, 424);
		}
		batch.end();
	}

	// Eventos da tela
	public void eventScreen(Board board) {
		// Menu
		if (screenActual == screenMenu) {
			motionScreen(buttonPlay, 188, 487, 164, 313, 1, 0);
			screenActual = clickScreen(screenMode, screenActual, camera, touch, 188, 487, 164, 313);
			motionScreen(buttonHelp, 6, 58, 421, 468, 1, 0);
			screenActual = clickScreen(screenHelp, screenActual, camera, touch, 6, 58, 12, 59);
		}

		// Help
		if (screenActual == screenHelp) {
			motionScreen(buttonSair, 6, 57, 4, 53, 1, 0);
			screenActual = clickScreen(screenMenu, screenActual, camera, touch, 6, 57, 424, 480);
		}

		// Mode
		if (screenActual == screenMode) {
			motionScreen(buttonPvP, 100, 600, 58, 158, 1, 0);
			screenActual = clickScreen(screenGame, screenActual, camera, touch, 100, 600, 480 - 158, 480 - 58, 1);
			motionScreen(buttonPvC, 100, 600, 320, 420, 1, 0);
			screenActual = clickScreen(screenGame, screenActual, camera, touch, 100, 600, 480 - 420, 480 - 320, 0);
			motionScreen(buttonSair, 6, 57, 6, 53, 1, 0);
			screenActual = clickScreen(screenMenu, screenActual, camera, touch, 6, 57, 424, 480);
			scoreBoard.setRegion(modoJogo * 373, 0, 373, 39);
		}

		// Game
		if (screenActual == screenGame) {
			motionScreen(buttonSair, 6, 57, 6, 53, 1, 0);
			screenActual = clickScreen(screenMenu, screenActual, camera, touch, 6, 57, 424, 480);

			board.eventBoard(batch, camera, touch, modoJogo);
		}

		// End
		if (screenActual == screenEnd) {
			motionScreen(buttonSair, 6, 57, 6, 53, 1, 0);
			screenActual = clickScreen(screenMenu, screenActual, camera, touch, 6, 57, 424, 480);
		}
	}

	// Verifica as Telas
	public void gameScreen(Board board) {
		drawScreen(board);
		eventScreen(board);
	}

	// Verfica algum click para mudar a tela
	private Texture clickScreen(Texture screenNew, Texture screenCurrent, OrthographicCamera auxCamera,
			Vector3 auxTouch, int xInicial, int xFinal, int yInicial, int yFinal) {

		if (Gdx.input.justTouched()) {
			auxTouch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			auxCamera.unproject(auxTouch);
			if ((auxTouch.x > xInicial && auxTouch.y > yInicial) && (auxTouch.x < xFinal && auxTouch.y < yFinal)) {
				soundClick.play();
				return screenNew;
			}
		}
		return screenCurrent;

	}

	// Verifca algum movimento para mudar o botao
	private void motionScreen(TextureRegion botaoChange, int xInicial, int xFinal, int yInicial, int yFinal, int tamX,
			int tamY) {

		if ((Gdx.input.getX() > xInicial && Gdx.input.getY() > yInicial)
				&& (Gdx.input.getX() < xFinal && Gdx.input.getY() < yFinal)) {
			botaoChange.setRegion(botaoChange.getRegionWidth() * tamX, botaoChange.getRegionHeight() * tamY,
					botaoChange.getRegionWidth(), botaoChange.getRegionHeight());
		} else {
			botaoChange.setRegion(0, 0, botaoChange.getRegionWidth(), botaoChange.getRegionHeight());
		}

	}

	// Verifica algum click para mudar a tela e setar o modo de jogo
	private Texture clickScreen(Texture screenNew, Texture screenCurrent, OrthographicCamera auxCamera,
			Vector3 auxTouch, int xInicial, int xFinal, int yInicial, int yFinal, int modoJogo) {
		if (Gdx.input.justTouched()) {
			auxTouch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			auxCamera.unproject(auxTouch);
			if ((auxTouch.x > xInicial && auxTouch.y > yInicial) && (auxTouch.x < xFinal && auxTouch.y < yFinal)) {
				soundClick.play();
				this.modoJogo = modoJogo;
				return screenNew;
			}
		}
		return screenCurrent;
	}

}
