package tag3.gamelogic.encounters;

import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.party.food.Quality;
import tag3.party.food.Water;
import tag3.states.PlayState;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/13/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MedicineMainEncounter implements RandomEncounter {
    @Override
    public double getChancePerHour() {
        return 15;
    }

    @Override
    public void handleEncounter(final PartyWrapper partyWrapper, final PlayState gameState) {
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                gameState.setResourceDialogText("You have run into a wise old man, he offers");
                gameState.setOtherResourceDialogText("to heal your sick for some supplies.");
            }

            @Override
            public void onYes() {
                if (partyWrapper.getRawParty().getFoodAmount()>=20 && partyWrapper.getRawParty().getWaterAmount()>=20) {
                    partyWrapper.getRawParty().removeFood(20);
                    partyWrapper.getRawParty().removeWater(20);
                    int dg = partyWrapper.getRawParty().getNumDiseasedGiraffe();
                    int dll = partyWrapper.getRawParty().getNumDiseasedLlama();
                    int dl = partyWrapper.getRawParty().getNumDiseasedLion();
                    partyWrapper.getRawParty().quietRemoveDiseasedLlama(dll);
                    partyWrapper.getRawParty().quietRemoveDiseasedLion(dl);
                    partyWrapper.getRawParty().quietRemoveDiseasedGiraffe(dg);
                    partyWrapper.getRawParty().quietAddGiraffe(dg);
                    partyWrapper.getRawParty().quietAddLlama(dll);
                    partyWrapper.getRawParty().quietAddLion(dl);
                } else {
                    //TODO: Make a temporary text label for what happens after one of these encounter things
                }
            }

            @Override
            public void onNo() {

            }

            @Override
            public boolean isAChoice() {
                return true;
            }
        });
    }
}
