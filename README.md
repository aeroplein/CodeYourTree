# 🌲 CodeYourTree 🌲

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.3.0" />
  <img src="https://img.shields.io/badge/Spring%20Security-STATELESS-red?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security STATELESS" />
  <img src="https://img.shields.io/badge/PostgreSQL-15%2B-blue?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <br/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5" />
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" alt="JavaScript" />
  <img src="https://img.shields.io/badge/JWT-Cookie--based-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT Cookie-based" />
</p>

---

## 🌸 Welcome to the Garden!
**CodeYourTree** is a cute and interactive habit-tracking web application designed to help developers build and maintain consistent coding habits. Every day you sit down to code, you get to **water your virtual tree**. Consistent coding makes your tree grow dynamically on an interactive HTML5 Canvas, while skipping days will pause your forest's flourish. 

> *“Grow your discipline, one line at a time.”* 🌱

---

## ✨ Features
*   🌳 **Procedural Canvas Tree**: Watch a virtual tree grow dynamically. Your streak determines the maximum depth and branches of the tree, rendered procedurally using fractal math on an HTML5 canvas!
*   📅 **GitHub-Style Contribution Heatmap**: A custom 35-day grid that lights up as your streak increases, showing off your hard work and consistency.
*   🥇 **Habit Evolution Ranks**:
    *   🌱 **Seedling** *(Default Rank)*
    *   🌿 **Sapling** *(7+ Day Streak)*
    *   🌳 **Young Tree** *(15+ Day Streak)*
    *   👑 **Mighty Oak** *(30+ Day Streak)*
*   🔒 **JWT Authentication with HttpOnly Cookies**: Stateless authentication using an **HttpOnly, SameSite=Lax** JWT cookie. The cookie is configured for local HTTP development with `Secure=false`.
*   🍞 **Bouncy Toast Notifications**: Custom non-blocking visual feedback when watering your tree, replacing annoying native alerts with smooth CSS transitions.

---

## 🛠️ Technology Stack
### Backend
*   **Java 21** & **Spring Boot 3.3.0**
*   **Spring Security** (Stateless Session Configuration)
*   **JSON Web Tokens (JWT)** via `jjwt`
*   **Spring Data JPA** & **Hibernate**
*   **PostgreSQL Database**
*   **Lombok** for clean, boilerplate-free code

### Frontend
*   **HTML5** (Semantic structure)
*   **Vanilla CSS3** (Custom properties & typography)
*   **Vanilla JavaScript** (Async Fetch APIs & 2D Canvas rendering)
*   **Google Fonts** (Outfit typography)

---

## 📁 Project Structure
```text
CodeYourTree/
├── backend/
│   ├── src/main/java/com/codeyourtree/backend/
│   │   ├── config/          # Security configs, CORS, JWT Filters
│   │   ├── controller/      # Auth & Tree endpoints
│   │   ├── model/           # User & Tree database entities
│   │   ├── repository/      # Database adapters
│   │   └── service/         # Core business logic (JWT, user, watering rules)
│   ├── src/main/resources/
│   │   └── application.properties # Database connection settings
│   ├── src/test/java/com/codeyourtree/backend/
│   │   ├── BackendApplicationTests.java
│   │   └── service/
│   │       └── TreeDataServiceTest.java
│   └── pom.xml
└── frontend/
    ├── css/                 # Premium design & responsive styles
    ├── js/                  # Authentication & Canvas tree rendering engines
    ├── landing.html         # Gorgeous welcome page
    ├── signin.html          # Clean login portal
    └── signup.html          # Registration form
```

---

## 🚀 Getting Started

### 1. Database Setup
Make sure you have PostgreSQL running. Open your PostgreSQL terminal/GUI (like pgAdmin or DBeaver) and run:
```sql
CREATE DATABASE codeyourtreedb;
```

### 2. Backend Setup
1. Navigate to the `backend` folder.
2. Open `src/main/resources/application.properties` and verify your credentials:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/codeyourtreedb
    spring.datasource.username=your_postgres_username
    spring.datasource.password=your_postgres_password
    ```
3. Run the Spring Boot application using your IDE or run the following command in terminal:
    ```bash
    mvn spring-boot:run
    ```
    *The backend server will spin up on port `8081`.*

### 3. Frontend Setup
1. Navigate to the `frontend` folder.
2. Since the frontend communicates with the backend via cross-origin cookies, run a local development server to serve the HTML pages (for example, using the **Live Server** extension in VS Code).
3. Open `landing.html` in your browser.
4. Click **Join the Garden**, create an account, log in, and water your first tree! 💦

---

## 🧪 Testing

The backend test suite uses **JUnit 5** and **Mockito**, provided by Spring Boot’s test starter.

`TreeDataServiceTest` contains focused service-layer unit tests for the tree progression rules:

* First watering applies the initial daily progression rules.
* A second watering on the same day is rejected without saving.
* Consecutive-day watering increases the streak and XP.
* Reaching the third consecutive day increases the tree’s maximum depth.
* Missing a day resets the streak and tree depth while preserving accumulated XP.

Run the complete backend test suite from the `backend` directory:

```bash
mvn test
```

The suite also contains a Spring application-context test, which uses the PostgreSQL configuration in `application.properties`. A configured local database is therefore required when running the complete suite.

---

## 🔒 Security Architecture
The current authentication flow stores the JWT in an HttpOnly cookie:

```text
  [Browser Client]                                              [Spring Boot Server]
         │                                                               │
         │ ─── 1. POST /api/users/login (Credentials) ─────────────────> │
         │ <── 2. Response with Set-Cookie: token=JWT (HttpOnly) ─────── │
         │                                                               │
         │ ─── 3. GET /api/users/me (Cookie automatically sent) ───────> │
         │      (Cookies are read & validated inside custom Filter)      │
         │ <── 4. Return user profile JSON ───────────────────────────── │
```

> [!IMPORTANT]
> **Why HttpOnly Cookies?**
> The `HttpOnly` flag prevents client-side JavaScript from reading the JWT through `document.cookie`, reducing direct token exposure. The current configuration uses `SameSite=Lax`, disables Spring Security’s CSRF protection, and sets `Secure=false` for local HTTP development. Production deployment would require reviewing these settings and enabling HTTPS-only cookies.

---

## 🎨 Visual Design Tokens
*   **Main Green (Branches)**: `#90AB8B`
*   **Leaf Bloom**: `#EBF4DD`
*   **Font Family**: `Outfit` (Google Fonts)

---

## 📄 License
This project is open-source and licensed under the **MIT License**. Feel free to fork, customize, and grow your own virtual forest! 🌸

---
<p align="center">Made with 💚 and discipline. Keep watering your tree!</p>
