package tag3.states;

import horsentp.gamelogic.GameState;

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

    @Override
    public void updateLogic() {

    }

    @Override
    public void initState() {

    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        return bufferedImage;
    }
}
