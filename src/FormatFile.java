package format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatFile {

    String pairsfileName = "C:\\Users\\shimonk\\data\\pairs.txt";
    String datafileName = "C:\\Users\\shimonk\\data\\data.txt";
    String formatfileName = "C:\\Users\\shimonk\\data\\format.txt";
    private static final String NEW_LINE = System.lineSeparator();

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world");
        FormatFile app = new FormatFile();

        Map<String,String> pairs = new HashMap<>();

        //read file into stream, try-with-resources
        pairs = app.loadPairs();
        app.printMap(pairs);
        app.createFormatFile(pairs);

    }

    Map<String,String> loadPairs() throws IOException {
        Map<String,String> pairs = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(pairsfileName));
        for (String line : lines){
            String[] split = line.split(" ");
            pairs.put(split[0],split[1]);
        }
        return pairs;
    }

    private void printMap(Map<String,String> map){
        map.keySet().forEach(k ->   System.out.print(k  + " " +map.get(k) +" \n"));
    }

    String getReplaceString(Map<String,String> map,String original){
        return map.getOrDefault(original,original);
    }

    private void createFormatFile(Map<String,String> map) throws IOException {
        Path formatFilepath = Paths.get(formatfileName);

        String newLine ="";
        List<String> lines = Files.readAllLines(Paths.get(datafileName));
        for (String line : lines){
            newLine ="";
            String[] split = line.split(" ");
            for (int i=0; i<split.length-1; i++){
                newLine = newLine + getReplaceString(map,split[i]) + " ";
            }
            newLine = newLine + getReplaceString(map,split[split.length-1]) + NEW_LINE;

            Files.write(formatFilepath, newLine.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
}
