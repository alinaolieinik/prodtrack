import {useState} from "react";
import OperatorDashboard from "./OperatorDashboard.tsx";

function ManagerDashboard() {
    const [activeTab, setActiveTab] = useState("users");

    return (
        <main className="dashboard">
            <header className="header">
                <div className="navigation">
                    <div className="home-icon">⌂</div>
                    <div className="back-icon">←</div>
                </div>

                <h1>PANEL MANAGERA</h1>

                <div className="logo">
                    Lukas
                </div>
            </header>

            <nav className="manager-tabs">
                <button
                    className={activeTab === "users" ? "active" : ""}
                    onClick={() => setActiveTab("users")}
                >
                    UŻYTKOWNICY
                </button>

                <button
                    className={activeTab === "standards" ? "active" : ""}
                    onClick={() => setActiveTab("standards")}
                >
                    NORMY
                </button>

                <button
                    className={activeTab === "products" ? "active" : ""}
                    onClick={() => setActiveTab("products")}
                >
                    PRODUKTY
                </button>

                <button
                    className={activeTab === "announcements" ? "active" : ""}
                    onClick={() => setActiveTab("announcements")}
                >
                    OGŁOSZENIA
                </button>
            </nav>

            {/*<section className="manager-content">*/}

            {/*    {activeTab === "users" && <UsersTab />}*/}

            {/*    {activeTab === "standards" && <StandardsTab />}*/}

            {/*    {activeTab === "products" && <ProductsTab />}*/}

            {/*    {activeTab === "announcements" && <AnnouncementsTab />}*/}

            {/*</section>*/}
        </main>
    );
}

export default ManagerDashboard;
