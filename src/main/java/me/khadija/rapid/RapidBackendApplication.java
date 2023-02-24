package me.khadija.rapid;

import me.khadija.rapid.data.UserConferenceService;
import me.khadija.rapid.registration.token.ConfirmationTokenService;
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

        conferenceService.find("test").ifPresent(conference ->
                userConferenceService.findMembers(conference).forEach(System.out::println));
        userService.find("mouad").ifPresent(user ->
                userConferenceService.findConferences(user).forEach(System.out::println));

//        final User user = new User("mouad",
//                passwordEncoder.encode("123456"),
//                "mouad",
//                "bouthaich",
//                "mouad@hmail.com",
//                true);
//
//        final Conference conference = new Conference("test",
//                user,
//                "Test",
//                "A testing conference");
//
//        userService.save(user);
//        conferenceService.save(conference);
//
//        userService.find("mouad")
//                .ifPresent(mouad -> {
//                    conferenceService.find("test").ifPresent(test -> {
//                        userConferenceService.addUserToConference(mouad, test);
//                    });
//                });


//        userService.find("mouad").ifPresent(user -> {
//            final Conference conference = new Conference("test", user, "Test", "A testing conference");
//            if (conferenceService.find(conference.getName()).isEmpty())
//                conferenceService.save(conference);
//            userConferenceService.addUserToConference(user, conference);
//        });
    }
}
