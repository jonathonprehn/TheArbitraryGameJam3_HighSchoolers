package tag3.test;

import horsentp.display.DisplayConfiguration;
import horsentp.gamelogic.GameRunner;

import tag3.test.states.ConsoleMenuState;
import tag3.utility.GraphicsFactory;

public class TestStartup {

    public static void main(String[] args) {
        System.out.println("Lets finish this game or at least start it.");
        GameRunner runner = new GameRunner(new DisplayConfiguration(800, 600, DisplayConfiguration.STATIC_WINDOW), false);
        GraphicsFactory.getFactory().initFactory(runner.getDisplayer(), runner.getInput());
        runner.changeState(new ConsoleMenuState());
        runner.startGame();    ///Don't forget to change the state!
    }
}
