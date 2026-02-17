# Sprint 1 Retrospective

## Sprint Information
- **Sprint Number:** 1
- **Date:** [Retrospective Date]
- **Participants:** Solo Developer
- **Duration:** [Sprint Duration]

---

## Retrospective Format
This retrospective follows the **Start-Stop-Continue** framework with additional focus on concrete improvements for Sprint 2.

---

## What Went Well ✅

### 1. Incremental Development
**What happened:**
- Made 10 small, focused commits instead of one large commit
- Each commit represented a logical unit of work
- Easy to track progress and understand changes

**Why it worked:**
- Following Git best practices from the start
- Clear commit message convention
- Thinking in small deliverable increments

**Impact:** Made code review easier and Git history useful

---

### 2. Test-First Mindset
**What happened:**
- Wrote comprehensive tests for all endpoints
- Achieved >85% code coverage
- Tests caught edge cases early

**Why it worked:**
- Thinking about acceptance criteria drove test cases
- Using MockMvc and Mockito effectively
- TDD-like approach (even if not strict TDD)

**Impact:** High confidence in code quality, caught bugs before they hit production

---

### 3. Clear Architecture
**What happened:**
- Clean separation between Controller, Service, and Repository layers
- Each class had single responsibility
- Easy to understand code flow

**Why it worked:**
- Following Spring Boot best practices
- Thinking about maintainability from the start
- Using dependency injection properly

**Impact:** Code is easy to extend for Sprint 2

---

### 4. Good Documentation
**What happened:**
- README includes all necessary information
- API endpoints well-documented with examples
- Inline code comments for complex logic

**Why it worked:**
- Documenting as we build, not as an afterthought
- Thinking from user perspective

**Impact:** Easy for others (or future self) to understand the project

---

## What Didn't Go Well ⚠️

### 1. No Actual CI/CD Execution
**What happened:**
- Created GitHub Actions workflow file
- But never actually ran it (no GitHub repository setup)
- Couldn't verify pipeline would actually work

**Why it happened:**
- Focused on local development
- Didn't prioritize pushing to remote
- Assumed workflow would work

**Impact:** No real CI/CD validation, potential hidden issues

---

### 2. Limited Error Handling
**What happened:**
- Basic error handling implemented
- Generic error messages ("Failed to create task")
- No proper exception handling strategy

**Why it happened:**
- Rushed to complete functionality
- Didn't think deeply about error scenarios
- Focused on happy path

**Impact:** Less user-friendly error messages, harder to debug issues

---

### 3. No Integration Tests
**What happened:**
- Only unit tests with mocked dependencies
- No full end-to-end tests of actual HTTP requests
- Repository layer not tested independently

**Why it happened:**
- Time constraints
- Focused on meeting minimum coverage requirement
- Unit tests seemed sufficient

**Impact:** Might miss integration issues between layers

---

### 4. No Logging Implementation
**What happened:**
- No structured logging in the application
- Hard to debug when issues occur
- No visibility into what the app is doing

**Why it happened:**
- Forgot about it until late in sprint
- Focused only on functional requirements
- Didn't prioritize observability

**Impact:** Difficult to troubleshoot issues in running application

---

## Concrete Improvements for Sprint 2 🎯

### Improvement #1: Set Up Actual CI/CD Pipeline
**Category:** DevOps Practice

**Current State:**
- Workflow file exists but never executed
- No real automation

**Target State:**
- Push code to GitHub repository
- Verify pipeline runs on every push
- Fix any pipeline issues that arise

**Action Items:**
1. Create GitHub repository
2. Push all code to remote
3. Trigger pipeline and verify it passes
4. Add status badge to README

**Success Criteria:**
- Pipeline runs successfully
- All tests pass in CI environment
- Coverage report generated

**Why this matters:**
- Core DevOps practice for the project
- Catches issues early
- Demonstrates automation

---

### Improvement #2: Implement Comprehensive Logging
**Category:** Monitoring & Observability

**Current State:**
- No logging framework configured
- No visibility into application behavior
- Hard to debug issues

**Target State:**
- SLF4J with Logback configured
- Structured logging at appropriate levels
- Request/response logging in controller

