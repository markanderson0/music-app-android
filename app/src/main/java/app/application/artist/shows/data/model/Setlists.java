package app.application.artist.shows.data.model;

import java.util.ArrayList;
import java.util.List;

public class Setlists {
    public String itemsPerPage;
    public String total;
    public List<Setlist> setlist = new ArrayList<Setlist>();

    public Setlists(){}

    public Setlists(List<Setlist> setlist, String total, String itemsPerPage) {
        this.setlist = setlist;
        this.total = total;
        this.itemsPerPage = itemsPerPage;
    }

    public String getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(String itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Setlist> getSetlist() {
        return setlist;
    }

    public void setSetlist(List<Setlist> setlist) {
        this.setlist = setlist;
    }
}
