package by.qulixsystem.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс отвечает только за функции над определенным файлом.
 * Подсчет символов, слов, определение типа файла, время обработки и т. п.
 */
public class HandlingFile {
    private FileManager manager;
    private ZonedDateTime timeWork;

    public FileManager getManager() {
        return manager;
    }

    public HandlingFile(FileManager manager) {
        this.manager = manager;
        this.timeWork = ZonedDateTime.now();
    }

    /**
     * Format string with shared information about this file.
     * @return string format
     * @throws IOException
     */
    public String SharedInformation() throws IOException {
        return String.format(" Name: %s\n"+
                        " Size: %dB\n"+
                        " Type file: %s\n"+
                        " Count char: %d\n"+
                        " Count words: %d",
                manager.getPath().getFileName(),
                manager.getPath().toFile().length(),
                TypeFiles(),
                manager.readFileCharStream().count(),
                manager.readFileWordStream().count());
    }

    /**
     * Running time of an algorithm
     * @return string format
     */
    public String TimeFileProcessing(){
        long finishTime = ZonedDateTime.now().toInstant().toEpochMilli() - timeWork.toInstant().toEpochMilli();
        return String.format("Time File Processing: %d msec",finishTime);
    }

    /**
     * Defining data types.
     * @return string: text, binary, unicode
     * @throws IOException
     */
    public String TypeFiles() throws IOException {
        String content = manager.convertListToString(Files.readAllLines(manager.getPath()));
        char[] charArray = content.toCharArray();
        int countAscii = 0;
        int countDecZeroOne = 0;
        for (int i = 0; i < charArray.length; i++){
            if (charArray[i] < 128){
                countAscii++;
            }
            if (charArray[i] == 48 || charArray[i] == 49){
                countDecZeroOne++;
            }
        }
        if (countDecZeroOne > charArray.length/2){
            return "binary";
        }
        if (countAscii == charArray.length)
            return "unicode";
        else return "text";
    }

    /**
     * String table format about chars in file.
     * @return string table
     */
    public String TableChar(){
        Stream<Character> threadChar = manager.readFileCharStream();
        long countThreadChar =  manager.readFileCharStream().count();
        Map<Character, Integer> map = createStreamToMapChar(threadChar);

        String result = String.format("%10s\t %10s\t %10s\n","Symbol", "Count", "Ratio");
        result += String.format("%10s","-------------------------------------\n");
        for (Character key: map.keySet()){
            result += String.format("%10s\t %10s\t %10s%%\n",
                    key, map.get(key),(map.get(key)*100)/countThreadChar);
        }
        return result;
    }

    /**
     * String table format about words in file.
     * @return string table
     */
    public String TableWord(){
        Stream<String> threadWord = manager.readFileWordStream();
        long countThreadWord =  manager.readFileWordStream()
                .toArray(String[]::new).length;
        Map<String, Integer> map = createStreamToMapWord(threadWord);

        String result = String.format("%10s\t %10s\t %10s\n","Word", "Count", "Ratio");
        result += String.format("%10s","-------------------------------------\n");
        for (String key: map.keySet()){
            result += String.format("%10s\t %10s\t %10s%%\n",
                    key, map.get(key),map.get(key)*100/countThreadWord);
        }
        return result;
    }

    /**
     * Convert stream to Map<String,Integer></>
     * @param thread
     * @return Map<String,Integer></>
     */
    public Map<String,Integer> createStreamToMapWord(Stream<String> thread){
        Map<String, Integer> map = thread
                .collect(HashMap::new, (m, c) -> {
                    if(m.containsKey(c))
                        m.put(c, m.get(c)+1);
                    else
                        m.put(c, 1);
                }, HashMap::putAll);

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Convert stream to Map<Character,Integer></>
     * @param thread
     * @return Map<Character,Integer></>
     */
    public Map<Character,Integer> createStreamToMapChar(Stream<Character> thread){
        Map<Character, Integer> map = thread
                .collect(HashMap::new, (m, c) -> {
                    if(m.containsKey(c))
                        m.put(c, m.get(c)+1);
                    else
                        m.put(c, 1);
                }, HashMap::putAll);

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
