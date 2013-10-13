package tag3.states;

import horsentp.gamelogic.GameState;
import horsentp.input.KeyDownListener;
import horsentp.input.MouseDownListener;
import tag3.gui.ImageLabel;
import tag3.gui.TextLabel;
import tag3.media.MediaLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public class GameOverState extends GameState implements KeyDownListener, MouseDownListener {

    private TextLabel gameOverText;
    private ImageLabel background;

    public GameOverState(String reason) {
        gameOverText = new TextLabel(reason, 400-(reason.length()*7), 300);
    }

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {
        gameOverText.setTextColor(Color.RED);
        gameOverText.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        background = new ImageLabel(MediaLoader.quickLoadImage("game_over/gameOverScreen.png"), 0, 0);

        getInput().addKeyDownListener(this);
        getInput().addMouseDownListener(this);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D) bufferedImage.getGraphics();
        bufferedImage = background.render(bufferedImage, graphics2D);
        bufferedImage = gameOverText.render(bufferedImage, graphics2D);
        return bufferedImage;
    }

    @Override
    public void reactToMouseDown(MouseEvent mouseEvent) {
        getRunner().changeState(new MainMenuState());
    }

    @Override
    public void reactToKeyDown(KeyEvent keyEvent) {
        getRunner().changeState(new MainMenuState());
    }
}
