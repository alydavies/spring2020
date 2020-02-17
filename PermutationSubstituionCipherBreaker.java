import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PermutationSubstituionCipherBreaker {
	public static void main(String [] args ) {
		// Reading in ciphertext and frequency statistics, creating an
		char[] cypherTextArray = readCypherText("src/ciphertext.txt");
		char[] englishFrequency = readFrequency("src/englishletterfreq.txt");
		ArrayList<CharacterFrequency> charFreq = new ArrayList<CharacterFrequency>();

		// Initialize the characters in Arraylist charFreq, assuming index 0 is 'a' and
		//				index 26 is 'z' in the ciphertext
		char charIndex = 97;
		for (int i = 0; i <= 25; i++) {
			charFreq.add(new CharacterFrequency((charIndex)));
			charIndex++;
		}

		// Initalizing the frequency of each letter seen
		int[] letterFrequency = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		// Iterating through the ciphertext and documenting frequency of letters.
		// - iterating -1 length to prevent reading EOF character
		for (int i = 0; i < cypherTextArray.length - 1; i++) { // -1 to offset EOF character
			char letter = cypherTextArray[i];
			int index = charToIntIndex(letter);
			letterFrequency[index] = letterFrequency[index] + 1;
		}

		// Load in the frequency text file into char array
		char temp = 97;
		for (int index = 0; index <= charFreq.size(); index++) {
			if (temp != '{') {
				charFreq.get(index).setFrequency(letterFrequency[index]);
				temp++;
			}
		}

		// Sort the collection by character freuqency
		Collections.sort((List) charFreq);

		// Initializing decoded values for characters, strictly based on frequency.
		System.out.println("Values, Frequency, and Suspected Decode Value\n");
		int englishFreqIndex = 0;
		for (CharacterFrequency updateDecodeChar : charFreq) {
			updateDecodeChar.setDecodedValue(englishFrequency[englishFreqIndex]);
			;
			System.out.println(updateDecodeChar.toString());
			englishFreqIndex++;
		}

		// Reprinting out decoded cypertext.
		int linebreakcontrol = 0;
		for (char cypherTextDecoding : cypherTextArray) {
			for (CharacterFrequency charFreqDecoding : charFreq) {
				Character tempToEnsureLC = (Character) cypherTextDecoding;
				char lcCyperText = tempToEnsureLC.toLowerCase(tempToEnsureLC);
				if (charFreqDecoding.getOriginalValue() == lcCyperText) {
					if (linebreakcontrol % 25 == 0) {
						System.out.println(charFreqDecoding.getDecodedValue());
					} else {
						System.out.print(charFreqDecoding.getDecodedValue());
					}
				}
			}
			linebreakcontrol++;
		}
	}

	// Utilizes the cypherText buffer to return similar good.
	private static char[] readFrequency(String absolutePath) {
			char [] freqLetters = readCypherText(absolutePath);
			return freqLetters;
	}

	// Read in file using BufferedReader
	private static char[] readCypherText(String absolutePath) {
		char [] buffer = new char[10];
		StringBuilder fileRecord = new StringBuilder();
		int numRead = 0;

		try {
			BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(absolutePath));
			// If -1, index at EOF
		 	while ((numRead = bufferedReader.read(buffer)) != -1) {
//				System.out.println(numRead);
				String readData = String.valueOf(buffer, 0, numRead);
				fileRecord.append(readData);
				buffer = new char [1024];
		 	}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileRecord.toString().toCharArray();
	}

	// Helperfunction
	private static int charToIntIndex(char letter) {
		char lowerLetter = Character.toLowerCase(letter);
		switch (lowerLetter) {
			case 'a' : return 0;
			case 'b' : return 1;
			case 'c' : return 2;
			case 'd' : return 3;
			case 'e' : return 4;
			case 'f' : return 5;
			case 'g' : return 6;
			case 'h' : return 7;
			case 'i' : return 8;
			case 'j' : return 9;
			case 'k' : return 10;
			case 'l' : return 11;
			case 'm' : return 12;
			case 'n' : return 13;
			case 'o' : return 14;
			case 'p' : return 15;
			case 'q' : return 16;
			case 'r' : return 17;
			case 's' : return 18;
			case 't' : return 19;
			case 'u' : return 20;
			case 'v' : return 21;
			case 'w' : return 22;
			case 'x' : return 23;
			case 'y' : return 24;
			case 'z': return 25;
			default:
				throw new IllegalStateException("Unexpected value: " + lowerLetter);
			}
	}
	}

	// CharacterFrequency Simple Object to track all required values.
	class CharacterFrequency implements Comparable {
	int frequency;
	char originalValue;
	char decodedValue;

	// Converts ciphertext based on frequency
	public char decodeChar(char cipherText) {
		 if (cipherText == originalValue) {
				 return decodedValue;
		 }
		 else {
				 return ' ';
		 }
	}

	public int getFrequency() {
			return frequency;
	}

	public void setFrequency(int frequency) {
			this.frequency = frequency;
	}

	public char getOriginalValue() {
			return originalValue;
	}

	public char getDecodedValue() {
			return decodedValue;
	}

	public void setDecodedValue(char decodedValue) {
			this.decodedValue = decodedValue;
	}


	CharacterFrequency(char ogValue) {
			this.frequency = 0;
			this.originalValue = ogValue;
			this.decodedValue = ' ';
	}

	@Override
	public String toString() {
			return ("Original Value: " + this.originalValue + " Frequency: " + this.frequency
							+ " Decode Value: " + this.decodedValue);
	}

	@Override
	public int compareTo(Object o) {
			CharacterFrequency cf = (CharacterFrequency) o;
			int compareFreq = cf.getFrequency();
			return compareFreq-this.frequency;
	}
}
