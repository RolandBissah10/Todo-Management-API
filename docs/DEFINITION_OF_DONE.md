# Definition of Done (DoD)

## Purpose
This document defines the criteria that must be met for any user story to be considered "Done" and ready for delivery.

---

## DoD Checklist

A user story is considered **DONE** when ALL of the following criteria are met:

### ✅ Code Quality
- [ ] **Code Complete:** Feature is fully implemented according to acceptance criteria
- [ ] **Code Standards:** Code follows project coding standards and conventions
- [ ] **Linting Passes:** No linting errors (all code passes linter checks)
- [ ] **No Commented Code:** Dead code and unnecessary comments removed

### ✅ Testing
- [ ] **Unit Tests Written:** Feature has unit tests covering main functionality
- [ ] **Tests Passing:** All tests (new and existing) pass locally
- [ ] **Code Coverage:** Test coverage is >80% for new code
- [ ] **Edge Cases Tested:** Error conditions and edge cases are tested

### ✅ Integration & Deployment
- [ ] **CI/CD Passes:** All pipeline checks pass (build, test, lint)
- [ ] **Merged to Main:** Code is merged to the main branch
- [ ] **No Breaking Changes:** Existing functionality still works
- [ ] **Deployed Successfully:** Changes are live in the target environment

### ✅ Documentation
- [ ] **API Documented:** Endpoint documented with request/response examples
- [ ] **README Updated:** Any setup or usage changes reflected in README
- [ ] **Comments Added:** Complex logic has explanatory comments

### ✅ Review & Validation
- [ ] **Code Review:** Code has been reviewed (self-review minimum for solo projects)
- [ ] **Acceptance Criteria Met:** All acceptance criteria from user story are satisfied
- [ ] **Manual Testing:** Feature manually tested and works as expected

---

## Notes

- **For Solo Projects:** Self-review is acceptable, but should be thorough
- **Exceptions:** Any deviation from DoD must be documented and justified
- **Continuous Improvement:** This DoD may be updated based on retrospective findings

---

## Definition of Ready (DoR)

For a user story to be pulled into a sprint, it should be:
- [ ] Well-defined with clear acceptance criteria
- [ ] Estimated (story points assigned)
- [ ] Prioritized in backlog
- [ ] Dependencies identified
- [ ] Small enough to complete in one sprint

---

*Last Updated: Sprint 0*
