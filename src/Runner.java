import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Semen on 20-Nov-16.
 */
public class Runner {

    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT1_FILE_NAME = "output1.txt";
    private static final String OUTPUT2_FILE_NAME = "output2.txt";
    private static final String DIVIDERS = "\\.,\\s;";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[^" + DIVIDERS + "]+");
        Pattern patternFirstFile = Pattern.compile("\\d+");
        Pattern patternSecondFile = Pattern.compile("[^\\d\\s]+");
        Matcher matcherFirst, matcherSecond, matcher;

        List<String> sourceList = readListOfStrings(INPUT_FILE_NAME);
        List<String> listOfWords = new ArrayList<>();
        List<String> listFirstTask = new ArrayList<>();
        List<String> listSecondTask = new ArrayList<>();

        for (String s : sourceList) {
            matcher = pattern.matcher(s);
            while (matcher.find()) {
                listOfWords.add(matcher.group());
            }
        }
        for (String word : listOfWords) {
            matcherFirst = patternFirstFile.matcher(word);
            matcherSecond = patternSecondFile.matcher(word);
            if (matcherFirst.matches()) {
                listFirstTask.add(word);
            }
            if (matcherSecond.matches()) {
                listSecondTask.add(word);
            }
        }
        Comparator<String> comparator = (o1, o2) -> {
            if (o1.length() < o2.length()) return -1;
            if (o1.length() > o2.length()) return 1;
            return o1.compareTo(o2);
        };
        Collections.sort(listFirstTask, comparator);
        Collections.sort(listSecondTask, comparator);
        writeToFile(OUTPUT1_FILE_NAME, listFirstTask.iterator());
        writeToFile(OUTPUT2_FILE_NAME, listSecondTask.iterator());
    }

    private static List<String> readListOfStrings(String path) {
        BufferedReader br = null;
        List<String> listOfStrings = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(new File(path)));
            String tempString = br.readLine();
            while (tempString != null) {
                listOfStrings.add(tempString);
                tempString = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Reading from file successfully failed. IOException");
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
        return listOfStrings;
    }

    private static void writeToFile(String filename, Iterator<?> iterator) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(filename));
            while (iterator.hasNext()) {
                fw.write((String) iterator.next());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Stream didn't closed or not exists. Reading from file failed. ");
            }
        }
    }
}