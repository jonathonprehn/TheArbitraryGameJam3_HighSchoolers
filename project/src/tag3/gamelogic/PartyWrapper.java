package tag3.gamelogic;

import tag3.media.MediaLoader;
import tag3.party.Party;
import tag3.party.food.Food;
import tag3.party.food.Quality;
import tag3.party.food.Water;
import tag3.party.supplycollection.SupplyCollectPoint;
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

    // timers for finding stuff
    private int hoursUntilNextFood = 24;
    private int hoursUntilNextWater = 20;

    private int daysPassed;
    private int frame;
    private GameCalender calender;
    private BufferedImage currentImage;
    private boolean choseConfirmation;

    private int distanceFromWyoming = 500;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        calender.setCounting(moving);
        state.setBackgroundScrolling(moving);
        if (isMoving()) { //Walking!
            MediaLoader.getLoadedSound("walking").loop();
        } else {
            MediaLoader.getLoadedSound("walking").stop();
        }
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

        int startFood = 20;
        int startWater = 15;
        for (int i=0; i<startFood; i++) {
            party.addFood(new Food(Quality.GOOD));
        }
        for (int i=0; i<startWater; i++) {
            party.addWater(new Water(Quality.GOOD));
        }
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

    public void setChoseConfirmation(boolean b) {
        this.choseConfirmation = b;
    }

    @Override
    public void hourPassed() {
        //System.out.println("Hour passed");
        updatePartyImage();
        if (isMoving()) {
            party.setIdle(false);
            getRawParty().moveForward();
           // System.out.println("Moved forward!");
        } else {
            party.setIdle(true);
        }

        if (hoursUntilNextFood <= 0) {
            setMoving(false);
            System.out.println("Spawning a food resource!");
            // spawn a food resource
            state.notifyRandomFood();

            state.askForConfirmation();

            hoursUntilNextFood= 20 + (int)(Math.random()*8);
        } else if (hoursUntilNextWater <=0) {
            setMoving(false);
            System.out.println("Spawning a water resource!");
            // spawn a water resource
            state.notifyWater();
            state.askForConfirmation();
            hoursUntilNextWater = 20 + (int)(Math.random()*8);
        } else if ((hoursUntilNextFood > 0) && (hoursUntilNextWater > 0)) {
            setMoving(state.getMoveToggleValue()); //Are we moving according to the toggle?
        }

        // if currentFoundResource!=null
        // ask player if they want to collect it

        hoursUntilNextFood--;
        hoursUntilNextWater--;

        if (party.getSize() <= 0) {

        }

    }

    public SupplyCollectPoint getResource() {
        return resource;
    }

    public void setResource(SupplyCollectPoint resource) {
        this.resource = resource;
    }

    private SupplyCollectPoint resource;

    public void givePartyResources() {
        if (resource!=null) {
            resource.collectFrom(party);
            setResource(null);
        }
    }

    public int getDistanceFromWyoming() {
        return distanceFromWyoming;
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
