MediCare Hospital Patient Admission System

A console-based, menu-driven Java application built for MediCare Hospital to
replace their paper-based patient admission process. Developed as part of a
BCAD (Bachelor of Computer and Information Science in Application Development)
assignment covering arrays, enums, inheritance, exception handling and unit
testing.

Features

	•	Patient Management — register, search, update, delete and display patients
	•	Bed Management — a single ward with 20 beds (4x5 layout), allocate and
release beds for Inpatients only
	•	Reports — full patient list, available/occupied beds, ward occupancy
percentage
	•	Patient Categories — Inpatient, Outpatient and Emergency, represented
using a PatientCategory enum
	•	Inheritance — Inpatient extends Patient, adding ward and bed details
and overriding displayDetails()
	•	Exception Handling — custom exceptions prevent duplicate Patient IDs,
searching for non-existent patients, and allocating unavailable beds
	•	Unit Testing — JUnit tests covering CRUD operations, bed allocation
rules, duplicate/occupied-bed prevention, and sorting

Project Structure

src/hospitalsystem/ contains: Patient.java, Inpatient.java, PatientCategory.java, Ward.java, HospitalManagementSystem.java, Main.java, DuplicatePatientIDException.java, PatientNotFoundException.java, BedUnavailableException.java

test/hospitalsystem/ contains: HospitalManagementSystemTest.java

How to Run

	1.	Open the project in Apache NetBeans.
	2.	Right-click Main.java and select Run File.
	3.	Follow the on-screen menu to register patients, allocate beds, and view reports.

Running the Tests

	1.	Ensure the JUnit library is added to the project (Test Libraries).
	2.	Right-click HospitalManagementSystemTest.java and select Test File.
	3.	All 14 tests should pass.

Author

Manqoba Hlatshwayo — BCAD, IIE Emeris, Durban North Campus
