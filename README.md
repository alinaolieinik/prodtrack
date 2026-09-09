# System monitorowania produkcji

Aplikacja webowa do monitorowania pracy operatorów i maszyn na linii produkcyjnej. Składa się z backendu REST w Spring Boot oraz frontendu React uruchamianego przez Vite.

System udostępnia dwa widoki zależne od roli zalogowanego użytkownika: panel operatora i panel managera.

## Jak działa aplikacja

### Logowanie

Użytkownik wybiera konto z listy i podaje PIN. Backend sprawdza dane, rozpoczyna sesję w historii logowań i zwraca rolę użytkownika. Frontend zapisuje zalogowanego użytkownika w `localStorage`, aby utrzymać widok po odświeżeniu strony.

To jest proste logowanie aplikacyjne — projekt nie korzysta obecnie ze Spring Security ani z tokenów sesyjnych.

### Panel operatora

Panel operatora odświeża dane co sekundę i prezentuje:

- imię i nazwisko operatora oraz przypisaną maszynę i produkt,
- godzinę rozpoczęcia bieżącej sesji,
- aktualną liczbę zapakowanych sztuk,
- czas pozostały na zapakowanie kolejnej sztuki,
- aktywną normę produktu: poziom świetny, podstawowy i minimalny,
- aktywne ogłoszenia managerów.

Licznik produkcji jest zwiększany przez endpoint `POST /api/production/machines/{machineId}/packages`. Sam panel operatora tylko wyświetla licznik — nie zawiera przycisku rejestrującego paczkę.

Podczas wylogowania operatora aplikacja:

1. zamyka bieżącą sesję logowania,
2. zapisuje wynik pracy wraz z maszyną, produktem, liczbą sztuk i osiągniętym poziomem,
3. zeruje licznik produkcji przypisanej maszyny.

### Panel managera

Nagłówek panelu pokazuje podsumowanie liczby aktywnych operatorów, pracujących maszyn, awarii i aktywnych ogłoszeń. Manager otrzymuje również ostrzeżenie, gdy pracująca maszyna ma przypisanych operatorów, ale żaden z nich nie jest zalogowany.

Panel zawiera pięć zakładek:

- **Użytkownicy** — lista, dodawanie, edycja i usuwanie użytkowników; operatorowi przypisuje się maszynę i produkt, a PIN jest ustawiany podczas tworzenia konta.
- **Normy** — wybór produktu, podgląd aktualnej normy, dodawanie nowej wersji oraz historia wszystkich wartości norm: poziomu świetnego, podstawowego, minimalnego i czasu na opakowanie.
- **Ogłoszenia** — publikowanie ogłoszeń w określonym przedziale czasu, wyświetlanie aktywnych i przyszłych wpisów oraz ich wcześniejsze anulowanie.
- **Maszyny** — podgląd nazwy, stanu i czasu ostatniej aktualizacji maszyn.
- **Produkty** — lista, dodawanie, edycja i usuwanie produktów.

## Reguły biznesowe

- Role użytkowników to `OPERATOR` i `MANAGER`.
- Operator musi mieć przypisaną maszynę oraz produkt.
- Manager nie ma przypisanej maszyny ani produktu.
- Jedna maszyna może być przypisana wielu operatorom.
- Normy są przypisane do konkretnych produktów, a nie globalnie do wszystkich maszyn.
- Dodanie nowej normy zamyka poprzednią aktywną normę danego produktu i tworzy nową wersję.
- Produkt ma unikalny kod.
- Nie można usunąć produktu używanego przez użytkownika, normę lub historię pracy.
- Tylko użytkownik z rolą managera może tworzyć i anulować ogłoszenia.
- Data zakończenia ogłoszenia musi być późniejsza niż data rozpoczęcia.
- Maszyna może mieć stan `WORKING`, `WAITING` albo `FAILURE`; backend blokuje bezpośrednie przejście z `FAILURE` do `WORKING`.
- Wynik zakończonej pracy operatora otrzymuje poziom `EXCELLENT`, `TARGET`, `MINIMUM`, `BELOW_MINIMUM` albo `NO_NORM`.

## Technologie

### Backend

- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA / Hibernate
- Microsoft SQL Server
- Maven Wrapper
- JUnit 5

### Frontend

- React 19
- TypeScript 6
- Vite 8
- CSS
- ESLint

## Wymagania

- JDK 21
- Node.js oraz npm
- działająca instancja Microsoft SQL Server
- baza danych z danymi wymaganymi przez aplikację

