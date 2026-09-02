//package pl.olieinik.__2026_summer_assessment_project_gr_20.user;
//import org.springframework.stereotype.Service;
//import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
//import pl.olieinik.__2026_summer_assessment_project_gr_20.product.Product;
//import pl.olieinik.__2026_summer_assessment_project_gr_20.product.ProductService;
//import java.util.List;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final ProductService productService;
//
//    public UserService(
//            UserRepository userRepository,
//            ProductService productService
//    ) {
//        this.userRepository = userRepository;
//        this.productService = productService;
//    }
//    // GET ALL USERS
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//
//    public User getUser(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found: " + id));
//    }
//
//
//    public User createUser(User user) {
//
//        validateUser(user);
//
//        return userRepository.save(user);
//    }
//
//
//    public User updateUser(Long id, User updated) {
//
//        User existing = getUser(id);
//
//        existing.setName(updated.getName());
//        existing.setSurname(updated.getSurname());
//        existing.setRole(updated.getRole());
//        existing.setMachine(updated.getMachine());
//
//        validateUser(existing);
//
//        return userRepository.save(existing);
//    }
//
//    public void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found: " + id);
//        }
//        userRepository.deleteById(id);
//    }
//
////    public User assignMachine(Long userId, Machine machine) {
////
////        User user = getUser(userId);
////
////        if (user.getRole() != UserRole.OPERATOR) {
////            throw new RuntimeException("Only OPERATOR can have a machine");
////        }
////
////        user.setMachine(machine);
////
////        return userRepository.save(user);
////    }
//
//    public User assignWorkstationAndProduct(
//            Long userId,
//            Machine machine,
//            Long productId
//    ) {
//        User user = getUser(userId);
//
//        if (user.getRole() != UserRole.OPERATOR) {
//            throw new RuntimeException(
//                    "Maszynę i produkt można przypisać tylko operatorowi"
//            );
//        }
//
//        Product product = productService.getById(productId);
//
//        user.setMachine(machine);
//        user.setProduct(product);
//
//        return userRepository.save(user);
//    }
//
//    private void validateUser(User user) {
//
//        if (user.getRole() == null) {
//            throw new RuntimeException("Role is required");
//        }
//
//        if (user.getRole() == UserRole.OPERATOR && user.getMachine() == null) {
//            throw new RuntimeException("Operator must have assigned machine");
//        }
//
//        if (user.getRole() == UserRole.MANAGER) {
//            user.setMachine(null); // manager never has machine
//        }
//    }
//}

package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.product.Product;
import pl.olieinik.__2026_summer_assessment_project_gr_20.product.ProductService;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MachineService machineService;
    private final ProductService productService;

    public UserService(
            UserRepository userRepository,
            MachineService machineService,
            ProductService productService
    ) {
        this.userRepository = userRepository;
        this.machineService = machineService;
        this.productService = productService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nie znaleziono użytkownika."
                ));
    }

    public User createUser(UserUpsertRequest request) {
        validateBasicData(request);

        if (request.pin() == null || request.pin().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "PIN jest wymagany podczas tworzenia użytkownika."
            );
        }

        User user = new User();
        user.setPin(request.pin());

        applyRequestToUser(user, request, null);

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserUpsertRequest request) {
        validateBasicData(request);

        User user = getUser(id);

        // PIN pozostaje bez zmian podczas edycji danych użytkownika.
        applyRequestToUser(user, request, id);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private void applyRequestToUser(
            User user,
            UserUpsertRequest request,
            Long editedUserId
    ) {
        user.setName(request.name().trim());
        user.setSurname(request.surname().trim());
        user.setRole(request.role());

        if (request.role() == UserRole.MANAGER) {
            user.setMachine(null);
            user.setProduct(null);
            return;
        }

        if (request.machineId() == null || request.productId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Operator musi mieć przypisaną maszynę i produkt."
            );
        }

//        boolean machineOccupied = editedUserId == null
//                ? userRepository.existsByMachine_Id(request.machineId())
//                : userRepository.existsByMachine_IdAndIdNot(
//                request.machineId(),
//                editedUserId
//        );

//        if (machineOccupied) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Wybrana maszyna jest już przypisana do innego operatora."
//            );
//        }

        Machine machine = machineService.getMachine(request.machineId());
        Product product = productService.getById(request.productId());

        user.setMachine(machine);
        user.setProduct(product);
    }

    private void validateBasicData(UserUpsertRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Imię jest wymagane."
            );
        }

        if (request.surname() == null || request.surname().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nazwisko jest wymagane."
            );
        }

        if (request.role() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rola jest wymagana."
            );
        }
    }

    public User assignMachine(Long userId, Machine machine) {

        User user = getUser(userId);

        if (user.getRole() != UserRole.OPERATOR) {
            throw new RuntimeException("Only OPERATOR can have a machine");
        }

        user.setMachine(machine);

        return userRepository.save(user);
    }
}