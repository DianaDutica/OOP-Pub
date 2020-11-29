package action;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static video.MovieGrade.movieGrade;
import static video.SerialGrade.serialGrade;

public class Recommendation_all {
    /**
     *
     * @param action
     * @param userData
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executeStandard(final ActionInputData action,
                                             final List<UserInputData> userData,
                                             final List<MovieInputData> movieData,
                                             final List<SerialInputData> serialData) {
        JSONObject jsonObject  = new JSONObject();

        jsonObject.put("id", action.getActionId());

        for (UserInputData user : userData) {
            if (action.getUsername().equals(user.getUsername())) {
                for (MovieInputData movie : movieData) {
                    if (!user.getHistory().containsKey(movie.getTitle())) {
                        jsonObject.put("message", "StandardRecommendation result: "
                                                                            + movie.getTitle());
                        return jsonObject;
                    }
                }
            }
        }

        for (UserInputData user : userData) {
            if (action.getUsername().equals(user.getUsername())) {
                for (SerialInputData serial : serialData) {
                    if (!user.getHistory().containsKey(serial.getTitle())) {
                        jsonObject.put("message", "StandardRecommendation result: "
                                                                            + serial.getTitle());
                        return jsonObject;
                    }
                }
            }
        }

        jsonObject.put("message", "StandardRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     *
     * @param action
     * @param userData
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executeBestUnseen(final ActionInputData action,
                                                final List<UserInputData> userData,
                                                final List<MovieInputData> movieData,
                                                final List<SerialInputData> serialData) {
        JSONObject jsonObject  = new JSONObject();

        jsonObject.put("id", action.getActionId());

        List<MovieInputData> copyMovieData;

        movieGrade(movieData); // reinitializez campul mediumgrade al filmelor
        copyMovieData = movieData.stream().sorted(Comparator.comparingDouble(
                MovieInputData::getMediumgrade).reversed()).collect(Collectors.toList());

        for (UserInputData user : userData) {
            if (action.getUsername().equals(user.getUsername())) {
                for (MovieInputData movie : copyMovieData) {
                    if (!user.getHistory().containsKey(movie.getTitle())) {
                        jsonObject.put("message", "BestRatedUnseenRecommendation result: "
                                                                            + movie.getTitle());
                        return jsonObject;
                    }
                }
            }
        }

        List<SerialInputData> copySerialData;

        serialGrade(serialData);
        copySerialData = serialData.stream().sorted(Comparator.comparingDouble(
                SerialInputData::getMediumgrade).reversed()).collect(Collectors.toList());

        for (UserInputData user : userData) {
            if (action.getUsername().equals(user.getUsername())) {
                for (SerialInputData serial : copySerialData) {
                    if (user.getHistory().containsKey(serial.getTitle()) == false) {
                        jsonObject.put("message", "BestRatedUnseenRecommendation result: "
                                                                            + serial.getTitle());
                        return jsonObject;
                    }
                }
            }
        }
        jsonObject.put("message", "BestRatedUnseenRecommendation cannot be applied!");
        return jsonObject;
    }
}
