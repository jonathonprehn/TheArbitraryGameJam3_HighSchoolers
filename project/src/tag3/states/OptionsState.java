package tag3.states;

import horsentp.display.DisplayConfiguration;
import horsentp.gamelogic.GameState;
import tag3.gui.GenericToggleListener;
import tag3.gui.ImageButton;
import tag3.gui.ToMainMenuListener;
import tag3.media.MediaLoader;
import tag3.utility.GraphicsFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class OptionsState extends GameState {

    private ImageButton backButton;

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {
        backButton = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/mainMenuButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/mainMenuButtonDown.png"),
                (getDisplayer().getDisplayWidth()/5), ((getDisplayer().getDisplayHeight()/5)*4), new ToMainMenuListener(getRunner())
        );
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        bufferedImage = backButton.render(bufferedImage, graphics2D);

        return bufferedImage;
    }

    class FullScreenToggle implements GenericToggleListener {

        @Override
        public void toggleChanged(boolean valueSetTo) {
            if (valueSetTo) {
                getRunner().getDisplayer().setDisplayConfiguration(new DisplayConfiguration(getRunner().getDisplayer().getDisplayWidth(), getRunner().getDisplayer().getDisplayHeight(), DisplayConfiguration.FULLSCREEN));
            } else {
                getRunner().getDisplayer().setDisplayConfiguration(new DisplayConfiguration(getRunner().getDisplayer().getDisplayWidth(), getRunner().getDisplayer().getDisplayHeight(), DisplayConfiguration.STATIC_WINDOW));
            }
        }
    }
}
