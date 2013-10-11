package tag3;

import horsentp.display.DisplayConfiguration;
import horsentp.display.DisplayLayer;
import horsentp.display.Displayer;
import horsentp.gametime.GameTimer;
import horsentp.input.InputBridge;
import horsentp.gamelogic.GameRunner;

import javax.swing.*;
import java.awt.*;

public class MainStartUp {

    public static void main(String[] args) {
        System.out.println("Lets finish this game or at least start it.");
        GameRunner runner = new GameRunner(new DisplayConfiguration(800, 600, DisplayConfiguration.STATIC_WINDOW));
        runner.startGame();
    }
}
