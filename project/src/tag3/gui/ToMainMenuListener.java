package tag3.gui;

import horsentp.gamelogic.GameRunner;
import tag3.states.MainMenuState;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToMainMenuListener implements GenericButtonListener {

    private GameRunner runner;

    public ToMainMenuListener(GameRunner runner) {
        this.runner = runner;
    }

    @Override
    public void buttonPushed() {
        runner.changeState(new MainMenuState());
    }
}
