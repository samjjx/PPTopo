package com.sample;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * @author 蒋家鑫  E-mail: samjjx@hotmail.com 
 * @version 创建时间：2015-8-6 下午5:31:14 
 * 类说明 
 */
public class CreateAll {

	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		ArrayList<String> dataList=new ArrayList<String>();
		dataList.add("dataset/test.txt");
//		dataList.add("dataset/p2p-Gnutella04.txt");
//		dataList.add("dataset/p2p-Gnutella05.txt");
//		dataList.add("dataset/p2p-Gnutella06.txt");
//		dataList.add("dataset/p2p-Gnutella08.txt");
//		dataList.add("dataset/p2p-Gnutella09.txt");
		dataList.add("dataset/p2p-Gnutella24.txt");
		dataList.add("dataset/p2p-Gnutella25.txt");
		dataList.add("dataset/p2p-Gnutella30.txt");
		//dataList.add("dataset/p2p-Gnutella31.txt");
//		dataList.add("dataset/Wiki-Vote.txt");
		PrintStream out = System.out;
		for(String dataSet:dataList)
		{
			SnapReader sr=new SnapReader(dataSet);
			HashMap<Integer, ArrayList<Integer>> temp=sr.format(sr.bigGraph);
			temp=sr.eliminateSCC(temp);
			System.out.println(dataSet+" Scc eliminated");
			System.out.println(temp.size());
			PPTopo pptlable=new PPTopo();
			HashMap<Integer, ArrayList<Integer>>[] label=pptlable.CreatePPTopoNode(temp);
//			System.out.println(label[0]);
//			System.out.println(label[1]);
			sr.storeLabel(label[0], sr.labelPath,"in");
			sr.storeLabel(label[1], sr.labelPath,"out");
			System.setOut(out);
			System.out.println(dataSet+" finished");
		}
	}

}
