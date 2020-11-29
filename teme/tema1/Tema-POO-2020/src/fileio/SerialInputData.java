package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private int timeEpisodes;

    private ArrayList<Double> seasonmediumgrade;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.timeEpisodes = seasons.get(0).getDuration() * seasons.size();
        this.seasonmediumgrade = new ArrayList<>();
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getTimeEpisodes() {
        return timeEpisodes;
    }

    public ArrayList<Double> getSeasonmediumgrade() {
        return seasonmediumgrade;
    }

    /**
     *
     * @param seasongrade
     */
    public void setSeasonmediumgrade(final Double seasongrade) {
        seasonmediumgrade.add(seasongrade);
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
