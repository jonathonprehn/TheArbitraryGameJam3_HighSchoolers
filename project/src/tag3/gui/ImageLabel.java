package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 8:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageLabel implements Displayable {

    private BufferedImage image;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int x;
    private int y;

    public ImageLabel(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        visible = true;
    }
    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        if (isVisible()) {
            graphics2D.drawImage(image, this.x, this.y, null);
        }
        return bufferedImage;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
