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
    private GameCalender calender;
    private BufferedImage currentImage;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        calender.setCounting(moving);
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
        this.calender = calender;
    }

    public BufferedImage getCurrentAnimationFrame() {
        if (currentImage==null) {
            updatePartyImage();
        }
        return currentImage;
    }

    @Override
    public void tickPassed() {
        //System.out.println("Calendar tick!");
    }

    @Override
    public void minutePassed() {
        //System.out.println("Minute passed");
    }

    public void updatePartyImage() {
        if (isMoving()) {
            if (frame==0) {
                currentImage = party.getPartyImage0();
                frame = 1;
            } else {
                currentImage = party.getPartyImage1();
                frame = 0;
            }
        } else {
            currentImage = party.getPartyIdleImage();
        }
    }

    @Override
    public void hourPassed() {
        System.out.println("Hour passed");
        updatePartyImage();
        if (isMoving()) {
            party.setIdle(false);
            getRawParty().moveForward();
            System.out.println("Moved forward!");
        } else {
            party.setIdle(true);
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
