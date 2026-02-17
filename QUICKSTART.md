# Quick Start Guide

## 🚀 Get Started in 3 Steps

### Step 1: Open the Project

**Option A: IntelliJ IDEA**
1. Open IntelliJ IDEA
2. Click "Open"
3. Navigate to `todo-management-api-complete` folder
4. Click "Open"
5. Wait for Maven to download dependencies

**Option B: Eclipse**
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Browse to `todo-management-api-complete`
4. Click "Finish"

**Option C: VS Code**
1. Open VS Code
2. Install "Extension Pack for Java"
3. File → Open Folder
4. Select `todo-management-api-complete`

---

### Step 2: Build & Run

**From IDE:**
- Right-click `TodoApiApplication.java`
- Select "Run As → Java Application" or "Run 'TodoApiApplication'"

**From Terminal:**
```bash
cd todo-management-api-complete
mvn spring-boot:run
```

You should see:
```
Started TodoApiApplication in X.XX seconds
```

---

### Step 3: Test the API

**Open a new terminal and try:**

```bash
# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Test Task", "description": "My first task"}'

# Get all tasks
curl http://localhost:8080/api/tasks
```

---

## 📚 What's Included

### Source Code (`src/main/java/`)
- ✅ Complete Spring Boot REST API
- ✅ 7 working endpoints
- ✅ Logging and monitoring
- ✅ Professional architecture

### Tests (`src/test/java/`)
- ✅ 26 automated tests
- ✅ Unit tests (controller + service)
- ✅ Integration tests (full stack)
- ✅ 92% code coverage

### Documentation (`docs/`)
- ✅ All sprint planning documents
- ✅ Sprint reviews (demos)
- ✅ Retrospectives (2 sprints)
- ✅ Complete project summary

---

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run with coverage
mvn clean test jacoco:report

# View coverage report (after running)
# Look in: target/site/jacoco/index.html
```

---

## 📖 Project Structure

```
todo-management-api-complete/
├── src/                     # All source code
│   ├── main/java/          # Application code
│   └── test/java/          # Test code
├── docs/                    # All documentation
│   ├── BACKLOG.md
│   ├── SPRINT1_REVIEW.md
│   ├── SPRINT2_REVIEW.md
│   └── PROJECT_SUMMARY.md  # ⭐ Start here!
├── pom.xml                  # Maven dependencies
└── README.md                # Main readme
```

---

## 🎯 API Endpoints Quick Reference

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/tasks` | POST | Create task |
| `/api/tasks` | GET | List all |
| `/api/tasks/{id}` | GET | Get one |
| `/api/tasks/{id}` | PUT | Update |
| `/api/tasks/{id}` | DELETE | Delete |
| `/api/tasks/{id}/complete` | PATCH | Mark done |
| `/api/tasks/health` | GET | Health check |

---

## 💡 Tips

1. **Start with the docs!** Read `docs/PROJECT_SUMMARY.md` first
2. **Check the tests** - They show how everything works
3. **Review commits** - 18 commits show incremental development
4. **All tests pass** - Run `mvn test` to verify

---

## ❓ Troubleshooting

**Problem: Port 8080 already in use**
- Change port in `src/main/resources/application.properties`
- Set: `server.port=8081`

**Problem: Maven dependencies not downloading**
- Check internet connection
- Run: `mvn clean install -U`

**Problem: Tests failing**
- Make sure you're using Java 17+
- Run: `mvn clean install`

---

## 📞 Need Help?

1. Check `docs/PROJECT_SUMMARY.md` for complete overview
2. Review test files for usage examples
3. Check code comments in Java files

---

**You're all set! 🎉**

The project is fully functional and ready to run. Start by exploring the documentation in the `docs/` folder!
