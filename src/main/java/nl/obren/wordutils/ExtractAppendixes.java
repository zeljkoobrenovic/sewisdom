package nl.obren.wordutils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ExtractAppendixes {
    private List<PatternInfo> patterns = new ArrayList();
    private List<SourceInfo> sources = new ArrayList();
    private SourceInfo source;
    private PatternInfo pattern;
    private File generatedAppendixes = new File("manuscript/generated-appendixes.txt");

    public ExtractAppendixes() {
    }

    public static void main(String args[]) throws IOException {
        ExtractAppendixes extractor = new ExtractAppendixes();

        extractor.extractPatterns(new File("manuscript/chapter2.txt"));
        extractor.extractSources(new File("manuscript/chapter3.txt"));
        extractor.save();
    }

    private void extractPatterns(File file) throws IOException {
        List<String> list = FileUtils.readLines(file, StandardCharsets.UTF_8);

        list.forEach(line -> {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("### ")) {
                String title = trimmedLine.substring(3).trim();
                pattern = new PatternInfo();
                patterns.add(pattern);
                pattern.setName(title);
            } else if (pattern != null) {
                if (trimmedLine.startsWith("**Motto**:")) {
                    String motto = trimmedLine.substring(10).trim();
                    pattern.setMotto(motto);
                } else if (trimmedLine.startsWith("**Summary**:")) {
                    String summary = trimmedLine.substring(12).trim();
                    pattern.setSummary(summary);
                }
            }
        });
    }


    private void extractSources(File file) throws IOException {
        List<String> list = FileUtils.readLines(file, StandardCharsets.UTF_8);

        list.forEach(line -> {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("## ")) {
                String title = trimmedLine.substring(6).trim();
                source = new SourceInfo();
                sources.add(source);
                source.setName(title);
            } else if (source != null) {
                if (trimmedLine.startsWith("**Summary**:")) {
                    String summary = trimmedLine.substring(12).trim();
                    source.setSummary(summary);
                }
            }
        });
    }

    private void save() throws IOException {
        StringBuilder content = new StringBuilder("# Appendixes\n" +
                "\n" +
                "* Appendix A. Patterns of Dynamics: Summaries\n" +
                "* Appendix B. Patterns of Dynamics: Mottos\n" +
                "* Appendix C. Sources of Dynamics: Summaries\n" +
                "\n");
        content.append("\n" +
                "## A. Patterns of Dynamics: Summaries\n" +
                "\n");
        patterns.forEach(pattern -> {
            if (StringUtils.isBlank(pattern.getName())) {
                throw new IllegalArgumentException("Pattern name is blank");
            }
            if (StringUtils.isBlank(pattern.getSummary())) {
                throw new IllegalArgumentException("Pattern " + pattern.getName() + " summary is blank");
            }
            content.append("\n")
                    .append("**")
                    .append(pattern.getName())
                    .append("**: ")
                    .append(pattern.getSummary())
                    .append("\n");
        });
        content.append("\n\n\n## B. Patterns of Dynamics: Mottos\n");
        patterns.forEach(pattern -> {
            if (StringUtils.isBlank(pattern.getName())) {
                throw new IllegalArgumentException("Pattern name is blank");
            }
            if (StringUtils.isBlank(pattern.getMotto())) {
                throw new IllegalArgumentException("Pattern " + pattern.getName() + " motto is blank");
            }
            content.append("\n")
                    .append("**")
                    .append(pattern.getName())
                    .append("**: ")
                    .append(pattern.getMotto())
                    .append("\n");
        });

        content.append("\n\n\n## C. Sources of Dynamics: Summaries\n");
        sources.forEach(source -> {
            if (StringUtils.isBlank(source.getName())) {
                throw new IllegalArgumentException("Source name is blank");
            }
            if (StringUtils.isBlank(source.getSummary())) {
                throw new IllegalArgumentException("Source summary is blank");
            }
            content.append("\n")
                    .append("**")
                    .append(source.getName())
                    .append("**: ")
                    .append(source.getSummary())
                    .append("\n");
        });

        String result = content.toString().replaceAll(" \\{#.*?\\}", "");
        result = result.toString().replaceAll("\\{#.*?\\}", "");
        result = result.toString().replace(" **", "**");
        result = result.toString().replace(" **", "**");
        result = result.toString().replace(" **", "**");

        FileUtils.writeStringToFile(generatedAppendixes, result, StandardCharsets.UTF_8);
    }

}

