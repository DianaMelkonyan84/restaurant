package org.example.chating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepasitory userRepasitory;

    public User saveUser(User user) {

        return userRepasitory.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepasitory.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepasitory.findAll();
    }


    public void deleteUser(Long id) {
        userRepasitory.deleteById(id);
    }


}
