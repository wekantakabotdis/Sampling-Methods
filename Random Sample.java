import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static ArrayList<Double> averages = new ArrayList<>();
  public static void main(String[] args) {
    ArrayList<Double> heights;
    ArrayList<String> names;
    ArrayList<String> gender;

    Scanner reader = new Scanner(System.in);
    heights = importFile("Heights.txt");
    names = importFileString("Names.txt");
    gender = importFileString("Gender.txt");

    for (int i = 0; i < heights.size(); i++) {
      System.out.println(names.get(i) + ": " + heights.get(i));
    }

    System.out.println("What kind of sample?");

while (true) {
  String option = reader.nextLine();
  if (option.equalsIgnoreCase("systematic random sample") || option.equalsIgnoreCase("systematic")) {
    System.out.println("How many samples?");
    int numSample = reader.nextInt();
    System.out.println("Interval length?");
    double interval = reader.nextDouble();
    System.out.println("How many students in sample?");
    int numStudents = reader.nextInt();
    for (int i = 0; i < numSample; i++) {
      System.out.println("Sample " + (i + 1));
      systematicRandomSample(heights, interval, numStudents, names);
      System.out.println("_____________________________________________________________________________________________________________________________________________________________________________________");
    }
  } else if (option.equalsIgnoreCase("stratified random sample") || option.equalsIgnoreCase("stratified")) {
    System.out.println("How many samples?");
    int numSample = reader.nextInt();
    System.out.println("How many per strata?");
    int numPerStrata = reader.nextInt();
    for (int i = 0; i < numSample; i++) {
      System.out.println("Sample " + (i + 1));
      stratifiedRandomSample(heights, numPerStrata, gender, names);
      System.out.println("_____________________________________________________________________________________________________________________________________________________________________________________");
    }
  }
    else if (option.equalsIgnoreCase("simple random sample") || option.equalsIgnoreCase("simple")) {
    System.out.println("How many samples?");
    int numSample = reader.nextInt();
    System.out.println("How many students?");
    int numStudents = reader.nextInt();
    for (int i = 0; i < numSample; i++) {
      System.out.println("Sample " + (i + 1));
      simpleRandomSample(heights, numStudents, names);
      System.out.println("_____________________________________________________________________________________________________________________________________________________________________________________");
    }
  }
  else if(option.equalsIgnoreCase("stop")) {
    break;
  }
  else {
    System.out.println("What kind of sample?");
    continue;
  }
  System.out.println("Averages");
  System.out.println(averages);
  System.out.println("True Average: " + findAverage(averages));
  System.out.println("True Population Average: " + findAverage(heights));
  averages.clear();
  
}

    reader.close();
  }

  private static void stratifiedRandomSample(ArrayList<Double> heights, int numPerStrata, ArrayList<String> gender, ArrayList<String> names) {
    ArrayList<Double> maleHeights = new ArrayList<>();
    ArrayList<Double> femaleHeights = new ArrayList<>();
    ArrayList<String> maleNames = new ArrayList<>();
    ArrayList<String> femaleNames = new ArrayList<>();
    ArrayList<Double> maleHeightsFinal = new ArrayList<>();
    ArrayList<Double> femaleHeightsFinal = new ArrayList<>();
    ArrayList<String> maleNamesFinal = new ArrayList<>();
    ArrayList<String> femaleNamesFinal = new ArrayList<>();
    ArrayList<Integer> randomMales;
    ArrayList<Integer> randomFemales;
    ArrayList<Double> stratifiedList = new ArrayList<>();
    for (int i = 0; i < heights.size(); i++) {
      if (gender.get(i).equalsIgnoreCase("male")) {
        maleHeights.add(heights.get(i));
        maleNames.add(names.get(i));
      }
      else {
        femaleHeights.add(heights.get(i));
        femaleNames.add(names.get(i));
      }
    }
    randomMales = generateRandomList(numPerStrata, 0, maleHeights.size() - 1);
    randomFemales = generateRandomList(numPerStrata, 0, femaleHeights.size() - 1);
    for(int i = 0; i < numPerStrata; i++) {
      maleHeightsFinal.add(maleHeights.get(randomMales.get(i)));
      femaleHeightsFinal.add(femaleHeights.get(randomFemales.get(i)));
      maleNamesFinal.add(maleNames.get(randomMales.get(i)));
      femaleNamesFinal.add(femaleNames.get(randomFemales.get(i)));
    }
    System.out.println("Male Names: " + maleNamesFinal);
    System.out.println("Male Heights: " + maleHeightsFinal);
    System.out.println("Female Names: " + femaleNamesFinal);
    System.out.println("Female Heights: " + femaleHeightsFinal);
    System.out.println("Male Average: " + findAverage(maleHeightsFinal));
    System.out.println("Female Average: " + findAverage(femaleHeightsFinal));
    for(int i = 0; i < numPerStrata; i++) {
      stratifiedList.add(maleHeightsFinal.get(i));
    }
    for(int i = 0; i < numPerStrata; i++) {
      stratifiedList.add(femaleHeightsFinal.get(i));
    }
    double stratifiedAverage = findAverage(stratifiedList);
    System.out.println("Stratified List: " + stratifiedList);
    System.out.println("Stratified Average: " + stratifiedAverage);
    averages.add(stratifiedAverage);
  }

  public static void systematicRandomSample (ArrayList<Double> heights, double interval, int numStudents, ArrayList<String> names) {
    ArrayList<Double> sampleList = new ArrayList<>();
    ArrayList<String> sampleNames = new ArrayList<>();
    Random rand = new Random();
    int start = rand.nextInt(heights.size());
    while (sampleList.size() < numStudents) {
      sampleList.add(heights.get(start));
      sampleNames.add(names.get(start));
      start += interval;
      if (start >= heights.size()) {
        start -= heights.size();
      }
    }
    System.out.println("Names: " + sampleNames);
    System.out.println("Heights: " + sampleList);
    System.out.println("Average: " + findAverage(sampleList));
    averages.add(findAverage(sampleList));
  }
public static void simpleRandomSample (ArrayList<Double> heights, int numStudents, ArrayList<String> names) {
    ArrayList<Double> sampleList = new ArrayList<>();
    ArrayList<String> sampleNames = new ArrayList<>();
    ArrayList<Integer> random = new ArrayList<>();
    random = generateRandomList(numStudents, 0, heights.size() - 1);
    for (int i = 0; i < numStudents; i++) {
      sampleList.add(heights.get(random.get(i)));
      sampleNames.add(names.get(random.get(i)));
    }
    System.out.println("Names: " + sampleNames);
    System.out.println("Heights: " + sampleList);
    System.out.println("Average: " + findAverage(sampleList));
    averages.add(findAverage(sampleList));
  }
  public static double findAverage (ArrayList<Double> list) {
    double sum = 0;
    for (double i : list) {
      sum += i;
    }
    return sum / list.size();
  }

  public static ArrayList<Double> importFile(String filePath) {
    ArrayList<Double> numbers = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // parse the line as a double and add it to the list
        numbers.add(Double.parseDouble(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return numbers;
  }
  public static ArrayList<String> importFileString(String filePath) {
    ArrayList<String> words = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // parse the line as a double and add it to the list
        words.add((line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return words;
  }
  public static ArrayList<Integer> generateRandomList(int numPerStrata, int min, int max) {
    ArrayList<Integer> list = new ArrayList<>();
    Random rand = new Random();
    while (list.size() < numPerStrata) {
      int n = rand.nextInt((max - min) + 1) + min;
      if (!list.contains(n)) {
        list.add(n);
      }
    }
    return list;
  }
}
