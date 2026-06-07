# 🚀 CodeEdge

**CodeEdge** is a premium, desktop-IDE styled campus placement and SDE interview preparation companion. Designed for SDE aspirants, it provides a comprehensive multi-user workspace to track DSA problems, schedule & evaluate mock interviews, search company-specific interview pipelines, and manage study timelines inside a sleek, glassmorphic dark-themed editor interface.

---

## 🛠️ Tech Stack

* **Backend Framework**: Java 17 + Spring Boot 3.2.5
* **Security & Authentication**: Spring Security 6 + BCrypt Password Hashing
* **Data Persistence**: MongoDB (via Spring Data MongoDB)
* **Frontend Engine**: Thymeleaf Template Engine + Vanilla HTML5/CSS3 (Theme-agnostic variables)
* **Interactive UI**: Native JavaScript (3D interactive constellation canvas, timer controls, modals)
* **API Documentation**: Swagger / OpenAPI 3 (Springdoc)
* **Build Tool**: Maven

---

## ✨ Key Features

1. **User Authentication & Session Management**:
   * Secure user registration (`signup.html`) and login (`login.html`) forms themed as an IDE shell.
   * Session security filters preventing unauthorized access to the SDE workspace.
   * Multi-user isolation (metrics, solved problems, notes, and mock interview reports are mapped and saved specifically to your account).
2. **Dashboard (`Dashboard.java`)**: 
   * Real-time user placement progress tracker (Solved vs Total metrics).
   * DSA difficulty and topic-wise visual progress status.
   * Recent completed mock interview summaries.
3. **DSA Problems Repository (`Problems.java`)**:
   * Filter problems by Topic, Difficulty (Easy/Medium/Hard), and solved status.
   * Case-insensitive keyword search for titles and tags.
   * Quick-solve toggle to log completion times and custom approach notes.
4. **Mock Interview Scheduler (`MockInterview.java`)**:
   * Schedule upcoming interviews specifying target company, date, duration, and topics.
   * Live countdown timer tool mimicking real placement conditions.
   * Structured feedback card generator to input performance scores and critiques.
5. **Company-Wise Question Browser (`Companies.java`)**:
   * View tailored preparation cards for top tech companies (Amazon, Google, Microsoft, Uber, GS, Flipkart, Salesforce, Adobe).
   * Displays target recruitment rounds, focus areas, package range, and tagged problems.

---

## 📋 Prerequisites

Before running the application, make sure you have installed:
* **Java Development Kit (JDK) 17**
* **Apache Maven 3.8+**
* **MongoDB** (Local instance running on port `27017` or a cloud-hosted MongoDB Atlas cluster)

---

## 💾 Installation & Setup

### 1. Download / Clone the Repository
Clone the repository to your local directory:
```bash
git clone https://github.com/techiedevang/codeEdge.git
cd codeEdge
```

### 2. Environment Configuration (`.env`)
Create a `.env` file at the root level of the project to securely load your database connection credentials (this file is ignored by Git):

```env
# Database Connection URI (Replace with your own string)
MONGODB_URI=mongodb://localhost:27017/codeedge
```

---

## 🚀 Running Locally

1. Build the application and compile dependencies:
   ```bash
   mvn clean install
   ```
2. Start the Spring Boot server:
   ```bash
   mvn spring-boot:run
   ```
3. Open your browser and navigate to:
   * **Application Homepage**: [http://localhost:8080](http://localhost:8080) (You will be automatically redirected to `/login` to register your account)
   * **Swagger API Docs**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ☁️ Deploying to Render (NoSQL Cloud Build)

This project has been pre-configured with a **Dockerfile** and a **Render Blueprint (`render.yaml`)** for deployment.

1. Push your repository to your own GitHub account.
2. Log into your [Render Dashboard](https://dashboard.render.com).
3. Click **New +** -> **Blueprint**.
4. Connect your GitHub repository.
5. In the configuration page, paste your MongoDB Atlas connection string into the **`MONGODB_URI`** variable field.
6. Click **Apply**. Render will automatically build the container and deploy the app!
