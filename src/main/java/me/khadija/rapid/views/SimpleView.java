package me.khadija.rapid.views;

import me.khadija.rapid.data.user.User;
import me.khadija.rapid.registration.RegistrationService;
import me.khadija.rapid.registration.token.ConfirmationToken;
import me.khadija.rapid.services.ConferenceService;
import me.khadija.rapid.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleView {

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final RegistrationService registrationService;

    public SimpleView(UserService userService,
                      ConferenceService conferenceService,
                      RegistrationService registrationService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String users() {
        final String result = String.join(" ", userService.fetchAll().stream().map(User::toString).toList());
        return result.isBlank() ? "Empty repository" : result;
    }

    @GetMapping("/mouad")
    public String error() {
        return userService.fetch("mouad").toString();
    }

    @GetMapping("/conferences")
    public String conferences() {
        return conferenceService.fetchAll().toString();
    }

    @GetMapping(path = "user")
    public String user(@RequestParam("username") String username) {
        final User user = userService.fetch(username);
        return user == null ? "Not found" : user.toString();
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        final boolean result = registrationService.confirm(token);
        return result ? "CONFIRMED" : "NOT CONFIRMED";
    }

    @GetMapping(path = "confirmed")
    public String confirmed(@RequestParam("username") String username) {
        final boolean result = userService.find(username).stream().anyMatch(User::isEnabled);
        return result ? "CONFIRMED" : "NOT CONFIRMED";
    }

}
