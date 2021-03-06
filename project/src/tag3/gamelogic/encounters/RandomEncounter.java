package tag3.gamelogic.encounters;

import horsentp.gamelogic.GameState;
import tag3.gamelogic.PartyWrapper;
import tag3.states.PlayState;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public interface RandomEncounter {
    /**
     * Gets the chance per tick of this encounter happening on a scale of 1 to 100.
     * This will be triggered if the random number is less than your chosen number. 100 means it will run every time.
     * @return The chance expressed as a percent.
     */
    public double getChancePerHour();

    /**
     * Runs the random encounter.
     * @param partyWrapper The current instance of party wrapper.
     * @param gameState The current {@link horsentp.gamelogic.GameState}.
     */
    public void handleEncounter(PartyWrapper partyWrapper, PlayState gameState);
}
