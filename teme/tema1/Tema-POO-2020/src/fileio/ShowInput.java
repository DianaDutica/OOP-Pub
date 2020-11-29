package fileio;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    // retin media unui video - daca este movie, media tuturor notelor,
    // daca este show, media mediilor sezoanelor
    private Double mediumgrade;

    private Integer totalviews;

    private Integer totalfavorites;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.mediumgrade = 0.0;
        this.totalviews = 0;
        this.totalfavorites = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     *
     * @return
     */
    public Double getMediumgrade() {
        return mediumgrade;
    }

    /**
     *
     * @param mediumgrade
     */
    public void setMediumgrade(final Double mediumgrade) {
        this.mediumgrade = mediumgrade;
    }

    /**
     *
     * @return
     */
    public Integer getTotalviews() {
        return totalviews;
    }

    /**
     *
     * @param totalviews
     */
    public void setTotalviews(final Integer totalviews) {
        this.totalviews = totalviews;
    }

    /**
     *
     * @return
     */
    public Integer getTotalfavorites() {
        return totalfavorites;
    }

    /**
     *
     * @param totalfavorites
     */
    public void setTotalfavorites(final Integer totalfavorites) {
        this.totalfavorites = totalfavorites;
    }

}