Projekt nie zawiera obecnie migracji ani automatycznego zestawu danych startowych. Aby panel operatora działał, w bazie muszą istnieć co najmniej: użytkownik-operator, przypisana maszyna i produkt, rekord `machine_production_data` dla maszyny oraz aktywna norma dla produktu.

## Konfiguracja bazy danych

Backend korzysta z ustawień w `src/main/resources/application.properties`. Przed uruchomieniem dopasuj połączenie do swojej instancji SQL Server:

```properties
spring.datasource.url=jdbc:sqlserver://localhost;databaseName=NAZWA_BAZY;encrypt=true;trustServerCertificate=true
spring.datasource.username=NAZWA_UZYTKOWNIKA
spring.datasource.password=HASLO
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

Hibernate ma ustawione `ddl-auto=update`, więc aktualizuje strukturę tabel na podstawie encji, ale nie tworzy danych biznesowych.

## Uruchomienie lokalne

### 1. Backend

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux lub macOS:

```bash
./mvnw spring-boot:run
```

API będzie dostępne pod adresem `http://localhost:8080`.

### 2. Frontend

W drugim terminalu:

```powershell
cd frontend
npm ci
npm run dev
```

Interfejs będzie dostępny pod adresem `http://localhost:5173`. Backend zezwala obecnie na żądania CORS właśnie z tego adresu.

## Najważniejsze endpointy API

| Metoda | Endpoint | Działanie |
| --- | --- | --- |
| `GET` | `/api/auth/users` | Lista kont dostępnych na ekranie logowania |
| `POST` | `/api/auth/login` | Logowanie przez `userId` i `pin` |
| `POST` | `/api/auth/logout` | Wylogowanie i zamknięcie sesji |
| `GET` | `/api/dashboard/operator/{operatorId}` | Dane panelu operatora |
| `GET` | `/api/manager/dashboard/{managerId}` | Dane i podsumowanie panelu managera |
| `GET/POST` | `/api/users` | Lista i tworzenie użytkowników |
| `GET/PUT/DELETE` | `/api/users/{id}` | Odczyt, edycja i usuwanie użytkownika |
| `GET/POST` | `/api/products` | Lista i tworzenie produktów |
| `PUT/DELETE` | `/api/products/{id}` | Edycja i usuwanie produktu |
| `GET` | `/api/machines` | Lista maszyn |
| `PATCH` | `/api/machines/{id}/state?state=...` | Zmiana stanu maszyny |
| `GET` | `/api/norms/products/{productId}/current` | Aktualna norma produktu |
| `GET` | `/api/norms/products/{productId}/history` | Historia norm produktu |
| `POST` | `/api/norms/products/{productId}` | Utworzenie nowej normy produktu |
| `GET` | `/api/announcements` | Aktywne ogłoszenia dla operatora |
| `GET` | `/api/announcements/manageable?managerId=...` | Aktywne i przyszłe ogłoszenia managera |
| `POST` | `/api/announcements` | Utworzenie ogłoszenia |
| `PATCH` | `/api/announcements/{id}/cancel` | Anulowanie ogłoszenia |
| `POST` | `/api/production/machines/{machineId}/packages` | Zarejestrowanie kolejnej zapakowanej sztuki |


## Struktura projektu

```text
.
├── src/main/java/...           # kontrolery, serwisy, repozytoria i encje Spring Boot
├── src/main/resources/         # konfiguracja backendu
├── src/test/java/...           # testy backendu
├── frontend/src/components/    # widoki logowania, operatora i managera
├── frontend/src/types/         # typy danych frontendu
├── frontend/package.json       # zależności i skrypty frontendu
└── pom.xml                     # konfiguracja Maven/Spring Boot
```

## Znane ograniczenia

- Adres backendu (`http://localhost:8080`) jest wpisany bezpośrednio w wywołaniach frontendu.
- CORS jest skonfigurowany dla `http://localhost:5173`.
- Projekt nie ma jeszcze mechanizmu uwierzytelniania opartego na tokenach ani Spring Security.
- PIN użytkownika jest obsługiwany jako prosty PIN aplikacyjny, a nie pełny system bezpiecznego przechowywania haseł.
- Zakładka maszyn w panelu managera jest tylko do odczytu.
- Rejestracja paczek odbywa się przez API, a nie przez przycisk w panelu operatora.
- Brakuje migracji bazy danych i automatycznych danych startowych.
