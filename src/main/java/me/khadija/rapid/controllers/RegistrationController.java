package me.khadija.rapid.controllers;

import me.khadija.rapid.data.user.User;
import me.khadija.rapid.registration.RegistrationRequest;
import me.khadija.rapid.registration.RegistrationService;
import me.khadija.rapid.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping(path = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirm(token) ? "<h1>CONFIRMED<h1>" : "<h1>NOT CONFIRMED<h1>";
    }

    @GetMapping(path = "confirmed")
    public String confirmed(@RequestParam("username") String username) {
        final boolean result = userService.find(username).stream().anyMatch(User::isEnabled);
        return result ? "CONFIRMED" : "NOT CONFIRMED";
    }
}
