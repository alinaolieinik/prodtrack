import {useState, type FormEvent, useEffect} from "react";
import "./LoginPage.css";

export type LoggedUser = {
    id: number;
    name: string;
    role: "OPERATOR" | "MANAGER";
};
type LoginUserOption = {
    id: number;
    fullName: string;
};

type LoginPageProps = {
    onLogin: (user: LoggedUser) => void;
};

function LoginPage({ onLogin }: LoginPageProps) {
    const [pin, setPin] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const [users, setUsers] = useState<LoginUserOption[]>([]);
    const [userId, setUserId] = useState("");
    const [loadingUsers, setLoadingUsers] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/api/auth/users")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Nie udało się pobrać listy użytkowników");
                }

                return response.json();
            })
            .then((data: LoginUserOption[]) => {
                setUsers(data);
            })
            .catch(() => {
                setError("Nie udało się pobrać listy użytkowników");
            })
            .finally(() => {
                setLoadingUsers(false);
            });
    }, []);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError("");
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    userId: Number(userId),
                    pin,
                }),            });

            if (!response.ok) {
                throw new Error("Nieprawidłowe imię lub PIN");
            }

            const user: LoggedUser = await response.json();
            onLogin(user);
        } catch (error) {
            setError(error instanceof Error ? error.message : "Wystąpił błąd");
        } finally {
            setLoading(false);
        }
    }

    return (
        <main className="login-page">
            <form className="login-card" onSubmit={handleSubmit}>
                <h1>Logowanie</h1>

                <label>
                    Imię i nazwisko
                    <select
                        value={userId}
                        onChange={(event) => setUserId(event.target.value)}
                        required
                        disabled={loadingUsers}
                    >
                        <option value="">
                            {loadingUsers ? "Wczytywanie użytkowników..." : "Wybierz użytkownika"}
                        </option>

                        {users.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.fullName}
                            </option>
                        ))}
                    </select>
                </label>

                <label>
                    PIN
                    <input
                        type="password"
                        inputMode="numeric"
                        pattern="[0-9]{4}"
                        maxLength={4}
                        value={pin}
                        onChange={(event) => setPin(event.target.value)}
                        required
                    />
                </label>

                {error && <p className="login-error">{error}</p>}

                <button disabled={loading || loadingUsers}>
                    {loading ? "Logowanie..." : "Zaloguj się"}
                </button>
            </form>
        </main>
    );
}

export default LoginPage;