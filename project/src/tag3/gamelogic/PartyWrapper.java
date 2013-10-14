package tag3.gamelogic;

import tag3.gamelogic.encounters.HunterEncounter;
import tag3.gamelogic.encounters.LionTradeEncounter;
import tag3.gamelogic.encounters.MedicineManEncounter;
import tag3.gamelogic.encounters.RandomEncounter;
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
    private List<RandomEncounterWrapper> randomEncounters = new ArrayList<RandomEncounterWrapper>();

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

        int startFood = 35;
        int startWater = 50;
        for (int i=0; i<startFood; i++) {
            party.addFood(new Food(Quality.GOOD));
        }
        for (int i=0; i<startWater; i++) {
            party.addWater(new Water(Quality.GOOD));
        }
        initRandomEncounters();
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
        boolean eventHappened = false;
        //System.out.println("Hour passed");
        updatePartyImage();
        if (isMoving()) {
            party.setIdle(false);
            getRawParty().moveForward();
            // System.out.println("Moved forward!");
        } else {
            party.setIdle(true);
        }

        if (!eventHappened && hoursUntilNextFood <= 0) {
            setMoving(false);
            System.out.println("Spawning a food resource!");
            // spawn a food resource
            state.notifyRandomFood();

            state.askForConfirmation(new ConfirmCommand() {  //The confirmation for wanting water...

                private String text = "";

                @Override
                public void preCommandAction() {
                    state.setResourceDialogText("You have found " + state.qualityToText(getResource().getQuality()) + " "
                            + state.resourceTypeToText(getResource()) + ".");
                    state.setOtherResourceDialogText("Collect it?");
                }

                @Override
                public void onYes() {
                    givePartyResources();
                    text = "Your party took the food.";
                    state.setResourceDialogText("");
                    state.setOtherResourceDialogText("");
                }

                @Override
                public void onNo() {
                    text = "Your party ignored the food.";
                    state.setResourceDialogText("");
                    state.setOtherResourceDialogText("");
                }

                public boolean isAChoice() { return true; }

                @Override
                public String afterChoiceText() {
                    return text;
                }
            });

            hoursUntilNextFood= 20 + (int)(Math.random()*8);
            eventHappened = true;
        } else if (!eventHappened && hoursUntilNextWater <=0) {
            setMoving(false);
            System.out.println("Spawning a water resource!");
            // spawn a water resource
            state.notifyWater();
            state.askForConfirmation(new ConfirmCommand() {  //The confirmation for wanting water...

                private String text = "";
                @Override
                public void preCommandAction() {
                    state.setResourceDialogText("You have found " + state.qualityToText(getResource().getQuality()) + " "
                            + state.resourceTypeToText(getResource()) + ".");
                    state.setOtherResourceDialogText("Collect it?");
                }

                @Override
                public void onYes() {
                    givePartyResources();
                    text = "Your party drank the water.";
                    state.setResourceDialogText("");
                    state.setOtherResourceDialogText("");
                }

                @Override
                public void onNo() {
                    text = "Your party ignored the water.";
                    state.setResourceDialogText("");
                    state.setOtherResourceDialogText("");
                }

                public boolean isAChoice() { return true; }

                @Override
                public String afterChoiceText() {
                    return text;
                }
            });
            hoursUntilNextWater = 20 + (int)(Math.random()*8);
            eventHappened = true;
        } else if ((hoursUntilNextFood > 0) && (hoursUntilNextWater > 0)) {
            setMoving(state.getMoveToggleValue()); //Are we moving according to the toggle?
        }

        // if currentFoundResource!=null
        // ask player if they want to collect it

        hoursUntilNextFood--;
        hoursUntilNextWater--;

        //If you didn't have any event that time yet then see if random encounter happens
        if (!eventHappened) {
            doRandomEncounter();
        }

        if (party.getSize() <= 0) {
            state.getRunner().changeState(new GameOverState("You have run out of animals."));
        }
    }

    private void initRandomEncounters() {
        if (randomEncounters == null) {
            randomEncounters = new ArrayList<RandomEncounterWrapper>();
        } else {
            randomEncounters.clear();
        }
        randomEncounters.add(new RandomEncounterWrapper(new MedicineManEncounter()));
        randomEncounters.add(new RandomEncounterWrapper(new LionTradeEncounter()));
        randomEncounters.add(new RandomEncounterWrapper(new HunterEncounter()));

        //Calcumalate the weight
        totalWeight = 0;
        for (int i=0; i<randomEncounters.size(); i++) {
            randomEncounters.get(i).assignInterval(totalWeight, (int)randomEncounters.get(i).getEncounter().getChancePerHour());
            totalWeight += randomEncounters.get(i).getEncounter().getChancePerHour();
        }
        //Add buffer so that encounters don't happen every second of your life
        totalWeight = (totalWeight*5);
    }
    //Does everyone a random encounter does, except for wrap it in this thing for giving it an interval for random events!
    class RandomEncounterWrapper {

        private int min, max;

        RandomEncounter encounter;
        public RandomEncounterWrapper(RandomEncounter encounter) {
            this.encounter = encounter;
        }

        public RandomEncounter getEncounter() {
            return encounter;
        }
        public void assignInterval(int min, int max) {
            this.min = min; this.max = max;
        }
        public boolean IsInInterval(int testingNumber) {
            if (min<testingNumber && max>=testingNumber) {
                return true;
            } else {
                return false;
            }
        }
    }

    /*public void doRandomEncounter() {
        double randomChance = Math.random();
        for (RandomEncounter randomEncounter : randomEncounters) {
            if (randomEncounter.getChancePerHour() >= (randomChance * 100)) {
                randomEncounter.handleEncounter(this, state);
                return;
            }
        }
    }*/

    //Variables and whatnot for random encounters
    int totalWeight;

    public void doRandomEncounter() {
        boolean alreadyHappened = false;
        int randomNum = (int)(Math.random()*totalWeight);
        for (int i=0; i<randomEncounters.size(); i++) {
            if (randomEncounters.get(i).IsInInterval(randomNum)) {
                if (alreadyHappened) {
                    System.out.println("FATAL ERROR: More than 1 encounter occurred! (not suppose to happen!)");
                }
                randomEncounters.get(i).getEncounter().handleEncounter(this, state);
                alreadyHappened = true;
            }
        } //OK for loop to continue since two shouldn't happen
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
