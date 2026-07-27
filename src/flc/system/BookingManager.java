package flc.system;

import java.util.ArrayList;
import java.util.List;

public class BookingManager {

    private List<Booking> bookings;
    private int bookingCounter;

    public BookingManager() {
        bookings = new ArrayList<>();
        bookingCounter = 1;
    }

    // CREATE BOOKING
    public Booking createBooking(Member member, Lesson lesson) {

        // duplicate check
        for (Booking b : bookings) {
            if (b.getMember().getMemberId() == member.getMemberId()
                    && b.getLesson().getLessonId() == lesson.getLessonId()
                    && b.getStatus() != BookingStatus.CANCELLED) {

                System.out.println("Duplicate booking not allowed!");
                return null;
            }
        }

        // capacity check
        if (!lesson.addMember(member)) {
            System.out.println("Lesson is full!");
            return null;
        }

        Booking booking = new Booking(bookingCounter++, member, lesson);
        bookings.add(booking);

        return booking;
    }

    // CANCEL BOOKING
    public void cancelBooking(int bookingId) {

        Booking b = findBooking(bookingId);

        if (b != null) {
            if (b.getStatus() == BookingStatus.CANCELLED) {
                System.out.println("Booking already cancelled.");
                return;
            }

            b.getLesson().removeMember(b.getMember());
            b.cancelBooking();

            System.out.println("Booking cancelled.");
        } else {
            System.out.println("Booking not found.");
        }
    }

    // CHANGE BOOKING
    public void changeBooking(int bookingId, Lesson newLesson) {

        Booking b = findBooking(bookingId);

        if (b != null) {

            // check same lesson
            if (b.getLesson().getLessonId() == newLesson.getLessonId()) {
                System.out.println("Already booked in this lesson.");
                return;
            }

            // capacity check
            if (!newLesson.addMember(b.getMember())) {
                System.out.println("New lesson is full!");
                return;
            }

            // remove from old lesson
            b.getLesson().removeMember(b.getMember());

            b.changeLesson(newLesson);

            System.out.println("Booking changed.");
        } else {
            System.out.println("Booking not found.");
        }
    }

    // ATTEND + REVIEW
    // Takes the rating/comment as parameters instead of reading them itself,
    // so this class stays pure business logic with no console I/O, and is
    // straightforward to unit test.
    public boolean attendLesson(int bookingId, int rating, String comment) {

        Booking b = findBooking(bookingId);

        if (b == null) {
            System.out.println("Booking not found.");
            return false;
        }

        if (b.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("Cannot attend a cancelled booking.");
            return false;
        }

        if (b.getStatus() == BookingStatus.ATTENDED) {
            System.out.println("Already attended.");
            return false;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return false;
        }

        b.attendLesson();
        b.addReview(comment, rating);

        System.out.println("Lesson attended and reviewed.");
        return true;
    }

    // HELPER METHOD (important for clean design)
    private Booking findBooking(int bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) {
                return b;
            }
        }
        return null;
    }

    // GET BOOKINGS
    public List<Booking> getBookings() {
        return bookings;
    }

    // SHOW BOOKINGS
    public void showBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}
