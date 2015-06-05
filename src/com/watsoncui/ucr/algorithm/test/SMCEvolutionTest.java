package com.watsoncui.ucr.algorithm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.watsoncui.ucr.algorithm.DoubleRead;
import com.watsoncui.ucr.algorithm.SMCEvolution;

public class SMCEvolutionTest {
	
	public final static String[] DATALISTA = {
		"ATGATATTAAAGTAAGTTTCAGTGTTTTTAGAATTAATATATAAGTAATATTGTGAACTAACATAGTTTTAATATTGAGAGACGCTGTTATATTTAAATA",
		"ATCTTTAATCTCTTTTTGTCTAGTTTCTATTATAAATATTACTGAAGGCCAAACATATTTCATGCAAAAGAAAACAAATAAAACAAAAGATATAGCTTGG",
		"GAGATTCTTTTGTTTGCAAGTTTTCTAAGTTTTTTGGAACATGTTTAGATGTATGATTTTCTGTATTGTTATTGTGTTTAATGTCATTATATGTCCCAAT",
		"GCATCTATTTCTTTTTTCTTAGATTTAAATAAACCCGATAAAAAACCAAAAGAACCATGTTTAGTTTGTTTCTCATTCATAGATAAATTCTTTTCTAATT",
		"ATACGCAACCTAATGAAGTTAACTATGCTTATGAACGCCTTTCTTCCGTCGGACCAGAATTTATTATAGCTGCGTCATTTGGAAACGTACATGGAGTATA",
		"TTATGGTTTTGAGAAGTAATCATAACAGTGTTAGATTTTAATTCTTTAACAGGATGATTGCTACCATGATGACCAAATTTCATTTTTATTATTTTTGCTC",
		"TTTTTTAATTTTATTAGGTACGCTGTGTTTTGAAAGCATTAATTTTGAACTTTCTAGAATAGAATTAATATTTTTAGAAAAAATTACTCCTTTTAATTTT",
		"AATTAATACTATAAATATAAGCATTTAAATTCTATTTGATTTATAGTAAAATTAAATTGTATTTCTGAACATGTGAAATACATATCAATAATATTTGTAT",
		"TACTCTAAATTTCTTATTAAAAATATGCATATTTAATTTATGATTTATGCGTTTATTCCAGTTCATGCAGTTTCTGCGTTATAAAATTGCTGGCTATGTA",
		"TAGATCAGACCAATGTTTTTTATTAGTATTTAAAACTCCATTGCGTTCACCAATTAGTCCTAAATTAGTGGATTTCCCAAGTTCAAATTTAGAATAAATA"
	};
	
	public final static String[] DATALISTB = {
		"GCATTGGAAAGGATCGGCATCTTGTTTGATGGGGATGGAATGAGGAATGTGGTTACCCAACTCTACCAACCCAACAAGGTGAAAAGTGGTCAATATCAAC",
		"GAAAGACTTGAAAAGTTCTTTGGTGGGGTCAAAAATTTAAAAAGACTTCCTAATCTAATAGTTATAGATGATCCTGTTTATGAAAAAAATGCAGTTTTAG",
		"AGTGAGTTTTACTGACAAGAAACTCATGGGCTTTTTGTGGATCAGAGCCAATCTGAACAAGCTGCTATTAGCTTTTGAAATTTATATCAGGATTTAATTA",
		"ACTGGTGACAAATTAGAACAAGTAATTATTGATAAGAAAAAAATGAATTTCTTATACAAAGATGGTAATAACTTTGTTTTTATGGATCAAAAAGACTACA",
		"ACTTTTAACTTATCGAAAGAATAACCTTGGTTATGTCTATCAGCGTTATAATCTGATTGAATTGTTAAGTGCTTATGATAATATTGCCATTTCACAAAAC",
		"TTGTAATTTGCTAAATTTAGGATTTCTTTTGTTATTTCTAAATACTCATTTAGATATTTTTTACTTGGTGATGATACTAATGATATTGGCAATTTTTCAT",
		"GTTATTGTTTCTGTTCTTATTGCTTTTGTTAACATTGGGGCAAGACCTGCTAATTTAAACCCTAATACTGCTAGTTTAGTTTTAGGAATCATGATGTATT",
		"AACTCACTCATGTTCAAGGTACTGATCTAATTAATGTTGATCAGATTACCGATACTAATTTAGGAAATGGCTTAATAAATTATGCTGGTTTATCACGCTT",
		"CTAATCCAGCAGCATGGGAATACTGAGCACCAATGGTGATGTTAATAGGTAAAGTTTTGTATTTAGCATCTATCTGACTACCTTTTTCATTACCATTTCA",
		"AACTTTCAGCATTGAATCATATTTTTCCTTTTGGTTAGGATCACTTAATATTGAATAAGCATTATTAATTTCAACAAAAGTTTGCGAACCTAATTTATTT"
	};
	
	public final static char[] CHARLIST = {'A', 'C', 'G', 'T'};
	
	public static void getPermutationsTest() {
		int transOrderParam = 2;
		List<String> permutationList = SMCEvolution.getPermutations(CHARLIST, transOrderParam + 1);
		for (int i = 0; i < permutationList.size(); i++) {
			System.out.println(permutationList.get(i));
		}
		System.out.println(permutationList.size());
	}
	
