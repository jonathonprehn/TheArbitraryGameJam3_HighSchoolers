package tag3.gamelogic.encounters;

import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.states.PlayState;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/13/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindGiraffeEncounter implements RandomEncounter {

    int amount;
    boolean accepted;

    @Override
    public double getChancePerHour() {
        return 6;
    }

    @Override
    public void handleEncounter(final PartyWrapper partyWrapper, final PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                amount = 5+(int)(Math.random()*20);
                accepted = false;

                gameState.setResourceDialogText("You have found "+amount+" stray giraffes");
                gameState.setOtherResourceDialogText("Would you like to add them to your party?");
            }

            @Override
            public void onYes() {
                accepted = true;
                partyWrapper.getRawParty().addGiraffe(amount);
            }

            @Override
            public void onNo() {
                accepted = false;
            }

            @Override
            public boolean isAChoice() {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String afterChoiceText() {
                String message;
                if (accepted) {
                    message = "You have gained "+amount+" giraffes";
                } else {
                    message = "You ignored the giraffes";
                }
                return message;
            }
        });
    }
}
