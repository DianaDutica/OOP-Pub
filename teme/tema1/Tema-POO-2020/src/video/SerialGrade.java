package video;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class Serial_grade {
    public static void serial_grade(List<SerialInputData> serialData) {
        for(SerialInputData serial : serialData) {  // parcurg fiecare serial
            Double sum = 0.0;
            for(Season season : serial.getSeasons()) {
                Double sum_season = 0.0;
                for(Double grade : season.getRatings()) { // parcurg lista notelor pe care le-a primit un sezon
                    sum_season = sum_season + grade;
                }
                if(season.getRatings().size() > 0)
                    sum = sum + sum_season/(season.getRatings().size()); // calculez media acelui sezon din serial si il adun la sum mediilor sezoanelor serialului
            }
            if(serial.getSeasons().size() > 0)
                serial.setMediumgrade(sum/(serial.getSeasons().size()));   // calculez media rating-urilor acelui serial
        }
    }
}
