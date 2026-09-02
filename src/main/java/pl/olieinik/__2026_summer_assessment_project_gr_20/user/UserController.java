//package pl.olieinik.__2026_summer_assessment_project_gr_20.user;
//
//
//import org.springframework.web.bind.annotation.*;
//import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineService;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//
//
//    private final UserService service;
//    private final MachineService machineService;
//
//    public UserController(UserService service, MachineService machineService) {
//        this.service = service;
//        this.machineService = machineService;
//    }
//
//    @GetMapping
//    public List<User> getAll() {
//        return service.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public User getById(@PathVariable Long id) {
//        return service.getUser(id);
//    }
//
//    @PostMapping
//    public User create(@RequestBody User user) {
//        return service.createUser(user);
//    }
//
//    @PutMapping("/{id}")
//    public User update(@PathVariable Long id, @RequestBody User user) {
//        return service.updateUser(id, user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        service.deleteUser(id);
//    }
//
//    @PutMapping("/{userId}/assignment")
//    public User assignWorkstationAndProduct(
//            @PathVariable Long userId,
//            @RequestBody UserAssignmentRequest request
//    ) {
//        return service.assignWorkstationAndProduct(
//                userId,
//                machineService.getMachine(request.machineId()),
//                request.productId()
//        );
//    }
//}


package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserUpsertRequest request) {
        return service.createUser(request);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody UserUpsertRequest request
    ) {
        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}