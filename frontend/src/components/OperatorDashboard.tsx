import { useEffect, useState } from "react";
import type { OperatorDashboard as OperatorDashboardType } from "../types/OperatorDashboard";
import Announcements from "./Announcements";
import "../App.css";

// function OperatorDashboard() {

interface OperatorDashboardProps {
    operatorId: number;
    onLogout: () => void;

}

function OperatorDashboard({ operatorId, onLogout }: OperatorDashboardProps) {
    const [dashboard, setDashboard] =
        useState<OperatorDashboardType | null>(null);

    // useEffect(() => {
    //     fetch(`http://localhost:8080/api/dashboard/operator/${operatorId}`)
    //         .then((response) => {
    //             if (!response.ok) {
    //                 throw new Error("Nie udało się pobrać danych");
    //             }
    //
    //             return response.json();
    //         })
    //         .then((data: OperatorDashboardType) => {
    //             setDashboard(data);
    //         })
    //         .catch((error) => {
    //             console.error(error);
    //         });
    // }, [operatorId]);

    useEffect(() => {
        let isActive = true;

        async function loadDashboard() {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/dashboard/operator/${operatorId}`
                );

                if (!response.ok) {
                    throw new Error("Nie udało się pobrać danych dashboardu");
                }

                const data: OperatorDashboardType = await response.json();

                if (isActive) {
                    setDashboard(data);
                }
            } catch (error) {
                console.error(error);
            }
        }

        loadDashboard();

        const intervalId = window.setInterval(loadDashboard, 1000);

        return () => {
            isActive = false;
            window.clearInterval(intervalId);
        };
    }, [operatorId]);

    if (!dashboard) {
        return <div className="loading">Ładowanie...</div>;
    }

    return (
        <main className="dashboard">

            {/* HEADER */}
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

                <h1>PANEL OPERATORA</h1>

                <div className="logo">
                    Lukas
                </div>
            </header>

            {/* INFO */}
            <p className="description">
                Twoim celem jest zapakowanie niżej wskazanych wartości przez całą zmianę:
            </p>

            {/* PERFORMANCE */}
            <section className="performance">

                <div className="performance-card excellent">
                    <strong>{dashboard.exceedTargetPerShift}</strong>
                    <span>Świetnie</span>
                </div>

                <div className="performance-card normal">
                    <strong>{dashboard.targetPerShift}</strong>
                    <span>Norma</span>
                </div>

                <div className="performance-card bad">
                    <strong>{dashboard.minimumPerShift}</strong>
                    <span>Do poprawy</span>
                </div>

            </section>

            <div className="separator" />

            {/* CURRENT DATA */}
            <section className="current-data">

                <div className="data-block">
                    <h3>Pozostały czas op/:</h3>

                    <div className="value-card grey">
                        <span className="small-icon">◷</span>

                        <strong>
                            {dashboard.remainingSeconds} sek.
                        </strong>
                    </div>
                </div>

                <div className="data-block">
                    <h3>Ilość spakowana:</h3>

                    <div className="value-card blue">
                        <strong>
                            {dashboard.packedCount}
                        </strong>

                        <span className="package-icon">📦</span>
                    </div>
                </div>

                <div className="data-block">
                    <h3>Czas na spakowanie 1 op.:</h3>

                    <div className="value-card cyan">
                        <span className="small-icon">◷</span>

                        <strong>
                            {dashboard.secondsPerPackage} sek.
                        </strong>
                    </div>
                </div>

            </section>

            <div className="separator" />

            {/* BOTTOM */}
            <section className="bottom">

                {/* OPERATOR */}
                <div className="operator-info">

                    <div className="info-row">
                        <span>Imię i nazwisko:</span>

                        <strong>
                            {dashboard.name} {dashboard.surname}
                        </strong>
                    </div>

                    <div className="info-row">
                        <span>Symbol produktu:</span>

                        <strong>
                            SUBITO OPAL 978
                        </strong>
                    </div>

                    <div className="start-date">
                        <span>Data i godz. rozpoczęcia:</span>

                        <strong>
                            01.09.2025 07:16
                        </strong>
                    </div>

                    <h2>
                        Stanowisko nr. 2
                    </h2>

                    <div className="current-time">
                        Aktualna data i godzina:

                        <strong>
                            <Clock />
                        </strong>
                    </div>

                </div>

                {/* ANNOUNCEMENTS */}
                <Announcements announcements={dashboard.announcements} />

            </section>

        </main>
    );
}

function Clock() {
    const [date, setDate] = useState(new Date());

    useEffect(() => {
        const interval = setInterval(() => {
            setDate(new Date());
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    return <div>{date.toLocaleString("pl-PL")}</div>;
}

export default OperatorDashboard;
