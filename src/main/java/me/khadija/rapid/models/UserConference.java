package me.khadija.rapid.models;

public class UserConference {

    private long id;
    private User user;
    private Conference conference;

    public UserConference() {
    }

    public UserConference(User user, Conference conference) {
        this.user = user;
        this.conference = conference;
    }

    @Override
    public String toString() {
        return "UserConference{" +
                "id=" + id +
                ", user=" + user +
                ", conference=" + conference +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
