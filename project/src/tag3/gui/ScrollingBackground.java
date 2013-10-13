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

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    private int verticalOffset;
    private boolean visible;

    public boolean isScrolling() {
        return scrolling;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }

    private boolean scrolling;

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
        if (scrolling) {
            scrollOffset += scrollSpeed;
            if(scrollOffset>=img.getImage().getWidth()) {
                scrollOffset = 0;
            }
        }
    }

    public ScrollingBackground(BufferedImage scrollImage, float scrollSpeed) {
        this.img = new ImageLabel(scrollImage, 0, 0);
        this.scrollOffset = 0;
        this.scrollSpeed = scrollSpeed;
        visible = true;
        verticalOffset = 0;
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        if (isVisible()) {
            graphics2D.drawImage(img.getImage(), (int)scrollOffset, verticalOffset, null);
            graphics2D.drawImage(img.getImage(), (int)(scrollOffset-(img.getImage().getWidth())), verticalOffset, null);
        }
        return bufferedImage;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }
}
