package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;
import horsentp.input.InputBridge;
import horsentp.input.KeyDownListener;
import horsentp.input.MouseDownListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTextField implements Displayable, KeyDownListener, MouseDownListener {

    private int x, y, width, height, maxText;
    private String text;
    private boolean focused;
    public SimpleTextField(int x, int y, int width, int height, int maxText, String defaultText, InputBridge bridge) {
        this.x = x; this.y = y; this.width = width; this.height = height; this.maxText = maxText;
        bridge.addMouseDownListener(this);
        bridge.addKeyDownListener(this);
        this.text = defaultText;
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(getUsingColor());
        graphics2D.fillRect(x, y, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+10, y+(height/3));
        return bufferedImage;
    }

    public String getText() {
        return text;
    }

    private Color getUsingColor() {
        if(focused) {
            return new Color(200, 200, 200);
        } else {
            return Color.WHITE;
        }
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reactToKeyDown(KeyEvent keyEvent) {
        if (focused) {
            switch(keyEvent.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE:
                    int textLength = text.length();
                    if (textLength>0) {
                        text = text.substring(0, (textLength-1));
                    } else {
                        text = "";
                    }
                    break;
                default:
                    if (text.length()<maxText) {
                        text = text + keyEvent.getKeyChar();
                    }
            }
        }
    }

    @Override
    public void reactToMouseDown(MouseEvent mouseEvent) {
        Rectangle rect=  new Rectangle(x, y, width, height);
        if (rect.contains(mouseEvent.getX(), mouseEvent.getY())) {
            focused = true;
        } else {
            focused = false;
        }
    }
}
