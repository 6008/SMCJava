package edu.ucr.watsoncui.algorithm.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;

public class BinTask {

	public static void printUsage() {
		System.out.println("java -jar SMCbin.jar abundance_species_equal.txt -o output.bin -w 6");
		System.out.println("The sequences should be pair end. Param w should be integer.");
	}
	
	public static String getTransReverse(String origin) {
		char[] charList = origin.toUpperCase().toCharArray();
		for (int i = 0; i < charList.length; i++) {
			switch (charList[i]) {
			case 'A':
				charList[i] = 'T';
				break;
			case 'C':
				charList[i] = 'G';
				break;
			case 'G':
				charList[i] = 'C';
				break;
			case 'T':
				charList[i] = 'A';
				break;
			default:
				break;	
			}
		}
		return (new StringBuilder(String.valueOf(charList))).reverse().toString();
	}
	
	public static TrieBuilder fillTrieBuilder(TrieBuilder tb, String content[], Integer windowLength) {
		
		for (int l = 0; l < content.length; l++) {
			if (windowLength >= content[l].length()) {
				tb.addKeyword(content[l]);
			} else {
				for (int i = 0; i < content[l].length() - windowLength; i++) {
					tb.addKeyword(content[l].substring(i, i + windowLength));
				}
			}
		}
		
		return tb;
		
	}
	
	public static void mainJob(String inputFileName, String outputFileName, Integer windowLength) throws Exception {
		
		List<TrieBuilder> trieList = new ArrayList<TrieBuilder>();
		List<List<Integer>> doubleReadList = new ArrayList<List<Integer>>();
		
		//Read sequences from data file, compare and make trie and put into different bins.  

		Integer sequenceCount = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFileName)));
		
		String line = null;
		
		while (null != (line = br.readLine())) {
			
			String seq1 = line;
			
			
			if (null == (line = br.readLine())) {
				break;
			}
			
			String seq2 = line;
			
			sequenceCount++;
			
			String pairEnds[] = {seq1, seq2, getTransReverse(seq1), getTransReverse(seq2)};
			
			boolean binFound = false;
			
			for (int i = 0; i < trieList.size(); i++) {
				Trie trie = trieList.get(i).build();
				for (int j = 0; j < pairEnds.length; j++) {
					if (trie.containsMatch(pairEnds[j])) {
						binFound = true;
						TrieBuilder tb = fillTrieBuilder(trieList.get(i), pairEnds, windowLength);
						trieList.set(i, tb);
						List<Integer> temp = doubleReadList.get(i);
						temp.add(sequenceCount);
						doubleReadList.set(i, temp);
						break;
					}
				}
				if (true == binFound) {
					break;
				}
			}
			
			if (false == binFound) {
				trieList.add(fillTrieBuilder(Trie.builder(), pairEnds, windowLength));
				List<Integer> temp = new ArrayList<Integer>();
				temp.add(sequenceCount);
				doubleReadList.add(temp);
			}
			
			System.out.println("" + sequenceCount + "\t" + doubleReadList.size());
		}
		
		br.close();
		
		//Sort doubleReadList according to length of each List<DoubleRead>
		
		Collections.sort(doubleReadList, new Comparator<List<Integer>>(){
		    public int compare(List<Integer> a1, List<Integer> a2) {
		        return a2.size() - a1.size(); // assumes you want biggest to smallest
		    }
		});
		
		//Output each doubleReadList
		
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFileName)));
		
		for (int i = 0; i < doubleReadList.size(); i++) {
			
			for (int j = 0; j < doubleReadList.get(i).size(); j++) {
				pw.print(doubleReadList.get(i).get(j));
				pw.print("\t");
			}
			pw.println();
		}
		pw.close();
	}
	
	public static void main(String[] args) throws Exception {

		String outputFileName = "output.bin";
		Integer w = 36;
		
		if (0 == args.length) {
			printUsage();
			return;
		}
		
		if (1 == args.length) {
			mainJob(args[0], outputFileName, w);
		} else {
			
			int pos = 0;
			while (pos < args.length - 1) {
				if (args[pos].charAt(0) == '-') {
					switch (args[pos].charAt(1)) {
					case 'o':
					case 'O':
						outputFileName = args[pos + 1];
						break;
					case 'w':
					case 'W':
						try {
							w = Integer.parseInt(args[pos + 1]);
						} catch (NumberFormatException nfe) {
							System.out.println("Param window_length parse error! Use default value 36");
							w = 36; 
						}
						break;
					default:
						break;
					}
				}
				pos += 2;
			}
			mainJob(args[0], outputFileName, w);
		}
	}

}
