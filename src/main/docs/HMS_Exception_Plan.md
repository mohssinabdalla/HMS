# HMS Exception Handling Plan

This file predicts the checked exception classes each domain will likely need, based on the combined class diagram. It's organized by **domain**, not by person, so ownership stays clear even if responsibilities shift later. Everyone should read the **Foundation** section before writing their own file, since every exception below extends it.

All exceptions are **checked** (extend `Exception`, not `RuntimeException`) — callers must either `catch` them or declare `throws`, which is intentional: it forces visible, deliberate error handling throughout the app, which is good practice to point to in the report's OOP justification.

---

## 1. Foundation — owned by Admin (shared, do not duplicate)

**File: `HMSException.java`**

```java
package com.mycompany.hms.exception;

/**
 * Base class for every custom exception in the HMS system.
 * Allows broad catching (catch HMSException) when the specific type
 * doesn't matter, or specific catching when it does.
 */
public class HMSException extends Exception {
    public HMSException(String message) {
        super(message);
    }
}
```

**File: `AuthExceptions.java`**

Covers login/logout AND the GUI access-control rule: Admin has two distinct view contexts —
a general "View Users" page (browsing any user's profile) and a separate personal profile
screen (editing only their own data). An attempt to reach the wrong context for a given
role/action should throw `UnauthorizedAccessException`, not a login-related exception —
the user IS logged in, they're just not permitted into that specific screen/action.

```java
package com.mycompany.hms.exception;


-[*] public class InvalidCredentialsException extends HMSException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}


-[*] public class UnauthorizedAccessException extends HMSException {
    // Thrown when a logged-in user attempts an action or opens a GUI
    // screen their role isn't permitted to reach.
    // Examples:
    //   - Non-admin opens the "View Users" browsing page
    //   - Admin attempts to edit another Admin's profile (self-only rule)
    //   - Doctor attempts to open an Admin-only screen
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
```

---

## 2. Admin domain — owned by Admin

**Files: `UserManagementExceptions.java`, `AssetExceptions.java`, `BillingExceptions.java`, `AssignmentExceptions.java`**

```java
-[] package com.mycompany.hms.exception;

// -- UserManagementExceptions.java --

-[*] public class DuplicateUserException extends HMSException {
    public DuplicateUserException(String message) { super(message); }
}


-[*] public class UserNotFoundException extends HMSException {
    public UserNotFoundException(String message) { super(message); }
}


-[*] public class SelfActionRestrictionException extends HMSException {
    // e.g. Admin attempting to edit/delete a DIFFERENT Admin account
    public SelfActionRestrictionException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- AssetExceptions.java --

-[*] public class AssetNotFoundException extends HMSException {
    public AssetNotFoundException(String message) { super(message); }
}


-[*] public class AssetUnavailableException extends HMSException {
    // Allocating a room/ward/lab/imaging room already occupied
    public AssetUnavailableException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- BillingExceptions.java --

-[*] public class InvalidConsultationRateException extends HMSException {
    // e.g. negative or zero baseAmount
    public InvalidConsultationRateException(String message) { super(message); }
}


-[*] public class DuplicateInsuranceNetworkException extends HMSException {
    public DuplicateInsuranceNetworkException(String message) { super(message); }
}


-[*] public class InsuranceNetworkNotFoundException extends HMSException {
    public InsuranceNetworkNotFoundException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- AssignmentExceptions.java --

-[*] public class DoctorAlreadyAssignedException extends HMSException {
    // Assigning a doctor who already has an active manager assignment
    public DoctorAlreadyAssignedException(String message) { super(message); }
}


-[*] public class AssignmentNotFoundException extends HMSException {
    public AssignmentNotFoundException(String message) { super(message); }
}
```

---

## 3. Doctor domain — owned by teammate handling Doctor

**Files: `MedicalRecordExceptions.java`, `PrescriptionExceptions.java`, `ScheduleExceptions.java`**

```java
package com.mycompany.hms.exception;

// -- MedicalRecordExceptions.java --
public class RecordNotFoundException extends HMSException {
    // Missing MedicalRecord / VitalSign entry — may also be thrown
    // from Patient-side code when viewing history, so keep this generic.
    public RecordNotFoundException(String message) { super(message); }
}

public class InvalidVitalSignException extends HMSException {
    // e.g. out-of-range blood pressure / heart rate / temperature entered
    public InvalidVitalSignException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- PrescriptionExceptions.java --
public class InvalidPrescriptionException extends HMSException {
    // e.g. empty medicationList
    public InvalidPrescriptionException(String message) { super(message); }
}

public class LabRequestNotFoundException extends HMSException {
    public LabRequestNotFoundException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- ScheduleExceptions.java --
public class ScheduleConflictException extends HMSException {
    // Overlapping shift or appointment slot for a doctor
    public ScheduleConflictException(String message) { super(message); }
}
```

---

## 4. Patient domain — owned by teammate handling Patient

**Files: `AppointmentExceptions.java`, `FeedbackExceptions.java`**

```java
package com.mycompany.hms.exception;

// -- AppointmentExceptions.java --
public class BookingConflictException extends HMSException {
    // Double-booking a slot
    public BookingConflictException(String message) { super(message); }
}

public class SlotUnavailableException extends HMSException {
    public SlotUnavailableException(String message) { super(message); }
}

public class AppointmentNotFoundException extends HMSException {
    // Cancel/reschedule on a nonexistent booking
    public AppointmentNotFoundException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- FeedbackExceptions.java --
public class InvalidRatingException extends HMSException {
    // Rating outside allowed range (e.g. not 1-5)
    public InvalidRatingException(String message) { super(message); }
}

public class DuplicateFeedbackException extends HMSException {
    // OPTIONAL — only needed if you restrict one feedback per visit.
    // Confirm with teammate before implementing.
    public DuplicateFeedbackException(String message) { super(message); }
}
```

---

## 5. Medical Manager domain — owned by teammate handling Medical Manager

**Files: `DepartmentExceptions.java`, `RosterExceptions.java`, `ReportExceptions.java`**

```java
package com.mycompany.hms.exception;

// -- DepartmentExceptions.java --
public class DuplicateDepartmentException extends HMSException {
    public DuplicateDepartmentException(String message) { super(message); }
}

public class DepartmentNotFoundException extends HMSException {
    public DepartmentNotFoundException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- RosterExceptions.java --
public class ShiftConflictException extends HMSException {
    public ShiftConflictException(String message) { super(message); }
}

public class InvalidShiftTimeException extends HMSException {
    // e.g. endTime before startTime
    public InvalidShiftTimeException(String message) { super(message); }
}
```

```java
package com.mycompany.hms.exception;

// -- ReportExceptions.java --
public class ReportGenerationException extends HMSException {
    // Generic failure generating HospitalMetricsReport / RevenueSummaryReport
    public ReportGenerationException(String message) { super(message); }
}
```

---

## How to use these once written

Example, inside `Admin.deleteUser()`:

```java
public boolean deleteUser(String userId) throws UserNotFoundException {
    User target = findUserById(userId);
    if (target == null) {
        throw new UserNotFoundException("No user found with ID: " + userId);
    }
    // ... proceed with deletion
    return true;
}
```

The GUI layer (`controller/`) will need `try { ... } catch (UserNotFoundException e) { ... }` blocks
wherever these methods are called, to show the error message to the user instead of crashing.

---

## Open items / things to confirm as a team

- [ ] Should `RecordNotFoundException` live in Doctor's file or a shared file, since both Doctor
      and Patient code may throw/catch it? (Currently placed under Doctor domain — flag if this
      causes friction once Patient's code is written.)
- [ ] `DuplicateFeedbackException` — confirm whether this business rule is actually wanted.
- [ ] Package name above assumes `com.mycompany.hms.exception` — adjust to match your team's
      actual base package if different.
- [ ] Everyone: once your file is written, do a quick pass checking you haven't duplicated an
      exception another teammate's file already covers.
