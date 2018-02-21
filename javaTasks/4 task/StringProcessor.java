package stringprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;

public class StringProcessor {
	public String readInputText() throws IOException {
                //add error handling
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	System.out.print("Enter String: ");
        	return br.readLine();
	}

	public String processText(String text) {
            System.out.print("Enter size of word to find: ");
            Scanner sizeOfWord = new Scanner(System.in);
            int sizeToFind = sizeOfWord.nextInt();
            
            HashSet<String> foundWords = new HashSet();
            
            String[] sentences = text.split("[.\\!]");
            for(String sentence: sentences) {
                //split to questions with '?' in the end
                String[] questions = sentence.split("(?<=\\?)");                         
                for(String question:questions) {
                    if(question.endsWith("?")) {
                        //get rid of '?'
                        question = question.substring(0, question.indexOf("?"));
                        //split to words
                        String[] words = question.split("\\s+");
                        for(String word:words) {
                            word = word.replaceAll("\"", "");
                            word = word.replaceAll(",$", "");
                            if(word.length() == sizeToFind) {
                                foundWords.add(word);
                            }
                        }
                    }
                }
            }
            return foundWords.toString();    
        }

	public void showResult(String resultText) {
		System.out.println("Found: " + resultText);
	}
}