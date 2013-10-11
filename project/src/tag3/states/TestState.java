package tag3.states;

import horsentp.display.ImageDisplay;
import horsentp.gamelogic.GameState;
import tag3.media.MediaLoader;

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

    private BufferedImage testImage;

    public void updateLogic() {

    }

    public void initState() {
        testImage = MediaLoader.quickLoadImage("testImage.png");
    }

    public BufferedImage render(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        g2d.drawImage(testImage, 0, 0, null);
        return bImage;
    }
}
