package app.application.search.data.model;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public String id;
    public String name;
    public List<Image> images = new ArrayList<Image>();

    public Item(String id, String name, List<Image> images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
