package tag3.party.food;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 3:34 PM
 * What the party eats. It might be infected
 */
public class Food {

    private Quality quality;
    private int diseasePercentMod;

    /**
     * A piece of food, enough for the party to eat for one day
     * @param foodQuality How is the quality of the food? The qualities of food and their effects are
     *                    - Good - 0% base chance of disease
     *                    - Questionable - +15% base chance of disease
     *                    - Strange - +30% base chance of disease
     *                    - Disgusting - +40% base chance of disease
     */
    public Food(Quality foodQuality) {
        this.quality = foodQuality;
        switch(foodQuality) {
            case GOOD:
                diseasePercentMod = 0;
                break;
            case QUESTIONABLE:
                diseasePercentMod = 15;
                break;
            case STRANGE:
                diseasePercentMod = 30;
                break;
            case DISGUSTING:
                diseasePercentMod = 40;
                break;
        }
    }

    public Quality getQuality() {
        return this.quality;
    }

    public int getDiseasePercentMod() {
        return this.diseasePercentMod;
    }
}
