package flc.system;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private int lessonId;
    private String exerciseType;
    private Day day;
    private TimeSlot timeSlot;
    private double price;
    private List<Member> members;

    private final int MAX_CAPACITY = 4;

    public Lesson(int lessonId, String exerciseType, Day day, TimeSlot timeSlot, double price) {
        this.lessonId = lessonId;
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.price = price;
        this.members = new ArrayList<>();
    }

    public boolean addMember(Member member) {
        if (members.size() >= MAX_CAPACITY) {
            return false;
        }
        return members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public int getAvailableSlots() {
        return MAX_CAPACITY - members.size();
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public Day getDay() {
        return day;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public double getPrice() {
        return price;
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "Lesson ID: " + lessonId +
                ", Exercise: " + exerciseType +
                ", Day: " + day +
                ", Time: " + timeSlot +
                ", Price: £" + price +
                ", Available Slots: " + getAvailableSlots();
    }
}