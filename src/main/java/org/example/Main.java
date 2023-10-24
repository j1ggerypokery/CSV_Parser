package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        List<Employee> list = parseCSV(columnMapping, "Users/Oleg/IdeaProjects/CSV_Parser/src/main/resources/data.csv");

        System.out.println("=== write() ===");
        write("data.csv");
    //    read();
        readToObjects();
        String json = listToJson(list);

    }
    private static void write(String fileName) {
        var employee = "1,John,Smith,USA,25"; "2,Inav,Petrov,RU,23".split(",");
        try (CSVWriter csvWriter = new CSVWriter(
                new FileWriter(fileName))
        ) {
            csvWriter.writeNext(new String[]{employee});
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void parseCSV(String columnMapping, String fileName ) {
        try (CSVReader csvReader = new CSVReader(
                new FileReader(fileName))
        ) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        } catch (CsvValidationException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Employee> readToObjects() {
        try (CSVReader csvReader = new CSVReader(new FileReader("Users/Oleg/IdeaProjects/CSV_Parser/src/main/resources/data.csv"))) {
            var strategy = new ColumnPositionMappingStrategy<Employee>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");
            var csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            var employees = csv.parse();

            employees.forEach(System.out::println);
            return employees;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    private static void listToJson(List <Employee> list) {

GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<T>>() {}.getType();
        String json = gson.toJson(list);

        try (FileWriter fileWriter = new FileWriter("Users/Oleg/IdeaProjects/CSV_Parser/src/main/resources/data.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}