import { useCallback, useEffect, useState, type FormEvent } from "react";
import "./ProductsTab.css";

type Product = {
    id: number;
    code: string;
    name: string;
};

type ProductForm = {
    code: string;
    name: string;
};

const emptyForm: ProductForm = {
    code: "",
    name: "",
};

function ProductsTab() {
    const [products, setProducts] = useState<Product[]>([]);
    const [form, setForm] = useState<ProductForm>(emptyForm);
    const [editingProduct, setEditingProduct] = useState<Product | null>(null);
    const [productToDelete, setProductToDelete] = useState<Product | null>(null);
    const [isFormOpen, setIsFormOpen] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const loadProducts = useCallback(async () => {
        const response = await fetch("http://localhost:8080/api/products");

        if (!response.ok) {
            throw new Error("Nie udało się pobrać produktów.");
        }

        const data: Product[] = await response.json();
        setProducts(data);
    }, []);

    useEffect(() => {
        loadProducts().catch((requestError) => {
            setError(
                requestError instanceof Error
                    ? requestError.message
                    : "Nie udało się pobrać produktów."
            );
        });
    }, [loadProducts]);

    function openCreateModal() {
        setEditingProduct(null);
        setForm({ ...emptyForm });
        setError(null);
        setIsFormOpen(true);
    }

    function openEditModal(product: Product) {
        setEditingProduct(product);
        setForm({
            code: product.code,
            name: product.name,
        });
        setError(null);
        setIsFormOpen(true);
    }

    function closeFormModal() {
        setEditingProduct(null);
        setForm({ ...emptyForm });
        setError(null);
        setIsFormOpen(false);
    }

    async function getErrorMessage(response: Response) {
        const message = await response.text();
        return message || "Nie udało się wykonać operacji na produkcie.";
    }

    async function saveProduct(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError(null);
        setIsSaving(true);

        try {
            const response = await fetch(
                editingProduct
                    ? `http://localhost:8080/api/products/${editingProduct.id}`
                    : "http://localhost:8080/api/products",
                {
                    method: editingProduct ? "PUT" : "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(form),
                }
            );

            if (!response.ok) {
                throw new Error(await getErrorMessage(response));
            }

            await loadProducts();
            closeFormModal();
        } catch (requestError) {
            setError(
                requestError instanceof Error
                    ? requestError.message
                    : "Nie udało się zapisać produktu."
            );
        } finally {
            setIsSaving(false);
        }
    }

    async function confirmDelete() {
        if (!productToDelete) {
            return;
        }

        setError(null);

        try {
            const response = await fetch(
                `http://localhost:8080/api/products/${productToDelete.id}`,
                { method: "DELETE" }
            );

            if (!response.ok) {
                throw new Error(await getErrorMessage(response));
            }

            await loadProducts();
            setProductToDelete(null);
        } catch (requestError) {
            setError(
                requestError instanceof Error
                    ? requestError.message
                    : "Nie udało się usunąć produktu."
            );
        }
    }

    return (
        <section className="products-tab">
            <div className="products-tab-header">
                <h2>Produkty</h2>

                <button
                    type="button"
                    className="add-product-button"
                    onClick={openCreateModal}
                >
                    Dodaj produkt
                </button>
            </div>

            {error && !isFormOpen && !productToDelete && (
                <p className="products-error">{error}</p>
            )}

            <table className="products-table">
                <thead>
                <tr>
                    <th>Kod</th>
                    <th>Nazwa</th>
                    <th>Akcje</th>
                </tr>
                </thead>

                <tbody>
                {products.map((product) => (
                    <tr key={product.id}>
                        <td>{product.code}</td>
                        <td>{product.name}</td>
                        <td>
                            <div className="product-actions">
                                <button
                                    type="button"
                                    className="edit-product-button"
                                    onClick={() => openEditModal(product)}
                                >
                                    Edytuj
                                </button>
                                <button
                                    type="button"
                                    className="delete-product-button"
                                    onClick={() => {
                                        setProductToDelete(product);
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
                <div className="product-modal-overlay">
                    <section className="product-modal-card" role="dialog" aria-modal="true">
                        <div className="product-modal-header">
                            <h3>
                                {editingProduct ? "Edytuj produkt" : "Dodaj produkt"}
                            </h3>
                            <button
                                type="button"
                                className="product-modal-close-button"
                                onClick={closeFormModal}
                                aria-label="Zamknij"
                            >
                                ×
                            </button>
                        </div>

                        <form className="product-form" onSubmit={saveProduct}>
                            <label>
                                Kod produktu
                                <input
                                    value={form.code}
                                    onChange={(event) =>
                                        setForm((previous) => ({
                                            ...previous,
                                            code: event.target.value,
                                        }))
                                    }
                                    required
                                />
                            </label>
                            <label>
                                Nazwa produktu
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

                            {error && <p className="product-form-error">{error}</p>}

                            <div className="product-modal-actions">
                                <button
                                    type="button"
                                    className="cancel-product-button"
                                    onClick={closeFormModal}
                                >
                                    Anuluj
                                </button>
                                <button
                                    type="submit"
                                    className="save-product-button"
                                    disabled={isSaving}
                                >
                                    {isSaving ? "Zapisywanie..." : "Zapisz"}
                                </button>
                            </div>
                        </form>
                    </section>
                </div>
            )}

            {productToDelete && (
                <div className="product-modal-overlay">
                    <section className="product-modal-card product-confirm-delete" role="dialog" aria-modal="true">
                        <h3>Usunąć produkt?</h3>
                        <p>
                            Czy na pewno chcesz usunąć produkt <strong>{productToDelete.code} — {productToDelete.name}</strong>?
                        </p>

                        {error && <p className="product-form-error">{error}</p>}

                        <div className="product-modal-actions">
                            <button
                                type="button"
                                className="cancel-product-button"
                                onClick={() => setProductToDelete(null)}
                            >
                                Anuluj
                            </button>
                            <button
                                type="button"
                                className="delete-product-button"
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

export default ProductsTab;
