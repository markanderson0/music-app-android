package app.application.artist.shows.data.model;

import java.util.ArrayList;
import java.util.List;

public class Sets {
    public List<Set> set = new ArrayList<Set>();

    public Sets() {
    }

    public Sets(List<Set> set) {
        this.set = set;
    }

    public List<Set> getSet() {
        return set;
    }

    public void setSet(List<Set> set) {
        this.set = set;
    }
}
