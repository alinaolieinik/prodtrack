import { useCallback, useEffect, useState, type FormEvent } from "react";
import type { Announcement } from "../../types/ManagerDashboard";
import "./AnnouncementsTab.css";

interface AnnouncementsTabProps {
    managerId: number;
    onRefresh: () => void;
}

function toDateTimeLocal(date: Date) {
    const timezoneOffset = date.getTimezoneOffset() * 60_000;
    return new Date(date.getTime() - timezoneOffset).toISOString().slice(0, 16);
}

function AnnouncementsTab({ managerId, onRefresh }: AnnouncementsTabProps) {
    const [announcements, setAnnouncements] = useState<Announcement[]>([]);
    const [title, setTitle] = useState("");
    const [message, setMessage] = useState("");
    const [validFrom, setValidFrom] = useState(() => toDateTimeLocal(new Date()));
    const [validTo, setValidTo] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [isSaving, setIsSaving] = useState(false);

    const loadAnnouncements = useCallback(async () => {
        const response = await fetch(
            `http://localhost:8080/api/announcements/manageable?managerId=${managerId}`
        );

        if (!response.ok) {
            throw new Error("Nie udało się pobrać ogłoszeń.");
        }

        setAnnouncements(await response.json() as Announcement[]);
    }, [managerId]);

    useEffect(() => {
        loadAnnouncements().catch((requestError) => {
            setError(requestError instanceof Error ? requestError.message : "Nie udało się pobrać ogłoszeń.");
        });
    }, [loadAnnouncements]);

    async function getErrorMessage(response: Response) {
        const responseText = await response.text();
        return responseText || "Nie udało się wykonać operacji na ogłoszeniu.";
    }

    async function createAnnouncement(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError(null);
        setIsSaving(true);

        try {
            const response = await fetch("http://localhost:8080/api/announcements", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ title, message, validFrom, validTo, authorId: managerId }),
            });

            if (!response.ok) {
                throw new Error(await getErrorMessage(response));
            }

            setTitle("");
            setMessage("");
            setValidFrom(toDateTimeLocal(new Date()));
            setValidTo("");
            await loadAnnouncements();
            onRefresh();
        } catch (requestError) {
            setError(requestError instanceof Error ? requestError.message : "Nie udało się zapisać ogłoszenia.");
        } finally {
            setIsSaving(false);
        }
    }

    async function cancelAnnouncement(id: number) {
        setError(null);

        try {
            const response = await fetch(
                `http://localhost:8080/api/announcements/${id}/cancel`,
                {
                    method: "PATCH",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ managerId }),
                }
            );

            if (!response.ok) {
                throw new Error(await getErrorMessage(response));
            }

            await loadAnnouncements();
            onRefresh();
        } catch (requestError) {
            setError(requestError instanceof Error ? requestError.message : "Nie udało się anulować ogłoszenia.");
        }
    }

    return (
        <section className="announcements-tab">
            <h2>Dodaj ogłoszenie</h2>

            <form className="announcement-form" onSubmit={createAnnouncement}>
                <input type="text" placeholder="Tytuł" value={title} onChange={(event) => setTitle(event.target.value)} required />
                <textarea placeholder="Treść ogłoszenia" value={message} onChange={(event) => setMessage(event.target.value)} required />

                <div className="announcement-date-fields">
                    <label>
                        Ważne od
                        <input type="datetime-local" value={validFrom} onChange={(event) => setValidFrom(event.target.value)} required />
                    </label>
                    <label>
                        Ważne do
                        <input type="datetime-local" value={validTo} min={validFrom} onChange={(event) => setValidTo(event.target.value)} required />
                    </label>
                </div>

                <button type="submit" disabled={isSaving}>
                    {isSaving ? "Zapisywanie..." : "Opublikuj"}
                </button>
            </form>

            {error && <p className="announcement-error">{error}</p>}

            <h2>Aktywne i przyszłe ogłoszenia</h2>

            {announcements.length === 0 ? (
                <p className="announcement-empty-manager">Brak aktywnych ani przyszłych ogłoszeń.</p>
            ) : announcements.map((announcement) => (
                <article className="manager-announcement-card" key={announcement.id}>
                    <div className="manager-announcement-header">
                        <h3>{announcement.title}</h3>
                        <button type="button" className="cancel-announcement-button" onClick={() => cancelAnnouncement(announcement.id)}>
                            Anuluj
                        </button>
                    </div>
                    <p>{announcement.message}</p>
                    <small>
                        Autor: {announcement.createdBy} | Utworzono: {formatDate(announcement.createdAt)}
                        <br />
                        Ważne od: {formatDate(announcement.validFrom)} | Ważne do: {formatDate(announcement.validTo)}
                    </small>
                </article>
            ))}
        </section>
    );
}

function formatDate(date: string | null) {
    return date ? new Date(date).toLocaleString("pl-PL") : "bezterminowo";
}

export default AnnouncementsTab;
