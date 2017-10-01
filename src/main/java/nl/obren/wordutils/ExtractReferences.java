package nl.obren.wordutils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractReferences {
    private List<String> fromReferences = new ArrayList<>();
    private List<String> definedReferences = new ArrayList<>();

    public ExtractReferences() {

    }

    public static void main(String args[]) throws IOException {
        ExtractReferences extractReferences = new ExtractReferences();
        extractReferences.getFromReferences(new File("manuscript/chapter1.txt"));
        extractReferences.getFromReferences(new File("manuscript/chapter2.txt"));
        extractReferences.getFromReferences(new File("manuscript/chapter3.txt"));
        extractReferences.getFromReferences(new File("manuscript/chapter4.txt"));

        extractReferences.getDefinedReferences(new File("manuscript/chapter1.txt"));
        extractReferences.getDefinedReferences(new File("manuscript/chapter2.txt"));
        extractReferences.getDefinedReferences(new File("manuscript/chapter3.txt"));
        extractReferences.getDefinedReferences(new File("manuscript/chapter4.txt"));
        extractReferences.getDefinedReferences(new File("manuscript/chapter5.txt"));

        System.out.println("Broken References:");
        extractReferences.fromReferences.stream()
                .filter(reference -> !extractReferences.definedReferences.contains(reference)).forEach(reference -> {
            System.out.println(reference);
        });
        System.out.println("");
        System.out.println("");
        System.out.println("Orphan References:");
        extractReferences.definedReferences.stream()
                .filter(reference -> !extractReferences.fromReferences.contains(reference)).forEach(reference -> {
            System.out.println(reference);
        });
        System.out.println("");
        System.out.println("");
    }

    private void getFromReferences(File file) throws IOException {
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        Pattern p = Pattern.compile("\\(#.*?\\)");   // the pattern to search for
        Matcher m = p.matcher(content);

        while (m.find()) {
            String group = m.group(0);

            if (!fromReferences.contains(group)) {
                fromReferences.add(group.replace("(", "").replace(")", ""));
            }
        }
    }

    private void getDefinedReferences(File file) throws IOException {
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        Pattern p = Pattern.compile("\\{#.*?\\}");   // the pattern to search for
        Matcher m = p.matcher(content);

        while (m.find()) {
            String group = m.group(0);

            if (!definedReferences.contains(group)) {
                definedReferences.add(group.replace("{", "").replace("}", ""));
            }
        }
    }
}