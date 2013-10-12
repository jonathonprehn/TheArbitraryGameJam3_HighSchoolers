package tag3.party;

import tag3.media.MediaLoader;
import tag3.party.food.Food;
import tag3.party.food.Water;
import tag3.utility.RandomChance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Party {

    // food and water supplies of the party
    private List<Food> foodSupply = new ArrayList<Food>();
    private List<Water> waterSupply = new ArrayList<Water>();

    // a number added to the percentage chance of getting a disease
    private int diseaseModifier;
    private int diseaseCureChance, diseaseKillChance;
    // the chance of being more successful when hunting or collecting food
    private int huntingSuccess, collectingSuccess;
    // number of members in your party
    private int numLion, numGiraffe, numLlama;
    private int numDiseasedLion, numDiseasedGiraffe, numDiseasedLlama;
    // the current image of the party (changes as party grows and shrinks)
    private BufferedImage partyImage0;
    private BufferedImage partyImage1;
    private BufferedImage partyIdleImage;

    private BufferedImage[] llamaImages = {
            MediaLoader.quickLoadImage("animals/llama0.png"),
            MediaLoader.quickLoadImage("animals/llama1.png")
    };
    private BufferedImage[] lionImages = {
            MediaLoader.quickLoadImage("animals/lion0.png"),
            MediaLoader.quickLoadImage("animals/lion1.png")
    };
    private BufferedImage[] giraffeImages = {
            MediaLoader.quickLoadImage("animals/giraffe0.png"),
            MediaLoader.quickLoadImage("animals/giraffe1.png")
    };

    // a percentage number between -100 and 100 on how much morale the party has
    private int morale;
    private double daysSinceSlept;

    private double walkingPace;
    private double distanceTraveled;

    private double daysWithNoFood = 0;
    private double daysWithNoWater = 0;

    private double temporaryMoraleModifier = 0;

    private boolean idle;

    public Party(int numberLion, int numberGiraffe, int numberLlama) {
        this.numLion = numberLion;
        this.numGiraffe = numberGiraffe;
        this.numLlama = numberLlama;
        this.morale = 0; // 0% morale
        this.daysSinceSlept = 0;
        this.walkingPace = 1;
        this.distanceTraveled = 0;
        this.idle = true;
        updateVariables();
        updatePartyImage();
    }

    private void updateVariables() {
        // increases with morale - 20% base chance
        this.diseaseCureChance = (int)(35*(1+(morale/100.0)));
        // decreases with morale - 2% base chance
        this.diseaseKillChance = (int)(2*(1+(-morale/200.0)));

        // morale modifiers
        int sleepMod = 0;
        if (getDaysSinceSlept() > 3) {
            sleepMod = -20;
        } else if (getDaysSinceSlept() > 1) {
            sleepMod = 0;
        } else if (getDaysSinceSlept() >= 0) {
            sleepMod = 15;
        }

        daysWithNoFood = daysWithNoFood + (1/24.0);
        daysWithNoWater = daysWithNoWater + (1/24.0);

        // -4% morale per day of no food
        int noFoodMod = 0;
        if (foodSupply.size() == 0) {
            noFoodMod = -(int)(daysWithNoFood*4.0);
        }

        // -7% morale per day of no water
        int noWaterMod = 0;
        if (waterSupply.size() == 0) {
            noWaterMod = -(int)(daysWithNoWater*7.0);
        }

        if (temporaryMoraleModifier > 3 || temporaryMoraleModifier < 3) {
            // temporary morale decays at a rate of 50% every update cycle
            temporaryMoraleModifier = temporaryMoraleModifier*0.5;
        } else {
            temporaryMoraleModifier = 0;
        }

        this.morale = (int)((this.getSize()*0.25) + sleepMod + noFoodMod + noWaterMod
            + temporaryMoraleModifier);

        // 15% diseased animal amount - 1% per llama - 1/2 morale percent
        this.diseaseModifier = (int)(-morale/2.0) + (int)(getNumberOfDiseased()*0.15) - (int)(getNumLlama());

        this.walkingPace = 1 + (morale/100.0);
        if (this.walkingPace < 0.1) {
            this.walkingPace = 0.1; // minimum walking pace
        }

        // 50% base with each animal adding 2%, each diseased animal subtracting 1% + morale percent
        // minimum 10% collection and hunting rate
        this.huntingSuccess = 40 + (numLion) - (int)(numDiseasedLion) + (morale/2);
        if (huntingSuccess < 10) {
            huntingSuccess = 10;
        }
        this.collectingSuccess = 40 + (numGiraffe) - (int)(numDiseasedGiraffe) + (morale/2);
        if (collectingSuccess < 10) {
            collectingSuccess = 10;
        }
    }

    public int getSize() {
        return (numLion + numGiraffe + numLlama +
                numDiseasedGiraffe + numDiseasedLion + numDiseasedLlama);
    }

    public int getNumberOfNonDiseased() {
        return (numLion + numGiraffe + numLlama);
    }

    public int getNumberOfDiseased() {
        return (numDiseasedGiraffe + numDiseasedLion + numDiseasedLlama);
    }

    public void addLion(int addedLions) {
        this.numLion = this.numLion + addedLions;
        addTemporaryMorale(5);
        updatePartyImage();
    }

    public void addGiraffe(int addedGiraffes) {
        this.numGiraffe = this.numGiraffe + addedGiraffes;
        addTemporaryMorale(5);
        updatePartyImage();
    }

    public void addLlama(int addedLlamas) {
        this.numLlama = this.numLlama + addedLlamas;
        addTemporaryMorale(5);
        updatePartyImage();
    }

    public void kickOutLlama() {
        this.numLlama--;
        addTemporaryMorale(-5);
    }

    public void kickOutDiseasedLlama() {
        this.numDiseasedLlama--;
        addTemporaryMorale(-5);
    }

    public void kickOutLion() {
        this.numLion--;
        addTemporaryMorale(-5);
    }

    public void kickOutDiseasedLion() {
        this.numDiseasedLion--;
        addTemporaryMorale(-5);
    }

    public void kickOutGiraffe() {
        this.numGiraffe--;
        addTemporaryMorale(-5);
    }

    public void kickOutDiseasedGiraffe() {
        this.numDiseasedGiraffe--;
        addTemporaryMorale(-5);
    }

    private void updatePartyImage() {
        int llamasInImage = (int)((numLlama + numDiseasedLlama)/5.0);
        int lionsInImage = (int)((numLion + numDiseasedLion)/5.0);
        int giraffesInImage = (int)((numGiraffe + numDiseasedGiraffe)/5.0);

        Point[] llamaPositions = new Point[llamasInImage];
        Point[] lionPositions = new Point[lionsInImage];
        Point[] giraffePositions = new Point[giraffesInImage];

        int width = 500;
        int height = 400;

        while (llamasInImage>0 || lionsInImage>0 || giraffesInImage>0) {
            if (llamasInImage > 0) {
                llamaPositions[(llamasInImage-1)] =
                        new Point((int)(Math.random()*(width-150)), height-280 + (int)(Math.random()*20));
                llamasInImage--;
            }
            if (lionsInImage > 0) {
                lionPositions[(lionsInImage-1)] =
                        new Point((int)(Math.random()*(width-200)), height-230 + (int)(Math.random()*20));
                lionsInImage--;
            }
            if (giraffesInImage > 0) {
                giraffePositions[(giraffesInImage-1)] =
                        new Point((int)(Math.random()*(width-150)), height-330 + (int)(Math.random()*20));
                giraffesInImage--;
            }
        }

        // idk what the third constructor is supposed to do :<
        partyImage0 = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        partyImage1 = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        partyIdleImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

        Graphics2D pi0 = (Graphics2D) partyImage0.getGraphics();
        Graphics2D pi1 = (Graphics2D) partyImage1.getGraphics();
        Graphics2D pii = (Graphics2D) partyIdleImage.getGraphics();

        byte num1, num2;

        for (int i=0; i<llamaPositions.length; i++) {
            if (RandomChance.randomBoolean()) {
                num1 = 1;
                num2 = 0;
            } else {
                num1 = 0;
                num2 = 1;
            }
            pi0.drawImage(llamaImages[num1], (int)llamaPositions[i].getX(), (int)llamaPositions[i].getY(), null);
            pi1.drawImage(llamaImages[num2], (int)llamaPositions[i].getX(), (int)llamaPositions[i].getY(), null);
            pii.drawImage(llamaImages[1], (int)llamaPositions[i].getX(), (int)llamaPositions[i].getY(), null);
        }

        for (int i=0; i<lionPositions.length; i++) {
            if (RandomChance.randomBoolean()) {
                num1 = 1;
                num2 = 0;
            } else {
                num1 = 0;
                num2 = 1;
            }
            pi0.drawImage(lionImages[num1], (int)lionPositions[i].getX(), (int)lionPositions[i].getY(), null);
            pi1.drawImage(lionImages[num2], (int)lionPositions[i].getX(), (int)lionPositions[i].getY(), null);
            pii.drawImage(lionImages[1], (int)lionPositions[i].getX(), (int)lionPositions[i].getY(), null);
        }

        for (int i=0; i<giraffePositions.length; i++) {
            if (RandomChance.randomBoolean()) {
                num1 = 1;
                num2 = 0;
            } else {
                num1 = 0;
                num2 = 1;
            }
            pi0.drawImage(giraffeImages[num1], (int)giraffePositions[i].getX(), (int)giraffePositions[i].getY(), null);
            pi1.drawImage(giraffeImages[num2], (int)giraffePositions[i].getX(), (int)giraffePositions[i].getY(), null);
            pii.drawImage(giraffeImages[1], (int)giraffePositions[i].getX(), (int)giraffePositions[i].getY(), null);
        }
    }

    /**
     * Makes the party move forwards
     * Increases distance traveled and makes timeSinceSlept increase
     * Decreases Food and Water amount
     */
    public void moveForward() {
        updateVariables();
        int beforePopulation = getSize();
        randomlyKill();
        // update the party if people died
        if (beforePopulation!=getSize()) {
            updatePartyImage();
        }
        int beforeDiseasedNum = getNumberOfDiseased();
        eatSupplies();
        int diseasedThatHour = getNumberOfDiseased() - beforeDiseasedNum;

        System.out.println("In one hour "+diseasedThatHour+" animals were diseased");
        daysSinceSlept = daysSinceSlept + (1/24.0);
        distanceTraveled = distanceTraveled + walkingPace;
    }

    public void sleep() {
        updateVariables();
        randomlyCure(); // when you sleep some animals have a chance of being cured from a good night's sleep
        daysSinceSlept = 0; // all refreshed!
    }

    private void eatSupplies() {
        // The party eats food and drinks water every 8 hours
        if (daysWithNoFood > (8/24.0)) {
            eatMeals();
        }
        if (daysWithNoWater > (8/24.0)) {
            quenchThirst();
        }
    }

    private void quenchThirst() {
        // each 5 animals eat 1 supply
        int suppliesConsumedToday = getConsumeRate();
        System.out.println("We needed to eat "+suppliesConsumedToday+" supplies");
        for (int i=0; i<suppliesConsumedToday; i++) {
            consumeWater();
        }
    }

    private void eatMeals() {
        int suppliesConsumedToday = getConsumeRate();
        System.out.println("We needed to eat "+suppliesConsumedToday+" supplies");
        for (int i=0; i<suppliesConsumedToday; i++) {
            consumeFood();
        }
    }

    public int getConsumeRate() {
        return (int)(this.getSize()/5.0);
    }

    private void randomlyKill() {
        // see if lions die
        for (int i=0; i<this.numDiseasedLion; i++) {
            if (RandomChance.rollForChance(diseaseKillChance))  {
                this.numDiseasedLion--;
                addTemporaryMorale(-10);
            }
        }

        // see if giraffe die
        for (int i=0; i<this.numDiseasedGiraffe; i++) {
            if (RandomChance.rollForChance(diseaseKillChance))  {
                this.numDiseasedGiraffe--;
                addTemporaryMorale(-10);
            }
        }

        // see if Llama die
        for (int i=0; i<this.numDiseasedLlama; i++) {
            if (RandomChance.rollForChance(diseaseKillChance))  {
                this.numDiseasedLlama--;
                addTemporaryMorale(-10);
            }
        }
    }

    private void randomlyCure() {
        // see if lions gets cured
        for (int i=0; i<this.numLion; i++) {
            if (RandomChance.rollForChance(diseaseCureChance))  {
                this.numLion++;
                this.numDiseasedLion--;
            }
        }

        // see if giraffes gets cured
        for (int i=0; i<this.numGiraffe; i++) {
            if (RandomChance.rollForChance(diseaseCureChance))  {
                this.numGiraffe++;
                this.numDiseasedGiraffe--;
            }
        }

        // see if llamas get cured
        for (int i=0; i<this.numLlama; i++) {
            if (RandomChance.rollForChance(diseaseCureChance))  {
                this.numLlama++;
                this.numDiseasedLlama--;
            }
        }
    }

    private void consumeFood() {
        if (foodSupply.size() > 0) {
            Food eatenFood = foodSupply.get(foodSupply.size()-1)  ;
            int diseasePercent = eatenFood.getDiseasePercentMod() + diseaseModifier;
            // minimum 3% chance of getting diseased
            if (diseasePercent < 3) {
                diseasePercent = 3;
            }
            // maximum 80% chance of getting diseased
            if (diseasePercent > 80) {
                diseasePercent = 80;
            }

            // see if lions get disease
            for (int i=0; i<this.numLion; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numLion--;
                    this.numDiseasedLion++;
                }
            }

            // see if giraffes get disease
            for (int i=0; i<this.numGiraffe; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numGiraffe--;
                    this.numDiseasedGiraffe++;
                }
            }

            // see if llamas get disease
            for (int i=0; i<this.numLlama; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numLlama--;
                    this.numDiseasedLlama++;
                }
            }

            daysWithNoFood = 0;
            foodSupply.remove(foodSupply.size()-1);
        }
    }

    private void consumeWater() {
        if (waterSupply.size() > 0) {
            Water drinkedWater = waterSupply.get(waterSupply.size()-1);
            int diseasePercent = drinkedWater.getDiseasePercentMod() + diseaseModifier;
            if (diseasePercent > 100) {
                diseasePercent = 100;
            }

            // see if lions get disease
            for (int i=0; i<this.numLion; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numLion--;
                    this.numDiseasedLion++;
                }
            }

            // see if giraffes get disease
            for (int i=0; i<this.numGiraffe; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numGiraffe--;
                    this.numDiseasedGiraffe++;
                }
            }

            // see if llamas get disease
            for (int i=0; i<this.numLlama; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numLlama--;
                    this.numDiseasedLlama++;
                }
            }

            daysWithNoWater = 0;
            waterSupply.remove(waterSupply.size()-1);
        }
    }

    private void addTemporaryMorale(double moraleAmount) {
        this.temporaryMoraleModifier = this.temporaryMoraleModifier + moraleAmount;
    }

    public void addFood(Food food) {
        this.foodSupply.add(food);
    }

    public void addWater(Water water) {
        this.waterSupply.add(water);
    }

    public int getFoodAmount() {
        return foodSupply.size();
    }

    public int getWaterAmount() {
        return waterSupply.size();
    }

    public int getNumLion() {
        return numLion;
    }

    public int getNumGiraffe() {
        return numGiraffe;
    }

    public int getNumLlama() {
        return numLlama;
    }

    public int getNumDiseasedLlama() {
        return numDiseasedLlama;
    }

    public int getNumDiseasedGiraffe() {
        return numDiseasedGiraffe;
    }

    public int getNumDiseasedLion() {
        return numDiseasedLion;
    }

    public int getTotalLlamas() {
        return (numLlama + numDiseasedLlama);
    }

    public int getTotalLions() {
        return (numLion + numDiseasedLion);
    }

    public int getTotalGiraffes() {
        return (numGiraffe + numDiseasedGiraffe);
    }

    public double getDaysSinceSlept() {
        return daysSinceSlept;
    }

    public double getWalkingPace() {
        return walkingPace;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getMorale() {
        return morale;
    }

    public BufferedImage getPartyImage0() {
        if (partyImage0==null) {
            updatePartyImage();
        }
        return partyImage0;
    }

    public BufferedImage getPartyImage1() {
        if (partyImage1==null) {
            updatePartyImage();
        }
        return partyImage1;
    }

    public BufferedImage getPartyIdleImage() {
        if (partyIdleImage==null) {
            updatePartyImage();
        }
        return partyIdleImage;
    }

    public int getHuntingSuccess() {
        return huntingSuccess;
    }

    public int getCollectingSuccess() {
        return collectingSuccess;
    }

    public double getDaysWithNoFood() {
        return daysWithNoFood;
    }

    public double getDaysWithNoWater() {
        return daysWithNoWater;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }
}
