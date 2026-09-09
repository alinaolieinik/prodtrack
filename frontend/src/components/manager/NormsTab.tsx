// import { useEffect, useState } from "react";
// import type { ProductionNorm } from "../../types/ManagerDashboard";
// import "./NormsTab.css";
//
// interface NormsTabProps {
//     currentNorm: ProductionNorm;
//     onRefresh: () => void;
// }
//
// function NormsTab({ currentNorm, onRefresh }: NormsTabProps) {
//     const [history, setHistory] = useState<ProductionNorm[]>([]);
//     const [targetPerShift, setTargetPerShift] = useState("");
//     const [exceedTargetPerShift, setExceedTargetPerShift] = useState("");
//     const [minimumPerShift, setMinimumPerShift] = useState("");
//     const [secondsPerPackage, setSecondsPerPackage] = useState("");
//     const [products, setProducts] = useState<Product[]>([]);
//     const [productId, setProductId] = useState("");
//     useEffect(() => {
//         fetch("http://localhost:8080/api/norms/history")
//             .then((response) => response.json())
//             .then((data: ProductionNorm[]) => setHistory(data));
//     }, []);
//     fetch("http://localhost:8080/api/products")
//         .then((response) => response.json())
//         .then((data: Product[]) => setProducts(data));
//
//     const createNorm = async (event: React.FormEvent) => {
//         event.preventDefault();
//
//         await fetch("http://localhost:8080/api/norms", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify({
//                 targetPerShift: Number(targetPerShift),
//                 exceedTargetPerShift: Number(exceedTargetPerShift),
//                 minimumPerShift: Number(minimumPerShift),
//                 secondsPerPackage: Number(secondsPerPackage),
//             }),
//         });
//
//         onRefresh();
//     };
//
//     return (
//         <section  className="norms-tab">
//             <h2>Aktualna norma</h2>
//
//             <p>Norma: {currentNorm.targetPerShift}</p>
//             <p>Świetnie: {currentNorm.exceedTargetPerShift}</p>
//             <p>Minimum: {currentNorm.minimumPerShift}</p>
//             <p>Czas na opakowanie: {currentNorm.secondsPerPackage} s</p>
//
//             <h2>Dodaj nową normę</h2>
//
//             <form  className="norm-form" onSubmit={createNorm}>
//                 <input
//                     type="number"
//                     placeholder="Norma na zmianę"
//                     value={targetPerShift}
//                     onChange={(event) => setTargetPerShift(event.target.value)}
//                     required
//                 />
//
//                 <input
//                     type="number"
//                     placeholder="Poziom świetny"
//                     value={exceedTargetPerShift}
//                     onChange={(event) =>
//                         setExceedTargetPerShift(event.target.value)
//                     }
//                     required
//                 />
//
//                 <input
//                     type="number"
//                     placeholder="Minimum"
//                     value={minimumPerShift}
//                     onChange={(event) => setMinimumPerShift(event.target.value)}
//                     required
//                 />
//
//                 <input
//                     type="number"
//                     placeholder="Sekundy na opakowanie"
//                     value={secondsPerPackage}
//                     onChange={(event) => setSecondsPerPackage(event.target.value)}
//                     required
//                 />
//
//                 <button type="submit">Zapisz normę</button>
//             </form>
//
//             <h2>Historia norm</h2>
//
//             <ul  className="norm-history">
//                 {history.map((norm) => (
//                     <li key={norm.id}>
//                         {norm.targetPerShift} szt. — od{" "}
//                         {new Date(norm.validFrom).toLocaleString("pl-PL")}
//                     </li>
//                 ))}
//             </ul>
//         </section>
//     );
// }
//
// export default NormsTab;
import { useEffect, useState, type FormEvent } from "react";
import type { ProductionNorm } from "../../types/ManagerDashboard";
import "./NormsTab.css";

type Product = {
    id: number;
    code: string;
    name: string;
};

