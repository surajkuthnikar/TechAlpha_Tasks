import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements KeyListener {
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final int UNIT_SIZE = 20;
    private final int DELAY = 100;

    private int[] x = new int[WIDTH / UNIT_SIZE];
    private int[] y = new int[HEIGHT / UNIT_SIZE];
    private int score = 0;
    private int foodX, foodY;
    private char direction = 'R';
    private boolean running = false;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        startGame();
    }

    public void startGame() {
        running = true;
        score = 0;
        direction = 'R';
        for (int i = 0; i < x.length; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        foodX = (int) (Math.random() * (WIDTH / UNIT_SIZE));
        foodY = (int) (Math.random() * (HEIGHT / UNIT_SIZE));
        Timer timer = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSnake();
                checkCollision();
                repaint();
            }
        });
        timer.start();
    }

    public void moveSnake() {
        for (int i = x.length - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (direction == 'R') {
            x[0] += UNIT_SIZE;
        } else if (direction == 'L') {
            x[0] -= UNIT_SIZE;
        } else if (direction == 'U') {
            y[0] -= UNIT_SIZE;
        } else if (direction == 'D') {
            y[0] += UNIT_SIZE;
        }
    }

    public void checkCollision() {
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }
        for (int i = 1; i < x.length; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        if (x[0] == foodX * UNIT_SIZE && y[0] == foodY * UNIT_SIZE) {
            score++;
            foodX = (int) (Math.random() * (WIDTH / UNIT_SIZE));
            foodY = (int) (Math.random() * (HEIGHT / UNIT_SIZE));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);
            g.setColor(Color.RED);
            g.fillRect(foodX * UNIT_SIZE, foodY * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.GREEN);
            for (int i = 0; i < x.length; i++) {
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over! Score: " + score, WIDTH / 2 - 50, HEIGHT / 2);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (key == KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        } else if (key == KeyEvent.VK_UP && direction != 'D') {
            direction = 'U';
        } else if (key == KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}