package game.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import game.mygame.entities.Bullet;
import game.mygame.entities.Enemy;
import game.mygame.entities.Player;
import game.mygame.factory.EnemyFactory;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final SpaceShooter game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    private Texture playerTex, bulletTex, bgTex;
    private Texture slowEnemyTex, fastEnemyTex, tankEnemyTex;

    private Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private EnemyFactory enemyFactory;

    private BitmapFont font;
    private float spawnTimer = 0f;
    private float spawnInterval = 2.0f;

    public GameScreen(SpaceShooter game) {
        this.game = game;
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        loadAssets();

        player = new Player(
                Gdx.graphics.getWidth() / 2f - playerTex.getWidth() / 2f,
                60f, playerTex, bulletTex
        );
        enemyFactory = new EnemyFactory(slowEnemyTex, fastEnemyTex, tankEnemyTex);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.5f);

        GameManager.getInstance().init();
    }

    private void loadAssets() {
        bgTex        = new Texture("background.png");
        playerTex    = new Texture("playerShip1_blue.png");
        bulletTex    = new Texture("laserBlue01.png");
        slowEnemyTex = new Texture("enemyBlack1.png");
        fastEnemyTex = new Texture("enemyRed1.png");
        tankEnemyTex = new Texture("enemyGreen1.png");
    }

    @Override
    public void render(float delta) {
        GameManager gm = GameManager.getInstance();
        if (!gm.isGameOver()) update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(bgTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.draw(batch);
        for (Enemy e : enemies) e.draw(batch);

        font.draw(batch, "Score: " + gm.getScore(), 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Lives: " + gm.getLives(),  10, Gdx.graphics.getHeight() - 35);

        if (gm.isGameOver()) {
            font.getData().setScale(3f);
            font.draw(batch, "GAME OVER",
                    Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f);
            font.getData().setScale(1.5f);
        }

        batch.end();
    }

    private void update(float delta) {
        player.update(delta);
        spawnEnemies(delta);
        for (Enemy e : enemies) e.update(delta);
        checkCollisions();
        enemies.removeIf(e -> !e.isAlive());
    }

    private void spawnEnemies(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0f;
            float x = MathUtils.random(20f, Gdx.graphics.getWidth() - 80f);

            int roll = MathUtils.random(2);
            EnemyFactory.EnemyType type = roll == 0
                    ? EnemyFactory.EnemyType.FAST
                    : roll == 1
                    ? EnemyFactory.EnemyType.TANK
                    : EnemyFactory.EnemyType.SLOW;

            enemies.add(enemyFactory.create(type, x, Gdx.graphics.getHeight() + 10f));
            spawnInterval = Math.max(0.5f, spawnInterval - 0.05f);
        }
    }

    private void checkCollisions() {
        GameManager gm = GameManager.getInstance();
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) continue;
            for (Bullet bullet : player.getBullets()) {
                if (!bullet.isAlive()) continue;
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    bullet.destroy();
                    enemy.hit(1);
                    if (!enemy.isAlive()) {
                        gm.addScore(enemy.getScoreValue());
                    }
                }
            }
            if (enemy.getBounds().overlaps(player.getBounds())) {
                enemy.hit(999);
                gm.loseLife();
            }
        }
    }

    @Override
    public void dispose() {
        playerTex.dispose(); bulletTex.dispose(); bgTex.dispose();
        slowEnemyTex.dispose(); fastEnemyTex.dispose(); tankEnemyTex.dispose();
        font.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

}
