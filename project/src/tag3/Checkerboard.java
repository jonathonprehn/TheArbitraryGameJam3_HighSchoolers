package tag3;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Starbuck
 * Date: 10/11/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Checkerboard extends JComponent {
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(0,0,20,20);
        g2.setColor(Color.WHITE);
        g2.fillRect(20,0,20,20);
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,20,20);
        g2.setColor(Color.WHITE);
        g2.fillRect(20,0,20,20);
    }
}
