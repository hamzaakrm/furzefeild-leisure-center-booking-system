package flc.system;

import java.util.ArrayList;
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
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter day (SATURDAY/SUNDAY): ");
                    Day day = Day.valueOf(sc.next().toUpperCase());

                    List<Lesson> dayLessons = timetable.getLessonsByDay(day);
                    for (Lesson l : dayLessons) {
                        System.out.println(l);
                    }
                    break;

                case 2:
                    System.out.print("Enter exercise name: ");
                    String exercise = sc.next();

                    List<Lesson> exLessons = timetable.getLessonsByExercise(exercise);
                    for (Lesson l : exLessons) {
                        System.out.println(l);
                    }
                    break;

                case 3:
                    System.out.print("Enter member ID (1-10): ");
                    int mid = sc.nextInt();

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
                    int lid = sc.nextInt();

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
                    int bid = sc.nextInt();

                    System.out.print("Enter new lesson ID: ");
                    int newLid = sc.nextInt();

                    Lesson newLesson = timetable.getLessonById(newLid);

                    if (newLesson != null) {
                        manager.changeBooking(bid, newLesson);
                    } else {
                        System.out.println("Invalid lesson.");
                    }
                    break;

                case 5:
                    System.out.print("Enter booking ID: ");
                    manager.cancelBooking(sc.nextInt());
                    break;

                case 6:
                    System.out.print("Enter booking ID: ");
                    manager.attendLesson(sc.nextInt());
                    break;

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
}