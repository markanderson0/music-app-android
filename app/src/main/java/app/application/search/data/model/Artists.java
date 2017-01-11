package app.application.search.data.model;

import java.util.ArrayList;
import java.util.List;

public class Artists {
    public List<Item> items = new ArrayList<Item>();

    public Artists(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
