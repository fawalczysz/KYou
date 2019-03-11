package fr.isima.kyou.beans.api;

import java.util.List;

public class Product {

    private List<Object> additivesTags = null;
    private Nutriments nutriments;

    public List<Object> getAdditivesTags() {
        return additivesTags;
    }

    public void setAdditivesTags(List<Object> additivesTags) {
        this.additivesTags = additivesTags;
    }

    public Nutriments getNutriments() {
        return nutriments;
    }

    public void setNutriments(Nutriments nutriments) {
        this.nutriments = nutriments;
    }

}
