import java.awt.geom.*;
import java.awt.*;

public class Ball implements Runnable{
    private final int MAX_WIDTH = 900, MAX_HEIGHT = 500;
    private Paddles paddles;
    private int x = MAX_WIDTH/2, y = MAX_HEIGHT/2, vx, vy;
    private final int DIAMETER=20;
    private Ellipse2D.Double ball;

    Ball(Ellipse2D.Double shape, Paddles paddles){
        ball = shape;
        vx = getRandomV();
        vy = getRandomV();
        this.paddles = paddles;
        ball.y = y;
        ball.x = x;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }

    private boolean checkCollision(){
        return paddles.getBounds1().intersects(getBounds()) || paddles.getBounds2().intersects(getBounds());
    }

    private int getRandomV(){
        boolean id = Math.random()<0.5;
        if(id) return 10;
        return -10;
    }

    public void run(){
        while(true){
            if(y + vy < 0 || y + DIAMETER + vy > MAX_HEIGHT){
                vy *= -1;
            }
            if(checkCollision()){
                vx *= -1;
            }

            y += vy;
            x += vx;

            ball.y = y;
            ball.x = x;

            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}