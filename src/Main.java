import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        System.out.println("Номер лабы");
        int lab = scan.nextInt();
        switch (lab) {
            case 1 -> lab1();
            case 2 -> lab2();
        }
    }

    public static void lab1() throws IOException {
        System.out.println("Введите размер алфавита");
        int sizeOfAlphabet = scan.nextInt();

        FileGenerator fileGenerator = new FileGenerator(sizeOfAlphabet);

        System.out.println("Хотите ввести свои вероятности? (y/n)");
        String answer = scan.next();

        double result, logM;
        String filename;

        switch (answer) {
            case "y" -> {
                fileGenerator.generateFile(TypeOfFile.WITH_PROBABILITIES);
                filename = "file1.txt";

                result = ShannonMethods.firstMethodEntropy(fileGenerator.getAlphabet(), filename);
                System.out.println("Энтропия файла с вероятностями \"" + filename + "\" по 1-му методу: " + result);

                logM = ShannonMethods.log2(fileGenerator.getAlphabet().size());

                System.out.println("log(m) = " + logM);

                if (result <= logM)
                    System.out.println("Энтропия 1-го метода не превышает log(m)!");
                else
                    System.out.println("Энтропия превысила log(m)");

                result = ShannonMethods.secondMethodEntropy(filename);
                System.out.println("\nЭнтропия файла без вероятностей \"" + filename + "\" по 2-му методу: " + result);

                logM = ShannonMethods.log2(fileGenerator.getAlphabet().size());

                System.out.println("log(m) = " + logM);

                if (result <= logM)
                    System.out.println("Энтропия 2-го метода не превышает log(m)!");
                else
                    System.out.println("Энтропия 2-го метода превысила log(m)");
            }

            case "n" -> {
                fileGenerator.generateFile(TypeOfFile.WITHOUT_PROBABILITIES);
                filename = "file2.txt";

                result = ShannonMethods.firstMethodEntropy(fileGenerator.getAlphabet(), filename);
                System.out.println("Энтропия файла с вероятностями \"" + filename + "\" по 1-му методу: " + result);

                logM = ShannonMethods.log2(fileGenerator.getAlphabet().size());
                System.out.println("log(m) = " + logM);

                if (result <= logM)
                    System.out.println("Энтропия 1-го метода не превышает log(m)!");
                else
                    System.out.println("Энтропия 1-го метода превысила log(m)");

                result = ShannonMethods.secondMethodEntropy(filename);
                System.out.println("\nЭнтропия файла без вероятностей\"" + filename + "\" по 2-му методу: " + result);

                logM = ShannonMethods.log2(fileGenerator.getAlphabet().size());
                System.out.println("log(m) = " + logM);

                if (result <= logM)
                    System.out.println("Энтропия 2-го метода не превышает log(m)!");
                else
                    System.out.println("Энтропия 2-го метода превысила log(m)");
            }

            default -> System.out.println("Нужно ввести \"y\" или \"n\"");
        }
    }

    public static void lab2() throws IOException {
        double result, logM;
        String filename = "book.txt";

        result = ShannonMethods.firstMethodParseAndEntropy(filename);
        System.out.println("Энтропия файла с литературой \"" + filename + "\" по 1-му методу: " + result);

        logM = ShannonMethods.log2(ShannonMethods.alphabetOfUpdatedFile(filename).size());
        System.out.println("log(m) = " + logM);

        if (result <= logM)
            System.out.println("Энтропия 1-го метода не превышает log(m)!");
        else
            System.out.println("Энтропия 1-го метода превысила log(m)");

        result = ShannonMethods.secondMethodParseAndEntropy(filename);
        System.out.println("\nЭнтропия файла с литературой \"" + filename + "\" по 2-му методу: " + result);

        logM = ShannonMethods.log2(ShannonMethods.alphabetOfUpdatedFile(filename).size());
        System.out.println("log(m) = " + logM);

        if (result <= logM)
            System.out.println("Энтропия 2-го метода не превышает log(m)!");
        else
            System.out.println("Энтропия 2-го метода превысила log(m)");

        filename = "src/ShannonMethods.java";

        result = ShannonMethods.firstMethodParseAndEntropyJava(filename);
        System.out.println("Энтропия файла с кодом \"" + filename + "\" по 1-му методу: " + result);

        logM = ShannonMethods.log2(ShannonMethods.alphabetOfUpdatedFile(filename).size());
        System.out.println("log(m) = " + logM);

        if (result <= logM)
            System.out.println("Энтропия 1-го метода не превышает log(m)!");
        else
            System.out.println("Энтропия 1-го метода превысила log(m)");

        result = ShannonMethods.secondMethodParseAndEntropyJava(filename);
        System.out.println("\nЭнтропия файла с кодом \"" + filename + "\" по 2-му методу: " + result);

        logM = ShannonMethods.log2(ShannonMethods.alphabetOfUpdatedFile(filename).size());
        System.out.println("log(m) = " + logM);

        if (result <= logM)
            System.out.println("Энтропия 2-го метода не превышает log(m)!");
        else
            System.out.println("Энтропия 2-го метода превысила log(m)");
    }
}