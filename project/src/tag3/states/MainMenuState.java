package tag3.states;

import horsentp.gamelogic.EmptyGameState;
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
        //Try to get screen size for positioning
        int width = getScreenWidth();
        int height = getScreenHeight();
        int buttonWidth = 120;
        int buttonHeight = 60;
        buttons = new ImageButton[3];
        //Play button
        buttons[0] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/playButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/playButtonDown.png"),
                (width/2)-buttonWidth, ((height/5)*2)-buttonHeight, new PlayGame()
        );
        //Options button
        buttons[1] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/optionsButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/optionsButtonDown.png"),
                (width/2)-buttonWidth, ((height/5)*3)-buttonHeight, new OptionsGame()
        );
        //Quit button
        buttons[2] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/quitButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/quitButtonDown.png"),
                (width/2)-buttonWidth, ((height/5)*4)-buttonHeight, new QuitGame()
        );
    }

    private int getScreenWidth() {
        try {
        Component comp = (Component)getDisplayer().getCanvas();
        return comp.getWidth();
        } catch (Exception e) {
            System.out.println("Unable to get Screen width!");
            return 0;
        }
    }

    private int getScreenHeight() {
        try {
            Component comp = (Component)getDisplayer().getCanvas();
            return comp.getHeight();
        } catch (Exception e) {
            System.out.println("Unable to get Screen height!");
            return 0;
        }
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
            for (int i=0; i< buttons.length; i++) {
                if (buttons[i]!=null) {
                    bufferedImage = buttons[i].render(bufferedImage, graphics2D);
                }
            }
        return bufferedImage;
    }

    class PlayGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            getRunner().changeState(new PlayState());
        }
    }

    class QuitGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            System.out.println("Trying to quit");
            getRunner().exitGame();
        }
    }

    class OptionsGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            getRunner().changeState(new OptionsState());
        }
    }
}
