package tag3.gamelogic;

import tag3.gamelogic.encounters.RandomEncounter;
import tag3.gamelogic.encounters.TestDeathEncounter;
import tag3.media.MediaLoader;
import tag3.party.Party;
import tag3.party.food.Food;
import tag3.party.food.Quality;
import tag3.party.food.Water;
import tag3.party.supplycollection.SupplyCollectPoint;
import tag3.states.GameOverState;
import tag3.states.PlayState;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

    public ConfirmCommand getCurrentDecision() {
        return currentDecision;
    }

    public void setCurrentDecision(ConfirmCommand currentDecision) {
        this.currentDecision = currentDecision;
    }

    private ConfirmCommand currentDecision;

    private int daysPassed;
    private int frame;
    private GameCalender calender;
    private BufferedImage currentImage;
    private boolean choseConfirmation;
    private List<RandomEncounter> randomEncounters = new ArrayList<RandomEncounter>();

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
        randomEncounters.add(new TestDeathEncounter());
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

            state.askForConfirmation(new ConfirmCommand() {  //The confirmation for wanting water...
                @Override
                public void preCommandAction() {
                    state.setResourceDialogText("You have found " + state.qualityToText(getResource().getQuality()) + " " + state.resourceTypeToText(getResource()) + ".");
                }

                @Override
                public void onYes() {
                    givePartyResources();
                }

                @Override
                public void onNo() {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                public boolean isAChoice() { return true; }
            });

            hoursUntilNextFood= 20 + (int)(Math.random()*8);
        } else if (hoursUntilNextWater <=0) {
            setMoving(false);
            System.out.println("Spawning a water resource!");
            // spawn a water resource
            state.notifyWater();
            state.askForConfirmation(new ConfirmCommand() {  //The confirmation for wanting water...
                @Override
                public void preCommandAction() {
                    state.setResourceDialogText("You have found " + state.qualityToText(getResource().getQuality()) + " " + state.resourceTypeToText(getResource()) + ".");
                }

                @Override
                public void onYes() {
                    givePartyResources();
                }

                @Override
                public void onNo() {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                public boolean isAChoice() { return true; }
            });
            hoursUntilNextWater = 20 + (int)(Math.random()*8);
        } else if ((hoursUntilNextFood > 0) && (hoursUntilNextWater > 0)) {
            setMoving(state.getMoveToggleValue()); //Are we moving according to the toggle?
        }

        // if currentFoundResource!=null
        // ask player if they want to collect it

        hoursUntilNextFood--;
        hoursUntilNextWater--;

        if (party.getSize() <= 0) {
            state.getRunner().changeState(new GameOverState("You have run out of animals."));
        }

        //Code for random encounters!
        doRandomEncounter();
    }

    private void initRandomEncounters() {
        if (randomEncounters == null) {
            randomEncounters = new ArrayList<RandomEncounter>();
        } else {
            randomEncounters.clear();
        }
    }

    public void doRandomEncounter() {
        double randomChance = Math.random();
        for (RandomEncounter randomEncounter : randomEncounters) {
            if (randomEncounter.getChancePerHour() >= (randomChance * 100)) {
                randomEncounter.handleEncounter(this, state);
                return;
            }
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
