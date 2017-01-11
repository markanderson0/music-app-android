package app.application.tickets.ticketssearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketsSearchModel implements Parcelable {
    public String name;
    public String url;
    public String date;
    public String venue;
    public String city;
    public String state;
    public String country;
    public Double lat;
    public Double lng;

    public TicketsSearchModel() {
    }

    public TicketsSearchModel(String name, String url, String date, String venue, String city, String state, String country, Double lat, Double lng) {
        this.name = name;
        this.url = url;
        this.date = date;
        this.venue = venue;
        this.city = city;
        this.state = state;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
    }

    protected TicketsSearchModel(Parcel in) {
        name = in.readString();
        url = in.readString();
        date = in.readString();
        venue = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<TicketsSearchModel> CREATOR = new Creator<TicketsSearchModel>() {
        @Override
        public TicketsSearchModel createFromParcel(Parcel in) {
            return new TicketsSearchModel(in);
        }

        @Override
        public TicketsSearchModel[] newArray(int size) {
            return new TicketsSearchModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(date);
        parcel.writeString(venue);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }
}
