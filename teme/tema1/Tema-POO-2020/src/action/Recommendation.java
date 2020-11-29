package action;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.List;

import static action.RecommendationAll.executeStandard;
import static action.RecommendationAll.executeBestUnseen;
import static action.RecommendationPremium.executePopular;
import static action.RecommendationPremium.executeFavorite;
import static action.RecommendationPremium.executeSearch;

public class Recommendation {
    /**
     * Execute recommendation methods
     * @param action data
     * @param userData users list
     * @param movieData movies list
     * @param serialData serials list
     * @return JSONObject
     */
    public static JSONObject executeRecommendation(final ActionInputData action,
                                                   final List<UserInputData> userData,
                                                   final List<MovieInputData> movieData,
                                                   final List<SerialInputData> serialData) {
        if (action.getType().equals("standard")) {
            return executeStandard(action, userData, movieData, serialData);
        }
        if (action.getType().equals("best_unseen")) {
            return executeBestUnseen(action, userData, movieData, serialData);
        }
        if (action.getType().equals("popular")) {
            return executePopular(action, userData, movieData, serialData);
        }
        if (action.getType().equals("favorite")) {
            return executeFavorite(action, userData, movieData, serialData);
        }
        if (action.getType().equals("search")) {
            return executeSearch(action, userData, movieData, serialData);
        }
        return null;
    }
}
