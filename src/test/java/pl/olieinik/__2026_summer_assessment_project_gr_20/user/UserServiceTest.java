package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void getAllUsers() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));

        List<User> result = service.getAllUsers();

        assertThat(result).hasSize(2);
    }

    @Test
    void getUser() {
        User user = new User();
        user.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(user));

        User result = service.getUser(2L);

        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    void createUser_operator_shouldSaveWithMachine() {

        Machine machine = new Machine();
        machine.setId(10L);

        User user = new User();
        user.setRole(UserRole.OPERATOR);
        user.setMachine(machine);

        when(repository.save(user)).thenReturn(user);

        User result = service.createUser(user);

        assertThat(result.getRole()).isEqualTo(UserRole.OPERATOR);
        assertThat(result.getMachine()).isEqualTo(machine);

        verify(repository).save(user);
    }

    @Test
    void createUser_manager_shouldSaveWithoutMachine() {

        User manager = new User();
        manager.setRole(UserRole.MANAGER);

        when(repository.save(manager)).thenReturn(manager);

        User result = service.createUser(manager);

        assertThat(result.getRole()).isEqualTo(UserRole.MANAGER);
        assertThat(result.getMachine()).isNull();

        verify(repository).save(manager);
    }

    @Test
    void updateUser() {

        User existing = new User();
        existing.setId(2L);
        existing.setRole(UserRole.MANAGER);

        User update = new User();
        update.setRole(UserRole.MANAGER);

        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        User result = service.updateUser(2L, update);

        assertThat(result).isNotNull();
        verify(repository).save(existing);
    }

    @Test
    void assignMachine() {

        User operator = new User();
        operator.setId(2L);
        operator.setRole(UserRole.OPERATOR);

        Machine machine = new Machine();
        machine.setId(1L);

        when(repository.findById(2L)).thenReturn(Optional.of(operator));
        when(repository.save(operator)).thenReturn(operator);

        User result = service.assignMachine(2L, machine);

        assertThat(result.getMachine()).isEqualTo(machine);

        verify(repository).save(operator);
    }
}