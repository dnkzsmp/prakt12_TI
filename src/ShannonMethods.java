import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShannonMethods {
    public static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    public static double firstMethodEntropy(@NotNull List<String> alphabet, @NotNull String filename) throws IOException {
        System.out.println("Размер алфавита: " + alphabet.size());
        List<Double> frequencies = new ArrayList<>();
        for (String letter : alphabet) {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int ch;
            char charToSearch = letter.charAt(0);
            int countOfSearchingChar = 0;
            int countOfAllChars = 0;
            while ((ch = reader.read()) != -1) {
                if (charToSearch == (char) ch) {
                    countOfSearchingChar++;
                }
                countOfAllChars++;
            }
            reader.close();
            frequencies.add((double) countOfSearchingChar / (double) countOfAllChars);
        }

        System.out.println("[Частоты символов файла \"" + filename + "\" в 1-ом методе]");
        for (int i = 0; i < alphabet.size(); ++i)
            System.out.println(alphabet.get(i) + " -> " + frequencies.get(i));
        System.out.println();

        double result = 0;
        for (double freq : frequencies)
            result += freq * log2(1 / freq);

        return result;
    }

    public static double secondMethodEntropy(@NotNull String filename) throws IOException {
        List<Double> frequencies = new ArrayList<>();
        List<String> pairs = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        StringBuilder newPair = new StringBuilder();
        int ch;

        while ((ch = reader.read()) != -1) {
            char c = (char) ch;
            if (newPair.length() == 2){
                pairs.add(newPair.toString());
                newPair = new StringBuilder();
            }
            if (!String.valueOf(c).matches("\\p{Punct}"))
                newPair.append(c);
        }
        reader.close();

        Collections.sort(pairs);
        List<String> pairsWithoutDuplicates = pairs.stream().distinct().toList();
        System.out.println("Количество пар: " + pairsWithoutDuplicates.size());

        for (String currentPair : pairsWithoutDuplicates) {
            int countOfCurrentPair = 0;
            for (String pair : pairs) {
                if (currentPair.equals(pair))
                    countOfCurrentPair++;
            }
            frequencies.add((double) countOfCurrentPair / (double) pairs.size());
        }

        System.out.println("[Частоты пар символов файла \"" + filename + "\" в 1-ом методе]");
        for (int i = 0; i < pairsWithoutDuplicates.size(); ++i)
            System.out.println(pairsWithoutDuplicates.get(i) + " -> " + frequencies.get(i));
        System.out.println();

        double result = 0;
        for (double freq : frequencies)
            result += freq * log2(1 / freq);

        return result / 2.0;
    }

    private static String updatedTextInFile(@NotNull String filename) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        while ((line = reader.readLine()) != null) {
            resultStringBuilder.append(line);
        }
        reader.close();

        String result = resultStringBuilder
                .toString()
                .toLowerCase()
                .replace("—", "");

        if (result.contains("ё"))
            result = result.replace("ё", "е");

        if (result.contains("ь"))
            result = result.replace("ь", "ъ");

        return result;
    }

    public static List<String> alphabetOfUpdatedFile(@NotNull String filename) throws IOException {
        List<String> alphabet = new ArrayList<>();

        StringBuilder text = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
        }
        reader.close();

        for (int i = 0; i < text.length(); ++i) {
            if (!alphabet.contains(String.valueOf(text.charAt(i))) &&
                    !String.valueOf(text.charAt(i)).matches("\\p{Punct}")) {
                alphabet.add(String.valueOf(text.charAt(i)));
            }
        }

        return alphabet;
    }

    public static double firstMethodParseAndEntropy(@NotNull String filename) throws IOException {
        String res = updatedTextInFile(filename);
        String updatedFilename = "book_updated.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(updatedFilename));
        writer.write(res);
        writer.close();
        List<String> alphabet = alphabetOfUpdatedFile(updatedFilename);
        return firstMethodEntropy(alphabet, updatedFilename);
    }

    public static double secondMethodParseAndEntropy(@NotNull String filename) throws IOException {
        String res = updatedTextInFile(filename);
        String updatedFilename = "book_updated.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(updatedFilename));
        writer.write(res);
        writer.close();
        return secondMethodEntropy(updatedFilename);
    }

    public static double firstMethodParseAndEntropyJava(@NotNull String filename) throws IOException {
        String res = updatedTextInFile(filename);
        String updatedFilename = "ShannonMethodsUpdated.java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(updatedFilename));
        writer.write(res);
        writer.close();
        List<String> alphabet = alphabetOfUpdatedFile(updatedFilename);
        return firstMethodEntropy(alphabet, updatedFilename);
    }

    public static double secondMethodParseAndEntropyJava(@NotNull String filename) throws IOException {
        String res = updatedTextInFile(filename);
        String updatedFilename = "ShannonMethodsUpdated.java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(updatedFilename));
        writer.write(res);
        writer.close();
        return secondMethodEntropy(updatedFilename);
    }
}