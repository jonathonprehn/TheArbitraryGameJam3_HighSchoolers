package tag3.gamelogic;

import tag3.party.Party;
import tag3.states.PlayState;

import java.awt.image.BufferedImage;

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
    private int frame;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        getRawParty().setIdle(!moving);
    }

    private boolean moving;
    private PlayState state;

    public Party getRawParty() {
        return party;
    }

    private Party party;

    public PartyWrapper(int numberLion, int numberGiraffe, int numberLlama, GameCalender calender, PlayState reference) {
        party = new Party(numberLion, numberGiraffe, numberLion);
        this.state = reference;
        daysPassed = 0;
        frame = 0;
    }

    public BufferedImage getCurrentAnimationFrame() {
        if (party.isIdle()) {
            //System.out.println("Party is idle!");
            return party.getPartyIdleImage();
        } else {
            switch(frame) {
                case 0:
                    return party.getPartyImage0();
                case 1:
                    return party.getPartyImage1();
                default:
                    return null;
            }
        }
    }

    @Override
    public void tickPassed() {
        //System.out.println("Calendar tick!");
    }

    @Override
    public void minutePassed() {
        //System.out.println("Minute passed");
    }

    @Override
    public void hourPassed() {
        System.out.println("Hour passed");
        if (isMoving()) {
            getRawParty().moveForward();
            System.out.println("Moved forward!");
            frame++;
            if (frame>1) {
                frame = 0;
            }
        }
    }

    @Override
    public void dayPassed() {
        System.out.println("Day passed");
        daysPassed++;
    }

    @Override
    public void weekPassed() {
        //System.out.println("Week passed");
    }

    public int getDaysPassed() {
        return daysPassed;
    }
}