**Action Items:**
1. Add Logback configuration
2. Add logging to all service methods
3. Log incoming requests with method, path, status
4. Log errors with full context

**Success Criteria:**
- All major operations logged
- Log levels used appropriately (INFO, DEBUG, ERROR)
- Logs are readable and useful

**Why this matters:**
- Required for Sprint 2 deliverables
- Essential for monitoring application health
- Helps debug issues quickly

---

### Improvement #3: Add Integration Tests
**Category:** Testing Quality

**Current State:**
- Only unit tests with mocks
- No full stack testing

**Target State:**
- At least 2-3 integration tests using @SpringBootTest
- Test full request → response flow
- Test actual HTTP calls without mocking

**Action Items:**
1. Create `TaskIntegrationTest` class
2. Use TestRestTemplate or WebTestClient
3. Test at least: create task, get all tasks, task not found
4. Run tests with in-memory repository

**Success Criteria:**
- Integration tests pass
- Cover critical user flows
- Complement existing unit tests

**Why this matters:**
- Catches integration issues unit tests miss
- Validates full stack works together
- Industry best practice

---

## Additional Observations

### Learning Moments 📚
1. **Spring Boot is powerful:** Autoconfiguration saved a lot of setup time
2. **Lombok is helpful:** Reduced boilerplate significantly
3. **Git workflow matters:** Small commits made development clearer
4. **Tests give confidence:** Made refactoring safe

### Technical Debt 💳
- In-memory storage will need database migration eventually
- No input sanitization (SQL injection not relevant, but XSS could be)
- No API versioning strategy (okay for now, but think about for future)
- No rate limiting or security

### Process Observations 🔄
- Solo development means no code review (mitigated by testing)
- Definition of Done was helpful checklist
- User stories with clear acceptance criteria worked well
- Story point estimates were accurate (7 points was achievable)

---

## Team Mood
**Sprint 1 Mood:** 😊 Satisfied

**Why:**
- Completed all committed work
- High code quality
- Good learning experience
- Clear path forward

**Areas of Concern:**
- Need to validate CI/CD actually works
- Want to see monitoring in action

---

## Action Items Summary

| # | Action | Owner | Priority | Target Sprint |
|---|--------|-------|----------|---------------|
| 1 | Set up GitHub repository and run CI/CD pipeline | Developer | HIGH | Sprint 2 |
| 2 | Implement logging with SLF4J/Logback | Developer | HIGH | Sprint 2 |
| 3 | Add 2-3 integration tests | Developer | MEDIUM | Sprint 2 |
| 4 | Improve error messages in controllers | Developer | MEDIUM | Sprint 2 |
| 5 | Add health monitoring endpoint | Developer | HIGH | Sprint 2 |

---

## Sprint 2 Commitment

Based on this retrospective, we commit to:

1. ✅ **Apply feedback:** Implement the 3 major improvements identified
2. ✅ **Complete planned stories:** US-003, US-004, US-005 (7 points)
3. ✅ **Add monitoring:** Logging + health endpoint
4. ✅ **Validate CI/CD:** Actually run pipeline and verify it works

**Expected Sprint 2 Capacity:** 7-8 story points (same as Sprint 1)

---

## Key Takeaways

### What we'll START doing:
1. ✅ Pushing to GitHub early and often
2. ✅ Adding logging as we write code
3. ✅ Writing integration tests alongside unit tests

### What we'll STOP doing:
1. ❌ Delaying CI/CD validation
2. ❌ Using generic error messages
3. ❌ Treating monitoring as optional

### What we'll CONTINUE doing:
1. ✅ Small, incremental commits
2. ✅ Comprehensive testing
3. ✅ Following Definition of Done strictly
4. ✅ Clear documentation

---

## Reflection

**Most Valuable Lesson:**
Having a working CI/CD pipeline on paper is not the same as having one that actually runs. Need to validate automation early.

**Biggest Success:**
Clean architecture and comprehensive testing. These will make Sprint 2 much easier.

**Looking Forward:**
Excited to add monitoring and see the application become more production-ready with logging and health checks.

---

*Retrospective Conducted: [Date]*  
*Next Retrospective: End of Sprint 2*  
*Prepared by: [Your Name]*
