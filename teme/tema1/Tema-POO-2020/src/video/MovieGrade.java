package video;

import fileio.MovieInputData;

import java.util.List;

/**
 *
 */
public class MovieGrade {
    /**
     *
     * @param movieData
     */
    public static void movieGrade(final List<MovieInputData> movieData) {
        for (MovieInputData movie : movieData) { // parcurg toate filmele
            Double sum = 0.0;
            for (Double grade : movie.getGrade()) { // adun rating-urile lor in sum
                sum = sum + grade;
            }
            if (movie.getGrade().size() > 0) {
                // setez media rating-urilor primite pana in acel moment
                movie.setMediumgrade(sum / movie.getGrade().size());
            }
        }
    }
}
