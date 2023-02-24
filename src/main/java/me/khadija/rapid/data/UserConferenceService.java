package me.khadija.rapid.data;

import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.user.User;
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

    public User addUserToConference(User user, Conference conference) {
        userConferenceMapper.insert(user, conference);
        return user;
    }

    public User removeUserFromConference(User user, Conference conference) {
        //userConferenceMapper.delete(user, conference);
        return user;
    }

    public boolean isInConference(User user, Conference conference) {
        return findConferences(user).contains(conference);
    }

    public void createTable() {
        userConferenceMapper.createTableIfNotExists();
    }
}
