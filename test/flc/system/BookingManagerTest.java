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
    void testCancelUnknownBookingDoesNotThrow() {
        BookingManager manager = new BookingManager();

        // Should just print "Booking not found." and return, not throw.
        assertDoesNotThrow(() -> manager.cancelBooking(999));
    }

    @Test
    void testAttendLesson() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        Booking b = manager.createBooking(m, lesson);

        boolean result = manager.attendLesson(b.getBookingId(), 4, "Good");

        assertTrue(result);
        assertEquals(BookingStatus.ATTENDED, b.getStatus());
        assertNotNull(b.getReview());
        assertEquals(4, b.getReview().getRating());
    }

    @Test
    void testAttendLessonRejectsInvalidRating() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson lesson = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);

        Booking b = manager.createBooking(m, lesson);

        boolean result = manager.attendLesson(b.getBookingId(), 9, "Too high");

        assertFalse(result);
        assertEquals(BookingStatus.BOOKED, b.getStatus());
        assertNull(b.getReview());
    }

    @Test
    void testChangeBookingSuccess() {
        BookingManager manager = new BookingManager();

        Member m = new Member(1, "John");
        Lesson yoga = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);
        Lesson zumba = new Lesson(2, "Zumba", Day.SATURDAY, TimeSlot.AFTERNOON, 12);

        Booking b = manager.createBooking(m, yoga);
        manager.changeBooking(b.getBookingId(), zumba);

        assertEquals(BookingStatus.CHANGED, b.getStatus());
        assertEquals(zumba.getLessonId(), b.getLesson().getLessonId());
        assertEquals(4, yoga.getAvailableSlots()); // member freed up their old slot
        assertEquals(3, zumba.getAvailableSlots());
    }

    @Test
    void testChangeBookingFailsWhenNewLessonFull() {
        BookingManager manager = new BookingManager();

        Lesson yoga = new Lesson(1, "Yoga", Day.SATURDAY, TimeSlot.MORNING, 10);
        Lesson zumba = new Lesson(2, "Zumba", Day.SATURDAY, TimeSlot.AFTERNOON, 12);

        // Fill zumba to capacity with other members first.
        manager.createBooking(new Member(2, "A"), zumba);
        manager.createBooking(new Member(3, "B"), zumba);
        manager.createBooking(new Member(4, "C"), zumba);
        manager.createBooking(new Member(5, "D"), zumba);

        Member john = new Member(1, "John");
        Booking b = manager.createBooking(john, yoga);

        manager.changeBooking(b.getBookingId(), zumba);

        // Should be unchanged since zumba was full.
        assertEquals(BookingStatus.BOOKED, b.getStatus());
        assertEquals(yoga.getLessonId(), b.getLesson().getLessonId());
    }
}
