package tag3.gui;

import horsentp.display.DisplayLink;
import horsentp.display.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Starbuck
 * Date: 10/12/13
 * Time: 8:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicGui implements GuiComponent {
    private Map<String, GuiComponent> guiComponents;
    private int unNamedCounter;
    private boolean visibility;

    public BasicGui() {
        unNamedCounter = 0;
        visibility = true;
        guiComponents = new HashMap<String, GuiComponent>();
    }

    public void addComponent(GuiComponent component) {
        this.guiComponents.put("unnamed" + unNamedCounter + "", component);
        unNamedCounter++;
    }

    public void addComponent(String name, GuiComponent component) {
        this.guiComponents.put(name, component);
    }

    public GuiComponent getComponent(String name) {
        return this.guiComponents.get(name);
    }

    public void removeComponent(String name) {
        this.guiComponents.remove(name);
    }


    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        if (!visibility) {
            return bufferedImage;
        }
        for (GuiComponent component : guiComponents.values()) {
            if (component == null) { continue;}
            BufferedImage bImage = component.render(bufferedImage, graphics2D);
            if (bImage != null) {
                bufferedImage = component.render(bufferedImage, graphics2D);
            }
        }
        return bufferedImage;
    }

    @Override
    public void setDisplayLink(DisplayLink displayLink) {

    }

    @Override
    public boolean isVisible() {
        return visibility;
    }

    @Override
    public void setVisible(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public void updateComponent() {
        for (GuiComponent component : guiComponents.values()) {
            if (component == null) {continue;}
            component.updateComponent();
        }
    }

    public int getComponentCount() {
        return guiComponents.size();
    }
}
