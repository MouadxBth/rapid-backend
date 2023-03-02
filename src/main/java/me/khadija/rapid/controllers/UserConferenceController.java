package me.khadija.rapid.controllers;

import me.khadija.rapid.data.UserConferenceService;
import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.user.User;
import me.khadija.rapid.services.ConferenceService;
import me.khadija.rapid.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class UserConferenceController {

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final UserConferenceService userConferenceService;

    public UserConferenceController(UserService userService,
                                    ConferenceService conferenceService,
                                    UserConferenceService userConferenceService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.userConferenceService = userConferenceService;
    }

    @PostMapping("/join/{conference}")
    public void joinConference(
            @PathVariable("conference") String conferenceName,
            @RequestParam String username) {
        final Conference conference = conferenceService.find(conferenceName)
                .orElseThrow(() -> new IllegalStateException("Could not find conference with the name " + conferenceName));

        final User user = userService.find(username)
                .orElseThrow(() -> new IllegalStateException("Could not find user with the username " + username));

        if (userConferenceService.isInConference(user, conference))
            throw new IllegalStateException("User " + username + " is already in conference " + conferenceName);

        if (conference.getMember_limit() > 0
                && userConferenceService.findMembers(conference).size() >= conference.getMember_limit()) {
            throw new IllegalStateException("Cannot exceed the conference member limit");
        }

        userConferenceService.addUserToConference(user, conference);
    }

    @PostMapping("/leave/{conference}")
    public void leaveConference(
            @PathVariable("conference") String conferenceName,
            @RequestParam String username) {
        final Conference conference = conferenceService.find(conferenceName)
                .orElseThrow(() -> new IllegalStateException("Could not find conference with the name " + conferenceName));

        final User user = userService.find(username)
                .orElseThrow(() -> new IllegalStateException("Could not find user with the username " + username));

        if (!userConferenceService.isInConference(user, conference))
            throw new IllegalStateException("User " + username + " is not in conference " + conferenceName);

        userConferenceService.removeUserFromConference(user, conference);
    }

    @PostMapping("/conferences/add/")
    public void addConference(
            @RequestParam String name,
            @RequestParam String owner,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) int member_limit) {
        final Optional<Conference> conference = conferenceService.find(name);

        if (conference.isPresent())
            throw new IllegalStateException("Conference " + name + " Already exists!");

        final User user = userService.find(owner)
                .orElseThrow(() -> new IllegalStateException("Could not find user with the username " + owner + " cannot set owner null"));

        conferenceService.save(new Conference(name, user, title, description, member_limit == 0 ? -1 : member_limit));
    }

}
