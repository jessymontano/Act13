import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Pong extends JComponent implements Runnable, KeyListener{
    private Paddles paddles;
    private Ellipse2D.Double ball;
    private Ball b;
    private int player1Score=0, player2Score=0;
    private String score1="0", score2="0";
    private Rectangle2D.Double player1, player2;
    final int WIDTH = 900, HEIGHT = 500, DIAMETER = 20, RECT_HEIGHT= 100, RECT_WIDTH = 20;
    private final Dimension SCREEN_SIZE = new Dimension(WIDTH, HEIGHT);

    Pong(){
        setOpaque(true);
        player1 = new Rectangle2D.Double(0, HEIGHT/2 - RECT_HEIGHT/2, RECT_WIDTH, RECT_HEIGHT);
        player2 = new Rectangle2D.Double(WIDTH - RECT_WIDTH, HEIGHT/2 - RECT_HEIGHT/2, RECT_WIDTH, RECT_HEIGHT);
        paddles = new Paddles(player1, player2);
        ball = new Ellipse2D.Double(WIDTH/2, HEIGHT/2, DIAMETER, DIAMETER);
        b = new Ball(ball, paddles);
        Thread t1 = new Thread(b);
        t1.start();
        Thread t2 = new Thread(this);
        t2.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.draw(new Line2D.Double(WIDTH/2, 0, WIDTH/2,HEIGHT));
        g2.setFont(new Font("LucidaSans", Font.PLAIN, 45));
        g2.drawString(score1, WIDTH/3, HEIGHT/10);
        g2.drawString(score2, 2*WIDTH/3, HEIGHT/10);
        g2.fill(ball);
        g2.fill(player1);
        g2.fill(player2);
    }

    public void run(){
        while(true){
            if(ball.x < 0 ){
                newPaddles();
                newBall();
                player2Score ++;
                score2 = Integer.toString(player2Score);
            }
            if(ball.x > WIDTH){
                newPaddles();
                newBall();
                player1Score ++;
                score1 = Integer.toString(player1Score);
            }
            paddles.move1();
            paddles.move2();
            repaint();
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public Dimension getPreferredSize(){
        return SCREEN_SIZE;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            paddles.vy1 = -10; 
            break;
            case KeyEvent.VK_S:
            paddles.vy1 = 10; 
            break;
            case KeyEvent.VK_UP:
            paddles.vy2 = -10; 
            break;
            case KeyEvent.VK_DOWN:
            paddles.vy2 = 10; 
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            paddles.vy1 = 0; 
            break;
            case KeyEvent.VK_S:
            paddles.vy1 = 0; 
            break;
            case KeyEvent.VK_UP:
            paddles.vy2 = 0; 
            break;
            case KeyEvent.VK_DOWN:
            paddles.vy2 = 0; 
            break;
        }
    }

    private void newPaddles(){
        player1 = new Rectangle2D.Double(0, HEIGHT/2 - RECT_HEIGHT/2, RECT_WIDTH, RECT_HEIGHT);
        player2 = new Rectangle2D.Double(WIDTH - RECT_WIDTH, HEIGHT/2 - RECT_HEIGHT/2, RECT_WIDTH, RECT_HEIGHT);
        paddles = new Paddles(player1, player2);
    }

    private void newBall(){
        ball = new Ellipse2D.Double(WIDTH/2, HEIGHT/2, DIAMETER, DIAMETER);
        Ball b = new Ball(ball, paddles);
        Thread t1 = new Thread(b);
        t1.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong");
        JPanel panel = new JPanel(new BorderLayout());
        Pong t = new Pong();
        panel.add(t, BorderLayout.CENTER);
        frame.setFocusable(true);
        frame.addKeyListener(t);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
