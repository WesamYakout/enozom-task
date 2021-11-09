import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("./countries.json")));

        //read as json array
        JSONObject jsonObject = new JSONObject(data);
        List<List<String>> output = new ArrayList<>();
        Map<String, Object> map = jsonObject.toMap();
        for (String country : map.keySet()) {
            List<String> array = Arrays.asList(map.get(country).toString().split("\\s*,\\s*"));
            String maxCity = null;
            Integer max = -1;
            for(int i = 0 ; i < array.size() ; i++) {
                if(array.get(i).toString().length() > max) {
                    max = array.get(i).toString().length();
                    maxCity = array.get(i).toString();
                }
            }
            List<String> row = new ArrayList<>();
            row.add(country);
            row.add(String.valueOf(array.size()));
            row.add(maxCity);
            output.add(row);
        }

        FileWriter csvWriter = new FileWriter("./output.csv");
        csvWriter.append("Country Name");
        csvWriter.append(",");
        csvWriter.append("Number of cities");
        csvWriter.append(",");
        csvWriter.append("City with max length");
        csvWriter.append("\n");

        for (List<String> rowData : output) {
            if(rowData.get(2).charAt(rowData.get(2).length() - 1) == ']')
                rowData.get(2).substring(0 , rowData.get(2).length() - 1);
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }
}
