package tag3.states;

import horsentp.gamelogic.GameState;
import tag3.gui.GenericButtonListener;
import tag3.gui.ImageButton;
import tag3.media.MediaLoader;
import tag3.utility.GraphicsFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelpState extends GameState {

    private ImageButton backButton;

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {

        backButton = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/mainMenuButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/mainMenuButtonDown.png"),
                (getDisplayer().getDisplayWidth()/6), ((getDisplayer().getDisplayHeight()/5)*4),
                new GenericButtonListener() {
                    @Override
                    public void buttonPushed() {
                        getRunner().changeState(new MainMenuState());
                    }
                }
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
}
