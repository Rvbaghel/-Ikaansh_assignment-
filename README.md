# 🖥️ Hardware Component Catalog

A production-grade, full-stack web application for managing hardware components. Built with a **Java Spring Boot 3** backend and a **React + Vite + Tailwind CSS v4** frontend, featuring JWT-based authentication, dynamic search, filtering, and pagination.

## **📸 Frontend Snapshot**

*(Replace this placeholder with an actual screenshot of your UI before submitting)*
![Hardware Catalog Dashboard]
<img width="1892" height="870" alt="dashboard" src="https://github.com/user-attachments/assets/499cae9a-2da1-468c-8e9c-01062af1a39f" />
![Paging]
<img width="1895" height="353" alt="paging" src="https://github.com/user-attachments/assets/523d132a-822a-48c5-a275-7fa5afb934ee" />
![Login]
<img width="646" height="421" alt="signup" src="https://github.com/user-attachments/assets/32e86862-00c0-4aaf-96a9-ec91fbd013ab" />
![Signup]
<img width="879" height="687" alt="login" src="https://github.com/user-attachments/assets/e0065ef5-d757-43b1-b6ee-2247fb152832" />


---

## **✨ Key Features**

* **Secure Authentication:** JWT-based user registration and login using Spring Security 6.
* **Full CRUD Operations:** Add, view, edit, and delete hardware components.
* **Advanced Search & Filtering:** Dynamic Criteria API searching by component name and filtering by category.
* **Pagination & Sorting:** Server-side pagination and multi-column sorting (e.g., Price: Low to High).
* **Modern UI:** Responsive, single-page React application styled with Tailwind CSS v4.
* **Zero-Config Database:** Pre-configured to connect to a cloud PostgreSQL instance (Neon) with auto-schema generation and data seeding.

---

## **🛠️ Tech Stack**

**Backend:**
* Java 21
* Spring Boot 3.3.4 (Web, Data JPA, Security, Validation)
* JJWT (0.12.6) for stateless authentication
* PostgreSQL & HikariCP
* Maven

**Frontend:**
* React 18
* Vite
* Tailwind CSS v4
* Lucide React (Icons)

---


## **💻 Manual Setup Instructions**

If you prefer to run the application locally without Docker, follow these steps.

### **Prerequisites**
* Java 21 JDK
* Node.js (v18+)
* Maven (or use the included wrapper)

### **1. Database Configuration & Schema**
The project is pre-configured to connect to a cloud-hosted **Neon PostgreSQL** database to make evaluation as simple as possible. 

* **Schema & Data Migration:** The database schema (`src/main/resources/schema.sql`) and 10+ initial seed records (`src/main/resources/data.sql`) will automatically execute when the Spring Boot application starts.
* **Environment Variables:** The `application.properties` file uses environment variables with fallback defaults. **No configuration is required to run the app out-of-the-box.**

If you wish to use your own local PostgreSQL database, you can supply a `.env` file or set the following environment variables:
`DB_URL=jdbc:postgresql://localhost:5432/your_db`
`DB_USERNAME=your_user`
`DB_PASSWORD=your_password`
`JWT_SECRET=your_base64_encoded_256_bit_secret`

### **2. Start the Backend (Spring Boot)**
Open a terminal in the root directory and run:
`./mvnw spring-boot:run`
*(The backend runs on port 8080)*

### **3. Start the Frontend (React + Vite)**
Open a second terminal, navigate to the frontend directory, and run:
`cd frontend`
`npm install`
`npm run dev`
*(The frontend runs on port 5173 and proxies API requests to 8080)*

---

## **📡 API Endpoints**

| HTTP Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/v1/auth/register` | Register a new user | ❌ |
| **POST** | `/api/v1/auth/login` | Authenticate and receive JWT | ❌ |
| **GET** | `/api/v1/components` | Get all components | ❌ |
| **GET** | `/api/v1/components/categories` | Get dynamically loaded categories | ❌ |
| **GET** | `/api/v1/components/{id}` | Get a specific component by ID | ❌ |
| **POST** | `/api/v1/components` | Create a new component | ✅ (JWT) |
| **PUT** | `/api/v1/components/{id}` | Update an existing component | ✅ (JWT) |
| **DELETE** | `/api/v1/components/{id}` | Delete a component | ✅ (JWT) |

---

## **🤖 AI Usage Statement**

In the spirit of modern engineering and productivity, **Google Gemini AI** was utilized as a collaborative thought partner during the development of this project. It was primarily used to accelerate boilerplate generation (such as Tailwind UI layouts and Spring Boot configuration classes), brainstorm architectural decisions regarding JWT filter chains, and debug minor integration issues between Vite and Spring Boot. All core business logic, schema design, and final code implementation were thoroughly reviewed, tested, and assembled by me.
