# Sprint Planning

## Sprint 1

### Sprint Goal
**"Deliver core CRUD functionality to create and view tasks, establishing the foundation for the Task Management API."**

### Sprint Duration
- **Start Date:** [To be filled]
- **End Date:** [To be filled]
- **Duration:** 1-2 weeks (academic project timeline)

### Team Capacity
- **Available Story Points:** 7-10 points
- **Team Members:** 1 (solo project)

---

### Selected User Stories

#### 1. US-001: Create Task
- **Priority:** HIGH
- **Story Points:** 3
- **Rationale:** Foundation for all other features - must have

#### 2. US-002: View All Tasks
- **Priority:** HIGH
- **Story Points:** 2
- **Rationale:** Essential for users to see their tasks

#### 3. US-008: View Task by ID
- **Priority:** HIGH
- **Story Points:** 2
- **Rationale:** Completes basic READ operations, needed for future features

**Total Committed:** 7 Story Points

---

### Sprint Deliverables

#### Functional Deliverables
1. Working API with 3 endpoints:
   - `POST /tasks` - Create a new task
   - `GET /tasks` - Get all tasks
   - `GET /tasks/:id` - Get task by ID

2. Data persistence (in-memory or file-based)

3. Basic error handling (404, 400 responses)

#### Technical Deliverables
1. Git repository with clean commit history
2. CI/CD pipeline configured (GitHub Actions)
3. Unit tests with >80% coverage
4. Automated testing in pipeline
5. README with API documentation

---

### Technical Decisions

#### Tech Stack
- **Language:** Python 3.x
- **Framework:** Flask (lightweight, simple for API development)
- **Testing:** pytest
- **CI/CD:** GitHub Actions
- **Data Storage:** In-memory (dict) or JSON file (to keep it simple)

#### Project Structure
```
todo-api/
├── src/
│   ├── app.py           # Main Flask application
│   ├── models.py        # Task model/data structure
│   └── routes.py        # API routes
├── tests/
│   ├── test_tasks.py    # Unit tests
│   └── conftest.py      # Test fixtures
├── .github/
│   └── workflows/
│       └── ci.yml       # CI/CD pipeline
├── requirements.txt     # Python dependencies
├── README.md
├── BACKLOG.md
├── DEFINITION_OF_DONE.md
└── SPRINT_PLANNING.md
```

---

### Sprint Tasks Breakdown

#### US-001: Create Task (3 points)
- [ ] Set up Flask project structure
- [ ] Create Task model/class
- [ ] Implement POST /tasks endpoint
- [ ] Add input validation
- [ ] Write unit tests for task creation
- [ ] Test 201 and 400 responses

#### US-002: View All Tasks (2 points)
- [ ] Implement GET /tasks endpoint
- [ ] Return tasks as JSON array
- [ ] Write unit tests for listing tasks
- [ ] Test empty array response

#### US-008: View Task by ID (2 points)
- [ ] Implement GET /tasks/:id endpoint
- [ ] Add ID lookup logic
- [ ] Handle 404 for missing tasks
- [ ] Write unit tests
- [ ] Test both 200 and 404 responses

#### DevOps Setup
- [ ] Initialize Git repository
- [ ] Create GitHub repository
- [ ] Set up GitHub Actions workflow
- [ ] Configure pytest
- [ ] Add linting (flake8 or pylint)
- [ ] Create requirements.txt

---

### Definition of Done Review

Reminder: All stories must meet the criteria in `DEFINITION_OF_DONE.md` before being marked as complete.

---

### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|-----------|
| CI/CD setup takes longer than expected | Medium | Allocate time early in sprint, use simple workflow first |
| Unfamiliar with testing framework | Medium | Review pytest docs, start with simple tests |
| Scope creep (adding extra features) | High | Stick to acceptance criteria, defer additional ideas to backlog |

---

## Sprint 2 (Planned)

### Tentative Sprint Goal
**"Extend API with update/delete operations and implement monitoring/logging."**

### Candidate Stories (To be finalized after Sprint 1)
- US-005: Mark Task as Complete (2 points)
- US-003: Update Task (3 points)
- US-004: Delete Task (2 points)

**Tentative Total:** 7 Story Points

*Note: Final Sprint 2 plan will be determined after Sprint 1 retrospective*

---

*Document Created: Sprint 0*  
*Last Updated: Sprint 0*
