package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class MerchListModel {
    public List<FindItemsAdvancedResponse> findItemsAdvancedResponse = new ArrayList<FindItemsAdvancedResponse>();

    public MerchListModel() {}

    public MerchListModel(List<FindItemsAdvancedResponse> findItemsAdvancedResponse) {
        this.findItemsAdvancedResponse = findItemsAdvancedResponse;
    }
}

