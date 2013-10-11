package tag3.states;

import horsentp.gamelogic.GameState;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestState extends GameState {

    public void updateLogic() {

    }

    public BufferedImage render(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();

        return bImage;
    }
}
