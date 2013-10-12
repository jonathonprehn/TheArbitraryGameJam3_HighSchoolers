package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 8:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextLabel implements GuiComponent {

    private boolean visible;
    private String text;
    private int x, y;
    private Color textColor;
    private Font font;

    public TextLabel(String text, int x, int y) {
        this.text = text;
        this.x = x; this.y = y;
        textColor = Color.BLACK;
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        visible = true;
    }

    public String getText() {
        return text;
    }

    public void setLocation(int x, int y) {
        this.x = x; this.y = y;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFontSize(int fontSize) {
        setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    @Override
    public void updateComponent() {

    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        if (isVisible()) {
            graphics2D.setColor(textColor);
            graphics2D.setFont(font);
            graphics2D.drawString(text, x, y);
        }
        return bufferedImage;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }
}
