package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class FindItemsAdvancedResponse {
    public List<SearchResult> searchResult = new ArrayList<SearchResult>();

    public FindItemsAdvancedResponse(List<SearchResult> searchResult) {
        this.searchResult = searchResult;
    }
}
