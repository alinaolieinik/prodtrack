import type { ManagerMachine } from "../../types/ManagerDashboard";
import "./MachinesTab.css";

interface MachinesTabProps {
    machines: ManagerMachine[];
}

function MachinesTab({ machines }: MachinesTabProps) {
    return (
        <section  className="machines-tab">
            <h2>Maszyny</h2>

            <table  className="machines-table">
                <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Status</th>
                    <th>Ostatnia aktualizacja</th>
                </tr>
                </thead>

                <tbody>
                {machines.map((machine) => (
                    <tr key={machine.id}>
                        <td>{machine.name}</td>
                        <td>{machine.state}</td>
                        <td>
                            {new Date(machine.lastUpdate)
                                .toLocaleString("pl-PL")}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </section>
    );
}

export default MachinesTab;
