// import { useState } from 'react';
// import './Announcements.css';
//
// type Announcement = {
//   title: string;
//   date: string;
//   content: string;
// };
//
// const announcements: Announcement[] = [
//   {
//     title: 'Awaria linii produkcyjnej',
//     date: '12.08.2026, 12:30',
//     content:
//       'W dniu dzisiejszym wystąpiła awaria linii produkcyjnej. Przewidywany czas usunięcia problemu to godzina 14:30.',
//   },
//   {
//     title: 'Przerwa techniczna',
//     date: '13.08.2026, 08:00',
//     content:
//       'W dniu 13.08 odbędzie się planowana przerwa techniczna. Prosimy o zakończenie pracy urządzeń przed rozpoczęciem przerwy.',
//   },
//   {
//     title: 'Nowa instrukcja BHP',
//     date: '14.08.2026',
//     content:
//       'Dostępna jest nowa wersja instrukcji BHP. Prosimy wszystkich operatorów o zapoznanie się z aktualnymi zasadami.',
//   },
// ];
//
// export default function Announcements() {
//   const [openIndex, setOpenIndex] = useState<number | null>(null);
//
//   const toggleAnnouncement = (index: number) => {
//     setOpenIndex((currentIndex) =>
//       currentIndex === index ? null : index
//     );
//   };
//
//   return (
//     <section className="announcements">
//       <div className="announcements-header">
//         <h2>OGŁOSZENIA</h2>
//
//         <span className="announcements-count">
//           {announcements.length}
//         </span>
//       </div>
//
//       <div className="announcement-list">
//         {announcements.map((announcement, index) => {
//           const isOpen = openIndex === index;
//
//           return (
//             <div
//               className={`announcement-item ${
//                 isOpen ? 'is-open' : ''
//               }`}
//               key={index}
//             >
//               <button
//                 type="button"
//                 className="announcement-title"
//                 onClick={() => toggleAnnouncement(index)}
//                 aria-expanded={isOpen}
//               >
//                 <span className="announcement-number">
//                   {String(index + 1).padStart(2, '0')}
//                 </span>
//
//                 <span className="announcement-main">
//                   <strong>{announcement.title}</strong>
//
//                   <small>{announcement.date}</small>
//                 </span>
//
//                 <span className="announcement-arrow">
//                   {isOpen ? '⌃' : '›'}
//                 </span>
//               </button>
//
//               <div
//                 className={`announcement-content ${
//                   isOpen ? 'is-open' : ''
//                 }`}
//               >
//                 <p>{announcement.content}</p>
//               </div>
//             </div>
//           );
//         })}
//       </div>
//     </section>
//   );
// }
//

import { useState } from "react";
import type { Announcement } from "../types/OperatorDashboard";
import "./Announcements.css";

interface AnnouncementsProps {
    announcements: Announcement[];
}

function Announcements({ announcements }: AnnouncementsProps) {
    const [openId, setOpenId] = useState<number | null>(null);

    const toggleAnnouncement = (id: number) => {
        setOpenId((currentId) =>
            currentId === id ? null : id
        );
    };

    return (
        <div className="announcements">

            <div className="announcements-header">
                <h2>Ogłoszenia</h2>

                <span className="announcements-count">
                    {announcements.length}
                </span>
            </div>

            <div className="announcement-list">

                {announcements.length === 0 ? (
                    <div className="announcement-empty">
                        Brak ogłoszeń
                    </div>
                ) : (
                    announcements.map((announcement, index) => {
                        const isOpen =
                            openId === announcement.id;

                        return (
                            <article
                                className={`announcement-item ${
                                    isOpen ? "is-open" : ""
                                }`}
                                key={announcement.id}
                            >
                                <button
                                    type="button"
                                    className="announcement-title"
                                    onClick={() =>
                                        toggleAnnouncement(
                                            announcement.id
                                        )
                                    }
                                    aria-expanded={isOpen}
                                >
                                    <span className="announcement-number">
                                        {String(index + 1).padStart(2, "0")}
                                    </span>

                                    <span className="announcement-main">
                                        <strong>
                                            {announcement.title}
                                        </strong>

                                    </span>

                                    <span className="announcement-arrow">
                                        {isOpen ? "⌃" : "›"}
                                    </span>
                                </button>

                                <div
                                    className={`announcement-content ${
                                        isOpen ? "is-open" : ""
                                    }`}
                                >
                                    <div className="announcement-content-inner">
                                        <p>
                                            {announcement.message}
                                        </p>

                                        <div className="announcement-meta">
                                            Autor: {announcement.createdBy}
                                            <br />
                                            Ważne do: {formatDate(announcement.validTo)}
                                        </div>
                                    </div>
                                </div>
                            </article>
                        );
                    })
                )}

            </div>
        </div>
    );
}

function formatDate(dateString: string | null): string {
    if (!dateString) {
        return "bezterminowo";
    }

    const date = new Date(dateString);

    if (Number.isNaN(date.getTime())) {
        return dateString;
    }

    return new Intl.DateTimeFormat("pl-PL", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
    }).format(date);
}

export default Announcements;
