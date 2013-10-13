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
    @Override
    public double getChancePerHour() {
        return 100;
    }

    @Override
    public void handleEncounter(PartyWrapper partyWrapper, PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onYes() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onNo() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isAChoice() {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String afterChoiceText() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
