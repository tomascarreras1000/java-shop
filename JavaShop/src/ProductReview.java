public class ProductReview {
    private final String text;
    private final int stars;
    public String getText() {return text;}
    public int getStars() {return stars;}
    public String getReviewAsText() {return stars + "*: \"" + text + "\"";}
    ProductReview(String text, int stars) {this.text = text; this.stars = stars;}
}
