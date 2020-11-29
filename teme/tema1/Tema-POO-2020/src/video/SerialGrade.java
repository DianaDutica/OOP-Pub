package video;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.List;

/**
 *
 */
public class SerialGrade {
    /**
     *
     * @param serialData
     */
    public static void serialGrade(final List<SerialInputData> serialData) {
        for (SerialInputData serial : serialData) {  // parcurg fiecare serial
            Double sum = 0.0;
            for (Season season : serial.getSeasons()) {
                Double sumseason = 0.0;
                for (Double grade : season.getRatings()) {
                    // parcurg lista notelor pe care le-a primit un sezon
                    sumseason = sumseason + grade;
                }
                if (season.getRatings().size() > 0) {
                    // calculez media acelui sezon din serial
                    // si il adun la sum mediilor sezoanelor serialului
                    sum = sum + sumseason / (season.getRatings().size());
                }
            }
            if (serial.getSeasons().size() > 0) {
                // calculez media rating-urilor acelui serial
                serial.setMediumgrade(sum / (serial.getSeasons().size()));
            }
        }
    }
}
