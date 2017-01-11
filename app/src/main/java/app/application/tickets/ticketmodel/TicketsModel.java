package app.application.tickets.ticketmodel;

public class TicketsModel {
    public Embedded _embedded;
    public Page page;

    public TicketsModel() {}

    public TicketsModel(Embedded _embedded, Page page) {
        this._embedded = _embedded;
        this.page = page;
    }
}
