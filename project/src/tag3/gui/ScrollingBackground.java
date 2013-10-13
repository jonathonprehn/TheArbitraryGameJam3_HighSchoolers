package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/13/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScrollingBackground implements GuiComponent {

    private ImageLabel img;
    private float scrollOffset, scrollSpeed;
    private boolean visible;

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visibility) {
        visible = visibility;
    }

    @Override
    public void updateComponent() {
        scrollOffset += scrollSpeed;
        if(scrollOffset>=img.getImage().getWidth()) {
            scrollOffset = 0;
        }
    }

    public ScrollingBackground(BufferedImage scrollImage, float scrollSpeed) {
        this.img = new ImageLabel(scrollImage, 0, 0);
        this.scrollOffset = 0;
        this.scrollSpeed = scrollSpeed;
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        if (isVisible()) {
            graphics2D.drawImage(img.getImage(), (int)scrollOffset, 0, null);
            graphics2D.drawImage(img.getImage(), (int)(scrollOffset-(img.getImage().getWidth())), 0, null);
        }
        return bufferedImage;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }
}
