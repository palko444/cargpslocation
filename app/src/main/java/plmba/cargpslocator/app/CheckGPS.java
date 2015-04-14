package plmba.cargpslocator.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pala on 5/3/14.
 */
public class CheckGPS {
    String gpsMatchLat = "Lat:\\s*(\\d+\\.\\d+)";
    String gpsMatchLng = "Lng:\\s*(\\d+\\.\\d+)";
    String gpstime = "Time:(.*)\\.";
    Boolean latCorrect = Boolean.FALSE;
    Boolean lngCorrect = Boolean.FALSE;


    public boolean isLatOK (){
        return latCorrect;
    }

    public boolean isLngOK (){
        return lngCorrect;
    }

    public String getTime (String line) {

        String time = "none";
        Matcher matcher = Pattern.compile(gpstime).matcher(line);

        if (matcher.find()){
            time = matcher.group(1);
        }

        return time;

    }

    public String getLat(String line, String type) {

        String coordinates;
        boolean startOk = type.startsWith(type);

        if (startOk) {
            Matcher matcher = Pattern.compile(gpsMatchLat).matcher(line);
            if (matcher.find()) {
                coordinates = matcher.group(1);
                latCorrect = Boolean.TRUE;
                return coordinates;
            } else {
                latCorrect = Boolean.FALSE;
                return ("Not a gps coordinates");
            }

        } else {
            latCorrect = Boolean.FALSE;
            return ("Not a gps coordinates");
        }

    }
    public String getLng(String line, String type) {

        String coordinates;
        boolean startOk = type.startsWith(type);

        if (startOk) {
            Matcher matcher = Pattern.compile(gpsMatchLng).matcher(line);
            if (matcher.find()) {
                coordinates = matcher.group(1);
                lngCorrect = Boolean.TRUE;
                return coordinates;
            } else {
                lngCorrect = Boolean.FALSE;
                return ("Not a gps coordinates");
            }

        } else {
            lngCorrect = Boolean.FALSE;
            return ("Not a gps coordinates");
        }

    }
}
