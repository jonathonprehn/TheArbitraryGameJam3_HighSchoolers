package tag3.gamelogic.encounters;

import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.states.GameOverState;
import tag3.states.PlayState;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public class HunterEncounter implements RandomEncounter {
    @Override
    public double getChancePerHour() {
        return 100;
    }

    @Override
    public void handleEncounter(PartyWrapper partyWrapper, final PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                gameState.setResourceDialogText("Hunters have appeared!");
            }

            @Override
            public void onYes() {gameState.setPressed(false);
                gameState.setAsking(true);
                gameState.setPressed(false);
                gameState.setResourceDialogText("You have lost half your party!");
                Future
            }

            @Override
            public void onNo() {gameState.setPressed(false);
                gameState.setAsking(true);
                gameState.setPressed(true);
                gameState.setResourceDialogText("You have lost half your party!!!");
            }
        });
    }
}
