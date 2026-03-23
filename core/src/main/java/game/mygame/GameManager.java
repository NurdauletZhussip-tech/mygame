package game.mygame;

public class GameManager {
    private static GameManager instance;

    private int score;
    private int lives;
    private boolean gameOver;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void init() {
        score = 0;
        lives = 3;
        gameOver = false;
    }

    public void addScore(int points) {
        score += points;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
        }
    }

    public int getScore()       { return score; }
    public int getLives()       { return lives; }
    public boolean isGameOver() { return gameOver; }
}
