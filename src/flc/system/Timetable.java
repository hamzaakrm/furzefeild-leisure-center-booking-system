package flc.system;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private List<Lesson> lessons;

    public Timetable() {
        lessons = new ArrayList<>();
        generateLessons();
    }

    private void generateLessons() {

        String[] exercises = {"Yoga", "Zumba", "BoxFit", "BodyBlitz"};
        double[] prices = {10, 12, 15, 14};

        int lessonId = 1;

        // 8 weekends
        for (int week = 1; week <= 8; week++) {

            for (Day day : Day.values()) {

                for (TimeSlot slot : TimeSlot.values()) {

                    int index = (lessonId - 1) % exercises.length;

                    Lesson lesson = new Lesson(
                            lessonId++,
                            exercises[index],
                            day,
                            slot,
                            prices[index]
                    );

                    lessons.add(lesson);
                }
            }
        }
    }

    public List<Lesson> getAllLessons() {
        return lessons;
    }

    // Search by day
    public List<Lesson> getLessonsByDay(Day day) {
        List<Lesson> result = new ArrayList<>();

        for (Lesson l : lessons) {
            if (l.getDay() == day) {
                result.add(l);
            }
        }
        return result;
    }

    // Search by exercise
    public List<Lesson> getLessonsByExercise(String exercise) {
        List<Lesson> result = new ArrayList<>();

        for (Lesson l : lessons) {
            if (l.getExerciseType().equalsIgnoreCase(exercise)) {
                result.add(l);
            }
        }
        return result;
    }

    public Lesson getLessonById(int id) {
        for (Lesson l : lessons) {
            if (l.getLessonId() == id) {
                return l;
            }
        }
        return null;
    }
}