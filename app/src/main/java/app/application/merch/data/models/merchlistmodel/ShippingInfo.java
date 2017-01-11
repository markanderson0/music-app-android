package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class ShippingInfo {
    public List<ShippingServiceCost> shippingServiceCost = new ArrayList<ShippingServiceCost>();

    public ShippingInfo(List<ShippingServiceCost> shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }
}
