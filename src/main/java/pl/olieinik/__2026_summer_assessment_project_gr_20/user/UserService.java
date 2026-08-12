package pl.olieinik.__2026_summer_assessment_project_gr_20.user;
import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }


    public User createUser(User user) {

        validateUser(user);

        return userRepository.save(user);
    }


    public User updateUser(Long id, User updated) {

        User existing = getUser(id);

        existing.setName(updated.getName());
        existing.setSurname(updated.getSurname());
        existing.setRole(updated.getRole());
        existing.setMachine(updated.getMachine());

        validateUser(existing);

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    public User assignMachine(Long userId, Machine machine) {

        User user = getUser(userId);

        if (user.getRole() != UserRole.OPERATOR) {
            throw new RuntimeException("Only OPERATOR can have a machine");
        }

        user.setMachine(machine);

        return userRepository.save(user);
    }

    private void validateUser(User user) {

        if (user.getRole() == null) {
            throw new RuntimeException("Role is required");
        }

        if (user.getRole() == UserRole.OPERATOR && user.getMachine() == null) {
            throw new RuntimeException("Operator must have assigned machine");
        }

        if (user.getRole() == UserRole.MANAGER) {
            user.setMachine(null); // manager never has machine
        }
    }
}