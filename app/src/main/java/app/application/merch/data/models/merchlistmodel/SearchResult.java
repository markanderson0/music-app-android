package app.application.merch.data.models.merchlistmodel;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    public List<Item> item = new ArrayList<Item>();

    public SearchResult(List<Item> item) {
        this.item = item;
    }
}
