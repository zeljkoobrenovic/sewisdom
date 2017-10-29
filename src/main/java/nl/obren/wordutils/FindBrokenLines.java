package nl.obren.wordutils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FindBrokenLines {
    public FindBrokenLines() {
    }

    public static void main(String args[]) throws IOException {
        searchForBrokenLines(new File("manuscript/chapter1.txt"));
        searchForBrokenLines(new File("manuscript/chapter2.txt"));
        searchForBrokenLines(new File("manuscript/chapter3.txt"));
        searchForBrokenLines(new File("manuscript/chapter4.txt"));
    }

    private static void searchForBrokenLines(File file) throws IOException {
        System.out.println();
        System.out.println(file.getPath());
        String text = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        text = text.replace("A> ", "");
        text = text.replace("A>", "");

        int oldN = 0;
        int n = 0;
        while (true) {
            n = text.indexOf("\n", n + 1);
            if (n == -1 || n == text.length() - 1) {
                break;
            }

            if (!text.substring(n + 1, n + 2).equals("\n")) {
                String line = text.substring(oldN, n);
                if (StringUtils.isNotBlank(line) && !line.trim().startsWith("*") && !StringUtils.isNumeric(line.trim().substring(0, 1))) {
                    System.out.println(line);
                }
            }

            oldN = n;
        }
    }
}