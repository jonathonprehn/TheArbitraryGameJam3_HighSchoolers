package tag3.party.supplycollection;

import tag3.party.Party;
import tag3.party.food.Food;
import tag3.party.food.Quality;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SupplyCollectPoint {

    // quality of supplies from this point
    private Quality quality;
    // the amount of stuff taken from this point
    private int size;

    public SupplyCollectPoint(int size, Quality resourceQuality) {
        this.quality = resourceQuality;
        this.size = size;
    }

    public Quality getQuality() {
        return quality;
    }

    public abstract Food collectFrom(Party collectingParty);

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
