package action;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static actor.NumberAwards.numberAwards;
import static video.ActorGrade.actorGrade;
import static video.MovieGrade.movieGrade;
import static video.SerialGrade.serialGrade;

public class Query_actor {
    /**
     *
     * @param query
     * @param movieData
     * @param serialData
     * @param actorData
     * @return
     */
    public static JSONObject executeQuery_actor(final ActionInputData query,
                                                final List<MovieInputData> movieData,
                                                final List<SerialInputData> serialData,
                                                final List<ActorInputData> actorData) {
        JSONObject jsonObject  = new JSONObject();

        if (query.getCriteria().equals("average")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            int maxActors = 0;

            List<ActorInputData> copyActorData = new ArrayList<>();
            List<ActorInputData> alfaActorData = new ArrayList<>();

            movieGrade(movieData); // reinitializez campul mediumgrade al filmelor
            serialGrade(serialData);   // reinitializez campul mediumgrade al serialelor
            actorGrade(movieData, serialData, actorData);

            if (query.getSortType().equals("asc")) {
                alfaActorData = actorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName)).collect(Collectors.toList());
                copyActorData = alfaActorData.stream().sorted(Comparator.comparingDouble(
                            ActorInputData::getGrade)).collect(Collectors.toList());
            } else {
                alfaActorData = actorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName).reversed()).collect(Collectors.toList());
                copyActorData = alfaActorData.stream().sorted(Comparator.comparingDouble(
                            ActorInputData::getGrade).reversed()).collect(Collectors.toList());
            }

            for (ActorInputData actor : copyActorData) {
                if (maxActors < query.getNumber() && actor.getGrade() != 0) {
                    list.add(actor.getName());
                    maxActors++;
                }
            }
            jsonObject.put("message", "Query result: " + list);
        }

        if (query.getCriteria().equals("awards")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            numberAwards(actorData);

            List<ActorInputData> duplicateActorData = new ArrayList<>(actorData);

            // parcurg lista de cuvinte care trebuie sa existe in descrierea unui actor
            final int accesDescription = 3;
            for (String award : query.getFilters().get(accesDescription)) {
                // sterg actorii care nu au premiile
                duplicateActorData.removeIf(actor -> !actor.getAwards().containsKey(
                                                            Utils.stringToAwards(award)));
            }

            List<ActorInputData> copyActorData = new ArrayList<>();
            List<ActorInputData> alfaActorData = new ArrayList<>();

            if (query.getSortType().equals("asc")) {
                alfaActorData = duplicateActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName)).collect(Collectors.toList());
                copyActorData = alfaActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getNrAwards)).collect(Collectors.toList());
            } else {
                alfaActorData = duplicateActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName).reversed()).collect(Collectors.toList());
                copyActorData = alfaActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getNrAwards).reversed()).collect(Collectors.toList());
            }

            for (ActorInputData actor : copyActorData) {
                list.add(actor.getName());
            }
            jsonObject.put("message", "Query result: " + list);
        }

        if (query.getCriteria().equals("filter_description")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            List<ActorInputData> duplicateActorData = new ArrayList<>(actorData);

            ArrayList<String> description;
            int counter;
            for (ActorInputData actor : actorData) {
                counter = 0;
                for (String word : query.getFilters().get(2)) {
                    if (Arrays.asList(actor.getCareerDescription().toLowerCase()
                                                            .split("[ -.,]")).contains(word)) {
                        counter++;
                    }
                }
                if (counter !=  query.getFilters().get(2).size()) {
                    duplicateActorData.remove(actor);
                }
            }

            List<ActorInputData> copyActorData = new ArrayList<>();

            if (query.getSortType().equals("asc")) {
                copyActorData = duplicateActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName)).collect(Collectors.toList());
            } else {
                copyActorData = duplicateActorData.stream().sorted(Comparator.comparing(
                            ActorInputData::getName).reversed()).collect(Collectors.toList());
            }

            for (ActorInputData actor : copyActorData) {
                list.add(actor.getName());
            }
            jsonObject.put("message", "Query result: " + list);
        }
        return jsonObject;
    }
}
