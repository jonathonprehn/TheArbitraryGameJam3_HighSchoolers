package tag3.utility;

import horsentp.display.Displayer;
import horsentp.display.ImageDisplay;
import horsentp.input.InputBridge;
import tag3.gui.*;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsFactory {

    private static GraphicsFactory factory = new GraphicsFactory();

    private Displayer displayer;
    private InputBridge bridge;

    public void initFactory(Displayer displayer, InputBridge bridge) {
        this.displayer = displayer;
        this.bridge = bridge;
    }

    public static GraphicsFactory getFactory() {
        return factory;
    }

    public ImageDisplay makeImageDisplayable(BufferedImage image) {
        return displayer.createImageDisplay(image);
    }

    public ImageButton makeImageButton(BufferedImage upImage, BufferedImage downImage, int x, int y) {
        return new ImageButton(upImage, downImage, bridge, x, y);
    }

    public ImageButton makeLinkedImageButton(BufferedImage upImage, BufferedImage downImage, int x, int y, GenericButtonListener... listeners) {
        ImageButton button = new ImageButton(upImage, downImage, bridge, x, y);
        for (int i=0; i<listeners.length; i++) {
            button.addButtonListener(listeners[i]);
        }
        return button;
    }

    public SimpleTextField makeTextField(int x, int y, int width, int height, String defaultText, int maxText) {
        return new SimpleTextField(x, y, width, height, maxText, defaultText, bridge);
    }

    public ImageToggle makeImageToggle(BufferedImage trueImage, BufferedImage falseImage, int x, int y, boolean defaultValue) {
        return new ImageToggle(trueImage, falseImage, bridge, x, y, defaultValue);
    }

    public ImageToggle makeLinkedImageToggle(BufferedImage trueImage, BufferedImage falseImage, int x, int y, boolean defaultValue, GenericToggleListener... listeners) {
        ImageToggle toggle= new ImageToggle(trueImage, falseImage, bridge, x, y, defaultValue);
        for (int i=0; i<listeners.length; i++) {
            toggle.addToggleListener(listeners[i]);
        }
        return toggle;
    }
}
