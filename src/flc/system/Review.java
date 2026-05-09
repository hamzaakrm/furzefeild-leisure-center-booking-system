package flc.system;

public class Review {

    private String comment;
    private int rating; // 1–5

    public Review(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + ", Comment: " + comment;
    }
}