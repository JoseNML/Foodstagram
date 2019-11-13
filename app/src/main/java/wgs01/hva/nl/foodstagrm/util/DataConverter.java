package wgs01.hva.nl.foodstagrm.util;

import wgs01.hva.nl.foodstagrm.model.Recipe;
import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;

/**
 * @author José Niemel
 * een class die ervoor zorgt dat de informatie die van de instagram api binnen komt
 * kan worden gebruikt in de applicatie
 */
public class DataConverter {

    /**
     * verandert de informatie van de instagram database in een Recipe object
     * @param data informatie van de instagram database
     * @return {Recipe} datamodel die wordt gebruikt binnen de applicatie
     */
    public static Recipe convertToPost(Data data){
        return new Recipe(data.getUser().getUsername(), data.getCaption().getText(), data.getId(), data.getImages().getStandard_resolution().getUrl(), "");
    }


}
