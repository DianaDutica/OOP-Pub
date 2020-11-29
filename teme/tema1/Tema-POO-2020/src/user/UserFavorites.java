package user;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;

/**
 *
 */
public class UserFavorites {
    /**
     *
     * @param userData
     * @param movieData
     * @param serialData
     */
    public static void initialFavorites(final List<UserInputData> userData,
                                         final List<MovieInputData> movieData,
                                         final List<SerialInputData> serialData) {
        for (UserInputData user : userData) {
            for (String favorite : user.getFavoriteMovies()) {
                for (MovieInputData movie : movieData) {
                    if (movie.getTitle().equals(favorite)) {
                        movie.setTotalfavorites(movie.getTotalfavorites() + 1);
                    }
                }

                for (SerialInputData serial : serialData) {
                    if (serial.getTitle().equals(favorite)) {
                        serial.setTotalfavorites(serial.getTotalfavorites() + 1);
                    }
                }
            }
        }
    }
}
