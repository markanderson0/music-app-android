package app.application.tickets.ticketmodel;

public class Venue {
    public String name;
    public City city;
    public State state;
    public Country country;
    public Location location;

    public Venue(String name, City city, State state, Country country, Location location) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.location = location;
    }
}
