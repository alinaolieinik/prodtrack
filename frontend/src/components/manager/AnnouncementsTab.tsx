import { useState } from "react";
import type { Announcement } from "../../types/ManagerDashboard";
import "./AnnouncementsTab.css";

interface AnnouncementsTabProps {
    announcements: Announcement[];
    onRefresh: () => void;
}

function AnnouncementsTab({
                              announcements,
                              onRefresh,
                          }: AnnouncementsTabProps) {
    const [title, setTitle] = useState("");
    const [message, setMessage] = useState("");
    const [createdBy, setCreatedBy] = useState("");

    const createAnnouncement = async (event: React.FormEvent) => {
        event.preventDefault();

        await fetch("http://localhost:8080/api/announcements", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title,
                message,
                createdBy,
            }),
        });

        setTitle("");
        setMessage("");
        onRefresh();
    };

    return (
        <section  className="announcements-tab">
            <h2>Dodaj ogłoszenie</h2>

            <form  className="announcement-form" onSubmit={createAnnouncement}>
                <input
                    type="text"
                    placeholder="Tytuł"
                    value={title}
                    onChange={(event) => setTitle(event.target.value)}
                    required
                />

                <textarea
                    placeholder="Treść ogłoszenia"
                    value={message}
                    onChange={(event) => setMessage(event.target.value)}
                    required
                />

                <input
                    type="text"
                    placeholder="Autor"
                    value={createdBy}
                    onChange={(event) => setCreatedBy(event.target.value)}
                    required
                />

                <button type="submit">Opublikuj</button>
            </form>

            <h2>Aktywne ogłoszenia</h2>

            {announcements.map((announcement) => (
                <article  className="manager-announcement-card" key={announcement.id}>
                    <h3>{announcement.title}</h3>
                    <p>{announcement.message}</p>
                    <small>
                        Autor: {announcement.createdBy} |{" "}
                        {new Date(announcement.createdAt)
                            .toLocaleString("pl-PL")}
                    </small>
                </article>
            ))}
        </section>
    );
}

export default AnnouncementsTab;