function NormsTab() {
    const [products, setProducts] = useState<Product[]>([]);
    const [productId, setProductId] = useState("");

    const [currentNorm, setCurrentNorm] =
        useState<ProductionNorm | null>(null);

    const [history, setHistory] = useState<ProductionNorm[]>([]);

    const [targetPerShift, setTargetPerShift] = useState("");
    const [exceedTargetPerShift, setExceedTargetPerShift] = useState("");
    const [minimumPerShift, setMinimumPerShift] = useState("");
    const [secondsPerPackage, setSecondsPerPackage] = useState("");

    const [error, setError] = useState("");

    async function loadNorms(selectedProductId: string) {
        if (!selectedProductId) {
            setCurrentNorm(null);
            setHistory([]);
            return;
        }

        const [currentResponse, historyResponse] = await Promise.all([
            fetch(
                `http://localhost:8080/api/norms/products/${selectedProductId}/current`
            ),
            fetch(
                `http://localhost:8080/api/norms/products/${selectedProductId}/history`
            ),
        ]);

        if (currentResponse.ok) {
            const currentData: ProductionNorm = await currentResponse.json();
            setCurrentNorm(currentData);
        } else {
            setCurrentNorm(null);
        }

        if (!historyResponse.ok) {
            throw new Error("Nie udało się pobrać historii norm");
        }

        const historyData: ProductionNorm[] = await historyResponse.json();
        setHistory(historyData);
    }

    useEffect(() => {
        fetch("http://localhost:8080/api/products")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Nie udało się pobrać produktów");
                }

                return response.json();
            })
            .then((data: Product[]) => {
                setProducts(data);
            })
            .catch((requestError) => {
                setError(
                    requestError instanceof Error
                        ? requestError.message
                        : "Nie udało się pobrać produktów"
                );
            });
    }, []);

    useEffect(() => {
        loadNorms(productId).catch((requestError) => {
            setError(
                requestError instanceof Error
                    ? requestError.message
                    : "Nie udało się pobrać norm"
            );
        });
    }, [productId]);

    async function createNorm(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError("");

        if (!productId) {
            setError("Wybierz produkt.");
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/norms/products/${productId}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        targetPerShift: Number(targetPerShift),
                        exceedTargetPerShift: Number(exceedTargetPerShift),
                        minimumPerShift: Number(minimumPerShift),
                        secondsPerPackage: Number(secondsPerPackage),
                    }),
                }
            );

            if (!response.ok) {
                throw new Error("Nie udało się zapisać normy");
            }

            setTargetPerShift("");
            setExceedTargetPerShift("");
            setMinimumPerShift("");
            setSecondsPerPackage("");

            await loadNorms(productId);
        } catch (requestError) {
            setError(
                requestError instanceof Error
                    ? requestError.message
                    : "Nie udało się zapisać normy"
            );
        }
    }

    return (
        <section className="norms-tab">
            <h2>Normy produktów</h2>

            <p className="product-selector-message">
                Najpierw wybierz produkt, aby wyświetlić lub dodać normę.
            </p>

            <label className="product-selector-label" htmlFor="norm-product">
                Produkt
            </label>
            <select
                id="norm-product"
                className="product-selector-input"
                value={productId}
                onChange={(event) => setProductId(event.target.value)}
            >
                <option value="">Wybierz produkt</option>

                {products.map((product) => (
                    <option key={product.id} value={product.id}>
                        {product.code} — {product.name}
                    </option>
                ))}
            </select>

            {error && <p>{error}</p>}

            {productId && !currentNorm && (
                <p>Ten produkt nie ma jeszcze aktywnej normy.</p>
            )}

            {currentNorm && (
                <>
                    <h2>Aktualna norma</h2>
                    <p>Norma: {currentNorm.targetPerShift}</p>
                    <p>Świetnie: {currentNorm.exceedTargetPerShift}</p>
                    <p>Minimum: {currentNorm.minimumPerShift}</p>
                    <p>
                        Czas na opakowanie: {currentNorm.secondsPerPackage} s
                    </p>
                </>
            )}

            <h2>Dodaj nową normę</h2>

            <form className="norm-form" onSubmit={createNorm}>
                <input
                    type="number"
                    placeholder="Norma na zmianę"
                    value={targetPerShift}
                    onChange={(event) => setTargetPerShift(event.target.value)}
                    required
                />

                <input
                    type="number"
                    placeholder="Poziom świetny"
                    value={exceedTargetPerShift}
                    onChange={(event) =>
                        setExceedTargetPerShift(event.target.value)
                    }
                    required
                />

                <input
                    type="number"
                    placeholder="Minimum"
                    value={minimumPerShift}
                    onChange={(event) => setMinimumPerShift(event.target.value)}
                    required
                />

                <input
                    type="number"
                    placeholder="Sekundy na opakowanie"
                    value={secondsPerPackage}
                    onChange={(event) => setSecondsPerPackage(event.target.value)}
                    required
                />

                <button type="submit" disabled={!productId}>
                    Zapisz normę
                </button>
            </form>

            {history.length > 0 && (
                <>
                    <h2>Historia norm</h2>

                    <ul className="norm-history">
                        {history.map((norm) => (
                            <li key={norm.id}>
                                <div className="norm-history-heading">
                                    <strong>
                                        Norma od {new Date(norm.validFrom).toLocaleString("pl-PL")}
                                    </strong>
                                    {currentNorm?.id === norm.id && (
                                        <span>Aktywna</span>
                                    )}
                                </div>

                                <dl className="norm-history-values">
                                    <div className="history-value-excellent">
                                        <dt>Świetnie</dt>
                                        <dd>{norm.exceedTargetPerShift} szt.</dd>
                                    </div>
                                    <div className="history-value-target">
                                        <dt>Norma</dt>
                                        <dd>{norm.targetPerShift} szt.</dd>
                                    </div>
                                    <div className="history-value-minimum">
                                        <dt>Do poprawy</dt>
                                        <dd>{norm.minimumPerShift} szt.</dd>
                                    </div>
                                    <div className="history-value-time">
                                        <dt>Czas na opakowanie</dt>
                                        <dd>{norm.secondsPerPackage} s</dd>
                                    </div>
                                </dl>
                            </li>
                        ))}
                    </ul>
                </>
            )}
        </section>
    );
}

export default NormsTab;
