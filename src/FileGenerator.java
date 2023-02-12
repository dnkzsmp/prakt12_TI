import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileGenerator {
    private final List<String> alphabet;
    private List<Double> probabilities = null;

    public FileGenerator(int sizeOfAlphabet) {
        if (sizeOfAlphabet < 1) {
            throw new RuntimeException("Размер алфавита не может быть меньше 1");
        }

        alphabet = new ArrayList<>();

        System.out.println("Введите символы алфавита");
        for (int i = 0; i < sizeOfAlphabet; ++i) {
            String newChar = Main.scan.next();
            if (!alphabet.contains(newChar))
                alphabet.add(newChar);
        }

        System.out.println("[Алфавит]");
        alphabet.forEach(System.out::println);
        System.out.println();
    }

    public void generateFile(TypeOfFile type) throws IOException {
        switch (type) {
            case WITH_PROBABILITIES -> {
                if (probabilities == null) {
                    probabilities = new ArrayList<>();
                    double sum = 0;

                    System.out.println("Введите вероятности");
                    for (int i = 0; i < alphabet.size(); ++i) {
                        double newProb = Main.scan.nextDouble();
                        probabilities.add(newProb * 100);
                        sum += newProb * 100;
                    }

                    if (sum != 100) {
                        throw new RuntimeException("Сумма вероятностей должна быть равна 1");
                    }
                }

                System.out.println("[Вероятности]");
                probabilities.forEach(System.out::println);
                System.out.println();

                StringBuilder textOfFirstFile = new StringBuilder();
                Random random = new Random();

                for (int i = 0; i < 20480; ++i) {
                    int randomValue = random.ints(1, 100).findFirst().getAsInt();
                    int ind = 0;
                    while (randomValue > 0) {
                        randomValue -= probabilities.get(ind);
                        ind++;
                    }
                    textOfFirstFile.append(alphabet.get(ind - 1));
                }

                BufferedWriter writerOfFirstFile = new BufferedWriter(new FileWriter("file1.txt"));
                writerOfFirstFile.write(textOfFirstFile.toString());
                writerOfFirstFile.close();
            }

            case WITHOUT_PROBABILITIES -> {
                StringBuilder textOfSecondFile = new StringBuilder();
                Random random = new Random();

                for (int i = 0; i < 20480; ++i) {
                    textOfSecondFile.append(alphabet.get(random.nextInt(alphabet.size())));
                }

                BufferedWriter writerOfSecondFile = new BufferedWriter(new FileWriter("file2.txt"));
                writerOfSecondFile.write(textOfSecondFile.toString());
                writerOfSecondFile.close();
            }
        }
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

}