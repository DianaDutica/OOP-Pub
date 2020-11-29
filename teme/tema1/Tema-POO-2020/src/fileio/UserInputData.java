package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private ArrayList<String> favoriteMovies;

    private ArrayList<String> givenMovieGrades;  // retin filmele la care a acordat nota

    // retin sezoanele la care a acordat nota
    private Map<String, ArrayList<Integer>> givenSeasonGrades;

    private int totalReviews;

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.givenMovieGrades = new ArrayList<String>();
        this.givenSeasonGrades = new HashMap<>();
        this.totalReviews = 0;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public ArrayList<String> getGivenMovieGrades() {
        return givenMovieGrades;
    }

    /**
     *
     * @param movie
     */
    public void addGivenMovieGrades(final String movie) {
        this.givenMovieGrades.add(movie);
    }

    public Map<String, ArrayList<Integer>> getGivenSeasonGrades() {
        return givenSeasonGrades;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(final int totalReviews) {
        this.totalReviews = totalReviews;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
