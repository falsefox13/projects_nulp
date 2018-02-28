package stringprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StringProcessor {
    public String readInputText() throws IOException {
        //add error handling
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String: ");
        return br.readLine();
    }

    public Set<String> processText(String text) {
        String[] sentences = text.split("[.\\!]");
        return processSentences(sentences);
    }

    public Set<String> processSentences(String[] sentences) {
        String[] questions = null;
        for(String sentence: sentences) {
            //split to questions with '?' in the end
            questions = sentence.split("(?<=\\?)");
        }
       return processQuestions( questions);
    }

    public Set<String> processQuestions(String[] questions) {
        String[] words = null;
        for(String question:questions) {
            if(question.endsWith("?")) {
                //get rid of '?'
                question = question.substring(0, question.indexOf("?"));
                //split to words
                words = question.split("\\s+");
            }
        }
       return processWords(words);
    }

    public Set<String> processWords(String[] words) {
        System.out.print("Enter size of word to find: ");
        Scanner sizeOfWord = new Scanner(System.in);
        int sizeToFind = sizeOfWord.nextInt();
        Set<String> foundWords = new HashSet();

        for(String word:words) {
            word = word.replaceAll("\"", "");
            word = word.replaceAll(",$", "");
            if(word.length() == sizeToFind) {
                foundWords.add(word);
            }
        }
        return foundWords;
    }

    public void showResult(Set<String> resultText) {
        System.out.println("Found: " + resultText.toString());
    }
}