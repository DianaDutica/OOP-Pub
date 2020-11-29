package action;

import fileio.ActionInputData;
import fileio.UserInputData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class QueryUser {
    /**
     * Sortez utilizatorii alfabetic si dupa campul totalReviews, iar cu ajutorul
     * variabilei maxUsers pun in lista doar n utilizatori, primii ordonati dupa
     * cate vizualizari au dat.
     * @param query
     * @param userData
     * @return
     */
    public static JSONObject executeQueryUser(final ActionInputData query,
                                              final List<UserInputData> userData) {
        ArrayList<String> list = new ArrayList<>();

        List<UserInputData> copyUserData = new ArrayList<>();
        List<UserInputData> alfaUserData = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", query.getActionId());

        int maxUsers = 0;

        if (query.getCriteria().equals("num_ratings")) {
            if (query.getSortType().equals("asc")) {
                alfaUserData = userData.stream().sorted(
                  Comparator.comparing(UserInputData::getUsername)).collect(Collectors.toList());
                copyUserData = alfaUserData.stream().sorted(Comparator.comparing(
                  UserInputData::getTotalReviews)).collect(Collectors.toList());
                for (UserInputData user : copyUserData) {
                    if (maxUsers < query.getNumber() && user.getTotalReviews() > 0) {
                        list.add(user.getUsername());
                        maxUsers++;
                    }
                }
            } else {
                alfaUserData = userData.stream().sorted(Comparator.comparing(
                        UserInputData::getUsername).reversed()).collect(Collectors.toList());
                copyUserData = alfaUserData.stream().sorted(Comparator.comparing(
                        UserInputData::getTotalReviews).reversed()).collect(Collectors.toList());
                for (UserInputData user : copyUserData) {
                    if (maxUsers < query.getNumber() && user.getTotalReviews() > 0) {
                        list.add(user.getUsername());
                        maxUsers++;
                    }
                }
            }
        }
        jsonObject.put("message", "Query result: " + list);
        return jsonObject;
    }
}
