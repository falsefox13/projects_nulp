package stringprocessor;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        StringProcessor proc = new StringProcessor();   
        String text = proc.readInputText();
        proc.showResult(proc.processText(text));
    }
}
