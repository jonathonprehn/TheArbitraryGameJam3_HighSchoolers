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

    private int walkingPace;
    private int distanceTraveled;

    public Party(int numberLion, int numberGiraffe, int numberLlama) {
        this.numLion = numberLion;
        this.numGiraffe = numberGiraffe;
        this.numLlama = numberLlama;
        this.morale = 0; // 0% morale
        this.daysSinceSlept = 0;
        this.walkingPace = 1;
        this.distanceTraveled = 0;
    }

    public void updateVariables() {
        this.diseaseModifier = (int)(getNumberOfDiseased()*0.10) - (getNumLlama()*5);

        int sleepMod = 0;
        if (getDaysSinceSlept() > 3) {
            sleepMod = -20;
        } else if (getDaysSinceSlept() > 1) {
            sleepMod = 0;
        } else if (getDaysSinceSlept() >= 0) {
            sleepMod = 15;
        }
        this.morale = (int)(this.getSize()*0.25)+sleepMod;
    }

    public int getSize() {
        return (numLion + numGiraffe + numLlama +
                numDiseasedGiraffe + numDiseasedLion + numDiseasedLlama);
    }

    public int getNumberOfDiseased() {
        return (numDiseasedGiraffe + numDiseasedLion + numLlama);
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
        consumeFood();
        consumeWater();

    }

    public void sleep() {
        updateVariables();
        consumeFood();
        consumeWater();
        daysSinceSlept = 0; // all refreshed!

    }

    public void consumeFood() {
        if (foodSupply.size() > 0) {
            Food eatenFood = foodSupply.get(foodSupply.size());
            int diseasePercent = eatenFood.getDiseasePercentMod() + diseaseModifier;
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

            foodSupply.remove(foodSupply.size());
        }
    }

    public void consumeWater() {
        if (waterSupply.size() > 0) {
            Water drinkedWater = waterSupply.get(waterSupply.size());
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

            waterSupply.remove(waterSupply.size());
        }
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

    public int getWalkingPace() {
        return walkingPace;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getMorale() {
        return morale;
    }

    public BufferedImage getPartyImage() {
        return partyImage;
    }
}