	public static void getGlobalCountListTest() {
		int transOrderParam = 1;
		List<String> permutationList = SMCEvolution.getPermutations(CHARLIST, transOrderParam + 1);
		List<DoubleRead> drList = new ArrayList<DoubleRead>();
		for (int i = 0; i < DATALISTA.length / 2; i++) {
			drList.add(new DoubleRead(DATALISTA[i * 2], DATALISTA[i * 2 + 1], transOrderParam + 1, permutationList));
		}
		for (DoubleRead dr : drList) {
			for (int i = 0; i < dr.getCountList().size(); i++) {
				System.out.print(dr.getCountList().get(i));
				System.out.print("\t");
			}
			System.out.println();
		}
		List<Integer> globalCountList = SMCEvolution.getGlobalCountList(drList);
		for (int i = 0; i < globalCountList.size(); i++) {
			System.out.print(globalCountList.get(i));
			System.out.print("\t");
		}
		System.out.println();
	}

	public static void getEfficiencyTest() {
		List<Double> dataList = new ArrayList<Double>();
		int length = 100;
		for (int i = 1; i < length; i++) {
			dataList.add(1.0 / length);
		}
		System.out.println(SMCEvolution.getEfficiency(dataList));
	}
	
	public static void processTest() {
		int readsParam = DATALISTA.length;
		int samplesParam = 10;
		int transOrderParam = 1;
		List<List<Integer>> zMatrix = new ArrayList<List<Integer>>(readsParam);
		List<Integer> sampleList = new ArrayList<Integer>(samplesParam);
		for (int j = 0; j < samplesParam; j++) {
			sampleList.add(1);
		}
		zMatrix.add(sampleList);
		for (int i = 1; i < readsParam; i++) {
			List<Integer> samplesList = new ArrayList<Integer>(samplesParam);
			for (int j = 0; j < samplesParam; j++) {
				samplesList.add(0);
			}
			zMatrix.add(samplesList);
		}
		List<Double> weightList = new ArrayList<Double>(samplesParam);
		for (int j = 0; j < samplesParam; j++) {
			weightList.add(1.0 / samplesParam);
		}
		List<String> permutationList = SMCEvolution.getPermutations(CHARLIST,
				transOrderParam + 1);
		
		List<Integer> countList = (new DoubleRead(1, DATALISTA[0], DATALISTA[1],
				transOrderParam + 1, permutationList)).getCountList();
		
		List<DoubleRead> doubleReadList = new ArrayList<DoubleRead>(readsParam);
		for (int i = 0; i < DATALISTA.length / 2; i++) {
			doubleReadList.add(new DoubleRead(i + 1, DATALISTA[i * 2], DATALISTA[i * 2 + 1],
					transOrderParam + 1, permutationList));
		}
		for (int i = 0; i < DATALISTB.length / 2; i++) {
			doubleReadList.add(new DoubleRead(i + DATALISTB.length / 2 + 1, DATALISTB[i * 2], DATALISTB[i * 2 + 1],
					transOrderParam + 1, permutationList));
		}
		
		for (int k = 0; k < doubleReadList.size(); k++) {
			for (int i = 0; i < doubleReadList.get(k).getCountList().size(); i++) {
				System.out.print(doubleReadList.get(k).getCountList().get(i));
				System.out.print('\t');
			}
			System.out.println();
			}
		
		List<Integer> globalCountList = SMCEvolution.getGlobalCountList(doubleReadList);
		
		
		
		int maxGroupId = 1;
		List<Map<Integer, List<Integer>>> sampleCountSum = new ArrayList<Map<Integer, List<Integer>>>(
				samplesParam);
		for (int i = 0; i < samplesParam; i++) {
			Map<Integer, List<Integer>> groupVectorMap = new TreeMap<Integer, List<Integer>>();
			groupVectorMap.put(1, doubleReadList.get(0).getCountList());
			sampleCountSum.add(groupVectorMap);
		}
		
		int readId = 1;
		int sampleId = 0;
		List<List<Double>> startP = SMCEvolution.startProbability(
				sampleCountSum.get(sampleId), doubleReadList, zMatrix,
				globalCountList, readId, sampleId, maxGroupId);
		
		for (int i = 0; i < startP.size(); i++) {
			for (int j = 0; j < startP.get(i).size(); j++) {
				System.out.print(startP.get(i).get(j));
				System.out.print('\t');
			}
			System.out.println();
		}
		
		int groupIdA = 1;
		int groupIdB = 2;
		System.out.println(sampleCountSum.get(sampleId).containsKey(groupIdA));
		System.out.println(sampleCountSum.get(sampleId).containsKey(groupIdB));
		List<Integer> accuCountList = sampleCountSum.get(sampleId).get(groupIdA);
		for (int i = 0; i < accuCountList.size(); i++) {
			System.out.print(accuCountList.get(i));
			System.out.print('\t');
		}
		System.out.println();
		accuCountList = globalCountList;
		for (int i = 0; i < accuCountList.size(); i++) {
			System.out.print(accuCountList.get(i));
			System.out.print('\t');
		}
		System.out.println();
		List<Double> transProbList = SMCEvolution.transProbability(
				sampleCountSum.get(sampleId), doubleReadList, zMatrix,
				globalCountList, readId, sampleId, groupIdA);
		for (int i = 0; i < transProbList.size(); i++) {
			System.out.print(transProbList.get(i));
			System.out.print('\t');
		}
		System.out.println();
		transProbList = SMCEvolution.transProbability(
				sampleCountSum.get(sampleId), doubleReadList, zMatrix,
				globalCountList, readId, sampleId, groupIdB);
		for (int i = 0; i < transProbList.size(); i++) {
			System.out.print(transProbList.get(i));
			System.out.print('\t');
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getPermutationsTest();
		//getGlobalCountListTest();
		//getEfficiencyTest();
		processTest();
	}

}
