# Hospital Management System (HMS)

CT038-3-2 OODJ Coursework — Group Project

## Project Structure

```
HMS/
├── src/
│   ├── model/          # All entity classes (User, Admin, Doctor, Patient, MedicalManager, etc.)
│   ├── service/         # Supporting logic classes (AuthService, AuditLog, etc.)
│   └── controller/       # GUI controllers / Swing logic go here
├── data/                 # All .txt data files (never use a database)
└── docs/                 # UML diagrams, exported reports, etc.
```

## Who owns what (avoid stepping on each other's files)

| Class                 | Owner                | Status                                                  |
| --------------------- | -------------------- | ------------------------------------------------------- |
| `User.java`           | Shared foundation    | ✅ Done — do not change without discussing with the team |
| `AuthService.java`    | Shared foundation    | ✅ Done                                                  |
| `AuditLog.java`       | Shared foundation    | ✅ Done                                                  |
| `Admin.java`          | Admin role           | ✅ Done                                                  |
| `Doctor.java`         | Doctor role          | 🔲 Stub only — teammate to complete                     |
| `Patient.java`        | Patient role         | 🔲 Stub only — teammate to complete                     |
| `MedicalManager.java` | Medical Manager role | 🔲 Stub only — teammate to complete                     |

**Rule of thumb:** only edit your own role's file. If you need to change something in `User.java`, `AuthService.java`, or `AuditLog.java`, message the group first — everyone depends on these.

## Data file conventions (IMPORTANT — read before writing any file I/O code)

- All data files are **comma-separated**, one record per line, no header row.
- `User.toFileString()` defines the base column order: `userId,username,password,fullName,email,phoneNumber,role`
- If your role class has extra fields (e.g. Doctor's `specialty`), **override `toFileString()`** in your class, call `super.toFileString()`, then append your own fields with a comma. Keep this consistent so the loader can always read `role` in the same column position, and role-specific fields always come after.
- `auditlog.txt` is **append-only** — never rewrite or delete lines in this file directly. This keeps Git merge conflicts to a minimum since everyone's new entries just get added to the end.
- Every other file (users.txt, assets.txt, etc.) may need full rewrites when updating/deleting a record (read all lines, modify, write all lines back) — coordinate on file locking/timing if this becomes an issue with the GUI running continuously.

## ID conventions

- Use a consistent prefix per entity so IDs never collide across roles, e.g.:
  - Admin: `A001`, `A002`...
  - Doctor: `D001`, `D002`...
  - Patient: `P001`, `P002`...
  - MedicalManager: `M001`, `M002`...
  - Assets: `AS001`...
  - Assignments: `AG001`...

## Git workflow (suggested, to avoid merge conflicts)

1. Never commit directly to `main`. Create a branch per feature: `git checkout -b feature/doctor-class`
2. Push your branch, open a Pull Request, and have at least one teammate review before merging.
3. Pull `main` frequently (`git pull origin main`) before starting new work, especially since everyone extends the same `User` class.
4. If you must touch a shared file (`User.java`, `AuthService.java`, `AuditLog.java`), flag it in your team chat first.

## Class Diagram & Use Case Diagram

See `/docs` for the current diagrams. Admin's diagrams are finalized; other roles' diagrams are in progress.

## AI Usage Note

Per the assignment's Yellow (Restricted) AI usage policy — all AI-assisted output in this codebase must be understood, adapted, and significantly modified by the student who owns it, and declared in each member's AI Prompt Log per the assignment's Appendix template.
