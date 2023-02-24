package me.khadija.rapid.services;

import me.khadija.rapid.data.user.UserMapper;
import me.khadija.rapid.data.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public User fetch(String username) {
        return userMapper.find(username);
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

    public User delete(User user) {
        userMapper.delete(user);
        return user;
    }

    public void createTable() {
        userMapper.createTableIfNotExists();
    }
}
