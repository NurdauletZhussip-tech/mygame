package game.mygame.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.mygame.Main;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Knight Game");
        config.setWindowedMode(1000, 1000);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new Main(), config);
    }
}
