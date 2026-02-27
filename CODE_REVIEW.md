# Recenzja kodu

## Zakres
Przegląd warstwy raportów (`ReportController`, `ReportService`, `ReportResultService`, repozytoria) pod kątem poprawności, API i utrzymania.

## Najważniejsze uwagi

### 1) Brak obsługi "not found" dla `GET /api/report/{id}` i potencjalny `NullPointerException` (HIGH)
- `ReportRepository.findById(Long id)` zwraca `Report` zamiast `Optional<Report>`.
- `ReportController.getById` przekazuje wynik bez walidacji do `getReportDTO(report)`.
- Gdy rekord nie istnieje, aplikacja może zwrócić 500 zamiast 404.

**Rekomendacja:** przejść na `Optional<Report>` i zwracać `ResponseStatusException(HttpStatus.NOT_FOUND, ...)` albo `ResponseEntity.notFound()`.

### 2) Nieefektywna i nieatomowa logika upsert w `PUT /api/report/{id}` (MEDIUM)
- `ReportController.put` pobiera całą listę (`findAll`) i ręcznie sprawdza obecność ID (`findId`).
- To jest kosztowne przy większej liczbie rekordów i podatne na race condition między odczytem a zapisem.

**Rekomendacja:** zastąpić `findAll + findId` pojedynczym odczytem po ID (najlepiej `existsById`/`findById`) i wykonać operację w jednej, spójnej ścieżce serwisowej.

### 3) Globalny handler wyjątków mapuje wszystko na 500 (MEDIUM)
- `@ExceptionHandler(Exception.class)` w kontrolerze zwraca zawsze `HttpStatus.INTERNAL_SERVER_ERROR`.
- Błędy walidacji danych wejściowych (`null`, puste pola) są błędami klienta i powinny kończyć się 400.

**Rekomendacja:** użyć dedykowanych wyjątków (np. `IllegalArgumentException`, `ResponseStatusException`) i mapować je do 400/404; 500 zostawić dla błędów nieoczekiwanych.

### 4) Podwójny zapis encji przy tworzeniu raportu (LOW)
- `ReportService.create` zapisuje `Report` przed i po wyliczeniu wyników.
- To generuje dodatkowy write i utrudnia śledzenie transakcji.

**Rekomendacja:** ograniczyć do jednego zapisu końcowego (lub jasno uzasadnić dwuetapowy zapis i objąć metodę transakcją).

## Plusy
- Warstwy kontrolera/serwisu/repozytorium są logicznie rozdzielone.
- DTO ograniczają ekspozycję encji w API.
