// import type { ManagerUser } from "../../types/ManagerDashboard";
// import "./UsersTab.css";
// import {useEffect, useState} from "react";
//
// interface UsersTabProps {
//     users: ManagerUser[];
//     onRefresh: () => void;
// }
// type Product = {
//     id: number;
//     code: string;
//     name: string;
// };
//
// type Machine = {
//     id: number;
//     name: string;
// };
//
//
// function UsersTab({ users, onRefresh }: UsersTabProps) {
//     const [products, setProducts] = useState<Product[]>([]);
//     const [machines, setMachines] = useState<Machine[]>([]);
//     const [selectedMachineIds, setSelectedMachineIds] =
//         useState<Record<number, string>>({});
//
//     const [selectedProductIds, setSelectedProductIds] =
//         useState<Record<number, string>>({});
//     const deleteUser = async (id: number) => {
//         await fetch(`http://localhost:8080/api/users/${id}`, {
//             method: "DELETE",
//         });
//
//         onRefresh();
//     };
//     useEffect(() => {
//         Promise.all([
//             fetch("http://localhost:8080/api/products").then(r => r.json()),
//             fetch("http://localhost:8080/api/machines").then(r => r.json()),
//         ]).then(([productsData, machinesData]) => {
//             setProducts(productsData);
//             setMachines(machinesData);
//         });
//     }, []);
//
//     // async function saveAssignment(
//     //     userId: number,
//     //     machineId: number,
//     //     productId: number
//     // ) {
//     //     await fetch(`http://localhost:8080/api/users/${userId}/assignment`, {
//     //         method: "PUT",
//     //         headers: {
//     //             "Content-Type": "application/json",
//     //         },
//     //         body: JSON.stringify({ machineId, productId }),
//     //     });
//     //
//     //     onRefresh();
//     // }
//     async function saveAssignment(
//         userId: number,
//         machineId: number,
//         productId: number
//     ) {
//         const response = await fetch(
//             `http://localhost:8080/api/users/${userId}/assignment`,
//             {
//                 method: "PUT",
//                 headers: {
//                     "Content-Type": "application/json",
//                 },
//                 body: JSON.stringify({ machineId, productId }),
//             }
//         );
//
//         if (!response.ok) {
//             throw new Error("Nie udało się zapisać przydziału");
//         }
//
//         onRefresh();
//     }
//
//     return (
//         <section  className="users-tab">
//             <h2>Użytkownicy</h2>
//
//             <table  className="users-table">
//                 <thead>
//                 <tr>
//                     <th>Imię i nazwisko</th>
//                     <th>Rola</th>
//                     <th>Maszyna</th>
//                     <th>Produkt</th>
//                     <th>Akcje</th>
//                 </tr>
//                 </thead>
//
//                 <tbody>
//                 {users.map((user) => (
//                     <tr key={user.id}>
//                         <td>{user.name} {user.surname}</td>
//                         <td>{user.role}</td>
//                         {/*<td>{user.machineName ?? "Brak"}</td>*/}
//                         {/*<td>*/}
//                         {/*    <button*/}
//                         {/*        type="button"*/}
//                         {/*        onClick={() => deleteUser(user.id)}*/}
//                         {/*    >*/}
//                         {/*        Usuń*/}
//                         {/*    </button>*/}
//                         {/*</td>*/}
//                         <td>
//                             {user.role === "OPERATOR" ? (
//                                 <select
//                                     value={
//                                         selectedMachineIds[user.id]
//                                         ?? user.machineId?.toString()
//                                         ?? ""
//                                     }
//                                     onChange={(event) =>
//                                         setSelectedMachineIds((previous) => ({
//                                             ...previous,
//                                             [user.id]: event.target.value,
//                                         }))
//                                     }
//                                 >
//                                     <option value="">Wybierz maszynę</option>
//
//                                     {machines.map((machine) => (
//                                         <option key={machine.id} value={machine.id}>
//                                             {machine.name}
//                                         </option>
//                                     ))}
//                                 </select>
//                             ) : (
//                                 "—"
//                             )}
//                         </td>
//
//                         <td>
//                             {user.role === "OPERATOR" ? (
//                                 <select
//                                     value={
//                                         selectedProductIds[user.id]
//                                         ?? user.productId?.toString()
//                                         ?? ""
//                                     }
//                                     onChange={(event) =>
//                                         setSelectedProductIds((previous) => ({
//                                             ...previous,
//                                             [user.id]: event.target.value,
//                                         }))
//                                     }
//                                 >
//                                     <option value="">Wybierz produkt</option>
//
//                                     {products.map((product) => (
//                                         <option key={product.id} value={product.id}>
//                                             {product.code} — {product.name}
//                                         </option>
//                                     ))}
//                                 </select>
//                             ) : (
//                                 "—"
//                             )}
//                         </td>
//
//                         <td>
//                             {user.role === "OPERATOR" && (
//                                 <button
//                                     type="button"
//                                     disabled={
//                                         !(
//                                             selectedMachineIds[user.id]
//                                             ?? user.machineId?.toString()
//                                         ) ||
//                                         !(
//                                             selectedProductIds[user.id]
//                                             ?? user.productId?.toString()
//                                         )
//                                     }
//                                     onClick={() => {
//                                         const machineId = Number(
//                                             selectedMachineIds[user.id]
//                                             ?? user.machineId?.toString()
//                                         );
//
//                                         const productId = Number(
//                                             selectedProductIds[user.id]
//                                             ?? user.productId?.toString()
//                                         );
//
//                                         void saveAssignment(user.id, machineId, productId);
//                                     }}
//                                 >
//                                     Zapisz przydział
//                                 </button>
//                             )}
//
//                             <button
//                                 type="button"
//                                 onClick={() => deleteUser(user.id)}
//                             >
//                                 Usuń
//                             </button>
//                         </td>
//                     </tr>
//                 ))}
//                 </tbody>
//             </table>
//         </section>
//     );
// }
//
// export default UsersTab;

