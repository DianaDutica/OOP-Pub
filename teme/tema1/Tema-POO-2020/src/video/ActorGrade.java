package video;

import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.List;

public class Actor_grade {
    public static void actor_grade(List<MovieInputData> movieData, List<SerialInputData> serialData, List<ActorInputData> actorData) {
        for(ActorInputData actor : actorData) {
            Double sum = 0.0;
            int number_of_ratings = 0;  // retin numarul filmelor care au primit un rating
            for(String film : actor.getFilmography()) {
                for (MovieInputData movie : movieData) {
                    if (movie.getTitle().equals(film) && movie.getMediumgrade() > 0) {
                        sum = sum + movie.getMediumgrade();
                        number_of_ratings++;
                    }
                }

                for (SerialInputData serial : serialData) {
                    if (serial.getTitle().equals(film) && serial.getMediumgrade() > 0) {
                        sum = sum + serial.getMediumgrade();
                        number_of_ratings++;
                    }
                }
            }
            if(number_of_ratings > 0) {
                actor.setGrade(sum / number_of_ratings);
            }
        }
    }
}
