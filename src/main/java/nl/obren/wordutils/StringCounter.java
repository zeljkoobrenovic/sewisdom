package nl.obren.wordutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class StringCounter {
    private List<String> orderOfIntroduction = new ArrayList<String>();
    private Map<String, Integer> stringCountMap = new HashMap<String, Integer>();
    private int index = 0;

    public Map<String, Integer> getStringCountMap() {
        return stringCountMap;
    }

    public void incrementCount(String key) {
        incrementCount(key, 1);
    }

    public void incrementCount(String key, int count) {
        if (key != null) {
            stringCountMap.put(key, stringCountMap.containsKey(key) ? stringCountMap.get(key) + count : count);
            if (!orderOfIntroduction.contains(key)) {
                orderOfIntroduction.add(key);
            }
        }
    }

    public void print() {
        for (String key : getKeysSortedBySize()) {
            System.out.println(key + "\t" + stringCountMap.get(key));
        }
    }

    public String saveStringCounterToTemporaryFile(String name) {
        try {
            File file = File.createTempFile("distribution_" + name.toLowerCase().replace(" ", "_"), ".txt");
            PrintWriter out = new PrintWriter(new FileWriter(file));
            out.println(name + "\tCount");
            for (String key : getKeysSortedBySize()) {
                out.println(key + "\t" + getStringCountMap().get(key));
            }
            out.flush();
            out.close();

            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public int getTotalNumberOfStringIncludingDuplicates() {
        int count = 0;

        for (int value : stringCountMap.values()) {
            count += value;
        }

        return count;
    }

    public int getMaxCount() {
        int max = 0;
        for (int value : stringCountMap.values()) {
            max = Math.max(value, max);
        }

        return max;
    }


    public void printWithNumbering() {
        for (String key : getKeysSortedBySize()) {
            System.out.println(++index + "\t" + key + "\t" + stringCountMap.get(key));
        }
    }

    public void printWithNumberingInOrderOfIntroduction() {
        for (String key : orderOfIntroduction) {
            System.out.println(++index + "\t" + key + "\t" + stringCountMap.get(key));
        }
    }

    public List<String> getKeysSortedBySize() {
        List<String> orderedKeys = new ArrayList<String>();
        for (String key : stringCountMap.keySet()) {
            orderedKeys.add(key);
        }
        Collections.sort(orderedKeys, new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                int value1 = stringCountMap.get(key1);
                int value2 = stringCountMap.get(key2);
                return value1 > value2 ? -1 : value1 == value2 ? 0 : 1;
            }
        });
        return orderedKeys;
    }
}
