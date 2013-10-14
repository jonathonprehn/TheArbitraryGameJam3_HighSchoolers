package tag3.states;

import horsentp.gamelogic.GameState;
import horsentp.input.KeyDownListener;
import tag3.MainStartUp;
import tag3.gui.ImageLabel;
import tag3.media.MediaLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public class WinGameState extends GameState {
    long lastTime;
    int millisecondsBetween;
    int imageOn = 0;

    BufferedImage[] winImages;
    ImageLabel currentImage;

    @Override
    public void updateLogic() {
        if (System.currentTimeMillis() > lastTime+millisecondsBetween) {
            imageOn++;
            if (imageOn>6) {
                 imageOn = 0;
            }
            currentImage.setImage(winImages[imageOn]);
            lastTime = System.currentTimeMillis();
        }
    }

    @Override
    public void initState() {
        millisecondsBetween = 2300;
        winImages = new BufferedImage[]{
                MediaLoader.quickLoadImage("welcome/welcome0.png"),
                MediaLoader.quickLoadImage("welcome/welcome1.png"),
                MediaLoader.quickLoadImage("welcome/welcome2.png"),
                MediaLoader.quickLoadImage("welcome/welcome3.png"),
                MediaLoader.quickLoadImage("welcome/welcome4.png"),
                MediaLoader.quickLoadImage("welcome/welcome5.png"),
                MediaLoader.quickLoadImage("welcome/welcome6.png")
            };

        currentImage = new ImageLabel(winImages[imageOn], 0, 0);
        lastTime = System.currentTimeMillis();

        getInput().addKeyDownListener(new ReturnToMainMenu());
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        bufferedImage = currentImage.render(bufferedImage, graphics2D);
        Graphics g2 = (Graphics2D) bufferedImage.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 540, 800, 60);
        g2.setColor(new Color(50, 50, 50));

        Font infoFont;
        // load the font
        String fontPath = "fonts/ERASMD.TTF";
        try {
            infoFont = Font.createFont(Font.TRUETYPE_FONT, MainStartUp.class.getResourceAsStream("assets/" + fontPath));
            infoFont = infoFont.deriveFont(15.0f);
            System.out.println("Loaded font: " + fontPath + "");
            g2.setFont(infoFont);
        } catch(Exception e) {
            System.out.println("Unable to load Font: " + fontPath + "");
            e.printStackTrace();
        }

        g2.drawString("Congratulations on making it to Wyoming! We welcomes you! (Press any key to go to the main menu)", 30, 560);
        return bufferedImage;
    }

    class ReturnToMainMenu implements KeyDownListener {
        @Override
        public void reactToKeyDown(KeyEvent keyEvent) {
            getRunner().changeState(new MainMenuState());
            System.out.println("Returned to the main menu :<");
        }
    }
}
