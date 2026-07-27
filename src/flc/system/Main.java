package flc.system;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingManager manager = new BookingManager();
        Timetable timetable = new Timetable();

        // 10 members (diverse names)
        List<Member> members = new ArrayList<>();
        members.add(new Member(1, "John"));
        members.add(new Member(2, "Emma"));
        members.add(new Member(3, "Liam"));
        members.add(new Member(4, "Sophia"));
        members.add(new Member(5, "Noah"));
        members.add(new Member(6, "Olivia"));
        members.add(new Member(7, "James"));
        members.add(new Member(8, "Isabella"));
        members.add(new Member(9, "Lucas"));
        members.add(new Member(10, "Mia"));

        while (true) {

            System.out.println("\n===== FLC Booking System =====");
            System.out.println("1. View Timetable (by Day)");
            System.out.println("2. View Timetable (by Exercise)");
            System.out.println("3. Book Lesson");
            System.out.println("4. Change Booking");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Attend Lesson");
            System.out.println("7. Show All Bookings");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            Integer choice = readInt(sc);

            if (choice == null) {
                System.out.println("Please enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter day (SATURDAY/SUNDAY): ");
                    Day day = readDay(sc);

                    if (day == null) {
                        System.out.println("Invalid day. Please enter SATURDAY or SUNDAY.");
                        break;
                    }

                    List<Lesson> dayLessons = timetable.getLessonsByDay(day);
                    for (Lesson l : dayLessons) {
                        System.out.println(l);
                    }
                    break;

                case 2:
                    System.out.print("Enter exercise name: ");
                    String exercise = sc.next();

                    List<Lesson> exLessons = timetable.getLessonsByExercise(exercise);
                    if (exLessons.isEmpty()) {
                        System.out.println("No lessons found for that exercise.");
                    }
                    for (Lesson l : exLessons) {
                        System.out.println(l);
                    }
                    break;

                case 3:
                    System.out.print("Enter member ID (1-10): ");
                    Integer mid = readInt(sc);

                    if (mid == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    Member member = null;

                    for (Member m : members) {
                        if (m.getMemberId() == mid) {
                            member = m;
                            break;
                        }
                    }

                    if (member == null) {
                        System.out.println("Invalid member ID.");
                        break;
                    }

                    System.out.print("Enter lesson ID: ");
                    Integer lid = readInt(sc);

                    if (lid == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    Lesson lesson = timetable.getLessonById(lid);

                    if (lesson != null) {
                        Booking b = manager.createBooking(member, lesson);
                        if (b != null) {
                            System.out.println("Booking successful!");
                        }
                    } else {
                        System.out.println("Invalid lesson ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter booking ID: ");
                    Integer bid = readInt(sc);

                    if (bid == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    System.out.print("Enter new lesson ID: ");
                    Integer newLid = readInt(sc);

                    if (newLid == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    Lesson newLesson = timetable.getLessonById(newLid);

                    if (newLesson != null) {
                        manager.changeBooking(bid, newLesson);
                    } else {
                        System.out.println("Invalid lesson.");
                    }
                    break;

                case 5: {
                    System.out.print("Enter booking ID: ");
                    Integer cancelId = readInt(sc);

                    if (cancelId == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    manager.cancelBooking(cancelId);
                    break;
                }

                case 6: {
                    System.out.print("Enter booking ID: ");
                    Integer attendId = readInt(sc);

                    if (attendId == null) {
                        System.out.println("Please enter a number.");
                        break;
                    }

                    System.out.print("Enter rating (1-5): ");
                    Integer rating = readInt(sc);

                    if (rating == null || rating < 1 || rating > 5) {
                        System.out.println("Rating must be a number between 1 and 5.");
                        break;
                    }

                    System.out.print("Enter review comment: ");
                    String comment = sc.nextLine();

                    manager.attendLesson(attendId, rating, comment);
                    break;
                }

                case 7:
                    manager.showBookings();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Reads an integer from the scanner, returning null (instead of crashing)
     * if the user enters something that isn't a whole number.
     */
    private static Integer readInt(Scanner sc) {
        try {
            int value = sc.nextInt();
            sc.nextLine(); // consume the rest of the line
            return value;
        } catch (InputMismatchException e) {
            sc.nextLine(); // discard the invalid input so we don't loop forever
            return null;
        }
    }

    /**
     * Reads a Day from the scanner, returning null (instead of crashing)
     * if the text doesn't match SATURDAY or SUNDAY.
     */
    private static Day readDay(Scanner sc) {
        try {
            return Day.valueOf(sc.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
