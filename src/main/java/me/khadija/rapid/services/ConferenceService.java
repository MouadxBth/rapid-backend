package me.khadija.rapid.services;

import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.conference.ConferenceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private final ConferenceMapper conferenceMapper;

    public ConferenceService(ConferenceMapper conferenceMapper) {
        this.conferenceMapper = conferenceMapper;
    }

    public Conference fetch(String name) {
        return conferenceMapper.find(name);
    }
    public List<Conference> fetchAll() {
        return conferenceMapper.findAll();
    }

    public Optional<Conference> find(String username) {
        return Optional.ofNullable(conferenceMapper.find(username));
    }

    public Conference save(Conference conference) {
        conferenceMapper.insert(conference);
        return conference;
    }

    public Conference update(Conference conference) {
        conferenceMapper.update(conference);
        return conference;
    }

    public Conference delete(Conference conference) {
        conferenceMapper.delete(conference);
        return conference;
    }

    public void createTable() {
        conferenceMapper.createTableIfNotExists();
    }
}
