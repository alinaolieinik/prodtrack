// // import { useState } from 'react'
// // import reactLogo from './assets/react.svg'
// // import viteLogo from './assets/vite.svg'
// // import heroImg from './assets/hero.png'
// // import './App.css'
// //
// // function App() {
// //   return (
// //       <div>
// //         <h1>Operator Dashboard</h1>
// //
// //         <p>Frontend działa!</p>
// //       </div>
// //   );
// // }
// //   const [count, setCount] = useState(0)
// //
// //   return (
// //     <>
// //       <section id="center">
// //         <div className="hero">
// //           <img src={heroImg} className="base" width="170" height="179" alt="" />
// //           <img src={reactLogo} className="framework" alt="React logo" />
// //           <img src={viteLogo} className="vite" alt="Vite logo" />
// //         </div>
// //         <div>
// //           <h1>Get started</h1>
// //           <p>
// //             Edit <code>src/App.tsx</code> and save to test <code>HMR</code>
// //           </p>
// //         </div>
// //         <button
// //           type="button"
// //           className="counter"
// //           onClick={() => setCount((count) => count + 1)}
// //         >
// //           Count is {count}
// //         </button>
// //       </section>
// //
// //       <div className="ticks"></div>
// //
// //       <section id="next-steps">
// //         <div id="docs">
// //           <svg className="icon" role="presentation" aria-hidden="true">
// //             <use href="/icons.svg#documentation-icon"></use>
// //           </svg>
// //           <h2>Documentation</h2>
// //           <p>Your questions, answered</p>
// //           <ul>
// //             <li>
// //               <a href="https://vite.dev/" target="_blank">
// //                 <img className="logo" src={viteLogo} alt="" />
// //                 Explore Vite
// //               </a>
// //             </li>
// //             <li>
// //               <a href="https://react.dev/" target="_blank">
// //                 <img className="button-icon" src={reactLogo} alt="" />
// //                 Learn more
// //               </a>
// //             </li>
// //           </ul>
// //         </div>
// //         <div id="social">
// //           <svg className="icon" role="presentation" aria-hidden="true">
// //             <use href="/icons.svg#social-icon"></use>
// //           </svg>
// //           <h2>Connect with us</h2>
// //           <p>Join the Vite community</p>
// //           <ul>
// //             <li>
// //               <a href="https://github.com/vitejs/vite" target="_blank">
// //                 <svg
// //                   className="button-icon"
// //                   role="presentation"
// //                   aria-hidden="true"
// //                 >
// //                   <use href="/icons.svg#github-icon"></use>
// //                 </svg>
// //                 GitHub
// //               </a>
// //             </li>
// //             <li>
// //               <a href="https://chat.vite.dev/" target="_blank">
// //                 <svg
// //                   className="button-icon"
// //                   role="presentation"
// //                   aria-hidden="true"
// //                 >
// //                   <use href="/icons.svg#discord-icon"></use>
// //                 </svg>
// //                 Discord
// //               </a>
// //             </li>
// //             <li>
// //               <a href="https://x.com/vite_js" target="_blank">
// //                 <svg
// //                   className="button-icon"
// //                   role="presentation"
// //                   aria-hidden="true"
// //                 >
// //                   <use href="/icons.svg#x-icon"></use>
// //                 </svg>
// //                 X.com
// //               </a>
// //             </li>
// //             <li>
// //               <a href="https://bsky.app/profile/vite.dev" target="_blank">
// //                 <svg
// //                   className="button-icon"
// //                   role="presentation"
// //                   aria-hidden="true"
// //                 >
// //                   <use href="/icons.svg#bluesky-icon"></use>
// //                 </svg>
// //                 Bluesky
// //               </a>
// //             </li>
// //           </ul>
// //         </div>
// //       </section>
// //
// //       <div className="ticks"></div>
// //       <section id="spacer"></section>
// //     </>
// //   )
// // }
//
// // export default App
//
// // import { useEffect, useState } from "react";
// // import type { OperatorDashboard } from "./types/OperatorDashboard";
// //
// // function App() {
// //     const [dashboard, setDashboard] =
// //         useState<OperatorDashboard | null>(null);
// //
// //     useEffect(() => {
// //         fetch("http://localhost:8080/api/dashboard/operator/1")
// //             .then((response) => {
// //                 if (!response.ok) {
// //                     throw new Error("Nie udało się pobrać danych");
// //                 }
// //
// //                 return response.json();
// //             })
// //             .then((data: OperatorDashboard) => {
// //                 setDashboard(data);
// //             })
// //             .catch((error) => {
// //                 console.error(error);
// //             });
// //     }, []);
// //
// //     if (!dashboard) {
// //         return <div>Ładowanie...</div>;
// //     }
// //
// //     return (
// //         <div>
// //             <h1>Operator Dashboard</h1>
// //
// //             <h2>
// //                 {dashboard.name} {dashboard.surname}
// //             </h2>
// //
// //             <p>Machine: {dashboard.machineName}</p>
// //
// //             <p>Packed: {dashboard.packedCount}</p>
// //
// //             <p>Target: {dashboard.targetPerShift}</p>
// //
// //             <p>Minimum: {dashboard.minimumPerShift}</p>
// //
// //             <p>
// //                 Seconds per package: {dashboard.secondsPerPackage}
// //             </p>
// //
// //             <p>
// //                 Remaining seconds: {dashboard.remainingSeconds}
// //             </p>
// //         </div>
// //     );
// // }
// //
// // export default App;
//
// import { useEffect, useState } from "react";
// import type { OperatorDashboard } from "./types/OperatorDashboard";
// import "./App.css";
//
// function App() {
//     const [dashboard, setDashboard] =
//         useState<OperatorDashboard | null>(null);
//
//     useEffect(() => {
//         fetch("http://localhost:8080/api/dashboard/operator/1")
//             .then((response) => {
//                 if (!response.ok) {
//                     throw new Error("Nie udało się pobrać danych");
//                 }
//
//                 return response.json();
//             })
//             .then((data: OperatorDashboard) => {
//                 setDashboard(data);
//             })
//             .catch((error) => {
//                 console.error(error);
//             });
//     }, []);
//
//     if (!dashboard) {
//         return <div className="loading">Ładowanie...</div>;
//     }
//
//     return (
//         <main className="dashboard">
//
//             {/* HEADER */}
//             <header className="header">
//                 <div className="navigation">
//                     <div className="home-icon">⌂</div>
//                     <div className="back-icon">←</div>
//                 </div>
//
//                 <h1>PANEL OPERATORA</h1>
//
//                 <div className="logo">
//                     Lukas
//                 </div>
//             </header>
//
//             {/* INFO */}
//             <p className="description">
//                 Twoim celem jest zapakowanie niżej wskazanych wartości przez całą zmianę:
//             </p>
//
//             {/* PERFORMANCE */}
//             <section className="performance">
//
//                 <div className="performance-card excellent">
//                     <strong>140</strong>
//                     <span>ŚWIETNIE</span>
//                 </div>
//
//                 <div className="performance-card normal">
//                     <strong>120</strong>
//                     <span>Norma</span>
//                 </div>
//
//                 <div className="performance-card bad">
//                     <strong>100</strong>
//                     <span>Do poprawy</span>
//                 </div>
//
//             </section>
//
//             <div className="separator" />
//
//             {/* CURRENT DATA */}
//             <section className="current-data">
//
//                 <div className="data-block">
//                     <h3>Pozostały czas op/:</h3>
//
//                     <div className="value-card grey">
//                         <span className="small-icon">◷</span>
//                         <strong>
//                             {dashboard.remainingSeconds} sek.
//                         </strong>
//                     </div>
//                 </div>
//
//                 <div className="data-block">
//                     <h3>Ilość spakowana:</h3>
//
//                     <div className="value-card blue">
//                         <strong>
//                             {dashboard.packedCount}
//                         </strong>
//
//                         <span className="package-icon">📦</span>
//                     </div>
//                 </div>
//
//                 <div className="data-block">
//                     <h3>Czas na spakowanie 1 op.:</h3>
//
//                     <div className="value-card cyan">
//                         <span className="small-icon">◷</span>
//
//                         <strong>
//                             {dashboard.secondsPerPackage} sek.
//                         </strong>
//                     </div>
//                 </div>
//
//             </section>
//
//             <div className="separator" />
//
//             {/* BOTTOM */}
//             <section className="bottom">
//
//                 {/* OPERATOR */}
//                 <div className="operator-info">
//
//                     <div className="info-row">
//                         <span>Imię i nazwisko:</span>
//                         <strong>
//                             {dashboard.name} {dashboard.surname}
//                         </strong>
//                     </div>
//
//                     <div className="info-row">
//                         <span>Symbol produktu:</span>
//                         <strong>
//                             SUBITO OPAL 978
//                         </strong>
//                     </div>
//
//                     <div className="start-date">
//                         <span>Data i godz. rozpoczęcia:</span>
//                         <strong>01.09.2025 07:16</strong>
//                     </div>
//
//                     <h2>
//                         Stanowisko nr. 2
//                     </h2>
//
//                     <div className="current-time">
//                         Aktualna data i godzina:
//                         <strong>01.09.2025 09:13</strong>
//                     </div>
//
//                 </div>
//
//                 {/* ANNOUNCEMENTS */}
//                 <div className="announcements">
//
//                     <div className="announcements-header">
//                         <h2>Ogłoszenia:</h2>
//                         <span>ⓘ</span>
//                     </div>
//
//                     <div className="announcement-box">
//
//                         {dashboard.announcements.length === 0 ? (
//                             <p>Brak ogłoszeń</p>
//                         ) : (
//                             dashboard.announcements.map((announcement, index) => (
//                                 <p key={index}>
//                                     {JSON.stringify(announcement)}
//                                 </p>
//                             ))
//                         )}
//
//                     </div>
//
//                 </div>
//
//             </section>
//
//         </main>
//     );
// }
//
// export default App;


