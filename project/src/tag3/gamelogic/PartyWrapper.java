package tag3.gamelogic;

import tag3.party.Party;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */

//For putting the logic for the party (and the game) in 1 place
public class PartyWrapper implements GameCalenderListener {

    private int daysPassed;

    public Party getRawParty() {
        return party;
    }

    private Party party;

    public PartyWrapper(int numberLion, int numberGiraffe, int numberLlama, GameCalender calender) {
        party = new Party(numberLion, numberGiraffe, numberLion);
        calender.addCalenderListener(this);
        daysPassed = 0;
    }

    @Override
    public void minutePassed() {
        System.out.println("Minute passed");
    }

    @Override
    public void hourPassed() {
        System.out.println("Hour passed");
    }

    @Override
    public void dayPassed() {
        System.out.println("Day passed");
        daysPassed++;
    }

    @Override
    public void weekPassed() {
        System.out.println("Week passed");
    }

    public int getDaysPassed() {
        return daysPassed;
    }
}
