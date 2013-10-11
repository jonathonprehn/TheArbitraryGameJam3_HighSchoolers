package tag3.states;

import horsentp.gamelogic.GameState;
import tag3.gui.GenericButtonListener;
import tag3.gui.ImageButton;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuState extends GameState {

    private ImageButton[] buttons;

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {

    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();

        return bufferedImage;
    }

    class PlayGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {

        }
    }

    class QuitGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {

        }
    }

    class OptionsGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {

        }
    }
}
