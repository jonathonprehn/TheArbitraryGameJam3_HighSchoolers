package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;
import horsentp.input.InputBridge;
import horsentp.input.MouseDownListener;
import horsentp.input.MouseMoveListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageButton implements Displayable , MouseDownListener, MouseMoveListener{

    private BufferedImage upImage, downImage;
    private Rectangle rect;
    private int x, y;
    private boolean hovering;
    private ArrayList<GenericButtonListener> listeners;

    public ImageButton(BufferedImage upImage, BufferedImage downImage, InputBridge input, int x, int y) {
        this.upImage = upImage;
        this.downImage = downImage;
        this.x = x; this.y = y;
        rect = new Rectangle(x, y, upImage.getWidth(), upImage.getHeight());
        listeners = new ArrayList<GenericButtonListener>();
        input.addMouseDownListener(this);
        input.addMouseMoveListener(this);
        hovering = false;
    }


    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.drawImage(getUsingImage(), this.x, this.y, null);
        return bufferedImage;
    }

    private boolean hoverPoint(int x, int y) {
        return rect.contains(x, y);
    }

    private BufferedImage getUsingImage() {
        if (hovering) {
            return downImage;
        } else {
            return upImage;
        }
    }

    public void setLocation(int x, int y) {
        this.x = x; this.y = y;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }

    public void addButtonListener(GenericButtonListener gbl) {
        listeners.add(gbl);
    }

    public void removeButtonListener(GenericButtonListener gbl) {
        listeners.remove(gbl);
    }

    public void removeAllListeners() {
        listeners.clear();
    }

    private void notifyListeners() {
        for (int i=0;i<listeners.size(); i++) {
            listeners.get(i).buttonPushed();
        }
    }

    @Override
    public void reactToMouseDown(MouseEvent mouseEvent) {
        if (hovering) {
            notifyListeners();
        }
    }

    @Override
    public void reactToMouseMove(MouseEvent mouseEvent) {
        if (hoverPoint(mouseEvent.getX(), mouseEvent.getY())) {
            hovering = true;
        } else {
            hovering = false;
        }
    }
}
