package tag3.gui;

import horsentp.display.Displayable;

/**
 * Make text labels and image labels compatible
 */
public interface GuiComponent extends Displayable{
    /**
     * Gets if the label will be displayed.
     * @return the visibility of the label
     */
    public boolean isVisible();

    /**
     * Sets the visibility of the label to be displayed
     * @param visibility The new visibility of the component.
     */
    public void setVisible(boolean visibility);

    /**
     * Tick for animation, unused currently
     */
    public void updateComponent();
}
