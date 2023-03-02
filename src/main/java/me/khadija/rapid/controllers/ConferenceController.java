package me.khadija.rapid.controllers;

import me.khadija.rapid.data.UserConferenceService;
import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.user.User;
import me.khadija.rapid.services.ConferenceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final UserConferenceService userConferenceService;

    public ConferenceController(ConferenceService conferenceService,
                                UserConferenceService userConferenceService) {
        this.conferenceService = conferenceService;
        this.userConferenceService = userConferenceService;
    }

    @GetMapping(path = {"/all", "/", ""})
    public List<Conference> conferences(){
        return conferenceService.fetchAll();
    }

    @GetMapping("/members/{name}")
    public Set<User> members(@PathVariable("name") String name){
        final Conference conference = conferenceService.find(name).orElse(null);
        if (conference != null) {
            return userConferenceService.findMembers(conference);
        }
        return new HashSet<>();
    }

    @GetMapping("/find/{name}")
    public Conference byName(@PathVariable("name") String name){
        return conferenceService.fetch(name);
    }

    @PutMapping("/update/{name}")
    public void updateConference(
            @PathVariable("name") String name,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) int member_limit) {
        conferenceService.update(name, owner, title, description, member_limit);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteConference(@PathVariable("name")String name){
        conferenceService.find(name).ifPresent(conferenceService::delete);
    }

}
