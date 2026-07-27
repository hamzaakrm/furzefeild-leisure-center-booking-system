# Furzefield Leisure Centre Booking System

> University coursework project — MSc Software Engineering, University of Hertfordshire.

A console-based Java application for managing group exercise class bookings at a leisure centre across an 8-week season. Members can browse a generated timetable, book lessons, change or cancel bookings, and leave a rating and review after attending.

## Features

- Timetable generation across 8 weekends (Saturday/Sunday), 3 time slots per day, 4 exercise types
- Book, change, and cancel bookings with capacity and duplicate-booking checks
- Attend a lesson and leave a rating (1–5) and review comment
- Input validation — invalid menu choices or malformed input no longer crash the program
- Unit tested with JUnit 5 (10 tests covering booking creation, duplicates, capacity limits, cancellation, attendance, and rating validation)

## Tech stack

Java 17 · Maven · JUnit 5

## Getting started

**Requirements:** JDK 17+ and Maven

```bash
git clone https://github.com/hamzaakrm/furzefeild-leisure-center-booking-system.git
cd furzefeild-leisure-center-booking-system
mvn test        # run the unit tests
mvn package      # build a runnable jar
java -jar target/furzefield-booking-system-1.0.0.jar
```

## Project structure

```
src/flc/system/    Application source (model classes, BookingManager, Main)
test/flc/system/   JUnit tests
```