// import OperatorDashboard from "./components/OperatorDashboard";
//
// function App() {
//     return <OperatorDashboard />;
// }
//
// export default App;
// import ManagerDashboard from "./components/ManagerDashboard";
//
// function App() {
//     return <ManagerDashboard managerId={2} />;
// }
//
// export default App;


import { useState } from "react";
import LoginPage, { type LoggedUser } from "./components/LoginPage";
import OperatorDashboard from "./components/OperatorDashboard";
import ManagerDashboard from "./components/ManagerDashboard";

const storedUserKey = "production-monitoring.logged-user";

function getStoredUser(): LoggedUser | null {
    const storedUser = localStorage.getItem(storedUserKey);

    if (!storedUser) {
        return null;
    }

    try {
        return JSON.parse(storedUser) as LoggedUser;
    } catch {
        localStorage.removeItem(storedUserKey);
        return null;
    }
}

function App() {
    const [user, setUser] = useState<LoggedUser | null>(getStoredUser);
    const [isLogoutDialogOpen, setIsLogoutDialogOpen] = useState(false);
    const [logoutError, setLogoutError] = useState<string | null>(null);
    const [isLoggingOut, setIsLoggingOut] = useState(false);

    function handleLogin(loggedUser: LoggedUser) {
        localStorage.setItem(storedUserKey, JSON.stringify(loggedUser));
        setUser(loggedUser);
    }

    function openLogoutDialog() {
        setLogoutError(null);
        setIsLogoutDialogOpen(true);
    }

    async function confirmLogout() {
        if (!user) {
            return;
        }

        setLogoutError(null);
        setIsLoggingOut(true);

        try {
            const response = await fetch("http://localhost:8080/api/auth/logout", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ userId: user.id }),
            });

            if (!response.ok) {
                throw new Error(await response.text());
            }

            localStorage.removeItem(storedUserKey);
            setUser(null);
            setIsLogoutDialogOpen(false);
        } catch (error) {
            setLogoutError(
                error instanceof Error && error.message
                    ? error.message
                    : "Nie udało się wylogować użytkownika."
            );
        } finally {
            setIsLoggingOut(false);
        }
    }

    if (!user) {
        return <LoginPage onLogin={handleLogin} />;
    }

    if (user.role === "OPERATOR") {
        return (
            <>
                <OperatorDashboard
                    operatorId={user.id}
                    onLogout={openLogoutDialog}
                />
ss                <LogoutDialog
                    isOpen={isLogoutDialogOpen}
                    error={logoutError}
                    isLoggingOut={isLoggingOut}
                    onCancel={() => setIsLogoutDialogOpen(false)}
                    onConfirm={confirmLogout}
                />
            </>
        );
    }

    return (
        <>
            <ManagerDashboard
                managerId={user.id}
                onLogout={openLogoutDialog}
            />
            <LogoutDialog
                isOpen={isLogoutDialogOpen}
                error={logoutError}
                isLoggingOut={isLoggingOut}
                onCancel={() => setIsLogoutDialogOpen(false)}
                onConfirm={confirmLogout}
            />
        </>
    );
}

type LogoutDialogProps = {
    isOpen: boolean;
    error: string | null;
    isLoggingOut: boolean;
    onCancel: () => void;
    onConfirm: () => void;
};

function LogoutDialog({
    isOpen,
    error,
    isLoggingOut,
    onCancel,
    onConfirm,
}: LogoutDialogProps) {
    if (!isOpen) {
        return null;
    }

    return (
        <div className="logout-modal-overlay">
            <section className="logout-modal-card" role="dialog" aria-modal="true">
                <h2>Wylogowanie</h2>
                <p>
                    Czy na pewno chcesz się wylogować? Po potwierdzeniu praca operatora zostanie zapisana.
                </p>

                {error && <p className="logout-modal-error">{error}</p>}

                <div className="logout-modal-actions">
                    <button type="button" onClick={onCancel} disabled={isLoggingOut}>
                        Anuluj
                    </button>
                    <button
                        type="button"
                        className="logout-confirm-button"
                        onClick={onConfirm}
                        disabled={isLoggingOut}
                    >
                        {isLoggingOut ? "Wylogowywanie..." : "Wyloguj"}
                    </button>
                </div>
            </section>
        </div>
    );
}

export default App;
