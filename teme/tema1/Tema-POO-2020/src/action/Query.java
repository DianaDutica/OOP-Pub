package action;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.List;

import static action.QueryActor.executeQueryActor;
import static action.QueryUser.executeQueryUser;
import static action.QueryVideo.executeQueryVideo;

public class Query {
    /**
     * Execute QueryCommands
     * @param query
     * @param userData
     * @param movieData
     * @param serialData
     * @param actorData
     * @return
     */
    public static JSONObject executeQuery(final ActionInputData query,
                                          final List<UserInputData> userData,
                                          final List<MovieInputData> movieData,
                                          final List<SerialInputData> serialData,
                                          final List<ActorInputData> actorData) {
        if (query.getObjectType().equals("actors")) {
            return executeQueryActor(query, movieData, serialData, actorData);
        }
        if (query.getObjectType().equals("movies") || query.getObjectType().equals("shows")) {
            return executeQueryVideo(query, movieData, serialData);
        }
        if (query.getObjectType().equals("users")) {
            return executeQueryUser(query, userData);
        }
        return null;
    }
}
