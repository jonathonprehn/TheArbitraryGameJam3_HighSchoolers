package tag3.party.supplycollection;

import tag3.party.Party;
import tag3.party.food.Food;
import tag3.party.food.Quality;
import tag3.party.food.Water;

/**
 * Created with IntelliJ IDEA.
 * User: Owner
 * Date: 10/11/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaterResource extends SupplyCollectPoint {

    public WaterResource(int size, Quality waterQuality) {
        super(size, waterQuality);
    }

    public void collectFrom(Party collectingParty) {
        if (getSize() > 0) {
            collectingParty.addWater(new Water(getQuality()));
        }
    }

}
