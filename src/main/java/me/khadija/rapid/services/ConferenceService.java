package me.khadija.rapid.services;

import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.conference.ConferenceMapper;
import me.khadija.rapid.data.user.User;
import me.khadija.rapid.data.user.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private final ConferenceMapper conferenceMapper;
    private final UserMapper userMapper;

    public ConferenceService(ConferenceMapper conferenceMapper, UserMapper userMapper) {
        this.conferenceMapper = conferenceMapper;
        this.userMapper = userMapper;
    }

    public Conference fetch(String name) {
        return conferenceMapper.find(name);
    }

    public List<Conference> fetchAll() {
        return conferenceMapper.findAll();
    }

    public Optional<Conference> find(String name) {
        return Optional.ofNullable(conferenceMapper.find(name));
    }

    public Conference save(Conference conference) {
        conferenceMapper.insert(conference);
        return conference;
    }

    public Conference update(Conference conference) {
        conferenceMapper.update(conference);
        return conference;
    }

    public void update(String name,
                       String ownerUsername,
                       String title,
                       String description,
                       int member_limit) {
        find(name).ifPresent(conference -> {
            if (ownerUsername != null && !ownerUsername.isBlank()) {
                conference.setOwner(userMapper.find(ownerUsername));
            }
            if (title != null && !title.isBlank()) {
                conference.setTitle(title);
            }
            if (description != null && !description.isBlank()) {
                conference.setDescription(description);
            }
            if (member_limit == 0) {
                throw new IllegalStateException("Cannot update the member limit to 0");
            }
            conference.setMember_limit(member_limit);
            update(conference);
        });
    }

    public Conference delete(Conference conference) {
        conferenceMapper.delete(conference);
        return conference;
    }

    public void createTable() {
        conferenceMapper.createTableIfNotExists();
    }
}
