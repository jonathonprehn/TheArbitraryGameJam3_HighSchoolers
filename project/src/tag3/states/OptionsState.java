package tag3.states;

import horsentp.display.DisplayConfiguration;
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
public class OptionsState extends GameState {

    private ImageButton backButton, applyButton;
    private ImageToggle fullScreenToggle;
    private SimpleTextField widthField, heightField;
    private ImageLabel optionsBackground;

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

        fullScreenToggle = GraphicsFactory.getFactory().makeImageToggle(
                MediaLoader.quickLoadImage("toggles/genericOn.png"),
                MediaLoader.quickLoadImage("toggles/genericOff.png"),
                (getDisplayer().getDisplayWidth()/6)*2, ((getDisplayer().getDisplayHeight()/5)*3), getDisplayer().isFullScreen()
        );

        applyButton = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/applyChangesButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/applyChangesButtonDown.png"),
                (getDisplayer().getDisplayWidth()/6)*4, (((getDisplayer().getDisplayHeight()/5)*3)+40),
                new ApplyNewDisplay()
        );

        widthField = GraphicsFactory.getFactory().makeTextField(
                (getDisplayer().getDisplayWidth()/3), ((getDisplayer().getDisplayHeight()/6)*2) ,
                100, 40, "" + getDisplayer().getDisplayWidth() + "" , 4
        );
        heightField = GraphicsFactory.getFactory().makeTextField(
                (getDisplayer().getDisplayWidth()/3), ((getDisplayer().getDisplayHeight()/6)*3) ,
                100, 40, "" + getDisplayer().getDisplayHeight() + "" , 4
        );
        optionsBackground = new ImageLabel(MediaLoader.quickLoadImage("optionsBackground.png"), 0, 0);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage = optionsBackground.render(bufferedImage, graphics2D);
        bufferedImage = backButton.render(bufferedImage, graphics2D);
        bufferedImage = widthField.render(bufferedImage, graphics2D);
        bufferedImage = heightField.render(bufferedImage, graphics2D);
        bufferedImage = applyButton.render(bufferedImage, graphics2D);
        bufferedImage = fullScreenToggle.render(bufferedImage, graphics2D);

        return bufferedImage;
    }

    class ApplyNewDisplay implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            getDisplayer().destroyAllDisplays();
            //Reality checks
            int newWidth = getDisplayer().getDisplayWidth();
            int newHeight = getDisplayer().getDisplayHeight();
            boolean fullScreen = fullScreenToggle.isToggle();
            try {
                newWidth = Integer.parseInt(widthField.getText());
                newHeight = Integer.parseInt(heightField.getText());
            } catch(Exception e) {
                System.out.println("Need to be acceptable widths and heights!");
            }
            DisplayConfiguration newConfig;
            if (fullScreen) {
                newConfig = new DisplayConfiguration(newWidth, newHeight, DisplayConfiguration.FULLSCREEN);
            } else {
                newConfig = new DisplayConfiguration(newWidth, newHeight, DisplayConfiguration.STATIC_WINDOW);
            }
            getDisplayer().setDisplayConfiguration(newConfig);
            getRunner().relinkDisplayer();
            getRunner().changeState(new OptionsState());
        }
    }
}
