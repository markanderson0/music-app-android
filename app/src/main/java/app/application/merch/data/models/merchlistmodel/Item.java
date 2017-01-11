package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public List<String> itemId = new ArrayList<String>();
    public List<String> title = new ArrayList<String>();
    public List<String> viewItemURL = new ArrayList<String>();
    public List<String> galleryURL = new ArrayList<String>();
    public List<SellingStatus> sellingStatus = new ArrayList<SellingStatus>();
    public List<ShippingInfo> shippingInfo = new ArrayList<ShippingInfo>();

    public Item(List<String> itemId, List<String> title, List<String> viewItemURL, List<String> galleryURL, List<SellingStatus> sellingStatus, List<ShippingInfo> shippingInfo) {
        this.itemId = itemId;
        this.title = title;
        this.viewItemURL = viewItemURL;
        this.galleryURL = galleryURL;
        this.sellingStatus = sellingStatus;
        this.shippingInfo = shippingInfo;
    }
}
