package flc.system;

public class Booking {

    private int bookingId;
    private Member member;
    private Lesson lesson;
    private BookingStatus status;
    private Review review;

    public Booking(int bookingId, Member member, Lesson lesson) {
        this.bookingId = bookingId;
        this.member = member;
        this.lesson = lesson;
        this.status = BookingStatus.BOOKED;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Member getMember() {
        return member;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Review getReview() {
        return review;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void changeLesson(Lesson newLesson) {
        this.lesson = newLesson;
        this.status = BookingStatus.CHANGED;
    }

    public void cancelBooking() {
        this.status = BookingStatus.CANCELLED;
    }

    public void attendLesson() {
        this.status = BookingStatus.ATTENDED;
    }

    public void addReview(String comment, int rating) {
        this.review = new Review(comment, rating);
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Member: " + member.getName() +
                ", Lesson: " + lesson.getExerciseType() +
                ", Status: " + status +
                (review != null ? ", " + review : "");
    }
}