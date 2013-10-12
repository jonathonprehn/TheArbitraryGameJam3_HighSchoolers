package tag3.party;

import tag3.party.food.Food;
import tag3.party.food.Water;
import tag3.utility.RandomChance;

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
    private BufferedImage partyImage;
    // a percentage number between -100 and 100 on how much morale the party has
    private int morale;
    private double daysSinceSlept;

    private double walkingPace;
    private double distanceTraveled;

    private double daysWithNoFood = 0;
    private double daysWithNoWater = 0;

    private double temporaryMoraleModifier = 0;

    public Party(int numberLion, int numberGiraffe, int numberLlama) {
        this.numLion = numberLion;
        this.numGiraffe = numberGiraffe;
        this.numLlama = numberLlama;
        this.morale = 0; // 0% morale
        this.daysSinceSlept = 0;
        this.walkingPace = 1;
        this.distanceTraveled = 0;
        updateVariables();
    }

    public void updateVariables() {
        this.diseaseModifier = 5 + (int)(getNumberOfDiseased()*0.10) - (int)(getNumLlama()*2);

        // increases with morale - 20% base chance
        this.diseaseCureChance = (int)(20*(1+(morale/200.0)));
        // decreases with morale - 2% base chance
        this.diseaseKillChance = (int)(2*(1+(-morale/200.0)));

        int sleepMod = 0;
        if (getDaysSinceSlept() > 3) {
            sleepMod = -20;
        } else if (getDaysSinceSlept() > 1) {
            sleepMod = 0;
        } else if (getDaysSinceSlept() >= 0) {
            sleepMod = 15;
        }

        int noFoodMod = 0;
        if (foodSupply.size() == 0) {
            daysWithNoFood = daysWithNoFood + (1/24.0);
            noFoodMod = -(int)(daysWithNoFood*20.0);
        }

        int noWaterMod = 0;
        if (waterSupply.size() == 0) {
            daysWithNoWater = daysWithNoWater + (1/24.0);
            noWaterMod = -(int)(daysWithNoWater*35.0);
        }

        if (temporaryMoraleModifier > 3 || temporaryMoraleModifier < 3) {
            // temporary morale decays at a rate of 50% every update cycle
            temporaryMoraleModifier = temporaryMoraleModifier*0.5;
        } else {
            temporaryMoraleModifier = 0;
        }

        this.morale = (int)((this.getSize()*0.25) + sleepMod + noFoodMod + noWaterMod
            + temporaryMoraleModifier);

        this.walkingPace = 1 + (morale/100.0);
        if (this.walkingPace < 0.1) {
            this.walkingPace = 0.1; // minimum walking pace
        }

        // 50% base with each animal adding 2%, each diseased animal subtracting 1.5% + morale percent
        this.huntingSuccess = 50 + (numLion*2) - (int)(numDiseasedLion*1.5) + morale;
        this.collectingSuccess = 50 + (numGiraffe*2) - (int)(numDiseasedGiraffe*1.5) + morale;

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
        updatePartyImage();
    }

    public void addGiraffe(int addedGiraffes) {
        this.numGiraffe = this.numGiraffe + addedGiraffes;
        updatePartyImage();
    }

    public void addLlama(int addedLlamas) {
        this.numLlama = this.numLlama + addedLlamas;
        updatePartyImage();
    }

    private void updatePartyImage() {

    }

    /**
     * Makes the party move forwards
     * Increases distance traveled and makes timeSinceSlept increase
     * Decreases Food and Water amount
     */
    public void moveForward() {
        updateVariables();
        randomlyKill();
        int beforeDiseasedNum = getNumberOfDiseased();
        consumeFood();
        consumeWater();
        int diseasedThatHour = getNumberOfDiseased() - beforeDiseasedNum;
        System.out.println("In one hour "+diseasedThatHour+" animals were diseased");
        daysSinceSlept = daysSinceSlept + (1/24.0);
        distanceTraveled = distanceTraveled + walkingPace;
    }

    public void sleep() {
        updateVariables();
        consumeFood();
        consumeWater();
        randomlyCure(); // when you sleep some animals have a chance of being cured from a good night's sleep
        daysSinceSlept = 0; // all refreshed!
    }

    public void randomlyKill() {
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

    public void randomlyCure() {
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

    public void consumeFood() {
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

    public void consumeWater() {
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
                    this.numDiseasedLion--;
                }
            }

            // see if giraffes get disease
            for (int i=0; i<this.numGiraffe; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numGiraffe--;
                    this.numDiseasedGiraffe--;
                }
            }

            // see if llamas get disease
            for (int i=0; i<this.numLlama; i++) {
                if (diseasePercent >= 0 && RandomChance.rollForChance(diseasePercent))  {
                    this.numLlama--;
                    this.numDiseasedLlama--;
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

    public BufferedImage getPartyImage() {
        return partyImage;
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
}
