package br.com.dotsandboxes.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.com.dotsandboxes.DotsAndBoxes;

public class DesktopLauncher {

	// Main
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		configurar(config);
		new LwjglApplication(new DotsAndBoxes(), config);
	}

	// Configurações da tela
	private static void configurar(LwjglApplicationConfiguration config) {
		config.height = 480;
		config.width = 640;
		config.fullscreen = false;
		config.resizable = false;
		config.useGL30 = false;
		config.backgroundFPS = 30;
		config.x = -1;
		config.y = -1;
		config.title = "Dots and Boxes";
	}
}
