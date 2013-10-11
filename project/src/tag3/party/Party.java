package tag3.party;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Party {

    // food and water supplies of the party
    private int foodAmount, waterAmount;
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
    private int daysSinceSlept;

    private int walkingPace;
    private int distanceTraveled;

    public Party(int numberLion, int numberGiraffe, int numberLlamma) {
        this.numLion = numberLion;
        this.numGiraffe = numberGiraffe;
        this.numLlamma = numberLlamma;
        this.morale = 0; // 0% morale
        this.daysSinceSlept = 0;
        this.walkingPace = 1;
    }

    public int getSize() {
        return (numLion + numGiraffe + numLlamma +
                numDiseasedGiraffe + numDiseasedLion + numDiseasedLlamma);
    }

    public void addLion(int addedLions) {
        this.numLion = this.numLion + addedLions;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public int getWaterAmount() {
        return waterAmount;
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

    public int getDaysSinceSlept() {
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
