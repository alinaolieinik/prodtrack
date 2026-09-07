import {useCallback, useEffect, useState} from "react";
import type {ManagerDashboard as ManagerDashboardData} from "../types/ManagerDashboard";
import UsersTab from "./manager/UsersTab.tsx";
import NormsTab from "./manager/NormsTab.tsx";
import AnnouncementsTab from "./manager/AnnouncementsTab.tsx";
import "../App.css";
import "./ManagerDashboard.css";
import MachinesTab from "./manager/MachinesTab.tsx";
import ProductsTab from "./manager/ProductsTab.tsx";

interface ManagerDashboardProps {
    managerId: number;
    onLogout: () => void;
}

function ManagerDashboard({managerId, onLogout}: ManagerDashboardProps) {
    const [dashboard, setDashboard] =
        useState<ManagerDashboardData | null>(null);
    const loadDashboard = useCallback(async () => {
        try {
            setError(null);

            const response = await fetch(
                `http://localhost:8080/api/manager/dashboard/${managerId}`
            );

            if (!response.ok) {
                throw new Error(
                    "Nie udało się pobrać danych panelu managera."
                );
            }

            const data: ManagerDashboardData = await response.json();
            setDashboard(data);
        } catch (error) {
            setError(
                error instanceof Error
                    ? error.message
                    : "Wystąpił nieznany błąd."
            );
        }
    }, [managerId]);
    const [activeTab, setActiveTab] = useState("users");
    const [error, setError] = useState<string | null>(null);

    // useEffect(() => {
    //     fetch(`http://localhost:8080/api/manager/dashboard/${managerId}`)
    //         .then((response) => {
    //             if (!response.ok) {
    //                 throw new Error("Nie udało się pobrać danych panelu managera.");
    //             }
    //
    //             return response.json();
    //         })
    //         .then((data: ManagerDashboardData) => setDashboard(data))
    //         .catch((err: Error) => setError(err.message));
    // }, [managerId]);

    useEffect(() => {
        void loadDashboard();
    }, [loadDashboard]);
    if (error) {
        return <p>{error}</p>;
    }

    if (!dashboard) {
        return <p>Ładowanie danych...</p>;
    }

    return (
        <main className="dashboard manager-dashboard">
            {/*<header className="header manager-header">*/}
            {/*    <h1>PANEL MANAGERA</h1>*/}
            {/*    /!*<p>*!/*/}
            {/*    /!*    {dashboard.manager.name} {dashboard.manager.surname}*!/*/}
            {/*    /!*</p>*!/*/}
            {/*    <div className="logo">*/}
            {/*        Lukas*/}
            {/*    </div>*/}
            {/*</header>*/}

            <header className="header">
                <div className="navigation">
                    <button
                        type="button"
                        className="home-icon"
                        onClick={onLogout}
                        title="Wyloguj się"
                        aria-label="Wyloguj się"
                    >
                        ⌂
                    </button>                    {/*<div className="back-icon">←</div>*/}
                </div>

                <h1>PANEL MANAGERA</h1>

                <div className="logo">
                    Lukas
                </div>
            </header>

            <section className="manager-summary">
                <div className="summary-operators">
                    <p>Operatorzy pracujący: {dashboard.summary.activeOperatorsCount}</p>

                    {dashboard.summary.unattendedMachines.length > 0 && (
                        <span className="operator-warning" tabIndex={0} aria-label="Ostrzeżenie o braku zalogowanego operatora">
                            ⚠
                            <span className="operator-warning-tooltip" role="tooltip">
                                {dashboard.summary.unattendedMachines.map((machine) => (
                                    <span className="operator-warning-entry" key={machine.machineId}>
                                        <strong>
                                            {machine.machineName} — {machine.productDescriptions.join(", ")}
                                        </strong>
                                        Maszyna {machine.machineName} jest uruchomiona, ale żaden operator przypisany do tej maszyny nie jest zalogowany.
                                    </span>
                                ))}
                            </span>
                        </span>
                    )}
                </div>
                <p>Maszyny pracujące: {dashboard.summary.workingMachinesCount}</p>
                <p>Awarie: {dashboard.summary.failedMachinesCount}</p>
                <p>Aktywne ogłoszenia: {dashboard.summary.activeAnnouncementsCount}</p>
            </section>

            <nav className="manager-tabs">
                <button
                    className={activeTab === "users" ? "active" : ""}
                    onClick={() => setActiveTab("users")}
                >
                    Użytkownicy
                </button>
                <button
                    className={activeTab === "norms" ? "active" : ""}
                    onClick={() => setActiveTab("norms")}>
                    Normy
                </button>
                <button
                    className={activeTab === "announcements" ? "active" : ""}
                    onClick={() => setActiveTab("announcements")}>
                    Ogłoszenia
                </button>
                <button
                    className={activeTab === "machines" ? "active" : ""}
                    onClick={() => setActiveTab("machines")}
                >
                    Maszyny
                </button>
                <button
                    className={activeTab === "products" ? "active" : ""}
                    onClick={() => setActiveTab("products")}
                >
                    Produkty
                </button>
            </nav>

            {activeTab === "users" && (
                <UsersTab users={dashboard.users} onRefresh={loadDashboard}/>)}

            {activeTab === "norms" && (
                <NormsTab />
            )}

            {activeTab === "announcements" && (
                <AnnouncementsTab announcements={dashboard.announcements} onRefresh={loadDashboard}/>
            )}
            {activeTab === "machines" && (
                <MachinesTab machines={dashboard.machines}/>
            )}
            {activeTab === "products" && <ProductsTab />}
        </main>
    );
}

export default ManagerDashboard;
