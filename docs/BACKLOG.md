# Product Backlog - Task/Todo Management API

## Product Vision
**"A lightweight RESTful API that enables users to create, manage, and track their daily tasks with priorities and status tracking."**

---

## Backlog Items

### US-001: Create Task
**Priority:** HIGH  
**Story Points:** 3  
**Status:** Ready for Sprint 1

**User Story:**
- **As a** user
- **I want to** create a new task with a title and description
- **So that** I can track things I need to do

**Acceptance Criteria:**
- [ ] POST endpoint accepts title & description
- [ ] Returns 201 with created task including auto-generated ID
- [ ] Default status is "pending"
- [ ] Returns 400 if title is missing

---

### US-002: View All Tasks
**Priority:** HIGH  
**Story Points:** 2  
**Status:** Ready for Sprint 1

**User Story:**
- **As a** user
- **I want to** view a list of all my tasks
- **So that** I can see what needs to be done

**Acceptance Criteria:**
- [ ] GET endpoint returns all tasks as JSON array
- [ ] Returns 200 with empty array if no tasks exist
- [ ] Each task includes id, title, description, status

---

### US-008: View Task by ID
**Priority:** HIGH  
**Story Points:** 2  
**Status:** Ready for Sprint 1

**User Story:**
- **As a** user
- **I want to** retrieve a specific task by its ID
- **So that** I can see detailed information about one task

**Acceptance Criteria:**
- [ ] GET endpoint accepts task ID
- [ ] Returns 200 with task details if found
- [ ] Returns 404 if task doesn't exist

---

### US-005: Mark Task as Complete
**Priority:** MEDIUM  
**Story Points:** 2  
**Status:** Backlog

**User Story:**
- **As a** user
- **I want to** mark a task as complete
- **So that** I can track my progress

**Acceptance Criteria:**
- [ ] PATCH/PUT endpoint updates status to "completed"
- [ ] Returns 200 with updated task
- [ ] Returns 404 if task doesn't exist

---

### US-003: Update Task
**Priority:** MEDIUM  
**Story Points:** 3  
**Status:** Backlog

**User Story:**
- **As a** user
- **I want to** edit a task's title, description, or status
- **So that** I can keep my task list accurate

**Acceptance Criteria:**
- [ ] PUT/PATCH endpoint accepts any combination of title/description/status
- [ ] Returns 200 with updated task
- [ ] Returns 404 if task doesn't exist
- [ ] Validates input fields

---

### US-004: Delete Task
**Priority:** MEDIUM  
**Story Points:** 2  
**Status:** Backlog

**User Story:**
- **As a** user
- **I want to** delete a task
- **So that** I can remove completed or irrelevant tasks

**Acceptance Criteria:**
- [ ] DELETE endpoint removes task by ID
- [ ] Returns 204 on success
- [ ] Returns 404 if task doesn't exist

---

### US-006: Filter Tasks by Status
**Priority:** LOW  
**Story Points:** 3  
**Status:** Backlog

**User Story:**
- **As a** user
- **I want to** filter tasks by status (pending/completed)
- **So that** I can focus on what's left to do

**Acceptance Criteria:**
- [ ] GET endpoint accepts status query parameter
- [ ] Returns filtered tasks matching status
- [ ] Returns 400 for invalid status values

---

### US-007: Add Priority Levels
**Priority:** LOW  
**Story Points:** 5  
**Status:** Backlog

**User Story:**
- **As a** user
- **I want to** assign priority levels (high/medium/low) to tasks
- **So that** I can organize my work by importance

**Acceptance Criteria:**
- [ ] Extend task model to include priority field
- [ ] Update create/update endpoints to accept priority
- [ ] Add filter by priority endpoint
- [ ] Default priority is "medium"

---

## Backlog Summary

| Priority | Story Points | Count |
|----------|-------------|-------|
| HIGH     | 7           | 3     |
| MEDIUM   | 7           | 3     |
| LOW      | 8           | 2     |
| **TOTAL**| **22**      | **8** |

---

## Story Point Reference

- **1 point:** Trivial change (< 1 hour)
- **2 points:** Simple feature (2-4 hours)
- **3 points:** Moderate complexity (4-8 hours)
- **5 points:** Complex feature (1-2 days)
- **8 points:** Very complex (needs breakdown)
