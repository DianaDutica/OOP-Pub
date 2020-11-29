package main;

import action.Command;
import action.Query;
import action.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static user.UserFavorites.initialFavorites;
import static user.UserViews.initialViews;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        List<ActionInputData> actionData = input.getCommands();
        List<UserInputData> userData = input.getUsers();
        List<MovieInputData> movieData = input.getMovies();
        List<SerialInputData> serialData = input.getSerials();
        List<ActorInputData> actorData = input.getActors();

        initialViews(userData, movieData, serialData);
        initialFavorites(userData, movieData, serialData);
        for (ActionInputData action : actionData) {
            if (action.getActionType().equals("command")) {
                arrayResult.add(Command.executeCommand(action, userData,
                                                                        movieData, serialData));
            }

            if (action.getActionType().equals("query")) {
                arrayResult.add(Query.executeQuery(action, userData, movieData,
                                                                        serialData, actorData));
            }

            if (action.getActionType().equals("recommendation")) {
                arrayResult.add(Recommendation.executeRecommendation(action, userData,
                                                                        movieData, serialData));
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
