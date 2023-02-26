package me.khadija.rapid;

import me.khadija.rapid.data.UserConferenceService;
import me.khadija.rapid.data.token.ConfirmationTokenService;
import me.khadija.rapid.services.ConferenceService;
import me.khadija.rapid.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RapidBackendApplication implements CommandLineRunner {

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final UserConferenceService userConferenceService;
    private final ConfirmationTokenService confirmationTokenService;

    public RapidBackendApplication(UserService userService,
                                   ConferenceService conferenceService,
                                   UserConferenceService userConferenceService,
                                   ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.userConferenceService = userConferenceService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RapidBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userService.createTable();
        conferenceService.createTable();
        userConferenceService.createTable();
        confirmationTokenService.createTable();
    }
}
