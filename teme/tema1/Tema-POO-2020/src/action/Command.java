package action;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Command {
    /**
     * Execute Commands
     * @param action data about action
     * @param userData list of users
     * @param movieData list of movies
     * @param serialData list of serials
     * @return JSONObject
     */
    public static JSONObject executeCommand(final ActionInputData action,
                                            final List<UserInputData> userData,
                                            final List<MovieInputData> movieData,
                                            final List<SerialInputData> serialData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", action.getActionId());

        if (action.getType().equals("favorite")) {
            // parcurg lista utilizatorilor
            for (UserInputData user : userData) {
                // gasesc utilizatorul meu
                if (action.getUsername().equals(user.getUsername())) {
                    // verific daca filmul deja exista
                    if (user.getFavoriteMovies().contains(action.getTitle())) {
                        //verific daca utilizatorul a vazut filmul
                        jsonObject.put("message", "error -> " + action.getTitle()
                                + " is already in favourite list");
                    } else {
                        // verific daca filmul a fost macar vazut
                        if (user.getHistory().containsKey(action.getTitle())) {
                            //adaug la favorite acel video
                            user.getFavoriteMovies().add(action.getTitle());
                            jsonObject.put("message", "success -> " + action.getTitle()
                                    + " was added as favourite");
                            // incrementez numarul de persoane care au acel film la favorite
                            for (MovieInputData movie : movieData) {
                                if (movie.getTitle().equals(action.getTitle())) {
                                    movie.setTotalfavorites(movie.getTotalfavorites() + 1);
                                }
                            }
                            // incrementez numarul de persoane care au acel serial la favorite
                            for (SerialInputData serial : serialData) {
                                if (serial.getTitle().equals(action.getTitle())) {
                                    serial.setTotalfavorites(serial.getTotalfavorites() + 1);
                                }
                            }
                        } else {
                            jsonObject.put("message", "error -> " + action.getTitle()
                                    + " is not seen");
                        }
                    }
                }
            }
        }

        if (action.getType().equals("view")) {
            // parcurg lista utilizatorilor
            for (UserInputData user : userData) {
                // gasesc utilizatorul meu
                if (action.getUsername().equals(user.getUsername())) {
                    // daca video-ul a mai fost vizualizat
                    if (user.getHistory().containsKey(action.getTitle())) {
                        user.getHistory().put(action.getTitle(),
                                // incrementez numarul de vizualizari
                                user.getHistory().get(action.getTitle()) + 1);
                        jsonObject.put("message", "success -> " + action.getTitle()
                                + " was viewed with total views of "
                                + user.getHistory().get(action.getTitle()));
                    } else {
                        // adaug la vizualizate acel video
                        user.getHistory().put(action.getTitle(), 1);
                        jsonObject.put("message", "success -> " + action.getTitle()
                                + " was viewed with total views of "
                                + user.getHistory().get(action.getTitle()));
                    }
                }
            }
            for (MovieInputData movie : movieData) {
                if (movie.getTitle().equals(action.getTitle())) {
                    movie.setTotalviews(movie.getTotalviews() + 1);
                }
            }
            for (SerialInputData serial : serialData) {
                if (serial.getTitle().equals(action.getTitle())) {
                    serial.setTotalviews(serial.getTotalviews() + 1);
                }
            }
        }

        if (action.getType().equals("rating")) {
            // verific daca am un film
            if (action.getSeasonNumber() == 0) {
                for (UserInputData user : userData) {
                    // gasesc utilizatorul meu
                    if (action.getUsername().equals(user.getUsername())) {
                        // daca video-ul a mai fost vizualizat
                        if (user.getHistory().containsKey(action.getTitle())) {
                            // verific ca video-ul sa nu fi primit deja o nota de la user
                            if (!user.getGivenMovieGrades().contains(action.getTitle())) {
                                // adaug filmul in lista celor la care user-ul a dat note
                                user.addGivenMovieGrades(action.getTitle());
                                for (MovieInputData movie : movieData) {
                                    // gasesc filmul caruia user-ul ii da nota
                                    if (movie.getTitle().equals(action.getTitle())) {
                                        // adaug nota in lista notelor acelui film
                                        movie.addGrade(action.getGrade());
                                        //  incrementez numarul de review-uri date de un user
                                        user.setTotalReviews(user.getTotalReviews() + 1);
                                        jsonObject.put("message", "success -> " + action.getTitle()
                                                + " was rated with " + action.getGrade() + " by "
                                                + action.getUsername());
                                    }
                                }
                            } else {
                                jsonObject.put("message", "error -> " + action.getTitle()
                                        + " has been already rated");
                            }
                        } else {
                            jsonObject.put("message", "error -> " + action.getTitle()
                                    + " is not seen");
                        }
                    }
                }
            } else {
                // in cazul in care am serial
                for (UserInputData user : userData) {
                    // gasesc utilizatorul meu
                    if (action.getUsername().equals(user.getUsername())) {
                        // daca video-ul a mai fost vizualizat
                        if (user.getHistory().containsKey(action.getTitle())) {
                            // daca utilizatorul nu a mai acordat nota acelui serial
                            if (!user.getGivenSeasonGrades().containsKey(action.getTitle())) {
                                ArrayList<Integer> myNewList = new ArrayList<>();
                                myNewList.add(action.getSeasonNumber());
                                // adaug serialul in lista celor la care user-ul a dat note
                                user.getGivenSeasonGrades().put(action.getTitle(), myNewList);
                                for (SerialInputData serial : serialData) {
                                    // gasesc serialul caruia user-ul ii da nota
                                    if (serial.getTitle().equals(action.getTitle())) {
                                        for (Season season : serial.getSeasons()) {
                                            if (season.getCurrentSeason()
                                                    == action.getSeasonNumber()) {
                                                // adaug nota in lista notelor acelui sezon
                                                season.getRatings().add(action.getGrade());
                                                // incrementez numarul de review-uri date de un user
                                                user.setTotalReviews(user.getTotalReviews() + 1);
                                                jsonObject.put("message", "success -> "
                                                        + action.getTitle() + " was rated with "
                                                        + action.getGrade() + " by "
                                                        + action.getUsername());
                                            }
                                        }
                                    }
                                }
                                // daca utilizatprul a mai acordat nota unui sezon din acel serial
                            } else {
                                if (!user.getGivenSeasonGrades().get(action.getTitle())
                                        // daca utilizatorul nu a mai acordat nota acelui sezon
                                        .contains(action.getSeasonNumber())) {
                                    user.getGivenSeasonGrades().get(action.getTitle())
                                            // adaug sezonul in lista celor la care utilizatorul a
                                            // dat nota
                                            .add(action.getSeasonNumber());
                                    for (SerialInputData serial : serialData) {
                                        // gasesc serialul caruia user-ul ii da nota
                                        if (serial.getTitle().equals(action.getTitle())) {
                                            for (Season season : serial.getSeasons()) {
                                                if (season.getCurrentSeason()
                                                        == action.getSeasonNumber()) {
                                                    // adaug nota in lista notelor acelui sezon
                                                    season.getRatings().add(action.getGrade());
                                                    // incrementez numarul de review-uri date de
                                                    // un user
                                                    user.setTotalReviews(user.getTotalReviews()
                                                            + 1);
                                                    jsonObject.put("message", "success -> "
                                                            + action.getTitle()
                                                            + " was rated with "
                                                            + action.getGrade()
                                                            + " by " + action.getUsername());
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    jsonObject.put("message", "error -> " + action.getTitle()
                                            + " has been already rated");
                                }
                            }
                        } else {
                            jsonObject.put("message", "error -> " + action.getTitle()
                                    + " is not seen");
                        }
                    }
                }
            }
        }
        return jsonObject;
    }
}

