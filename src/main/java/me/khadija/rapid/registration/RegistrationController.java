package me.khadija.rapid.registration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "api/v1/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "api/v1/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirm(token) ? "<h1>CONFIRMED<h1>" : "<h1>NOT CONFIRMED<h1>";
    }
}
