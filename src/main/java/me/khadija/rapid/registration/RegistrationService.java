package me.khadija.rapid.registration;

import me.khadija.rapid.data.user.User;
import me.khadija.rapid.email.EmailSender;
import me.khadija.rapid.registration.token.ConfirmationToken;
import me.khadija.rapid.registration.token.ConfirmationTokenService;
import me.khadija.rapid.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static me.khadija.rapid.utilities.Utilities.buildEmail;

@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserService userService,
                               ConfirmationTokenService confirmationTokenService,
                               EmailSender emailSender,
                               PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegistrationRequest request) {
        System.out.println(request);
        final User user = new User(request.username(),
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName(),
                request.email(),
                false);
        if (userService.find(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already signed up!");
        }

        final ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                null,
                user
        );

        confirmationTokenService.save(confirmationToken);
        userService.save(user);

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + confirmationToken.getToken();
        emailSender.send(
                request.email(),
                buildEmail(request.firstName(), link));
        return confirmationToken.getToken();
    }

    public boolean confirm(String token) {
        final Optional<ConfirmationToken> optional = confirmationTokenService.find(token);
        optional.ifPresent(confirmationToken -> {
            if (confirmationToken.getConfirmedAt() != null) {
                throw new IllegalStateException("Email already confirmed!");
            }
            LocalDateTime expiredAt = confirmationToken.getExpiresAt();

            if (expiredAt.isBefore(LocalDateTime.now())) {
                throw new IllegalStateException("Confirmation token expired!");
            }
            if (confirmationToken.getUser() == null) {
                throw new IllegalStateException("Invalid token!");
            }
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenService.update(confirmationToken);

            confirmationToken.getUser().setEnabled(true);
            userService.update(confirmationToken.getUser());
        });
        return optional.isPresent();
    }

}
