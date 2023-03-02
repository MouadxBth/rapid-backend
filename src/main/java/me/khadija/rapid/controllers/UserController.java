package me.khadija.rapid.controllers;

import me.khadija.rapid.data.UserConferenceService;
import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.user.User;
import me.khadija.rapid.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserConferenceService userConferenceService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          UserConferenceService userConferenceService,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userConferenceService = userConferenceService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(path = {"/all", "/", ""})
    public List<User> users(){
        return userService.fetchAll();
    }
    @GetMapping("/conferences/{username}")
    public Set<Conference> conferences(@PathVariable("username") String username) {
        final User user = userService.find(username).orElse(null);
        if (user != null) {
            return userConferenceService.findConferences(user);
        }
        return new HashSet<>();
    }

    @GetMapping("/find/{username}")
    public User byUsername(@PathVariable("username") String username){
        return userService.fetch(username);
    }

    @GetMapping("/find_email/{email}")
    public User byEmail(@PathVariable("email") String email){
        return userService.fetchByEmail(email);
    }

    @PutMapping("/update/{username}")
    public void updateUser(
            @PathVariable("username") String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Boolean enabled) {
        userService.update(username,
                firstName,
                lastName,
                email,
                passwordEncoder.encode(password),
                enabled);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable("username")String username ){
        userService.find(username).ifPresent(userService::delete);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        System.out.println(username + " " + password);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("AUTHENTICATED " + authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new LoginResponse(userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/logout")
    public void logout() {

    }

}
