package flc.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {

    @Test
    void testCreateBookingSuccess() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        Booking booking = manager.createBooking(m, lesson);

        assertNotNull(booking);
        assertEquals(BookingStatus.BOOKED, booking.getStatus());
    }

    @Test
    void testDuplicateBookingFails() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        manager.createBooking(m, lesson);
        Booking second = manager.createBooking(m, lesson);

        assertNull(second);
    }

    @Test
    void testLessonCapacityLimit() {
        BookingManager manager = new BookingManager();

        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        manager.createBooking(new Member(1, "A"), lesson);
        manager.createBooking(new Member(2, "B"), lesson);
        manager.createBooking(new Member(3, "C"), lesson);
        manager.createBooking(new Member(4, "D"), lesson);

        Booking fifth = manager.createBooking(new Member(5, "E"), lesson);

        assertNull(fifth);
    }

    @Test
    void testCancelBooking() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        Booking b = manager.createBooking(m, lesson);

        manager.cancelBooking(b.getBookingId());

        assertEquals(BookingStatus.CANCELLED, b.getStatus());
    }

    @Test
    void testAttendLesson() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        Booking b = manager.createBooking(m, lesson);

        // simulate attendance (avoid Scanner)
        b.attendLesson();
        b.addReview("Good", 4);

        assertEquals(BookingStatus.ATTENDED, b.getStatus());
        assertNotNull(b.getReview());
    }
}