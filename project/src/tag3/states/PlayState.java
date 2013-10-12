package tag3.states;

import horsentp.gamelogic.GameState;
import tag3.gui.*;
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
public class PlayState extends GameState {

    //Graphics and UI
    protected ImageLabel[] labels;
    protected ImageButton[] buttons;
    protected ImageToggle[] toggles;
    private ImageLabel background;

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {
    buttons = new ImageButton[2]; //Sleep and manage buttons
    buttons[0] = GraphicsFactory.getFactory().makeLinkedImageButton(
            MediaLoader.quickLoadImage("play_state_images/sleepUp.png"),
            MediaLoader.quickLoadImage("play_state_images/sleepDown.png"),
            0, 0, new SleepButtonListener()
    );
     buttons[1] = GraphicsFactory.getFactory().makeLinkedImageButton(
            MediaLoader.quickLoadImage("play_state_images/manageUp.png"),
            MediaLoader.quickLoadImage("play_state_images/manageDown.png"),
            150, 0, new ManageButtonListener()
     );

    toggles = new ImageToggle[2]; //More info and move toggles
    toggles[0] = GraphicsFactory.getFactory().makeLinkedImageToggle(
                MediaLoader.quickLoadImage("play_state_images/moving.png"),
                MediaLoader.quickLoadImage("play_state_images/notMoving.png"),
                300, 0, false, new MovementToggleListener()
        );
    toggles[1] = GraphicsFactory.getFactory().makeLinkedImageToggle(
            MediaLoader.quickLoadImage("play_state_images/moreInfoOn.png"),
            MediaLoader.quickLoadImage("play_state_images/moreInfoOff.png"),
            725, 70, false, new MoreInfoListener()
    );

    labels = new ImageLabel[3]; //Info bar, quick info, and much info
    labels[0] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/quickInfoBackground.png"), 500, 0);
    labels[1] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/muchInfoBackground.png"), 500, 100);
    labels[2] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/distanceBar.png"), 0, 100);

    labels[1].setVisible(false);
    background = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/gameBackground.png"), 0, 0);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        bufferedImage = background.render(bufferedImage, graphics2D);
        for (int i=0; i<labels.length; i++) {
            bufferedImage = labels[i].render(bufferedImage, graphics2D);
        }
        for (int i=0; i<buttons.length; i++) {
            bufferedImage = buttons[i].render(bufferedImage, graphics2D);
        }
        for (int i=0; i<toggles.length; i++) {
            bufferedImage = toggles[i].render(bufferedImage, graphics2D);
        }
        return bufferedImage;
    }

    class SleepButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class ManageButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class MoreInfoListener implements GenericToggleListener {
        @Override
        public void toggleChanged(boolean valueSetTo) {
            labels[1].setVisible(valueSetTo);
        }
    }

    class MovementToggleListener implements GenericToggleListener {
        @Override
        public void toggleChanged(boolean valueSetTo) {

        }
    }
}
