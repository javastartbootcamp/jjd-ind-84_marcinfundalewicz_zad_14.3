import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    String fileName = "countries.csv";
    File file = new File(fileName);

    // nie zmieniaj nic w main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        main.run(scanner);
    }

    void run(Scanner scanner) {
        try {
            if (fileExists(file)) {
                Map<String, Country> countries = fillMapFromFile();
                String line = reader(scanner);
                printInfo(line, countries);
            } else {
                System.out.println("Brak pliku " + fileName + ".");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean fileExists(File file) {
        return file.exists();
    }

    private static String reader(Scanner scanner) {
        System.out.println("Proszę podać kod kraju");
        return scanner.nextLine();
    }

    private Map<String, Country> fillMapFromFile() throws FileNotFoundException {
        Map<String, Country> countries = new TreeMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Country country = takeCountryFromText(line);
                countries.put(country.getCode(), country);
            }
            return countries;
        }
    }

    private Country takeCountryFromText(String line) {
        String[] lines = line.split(";");
        String code = lines[0];
        String name = lines[1];
        long population = Long.parseLong(lines[2]);
        return new Country(code, name, population);
    }

    private void printInfo(String code, Map<String, Country> countries) {
        if (countries.containsKey(code)) {
            Country country = countries.get(code);
            System.out.println(country.getName() + " (" + country.getCode() + ") ma "
                    + country.getPopulation() + " ludności.");
        } else {
            System.out.println("Kod kraju " + code + " nie został znaleziony.");
        }
    }
}
