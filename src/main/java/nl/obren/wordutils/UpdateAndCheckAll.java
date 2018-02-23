package nl.obren.wordutils;

import java.io.IOException;

public class UpdateAndCheckAll {
    public static void main(String args[]) throws IOException {
        ExtractAppendixes.main(args);
        FindBrokenLines.main(args);
        FindBrokenReferences.main(args);
    }
}
