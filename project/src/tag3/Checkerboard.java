package tag3;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;
import horsentp.display.Displayer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Starbuck
 * Date: 10/11/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Checkerboard implements Displayable {

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D.fill(new Rectangle2D.Double(0,0,20,20));
        return bufferedImage;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(0,0,20,20);
        g2.setColor(Color.WHITE);
        g2.fillRect(20,0,20,20);
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,20,20);
        g2.setColor(Color.WHITE);
        g2.fillRect(20,0,20,20);
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }

}
