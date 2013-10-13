package tag3.gamelogic.encounters;

import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.states.PlayState;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/13/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LionTradeEncounter implements RandomEncounter {

    int lionAmount;
    boolean accepted;

    @Override
    public double getChancePerHour() {
        return 100;
    }

    @Override
    public void handleEncounter(final PartyWrapper partyWrapper, final PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                lionAmount = 10+(int)(Math.random()*5);
                accepted = false;

                gameState.setOtherResourceDialogText("A party of "+lionAmount+" lions offer to join you if");
                gameState.setResourceDialogText("you let them eat half of your healthy llamas");
            }

            @Override
            public void onYes() {
                accepted = true;
                partyWrapper.getRawParty().quietRemoveLlama((int)(partyWrapper.getRawParty().getNumLlama()/2.0));
                partyWrapper.getRawParty().quietAddLion(lionAmount);
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
                    message = "You have gained "+lionAmount+" lions";
                } else {
                    message = "You have refused their offer";
                }
                return message;
            }
        });
    }
}
