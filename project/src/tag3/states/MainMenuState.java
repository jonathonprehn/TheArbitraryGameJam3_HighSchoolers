package tag3.states;

import horsentp.gamelogic.EmptyGameState;
import horsentp.gamelogic.GameRunner;
import horsentp.gamelogic.GameState;
import tag3.gui.GenericButtonListener;
import tag3.gui.ImageButton;
import tag3.gui.ImageLabel;
import tag3.gui.ScrollingBackground;
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
    private ImageLabel credit, title;
    private ScrollingBackground[] backgrounds;

    @Override
    public void updateLogic() {
        for (int i=0; i<backgrounds.length; i++) {
            backgrounds[i].updateComponent();
        }
    }

    @Override
    public void initState() {
        //Try to get screen size for positioning
        int width = getRunner().getDisplayer().getDisplayWidth();
        int height = getRunner().getDisplayer().getDisplayHeight();
        int buttonWidth = 120;
        int buttonHeight = 60;
        buttons = new ImageButton[4];
        //Play button
        buttons[0] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/playButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/playButtonDown.png"),
                (width/2)-(buttonWidth/2), ((height/5)*2)-(buttonHeight/2), new PlayGame()
        );
        //Options button
        buttons[1] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/optionsButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/optionsButtonDown.png"),
                (width/2)-(buttonWidth/2)-100, ((height/5)*3)-(buttonHeight/2), new OptionsGame()
        );
        //Quit button
        buttons[2] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/quitButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/quitButtonDown.png"),
                (width/2)-(buttonWidth/2), ((height/5)*4)-(buttonHeight/2), new QuitGame()
        );
        //Help button
        buttons[3] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("buttons/helpButtonUp.png"),
                MediaLoader.quickLoadImage("buttons/helpButtonDown.png"),
                (width/2)-(buttonWidth/2)+100, ((height/5)*3)-(buttonHeight/2), new HelpGame()
        );

        //Init scrolling backgrounds
        backgrounds = new ScrollingBackground[4];

        backgrounds[3] = new ScrollingBackground(MediaLoader.quickLoadImage("play_state_images/gameBackground.png"), 0.8f);
        backgrounds[2] = new ScrollingBackground(MediaLoader.quickLoadImage("play_state_images/treeBackground.png"), 0.3f);
        backgrounds[1] = new ScrollingBackground(MediaLoader.quickLoadImage("play_state_images/hillBackground.png"), 0.15f);
        backgrounds[0] = new ScrollingBackground(MediaLoader.quickLoadImage("play_state_images/cloudBackground.png"), 0.08f);
        backgrounds[0].setVerticalOffset(-80);
        for (int i=0; i<backgrounds.length; i++) {
            backgrounds[i].setScrolling(true);
        }
        title = new ImageLabel(MediaLoader.quickLoadImage("main_menu/title.png"), 0, 0);
        credit = new ImageLabel(MediaLoader.quickLoadImage("main_menu/credit.png"), 25, 100);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(new Color(110, 210, 230));
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        for (int i=0; i<backgrounds.length; i++) {
            bufferedImage = backgrounds[i].render(bufferedImage, graphics2D);
        }
        for (int i=0; i< buttons.length; i++) {
            if (buttons[i]!=null) {
                bufferedImage = buttons[i].render(bufferedImage, graphics2D);
            }
        }
        bufferedImage = credit.render(bufferedImage, graphics2D);
        bufferedImage = title.render(bufferedImage, graphics2D);
        return bufferedImage;
    }

    class PlayGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            getRunner().changeState(new LoadingState(new LoadNewState(getRunner(), new PlayState())));
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
            getRunner().changeState(new LoadingState(new LoadNewState(getRunner(), new OptionsState())));
        }
    }

    class HelpGame implements GenericButtonListener {

        @Override
        public void buttonPushed() {
            getRunner().changeState(new LoadingState(new LoadNewState(getRunner(), new HelpState())));
        }
    }

    class LoadNewState implements LoadStateCommand {
        private GameRunner runner;
        private GameState state;
        public LoadNewState(GameRunner runner, GameState state) {
            this.runner = runner;
            this.state = state;
        }
        @Override
        public void doForLoading() {
            runner.changeState(state);
        }
    }
}
