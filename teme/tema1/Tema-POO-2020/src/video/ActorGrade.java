package video;

import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.List;

/**
 *
 */
public class ActorGrade {
    /**
     *
     * @param movieData
     * @param serialData
     * @param actorData
     */
    public static void actorGrade(final List<MovieInputData> movieData,
                                  final List<SerialInputData> serialData,
                                  final List<ActorInputData> actorData) {
        for (ActorInputData actor : actorData) {
            Double sum = 0.0;
            int numberofratings = 0;  // retin numarul filmelor care au primit un rating
            for (String film : actor.getFilmography()) {
                for (MovieInputData movie : movieData) {
                    if (movie.getTitle().equals(film) && movie.getMediumgrade() > 0) {
                        sum = sum + movie.getMediumgrade();
                        numberofratings++;
                    }
                }

                for (SerialInputData serial : serialData) {
                    if (serial.getTitle().equals(film) && serial.getMediumgrade() > 0) {
                        sum = sum + serial.getMediumgrade();
                        numberofratings++;
                    }
                }
            }
            if (numberofratings > 0) {
                actor.setGrade(sum / numberofratings);
            }
        }
    }
}
