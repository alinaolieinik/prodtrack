package pl.olieinik.__2026_summer_assessment_project_gr_20.machine;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MachineState state;

    private LocalDateTime lastUpdate;

    public Machine() {}

    public Machine(String name, MachineState status) {
        this.name = name;
        this.state = state;
        this.lastUpdate = LocalDateTime.now();
    }

    // getters & setters
}