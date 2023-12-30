package application;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileReader {

    public String readFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    //public static void main(String[] ) {
     //   String filePath = "path/to/your/file.txt"; // Replace with the actual file path

    //    try {
    //        String fileContent = readFileToString(filePath);
    //        System.out.println(fileContent);
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    }

