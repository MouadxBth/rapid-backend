package me.khadija.rapid.services;

import me.khadija.rapid.mappers.UserConferenceMapper;
import me.khadija.rapid.models.Conference;
import me.khadija.rapid.models.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserConferenceService {

    private final UserConferenceMapper userConferenceMapper;

    public UserConferenceService(UserConferenceMapper userConferenceMapper) {
        this.userConferenceMapper = userConferenceMapper;
    }

    public Set<User> findMembers(Conference conference) {
        return userConferenceMapper.findMembers(conference);
    }

    public Set<Conference> findConferences(User user) {
        return userConferenceMapper.findConferences(user);
    }

    public void addUserToConference(User user, Conference conference) {
        userConferenceMapper.insert(user, conference);
    }

    public void removeUserFromConference(User user, Conference conference) {
        userConferenceMapper.delete(user, conference);
    }

    public boolean isInConference(User user, Conference conference) {
        return findConferences(user).contains(conference);
    }

    public void createTable() {
        userConferenceMapper.createTableIfNotExists();
    }

    public void dropTable() {
        userConferenceMapper.dropTableIfExists();
    }
}
