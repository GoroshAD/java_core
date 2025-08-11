import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String json = readString("new_data.json");
        List<Employee> list = jsonToList(json);

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    public static String readString(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<Employee> jsonToList(String json) {
        List<Employee> employees = new ArrayList<>();
        JsonParser parser = new JsonParser();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JsonArray jsonArray = parser.parse(json).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Employee employee = gson.fromJson(jsonObject, Employee.class);
            employees.add(employee);
        }

        return employees;
    }
}
