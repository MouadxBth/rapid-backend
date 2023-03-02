package me.khadija.rapid.services;

import me.khadija.rapid.data.user.UserMapper;
import me.khadija.rapid.data.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User result = userMapper.find(username);

        if (result == null)
            throw new UsernameNotFoundException("Could not find user with the username: " + username);
        return new org.springframework.security.core.userdetails.User(result.getUsername(),
                result.getHashedPassword(),
                result.isEnabled(),
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public User fetch(String username) {
        return userMapper.find(username);
    }

    public User fetchByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public List<User> fetchAll() {
        return userMapper.findAll();
    }

    public Optional<User> find(String username) {
        return Optional.ofNullable(userMapper.find(username));
    }

    public User save(User user) {
        userMapper.insert(user);
        return user;
    }

    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    public void update(String username,
                       String hashedPassword,
                       String firstName,
                       String lastName,
                       String email,
                       Boolean enabled) {

        find(username).ifPresent(user -> {
            if (hashedPassword != null && !hashedPassword.isBlank()) {
                user.setHashedPassword(hashedPassword);
            }
            if (firstName != null && !firstName.isBlank()) {
                user.setFirstName(firstName);
            }
            if (lastName != null && !lastName.isBlank()) {
                user.setLastName(lastName);
            }
            if (email != null && !email.isBlank()) {
                user.setEmail(email);
            }
            if (enabled != null) {
                user.setEnabled(enabled);
            }
            save(user);
        });

    }

    public User delete(User user) {
        userMapper.delete(user);
        return user;
    }

    public void createTable() {
        userMapper.createTableIfNotExists();
    }
}
