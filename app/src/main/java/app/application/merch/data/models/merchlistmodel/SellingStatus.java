package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class SellingStatus {
    public List<ConvertedCurrentPrice> convertedCurrentPrice = new ArrayList<ConvertedCurrentPrice>();

    public SellingStatus(List<ConvertedCurrentPrice> convertedCurrentPrice) {
        this.convertedCurrentPrice = convertedCurrentPrice;
    }
}
