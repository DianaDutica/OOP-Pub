package user;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;
import java.util.Map;

public class User_favorites {
    public static void initial_favorites(List<UserInputData> userData, List<MovieInputData> movieData, List<SerialInputData> serialData) {
        for(UserInputData user : userData) {
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
