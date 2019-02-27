package com.regextest.android.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileManager {

    public static List<String> readPageFromFile(File fileToRead, Integer offset, Integer count) {
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
            Integer index = 0;
            Integer startIndex = offset;
            Integer endIndex =  offset + count;
            String line = reader.readLine();
            while (line != null) {

                if(index >= startIndex && index < endIndex){
                    result.add(line);
                }

                if(index > endIndex)
                    break;

                line = reader.readLine();
                index++;
            }
        } catch (IOException exception) {

        }
        return result;
    }

    public static String getStringByIndexes(File fileToRead, Set<Integer> integerSet){
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
            Integer index = 0;
            String line = reader.readLine();
            while (line != null) {

                if(integerSet.contains(index)){
                    if(result.length() > 0){
                        result.append("\n");
                    }
                    result.append(line);
                }

                line = reader.readLine();
                index++;
            }
        } catch (IOException exception) {

        }
        return result.toString();
    }

    public static boolean writeToFile(File fileToWrite, String content) {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileToWrite, true));
            bufferedWriter.append(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        } catch (IOException exception) {
            return false;
        }
    }
}
