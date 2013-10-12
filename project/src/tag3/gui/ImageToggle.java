package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;
import horsentp.input.InputBridge;
import horsentp.input.MouseDownListener;
import horsentp.input.MouseMoveListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageToggle implements Displayable, MouseDownListener {

    private BufferedImage trueImage, falseImage;
    private Rectangle rect;
    private int x, y;

    public boolean isToggle() {
        return toggle;
    }

    private boolean toggle, enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible;
    private ArrayList<GenericToggleListener> listeners;

    public ImageToggle(BufferedImage trueImage, BufferedImage falseImage, InputBridge bridge, int x, int y, boolean defaultValue) {
        this.falseImage = falseImage; this.trueImage = trueImage;
        this.x = x; this.y = y;
        bridge.addMouseDownListener(this);
        rect = new Rectangle(x, y, trueImage.getWidth(), trueImage.getHeight());
        toggle = defaultValue;
        listeners = new ArrayList<GenericToggleListener>();
        visible = true;
        enabled = true;
    }

    public void addToggleListener(GenericToggleListener gtl) {
        listeners.add(gtl);
    }

    public void clearAllListeners() {
        listeners.clear();
    }

    public void removeToggleListener(GenericToggleListener gtl) {
        listeners.remove(gtl);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        if (isVisible()) {
            graphics2D.drawImage(getUsingImage(), x, y, null);
        }
        return bufferedImage;
    }

    private BufferedImage getUsingImage() {
        if (toggle) {
            return trueImage;
        } else {
            return falseImage;
        }
    }

    private boolean hoverPoint(int x, int y) {
        return rect.contains(x, y);
    }

    public void setLocation(int x, int y) {
        this.x = x; this.y = y;
        rect = new Rectangle(x, y, trueImage.getWidth(), trueImage.getHeight());
    }

    private void notifyListeners() {
        for (int i=0;i<listeners.size(); i++) {
            listeners.get(i).toggleChanged(toggle);
        }
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }

    @Override
    public void reactToMouseDown(MouseEvent mouseEvent) {
        if(hoverPoint(mouseEvent.getX(), mouseEvent.getY()) && isVisible() && isEnabled()) {
            if(toggle) {
                toggle = false;
            } else {
                toggle = true;
            }
            notifyListeners();
        }
    }
}
