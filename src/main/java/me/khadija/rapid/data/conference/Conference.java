package me.khadija.rapid.data.conference;

import me.khadija.rapid.data.user.User;

import java.util.Objects;
import java.util.Set;

public class Conference {
    private long id;
    private String name;
    private User owner;
    private String title;
    private String description;

    public Conference() {
    }

    public Conference(String name, User owner, String title, String description) {
        this.name = name;
        this.owner = owner;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner.toString() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return id == that.id
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

}
