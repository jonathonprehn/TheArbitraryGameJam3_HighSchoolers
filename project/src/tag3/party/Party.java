package tag3.party;

import tag3.party.food.Food;
import tag3.party.food.Water;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Party {

    // food and water supplies of the party
    private ArrayList<Food> foodSupply;
    private ArrayList<Water> waterSupply;

    // a number added to the percentage chance of getting a disease
    private int diseaseModifier;
    // the chance of being more successful when hunting or collecting food
    private int huntingSuccess, collectingSuccess;
    // number of members in your party
    private int numLion, numGiraffe, numLlamma;
    private int numDiseasedLion, numDiseasedGiraffe, numDiseasedLlamma;
    // the current image of the party (changes as party grows and shrinks)
    private BufferedImage partyImage;
    // a percentage number between -100 and 100 on how much morale the party has
    private int morale;
    private double daysSinceSlept;

    private int walkingPace;
    private int distanceTraveled;

    public Party(int numberLion, int numberGiraffe, int numberLlamma) {
        this.numLion = numberLion;
        this.numGiraffe = numberGiraffe;
        this.numLlamma = numberLlamma;
        this.morale = 0; // 0% morale
        this.daysSinceSlept = 0;
        this.walkingPace = 1;
        this.distanceTraveled = 0;
    }

    public int getSize() {
        return (numLion + numGiraffe + numLlamma +
                numDiseasedGiraffe + numDiseasedLion + numDiseasedLlamma);
    }

    public int getNumberOfDiseased() {
        return (numDiseasedGiraffe + numDiseasedLion + numLlamma);
    }

    public void addLion(int addedLions) {
        this.numLion = this.numLion + addedLions;
        updatePartyImage();
    }

    public void addGiraffe(int addedGiraffes) {
        this.numGiraffe = this.numGiraffe + addedGiraffes;
        updatePartyImage();
    }

    public void addLlamma(int addedLlammas) {
        this.numLlamma = this.numLlamma + addedLlammas;
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
        updateDiseaseModifier();
        consumeFood();
        consumeWater();

    }

    public void consumeFood() {
        Food eatenFood = foodSupply.get(foodSupply.size());
        int diseasePercent = eatenFood.getDiseasePercentMod() + diseaseModifier;
        // see if lions get disease
        for (int i=0; i<this.numLion; i++) {

        }

        // see if girrafes get disease
        for (int i=0; i<this.numGiraffe; i++) {

        }

        // see if llamas get disease
        for (int i=0; i<this.numLlamma; i++) {

        }

        foodSupply.remove(foodSupply.size());
    }

    public void consumeWater() {

    }

    public void updateDiseaseModifier() {
        this.diseaseModifier = (int)(getNumberOfDiseased()*0.10) - (getNumLlamma()*5);
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

    public int getNumLlamma() {
        return numLlamma;
    }

    public int getNumDiseasedLlamma() {
        return numDiseasedLlamma;
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
