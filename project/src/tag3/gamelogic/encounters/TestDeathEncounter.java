package tag3.gamelogic.encounters;

import horsentp.gamelogic.GameState;
import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.states.GameOverState;
import tag3.states.PlayState;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public class TestDeathEncounter implements RandomEncounter {

    @Override
    public double getChancePerHour() {
        return 100;
    }

    @Override
    public void handleEncounter(final PartyWrapper partyWrapper, final PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {  //The confirmation for wanting water...
            @Override
            public void preCommandAction() {
                gameState.setResourceDialogText("You can die, yes or no?");
            }

            @Override
            public void onYes() {
                gameState.getRunner().changeState(new GameOverState("You chose death!"));
            }

            @Override
            public void onNo() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
