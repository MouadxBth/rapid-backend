package me.khadija.rapid.models;

import java.util.Objects;

public class Conference {
    private long id;
    private String name;
    private User owner;
    private String title;
    private String description;
    private int member_limit;

    private boolean privateConference;

    public Conference() {
    }

    public Conference(String name, User owner,
                      String title, String description,
                      int member_limit, boolean privateConference) {
        this.name = name;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.member_limit = member_limit;
        this.privateConference = privateConference;
    }

    public boolean isPrivateConference() {
        return privateConference;
    }

    public void setPrivateConference(boolean privateConference) {
        this.privateConference = privateConference;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", member_limit=" + member_limit +
                ", privateConference=" + privateConference +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return id == that.id
                && member_limit == that.member_limit
                && privateConference == that.privateConference
                && Objects.equals(name, that.name)
                && Objects.equals(owner, that.owner)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMember_limit() {
        return member_limit;
    }

    public void setMember_limit(int member_limit) {
        this.member_limit = member_limit;
    }
}
