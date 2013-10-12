package tag3.states;

import horsentp.gamelogic.GameState;
import tag3.gui.ImageLabel;
import tag3.media.MediaLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 8:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoadingState extends GameState {

    private ImageLabel background;
    private LoadStateCommand command;
    private boolean commandRunning;

    public LoadingState(LoadStateCommand command) {
        this.command = command;
        if (this.command!=null) {
            System.out.println("Loading state has its' command ready");
        } else {
            System.out.println("Loading state have encountered an ERROR: No command!");
        }
    }

    @Override
    public void updateLogic() {
        if(!commandRunning) {
            System.out.println("Loading state is invoking its' command");
            command.doForLoading();
            commandRunning = true;
        }
        System.out.println("Loading state has updated");
    }

    @Override
    public void initState() {
        commandRunning = false;
        background = new ImageLabel(MediaLoader.quickLoadImage("loadingBackground.png"), 0, 0);
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        bufferedImage = background.render(bufferedImage, graphics2D);
        return bufferedImage;
    }
}
