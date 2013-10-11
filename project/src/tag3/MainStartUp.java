package tag3;

import javax.swing.*;
import java.awt.*;

public class MainStartUp {

    public static void main(String[] args) {
        System.out.println("Lets finish this game or at least start it.");
        JFrame frame = new JFrame("Checkerboard");
        frame.add(new Checkerboard(), BorderLayout.CENTER);
        frame.setSize(40,20);
        frame.setVisible(true);
    }
}
