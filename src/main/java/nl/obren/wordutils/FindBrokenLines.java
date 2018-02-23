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
        int brokenLinesCount = 0;
        brokenLinesCount += searchForBrokenLines(new File("manuscript/chapter1.txt"));
        brokenLinesCount += searchForBrokenLines(new File("manuscript/chapter2.txt"));
        brokenLinesCount += searchForBrokenLines(new File("manuscript/chapter3.txt"));
        brokenLinesCount += searchForBrokenLines(new File("manuscript/chapter4.txt"));

        System.out.println();
        System.out.println("Found " + brokenLinesCount + " broken lines");
    }

    private static int searchForBrokenLines(File file) throws IOException {
        boolean pathPrinted = false;
        int brokenLinesCount = 0;

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
                String line = text.substring(oldN + 1, n);
                if (StringUtils.isNotBlank(line)
                        && !line.trim().startsWith("*")
                        && !line.trim().startsWith("|")
                        && !StringUtils.isNumeric(line.trim().substring(0, 1))) {
                    if (!pathPrinted) {
                        pathPrinted = true;
                        System.out.println("==================================");
                        System.out.println(file.getPath());
                    }
                    System.out.println("  - \"" + line + "\"");
                    brokenLinesCount++;
                }
            }

            oldN = n;
        }
        return brokenLinesCount;
    }
}