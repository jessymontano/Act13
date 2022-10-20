import java.awt.*;
import java.awt.geom.*;

public class Paddles{
    private Rectangle2D.Double player1, player2;
    private final int MAX_WIDTH = 900, MAX_HEIGHT = 500;
    private final int WIDTH = 30, HEIGHT = 100;
    int y1 = MAX_HEIGHT/2 - HEIGHT/2, y2 = MAX_HEIGHT/2 - HEIGHT/2, vy1, vy2;

    Paddles(Rectangle2D.Double p1, Rectangle2D.Double p2){
        vy1 = 0;
        vy2 = 0;
        player1 = p1;
        player1.y = y1;
        player2 = p2;
        player2.y = y2;
    }

    public void move1(){ 
        y1 += vy1;
        player1.y = y1;
        if(player1.y < 0) player1.y = 0;
        if(player1.y > MAX_HEIGHT - HEIGHT) player1.y = MAX_HEIGHT - HEIGHT;
    }

    public void move2(){
        y2 += vy2;
        player2.y = y2;
        if(player2.y < 0) player2.y = 0;
        if(player2.y > MAX_HEIGHT - HEIGHT) player2.y = MAX_HEIGHT - HEIGHT;
    }

    public Rectangle getBounds1(){
        return new Rectangle(0, y1, WIDTH, HEIGHT);
    }

    public Rectangle getBounds2(){
        return new Rectangle(MAX_WIDTH-30, y2, WIDTH, HEIGHT);
    }
}
