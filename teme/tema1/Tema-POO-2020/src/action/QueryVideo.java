package action;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static video.MovieGrade.movieGrade;
import static video.SerialGrade.serialGrade;

public class Query_video {
    /**
     *
     * @param query
     * @param movieData
     * @param serialData
     * @return
     */
    public static JSONObject executeQuery_video(final ActionInputData query,
                                                final List<MovieInputData> movieData,
                                                final List<SerialInputData> serialData) {
        JSONObject jsonObject  = new JSONObject();

        if (query.getCriteria().equals("ratings")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            // daca sunt in cazul in care am filme
            if (query.getObjectType().equals("movies")) {
                int max_movies = 0;

                // reinitializez campul mediumgrade al filmelor
                movieGrade(movieData);

                List<MovieInputData> duplicateMovieData = new ArrayList<>(movieData);

                // daca am filtre de an
                if(query.getFilters().get(0).get(0) != null) {
                    duplicateMovieData.removeIf(movie ->
                            !query.getFilters().get(0).contains(String.valueOf(movie.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateMovieData.removeIf(movie ->
                            Collections.disjoint(query.getFilters().get(1), movie.getGenres()));
                }

                List<MovieInputData> copyMovieData;
                List<MovieInputData> alfaMovieData;

                if (query.getSortType().equals("asc")) {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle))
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingDouble(MovieInputData::getMediumgrade))
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (max_movies < query.getNumber() && movie.getMediumgrade() > 0) {
                            list.add(movie.getTitle());
                            max_movies++;
                        }
                    }
                } else {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle).reversed())
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingDouble(MovieInputData::getMediumgrade)
                                    .reversed()).collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (max_movies < query.getNumber() && movie.getMediumgrade() > 0) {
                            list.add(movie.getTitle());
                            max_movies++;
                        }
                    }
                }

            // daca sunt in cazul in care am seriale
            } else {
                int max_serials = 0;

                // reinitializez campul mediumgrade al serialelor
                serialGrade(serialData);

                List<SerialInputData> duplicateSerialData = new ArrayList<>(serialData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                    duplicateSerialData.removeIf(serial -> !query.getFilters().get(0)
                            .contains(String.valueOf(serial.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateSerialData.removeIf(serial -> Collections.disjoint(query.getFilters()
                            .get(1), serial.getGenres()));
                }

                List<SerialInputData> copySerialData = new ArrayList<>();
                List<SerialInputData> alfaSerialData = new ArrayList<>();

                if (query.getSortType().equals("asc")) {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle))
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingDouble(SerialInputData::getMediumgrade))
                            .collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (max_serials < query.getNumber() && serial.getMediumgrade() > 0) {
                            list.add(serial.getTitle());
                            max_serials++;
                        }
                    }
                } else {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle).reversed())
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingDouble(SerialInputData::getMediumgrade)
                                    .reversed()).collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (max_serials < query.getNumber() && serial.getMediumgrade() > 0) {
                            list.add(serial.getTitle());
                            max_serials++;
                        }
                    }
                }
            }
            jsonObject.put("message", "Query result: " + list);
        }

        if (query.getCriteria().equals("favorite")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            // daca sunt in cazul in care am filme
            if (query.getObjectType().equals("movies")) {
                int maxMovies = 0;

                List<MovieInputData> duplicateMovieData = new ArrayList<>(movieData);

                // daca am filtre de an
                if ( query.getFilters().get(0).get(0) != null ) {
                    duplicateMovieData.removeIf(movie ->
                            !query.getFilters().get(0).contains(String.valueOf(movie.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateMovieData.removeIf(movie -> Collections.disjoint(query.getFilters()
                            .get(1), movie.getGenres()));
                }

                List<MovieInputData> copyMovieData;
                List<MovieInputData> alfaMovieData;

                if (query.getSortType().equals("asc")) {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle))
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingInt(MovieInputData::getTotalfavorites))
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber()) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                } else {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle)
                                    .reversed()).collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingInt(MovieInputData::getTotalfavorites)
                                    .reversed()).collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber()) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                }
            // daca sunt in cazul in care am seriale
            } else {
                int maxSerials = 0;

                List<SerialInputData> duplicateSerialData = new ArrayList<>(serialData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                    duplicateSerialData.removeIf(serial -> !query.getFilters().get(0)
                            .contains(String.valueOf(serial.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateSerialData.removeIf(serial -> Collections.disjoint(query.getFilters()
                            .get(1), serial.getGenres()));
                }

                List<SerialInputData> copySerialData = new ArrayList<>();
                List<SerialInputData> alfaSerialData = new ArrayList<>();

                if (query.getSortType().equals("asc")) {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle))
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingInt(SerialInputData::getTotalfavorites))
                            .collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                } else {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle).reversed())
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingInt(SerialInputData::getTotalfavorites)
                                    .reversed()).collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                }
            }
            jsonObject.put("message", "Query result: " + list);
        }

        if (query.getCriteria().equals("longest")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            // daca sunt in cazul in care am filme
            if (query.getObjectType().equals("movies")) {
                int maxMovies = 0;

                List<MovieInputData> duplicateMovieData = new ArrayList<>(movieData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                    duplicateMovieData.removeIf(movie -> !query.getFilters().get(0)
                            .contains(String.valueOf(movie.getYear())));
                }

                // daca am filtre de genre
                if(query.getFilters().get(1).get(0) != null) {
                    duplicateMovieData.removeIf(movie -> Collections
                            .disjoint(query.getFilters().get(1), movie.getGenres()));
                }

                List<MovieInputData> copyMovieData;
                List<MovieInputData> alfaMovieData;

                if (query.getSortType().equals("asc")) {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle))
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingInt(MovieInputData::getDuration))
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber()) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                } else {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle).reversed())
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                           .sorted(Comparator.comparingInt(MovieInputData::getDuration).reversed())
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber()) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                }
            // daca sunt in cazul in care am seriale
            } else {
                int maxSerials = 0;

                List<SerialInputData> duplicateSerialData = new ArrayList<>(serialData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                    duplicateSerialData.removeIf(serial -> !query.getFilters()
                            .get(0).contains(String.valueOf(serial.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateSerialData.removeIf(serial ->
                            Collections.disjoint(query.getFilters().get(1), serial.getGenres()));
                }

                List<SerialInputData> copySerialData = new ArrayList<>();
                List<SerialInputData> alfaSerialData = new ArrayList<>();

                if (query.getSortType().equals("asc")) {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle))
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingInt(SerialInputData::getTimeEpisodes))
                            .collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                } else {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle).reversed())
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingInt(SerialInputData::getTimeEpisodes)
                                    .reversed()).collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                }
            }
            jsonObject.put("message", "Query result: " + list);
        }

        if (query.getCriteria().equals("most_viewed")) {
            ArrayList<String> list = new ArrayList<>();

            jsonObject.put("id", query.getActionId());

            // daca sunt in cazul in care am filme
            if (query.getObjectType().equals("movies")) {
                int maxMovies = 0;

                List<MovieInputData> duplicateMovieData = new ArrayList<>(movieData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                    duplicateMovieData.removeIf(movie -> !query.getFilters().get(0)
                            .contains(String.valueOf(movie.getYear())));
                }

                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateMovieData.removeIf(movie ->
                            Collections.disjoint(query.getFilters().get(1), movie.getGenres()));
                }

                List<MovieInputData> copyMovieData;
                List<MovieInputData> alfaMovieData;

                if (query.getSortType().equals("asc")) {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle))
                            .collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                            .sorted(Comparator.comparingInt(MovieInputData::getTotalviews))
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber() && movie.getTotalviews() > 0) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                } else {
                    alfaMovieData = duplicateMovieData.stream()
                            .sorted(Comparator.comparing(MovieInputData::getTitle)
                                    .reversed()).collect(Collectors.toList());
                    copyMovieData = alfaMovieData.stream()
                         .sorted(Comparator.comparingInt(MovieInputData::getTotalviews).reversed())
                            .collect(Collectors.toList());
                    for (MovieInputData movie : copyMovieData) {
                        if (maxMovies < query.getNumber() && movie.getTotalviews() > 0) {
                            list.add(movie.getTitle());
                            maxMovies++;
                        }
                    }
                }

            // daca sunt in cazul in care am seriale
            } else {
                int maxSerials = 0;

                List<SerialInputData> duplicateSerialData = new ArrayList<>(serialData);

                // daca am filtre de an
                if (query.getFilters().get(0).get(0) != null) {
                        duplicateSerialData.removeIf(serial -> !query.getFilters().get(0)
                                .contains(String.valueOf(serial.getYear())));
                    }
                // daca am filtre de genre
                if (query.getFilters().get(1).get(0) != null) {
                    duplicateSerialData.removeIf(serial ->
                            Collections.disjoint(query.getFilters().get(1), serial.getGenres()));
                }

                List<SerialInputData> copySerialData = new ArrayList<>();
                List<SerialInputData> alfaSerialData = new ArrayList<>();

                if (query.getSortType().equals("asc")) {
                    alfaSerialData = duplicateSerialData.stream()
                            .sorted(Comparator.comparing(SerialInputData::getTitle))
                            .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                            .sorted(Comparator.comparingInt(SerialInputData::getTotalviews))
                            .collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                } else {
                    alfaSerialData = duplicateSerialData.stream()
                        .sorted(Comparator.comparing(SerialInputData::getTitle).reversed())
                        .collect(Collectors.toList());
                    copySerialData = alfaSerialData.stream()
                        .sorted(Comparator.comparingInt(SerialInputData::getTotalviews).reversed())
                        .collect(Collectors.toList());
                    for (SerialInputData serial : copySerialData) {
                        if (maxSerials < query.getNumber() && serial.getTotalviews() > 0) {
                            list.add(serial.getTitle());
                            maxSerials++;
                        }
                    }
                }
            }
            jsonObject.put("message", "Query result: " + list);
        }
        return jsonObject;
    }
}
