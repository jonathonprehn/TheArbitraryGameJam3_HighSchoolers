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

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }

}