import { useEffect, useState } from "react";
import type { ManagerUser } from "../../types/ManagerDashboard";
import "./UsersTab.css";

interface UsersTabProps {
    users: ManagerUser[];
    onRefresh: () => void;
}

type Product = {
    id: number;
    code: string;
    name: string;
};

type Machine = {
    id: number;
    name: string;
};

type UserForm = {
    name: string;
    surname: string;
    role: "OPERATOR" | "MANAGER";
    machineId: string;
    productId: string;
    pin: string;
};

const emptyForm: UserForm = {
    name: "",
    surname: "",
    role: "OPERATOR",
    machineId: "",
    productId: "",
    pin: "",
};

function UsersTab({ users, onRefresh }: UsersTabProps) {
    const [products, setProducts] = useState<Product[]>([]);
    const [machines, setMachines] = useState<Machine[]>([]);
    const [form, setForm] = useState<UserForm>(emptyForm);
    const [editingUser, setEditingUser] = useState<ManagerUser | null>(null);
    const [userToDelete, setUserToDelete] = useState<ManagerUser | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [isSaving, setIsSaving] = useState(false);
    const [isFormOpen, setIsFormOpen] = useState(false);

    useEffect(() => {
        Promise.all([
            fetch("http://localhost:8080/api/products").then((response) => response.json()),
            fetch("http://localhost:8080/api/machines").then((response) => response.json()),
        ]).then(([productsData, machinesData]) => {
            setProducts(productsData);
            setMachines(machinesData);
        });
    }, []);

    function openCreateModal() {
        setEditingUser(null);
        setForm(emptyForm);
        setError(null);
        setIsFormOpen(true);
    }

    function openEditModal(user: ManagerUser) {
        setEditingUser(user);
        setForm({
            name: user.name,
            surname: user.surname,
            role: user.role,
            machineId: user.machineId?.toString() ?? "",
            productId: user.productId?.toString() ?? "",
            pin: "",
        });
        setError(null);
        setIsFormOpen(true);
    }

    function closeFormModal() {
        setEditingUser(null);
        setForm(emptyForm);
        setError(null);
        setIsFormOpen(false);
    }

    function changeRole(role: "OPERATOR" | "MANAGER") {
        setForm((previous) => ({
            ...previous,
            role,
            machineId: role === "MANAGER" ? "" : previous.machineId,
            productId: role === "MANAGER" ? "" : previous.productId,
        }));
    }

    async function saveUser(event: React.FormEvent) {
        event.preventDefault();
        setError(null);
        setIsSaving(true);

        try {
            const response = await fetch(
                editingUser
                    ? `http://localhost:8080/api/users/${editingUser.id}`
                    : "http://localhost:8080/api/users",
                {
                    method: editingUser ? "PUT" : "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        name: form.name,
                        surname: form.surname,
                        role: form.role,
                        machineId:
                            form.role === "OPERATOR"
                                ? Number(form.machineId)
                                : null,
                        productId:
                            form.role === "OPERATOR"
                                ? Number(form.productId)
                                : null,
                        ...(editingUser ? {} : { pin: form.pin }),
                    }),
                }
            );

            if (!response.ok) {
                throw new Error(await response.text());
            }

            closeFormModal();
            onRefresh();
        } catch (caughtError) {
            setError(
                caughtError instanceof Error
                    ? caughtError.message
                    : "Nie udało się zapisać użytkownika."
            );
        } finally {
            setIsSaving(false);
        }
    }

    async function confirmDelete() {
        if (!userToDelete) {
            return;
        }

        setError(null);

        try {
            const response = await fetch(
                `http://localhost:8080/api/users/${userToDelete.id}`,
                { method: "DELETE" }
            );

            if (!response.ok) {
                throw new Error(await response.text());
            }

            setUserToDelete(null);
            onRefresh();
        } catch (caughtError) {
            setError(
                caughtError instanceof Error
                    ? caughtError.message
                    : "Nie udało się usunąć użytkownika."
            );
        }
    }

    return (
        <section className="users-tab">
            <div className="users-tab-header">
                <h2>Użytkownicy</h2>

                <button
                    type="button"
                    className="add-user-button"
                    onClick={openCreateModal}
                >
                    Dodaj użytkownika
                </button>
            </div>

            <table className="users-table">
                <thead>
                <tr>
                    <th>Imię i nazwisko</th>
                    <th>Rola</th>
                    <th>Maszyna</th>
                    <th>Produkt</th>
                    <th>Akcje</th>
                </tr>
                </thead>

                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.name} {user.surname}</td>
                        <td>{user.role}</td>
                        <td>{user.machineName ?? "—"}</td>
                        <td>
                            {user.productCode
                                ? `${user.productCode} — ${user.productName}`
                                : "—"}
                        </td>
                        <td>
                            <div className="user-actions">
                                <button
                                    type="button"
                                    className="edit-user-button"
                                    onClick={() => openEditModal(user)}
                                >
                                    Edytuj
                                </button>

                                <button
                                    type="button"
                                    className="delete-user-button"
                                    onClick={() => {
                                        setUserToDelete(user);
                                        setError(null);
                                    }}
                                >
                                    Usuń
                                </button>
                            </div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {isFormOpen && (
                <div className="modal-overlay">
                    <section
                        className="modal-card"
                        role="dialog"
                        aria-modal="true"
                    >
                        <div className="modal-header">
                            <h3>
                                {editingUser
                                    ? "Edytuj użytkownika"
                                    : "Dodaj użytkownika"}
                            </h3>

                            <button
                                type="button"
                                className="modal-close-button"
                                onClick={closeFormModal}
                                aria-label="Zamknij"
                            >
                                ×
                            </button>
                        </div>

                        <form className="user-form" onSubmit={saveUser}>
                            <label>
                                Imię
                                <input
                                    value={form.name}
                                    onChange={(event) =>
                                        setForm((previous) => ({
                                            ...previous,
                                            name: event.target.value,
                                        }))
                                    }
                                    required
                                />
                            </label>

                            <label>
                                Nazwisko
                                <input
                                    value={form.surname}
                                    onChange={(event) =>
                                        setForm((previous) => ({
                                            ...previous,
                                            surname: event.target.value,
                                        }))
                                    }
                                    required
                                />
                            </label>

                            <label>
                                Rola
                                <select
                                    value={form.role}
                                    onChange={(event) =>
                                        changeRole(
                                            event.target.value as
                                                | "OPERATOR"
                                                | "MANAGER"
                                        )
                                    }
                                >
                                    <option value="OPERATOR">Operator</option>
                                    <option value="MANAGER">Manager</option>
                                </select>
                            </label>

                            {form.role === "OPERATOR" && (
                                <>
                                    <label>
                                        Maszyna
                                        <select
                                            value={form.machineId}
                                            onChange={(event) =>
                                                setForm((previous) => ({
                                                    ...previous,
                                                    machineId: event.target.value,
                                                }))
                                            }
                                            required
                                        >
                                            <option value="">Wybierz maszynę</option>
                                            {machines.map((machine) => (
                                                <option
                                                    key={machine.id}
                                                    value={machine.id}
                                                >
                                                    {machine.name}
                                                </option>
                                            ))}
                                        </select>
                                    </label>

                                    <label>
                                        Produkt
                                        <select
                                            value={form.productId}
                                            onChange={(event) =>
                                                setForm((previous) => ({
                                                    ...previous,
                                                    productId: event.target.value,
                                                }))
                                            }
                                            required
                                        >
                                            <option value="">Wybierz produkt</option>
                                            {products.map((product) => (
                                                <option
                                                    key={product.id}
                                                    value={product.id}
                                                >
                                                    {product.code} — {product.name}
                                                </option>
                                            ))}
                                        </select>
                                    </label>
                                </>
                            )}

                            {!editingUser && (
                                <label>
                                    PIN
                                    <input
                                        type="password"
                                        value={form.pin}
                                        onChange={(event) =>
                                            setForm((previous) => ({
                                                ...previous,
                                                pin: event.target.value,
                                            }))
                                        }
                                        required
                                    />
                                </label>
                            )}

                            {error && (
                                <p className="form-error">{error}</p>
                            )}

                            <div className="modal-actions">
                                <button
                                    type="button"
                                    className="cancel-button"
                                    onClick={closeFormModal}
                                >
                                    Anuluj
                                </button>

                                <button
                                    type="submit"
                                    className="save-user-button"
                                    disabled={isSaving}
                                >
                                    {isSaving ? "Zapisywanie..." : "Zapisz"}
                                </button>
                            </div>
                        </form>
                    </section>
                </div>
            )}

            {userToDelete && (
                <div className="modal-overlay">
                    <section
                        className="modal-card confirm-delete-modal"
                        role="dialog"
                        aria-modal="true"
                    >
                        <h3>Usunąć użytkownika?</h3>

                        <p>
                            Czy na pewno chcesz usunąć użytkownika{" "}
                            <strong>
                                {userToDelete.name} {userToDelete.surname}
                            </strong>?
                        </p>

                        {error && <p className="form-error">{error}</p>}

                        <div className="modal-actions">
                            <button
                                type="button"
                                className="cancel-button"
                                onClick={() => setUserToDelete(null)}
                            >
                                Anuluj
                            </button>

                            <button
                                type="button"
                                className="delete-user-button"
                                onClick={confirmDelete}
                            >
                                Usuń
                            </button>
                        </div>
                    </section>
                </div>
            )}
        </section>
    );
}

export default UsersTab;
