package tag3;

import horsentp.display.DisplayConfiguration;
import horsentp.display.DisplayLayer;
import horsentp.display.Displayer;

import javax.swing.*;
import java.awt.*;

public class MainStartUp {

    public static void main(String[] args) {
        System.out.println("Lets finish this game or at least start it.");
        DisplayLayer tes = new DisplayLayer();
        tes.addDisplayable(new Checkerboard());
    }
}
