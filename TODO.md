# Projekt 4 – Tablica ogłoszeń pracowniczych

## Opis projektu
Wewnętrzna aplikacja do publikowania ogłoszeń i komentowania ich przez pracowników firmy.
Obsługuje kategorie ogłoszeń, wyszukiwanie i statystyki per autor.
Projekt realizowany w zespole 2–3 osobowym w ciągu 2 tygodni.

---

## Encje

### Post
| Pole      | Typ        | Opis                                        |
|-----------|------------|---------------------------------------------|
| id        | Long       | klucz główny, auto                          |
| title     | String     | tytuł ogłoszenia                            |
| content   | String     | treść ogłoszenia                            |
| author    | String     | imię i nazwisko autora                      |
| category  | PostCategory | URGENT, INFO, EVENT, JOB                 |
| createdAt | LocalDateTime | data i czas publikacji                   |
| pinned    | boolean    | czy ogłoszenie jest przypięte na górze      |

### Comment
| Pole      | Typ        | Opis                          |
|-----------|------------|-------------------------------|
| id        | Long       | klucz główny, auto            |
| post      | Post (FK)  | powiązane ogłoszenie          |
| author    | String     | autor komentarza              |
| content   | String     | treść komentarza              |
| createdAt | LocalDateTime | data komentarza            |

---

## Wymagane endpointy REST

### Ogłoszenia (/api/posts)
| Metoda | Ścieżka                  | Opis                               | Kod     |
|--------|--------------------------|------------------------------------|---------|
| GET    | /api/posts               | lista ogłoszeń                     | 200     |
| GET    | /api/posts/{id}          | szczegóły ogłoszenia               | 200/404 |
| GET    | /api/posts/search        | szukaj (?keyword=, ?category=)     | 200     |
| GET    | /api/posts/pinned        | ogłoszenia przypięte               | 200     |
| POST   | /api/posts               | opublikuj ogłoszenie               | 201     |
| PUT    | /api/posts/{id}          | edytuj ogłoszenie                  | 200/404 |
| DELETE | /api/posts/{id}          | usuń ogłoszenie                    | 204/404 |

### Komentarze (/api/comments)
| Metoda | Ścieżka                      | Opis                          | Kod     |
|--------|------------------------------|-------------------------------|---------|
| GET    | /api/comments/post/{postId}  | komentarze ogłoszenia         | 200     |
| POST   | /api/comments                | dodaj komentarz               | 201     |
| DELETE | /api/comments/{id}           | usuń komentarz                | 204/404 |

### Statystyki (/api/stats)
| Metoda | Ścieżka                      | Opis                          | Kod |
|--------|------------------------------|-------------------------------|-----|
| GET    | /api/stats/posts-per-author  | liczba postów na autora       | 200 |
| GET    | /api/stats/posts-per-category| liczba postów per kategoria   | 200 |

---

## Wymagania techniczne

### Struktura pakietów
```
com.example.noticeboard
├── controller     ← tylko REST, zero logiki biznesowej
├── service        ← interfejs + implementacja dla każdego serwisu
├── repository     ← interfejsy Spring Data JPA
├── model          ← encje JPA + klasy żądań/odpowiedzi (bez osobnego dto)
├── event          ← klasy eventów i listenery
├── exception      ← własne wyjątki + GlobalExceptionHandler
└── config         ← klasy @Configuration z @Bean
```

### Interfejsy serwisów (obowiązkowe)
```java
public interface PostService {
    List<Post> findAll();
    Post findById(Long id);
    Post publish(Post post);
    void delete(Long id);
    List<Post> search(String keyword, PostCategory category);
}
```
Controller wstrzykuje interfejs, nie implementację.

### JPA
- Post -> Comment: @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
- Własne metody:
  List<Post> findByCategoryOrderByCreatedAtDesc(PostCategory category);
  List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String t, String c);

### Eventy Spring
Po opublikowaniu ogłoszenia opublikuj event:
```java
public class PostPublishedEvent extends ApplicationEvent {
    private final Post post;
}
```
Listener (@EventListener) aktualizuje mapę authorPostCount (in-memory, w singleton beanie) i loguje info.

### Scopy beanów
- singleton – serwisy + AuthorStatsHolder (mapa autorów i liczby postów)
- request – RequestScopedAuditLog logujący akcje w danym żądaniu (kto, co, kiedy)

Komentarz w kodzie wyjaśniający wybór scope.

### Własna konfiguracja @Bean
```java
@Bean
public AuthorStatsHolder authorStatsHolder() { /* singleton, przechowuje statystyki */ }

@Bean
@Scope("request")
@ScopedProxyMode(TARGET_CLASS)
public RequestScopedAuditLog auditLog() { /* log akcji na jedno żądanie HTTP */ }
```

### Obsługa wyjątków
@RestControllerAdvice z obsługą:
- ResourceNotFoundException (404) – brak ogłoszenia lub komentarza
- Odpowiedź JSON: { "status": 404, "message": "...", "timestamp": "..." }

Brak try/catch w kontrolerach.

### Testy jednostkowe
Min. 5 testów dla PostServiceImpl:
- should_returnPost_when_idExists
- should_throwException_when_postNotFound
- should_returnPosts_when_searchByKeyword
- should_publishPost_and_fireEvent
- should_deletePost_when_exists

### Plik API.http
Plik API.http z min. 10 wywołaniami pokrywającymi wszystkie endpointy.

---

## Baza danych
- H2/Sqlite in-memory
- Dane inicjalne: kilka ogłoszeń każdej kategorii

---

## README.md (wymagany)
1. Opis projektu
2. Diagram encji (ASCII)
3. Instrukcja uruchomienia
4. Opis scopów i uzasadnienie

---

## Punktacja
| Element                              | Punkty |
|--------------------------------------|--------|
| Struktura projektu i pakiety         | 10     |
| JPA – encje, relacje, zapytania      | 15     |
| REST API + kody odpowiedzi           | 20     |
| Interfejsy serwisów + DI             | 10     |
| Eventy Spring                        | 10     |
| Scopy beanów                         | 10     |
| Obsługa wyjątków (@ControllerAdvice) | 10     |
| Testy jednostkowe (min. 5)           | 15     |
| Suma                                 | 100    |

### Przelicznik na ocenę
| Punkty | Ocena           |
|--------|-----------------|
| 90–100 | 6 celujący      |
| 80–89  | 5 bardzo dobry  |
| 65–79  | 4 dobry         |
| 50–64  | 3 dostateczny   |
| 30–49  | 2 dopuszczający |
| 0–29   | 1 niedostateczny|

### Typowe błędy odejmujące punkty
- Logika biznesowa w kontrolerze: -5 pkt
- Brak pliku API.http: -5 pkt
- Controller wstrzykuje implementację zamiast interfejsu: -3 pkt
- Brak komentarza przy niestandardowym scopie: -2 pkt
