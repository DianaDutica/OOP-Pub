package user;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User_views {
    public static void initial_views(List<UserInputData> userData, List<MovieInputData> movieData, List<SerialInputData> serialData) {
        for(UserInputData user : userData) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                for(MovieInputData movie : movieData) {
                    if(movie.getTitle().equals(entry.getKey())) {
                        movie.setTotalviews(movie.getTotalviews() + entry.getValue());
                    }
                }

                for(SerialInputData serial : serialData) {
                    if(serial.getTitle().equals(entry.getKey())) {
                        serial.setTotalviews(serial.getTotalviews() + entry.getValue());
                    }
                }
            }
        }
    }
}
