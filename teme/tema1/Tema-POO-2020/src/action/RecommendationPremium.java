package action;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static video.MovieGrade.movieGrade;
import static video.SerialGrade.serialGrade;

public class RecommendationPremium {
    /**
     * Iterez prin filme si seriale si pun in popularGenre ca si key genurile
     * si ca si rezultat al key-ului numarul de vizualizari total obtinute de filmele din acel gen.
     * Fac o sortare a key-ilor dupa rezultat, adica dupa numarul de vizualizari
     * si ii fac utilizatorului sugestia, adica filmul din cel mai popular gen pe care user-ul inca
     * nu l-a vazut
     * @param action
     * @param userData
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executePopular(final ActionInputData action,
                                            final List<UserInputData> userData,
                                            final List<MovieInputData> movieData,
                                            final List<SerialInputData> serialData) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", action.getActionId());

        Map<String, Integer> popularGenre = new HashMap<>();

        for (MovieInputData movie : movieData) {
            for (String genre : movie.getGenres()) {
                if (popularGenre.containsKey(genre)) {
                    popularGenre.put(genre, popularGenre.get(genre) + movie.getTotalviews());
                } else {
                    popularGenre.put(genre, movie.getTotalviews());
                }
            }
        }

        for (SerialInputData serial : serialData) {
            for (String genre : serial.getGenres()) {
                if (popularGenre.containsKey(genre)) {
                    popularGenre.put(genre, popularGenre.get(genre) + serial.getTotalviews());
                } else {
                    popularGenre.put(genre, serial.getTotalviews());
                }
            }
        }

        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(popularGenre.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        for (UserInputData user : userData) {    // parcurg toti utilizatorii
            // gasesc utilizatorul care face actiunea
            if (action.getUsername().equals(user.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                // parcurg genres din map ordonate dupa popularitate
                for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                    for (MovieInputData movie : movieData) {    // parcurg mai intai toate filmele
                        if (movie.getGenres().contains(entry.getKey())
                                && !user.getHistory().containsKey(movie.getTitle())) {
                            jsonObject.put("message", "PopularRecommendation result: "
                                    + movie.getTitle());
                            return jsonObject;
                        }
                    }

                    for (SerialInputData serial : serialData) { // apoi parcurg toate serialele
                        if (serial.getGenres().contains(entry.getKey())
                                && !user.getHistory().containsKey(serial.getTitle())) {
                            jsonObject.put("message", "PopularRecommendation result: "
                                    + serial.getTitle());
                            return jsonObject;
                        }
                    }
                }
            }
        }
        jsonObject.put("message", "PopularRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     * Sortez video-urile dupa campul totalfavorites si parcurg aceaasta lista
     * pana il intalnesc pe cel care nu a fost vizualizat de utilizator.
     * @param action
     * @param userData
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executeFavorite(final ActionInputData action,
                                             final List<UserInputData> userData,
                                             final List<MovieInputData> movieData,
                                             final List<SerialInputData> serialData) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", action.getActionId());

        List<ShowInput> showData = new ArrayList<>();
        List<ShowInput> copyShowData = new ArrayList<>();

        for (MovieInputData movie : movieData) {
            showData.add(movie);
        }

        for (SerialInputData serial : serialData) {
            showData.add(serial);
        }

        for (UserInputData user : userData) {   // parcurg toti utilizatorii
            for (String favorites : user.getFavoriteMovies()) {  // parcurg fiecare film favorit
                for (ShowInput video : showData) {
                    if (video.getTitle().equals(favorites)) {
                        video.setTotalfavorites(video.getTotalfavorites() + 1);
                    }
                }
            }
        }

        copyShowData = showData.stream().sorted(Comparator.comparingInt(
                ShowInput::getTotalfavorites).reversed()).collect(Collectors.toList());

        for (UserInputData user : userData) {    // parcurg toti utilizatorii
            // gasesc utilizatorul care face actiunea
            if (action.getUsername().equals(user.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                // parcurg filmele din map ordonate dupa numarul de aparitii in favorite
                for (ShowInput video : copyShowData) {
                    if (!user.getHistory().containsKey(video.getTitle())) {
                        jsonObject.put("message", "FavoriteRecommendation result: "
                                + video.getTitle());
                        return jsonObject;
                    }
                }
            }
        }
        jsonObject.put("message", "FavoriteRecommendation cannot be applied!");
        return jsonObject;
    }

    /**
     * Apelez functiile movieGrade si serialGrade pentru a avea mediumGrade-ul videourilor
     * dupa care se face sortarea lui showData(care contine atat filme, cat si seriale).
     * Inainte de a oune filmele in showData, le-am eliminat pe cele care nu respecta genul.
     * @param action
     * @param userData
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executeSearch(final ActionInputData action,
                                           final List<UserInputData> userData,
                                           final List<MovieInputData> movieData,
                                           final List<SerialInputData> serialData) {
        JSONObject jsonObject = new JSONObject();

        ArrayList<String> list = new ArrayList<>();

        jsonObject.put("id", action.getActionId());

        movieGrade(movieData); // reinitializez campul mediumgrade al filmelor
        serialGrade(serialData); // reinitializez campul mediumgrade al serialelor

        List<MovieInputData> copyMovieData = new ArrayList<>(movieData);
        copyMovieData.removeIf(movie -> !movie.getGenres().contains(action.getGenre()));

        List<SerialInputData> copySerialData = new ArrayList<>(serialData);
        copySerialData.removeIf(movie -> !movie.getGenres().contains(action.getGenre()));

        List<ShowInput> showData = new ArrayList<>();
        for (MovieInputData movie : copyMovieData) {
            showData.add(movie);
        }

        for (SerialInputData serial : copySerialData) {
            showData.add(serial);
        }

        List<ShowInput> alfaShowData;
        List<ShowInput> copyShowData;

        alfaShowData = showData.stream().sorted(Comparator.comparing(
                ShowInput::getTitle)).collect(Collectors.toList());
        copyShowData = alfaShowData.stream().sorted(Comparator.comparingDouble(
                ShowInput::getMediumgrade)).collect(Collectors.toList());

        for (UserInputData user : userData) {
            if (action.getUsername().equals(user.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                for (ShowInput video : copyShowData) {
                    if (!user.getHistory().containsKey(video.getTitle())) {
                        list.add(video.getTitle());
                    }
                }
            }
        }
        if (list.size() > 0) {
            jsonObject.put("message", "SearchRecommendation result: " + list);
        } else {
            jsonObject.put("message", "SearchRecommendation cannot be applied!");
        }
        return jsonObject;
    }
}
