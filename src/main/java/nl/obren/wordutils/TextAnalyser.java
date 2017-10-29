package nl.obren.wordutils;


import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TextAnalyser {
    public static void main(String args[]) {
        String text = getText();
        //countWordFrequency(text);
        countSentenceLength(text);
    }

    private static String getText() {
        try {
            return FileUtils.readFileToString(new File("manuscript/chapter2.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getTextFromCLipboard() {
        String data = "";
        try {
            data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void countWordFrequency(String text) {
        text = cleanInterpuncion(text, new String[]{".", ",", "!", "?", "(", ")", "\r", "\n"});

        String words[] = text.split(" ");
        StringCounter wordCounter = new StringCounter();

        for (String word : words) {
            //wordCounter.incrementCount(word.toLowerCase());
        }

        for (int i = 2; i < words.length; i++) {
            wordCounter.incrementCount((words[i - 2] + " " + words[i-1] + " " + words[i]).toLowerCase());
        }

        wordCounter.printWithNumbering();
    }

    private static void countSentenceLength(String text) {
        text = text.replace("e.g.", " ");
        text = text.replace("i.e.", " ");
        text = text.replace("et. al", " ");
        text = text.replace("vs.", "vs");
        text = text.replace("\t", " ");
        text = cleanInterpuncion(text, new String[]{",", "(", ")", "\r", "\t", "\"", "“", "”"});

        String sentences[] = text.split("(\\. |\\.\\*|\\! |\\? |\"|:|\\n|\\•)");
        StringCounter sentenceCounter = new StringCounter();

        for (String sentence : sentences) {
            int length = sentence.split(" ").length;
            sentenceCounter.incrementCount(length + "\t" + sentence, length);
        }

        sentenceCounter.printWithNumbering();
    }

    private static String cleanInterpuncion(String text, String interpunction[]) {
        for (String block : interpunction) {
            while (text.contains(block)) {
                text = text.replace(block, " ");
            }
        }
        while (text.contains("  ")) {
            text = text.replace("  ", " ");
        }
        return text;
    }
}