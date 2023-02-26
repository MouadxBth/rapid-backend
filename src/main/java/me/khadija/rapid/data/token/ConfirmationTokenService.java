package me.khadija.rapid.data.token;

import me.khadija.rapid.data.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenMapper confirmationTokenMapper;

    public ConfirmationTokenService(ConfirmationTokenMapper confirmationTokenMapper) {
        this.confirmationTokenMapper = confirmationTokenMapper;
    }

    public List<ConfirmationToken> fetch(User user) {
        return confirmationTokenMapper.findUser(user);
    }

    public List<ConfirmationToken> fetchAll() {
        return confirmationTokenMapper.findAll();
    }

    public Optional<ConfirmationToken> find(String id) {
        return Optional.ofNullable(confirmationTokenMapper.find(id));
    }

    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        confirmationTokenMapper.insert(confirmationToken);
        return confirmationToken;
    }

    public ConfirmationToken update(ConfirmationToken confirmationToken) {
        confirmationTokenMapper.update(confirmationToken);
        return confirmationToken;
    }

    public ConfirmationToken delete(ConfirmationToken confirmationToken) {
        confirmationTokenMapper.delete(confirmationToken);
        return confirmationToken;
    }

    public void createTable() {
        confirmationTokenMapper.createTableIfNotExists();
    }
}
