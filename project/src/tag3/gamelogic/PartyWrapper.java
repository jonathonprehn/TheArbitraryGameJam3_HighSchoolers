package tag3.gamelogic;

import tag3.party.Party;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartyWrapper implements GameCalenderListener {

    private Party party;

    public PartyWrapper(int numberLion, int numberGiraffe, int numberLlama, GameCalender calender) {
        party = new Party(numberLion, numberGiraffe, numberLion);
        calender.addCalenderListener(this);
    }

    @Override
    public void minutePassed() {

    }

    @Override
    public void hourPassed() {

    }

    @Override
    public void dayPassed() {

    }

    @Override
    public void weekPassed() {

    }
}
