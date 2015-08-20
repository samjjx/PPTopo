package com.sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/** 
 * @author 蒋家鑫  E-mail: samjjx@hotmail.com 
 * @version 创建时间：2015-8-8 下午11:04:20 
 * 类说明 
 */
public class Statistic {

	HashMap<Integer, ArrayList<Integer>> labelIn;
	HashMap<Integer, ArrayList<Integer>> labelOut;
	String dataset="";
	public Statistic(String dataset,int NorL) throws IOException
	{
		this.dataset=dataset;
		labelIn=getLabel(dataset+"(in).txt", NorL);
		labelOut=getLabel(dataset+"(out).txt", NorL);
	}
	public void printInfo()
	{
		int disCenterIn=disCenter(labelIn);
		int disCenterOut=disCenter(labelOut);
		int disCenterTotal=disCenterTotal(labelIn,labelOut);
		int labelInSize=labelSize(labelIn);
		int labelOutSize=labelSize(labelOut);
		System.out.println("**********************************************");
		System.out.println("The graph is: "+dataset);
		System.out.println("The distinct centers in labelIn is "+disCenterIn);
		System.out.println("The distinct centers in labelOut is "+disCenterOut);
		System.out.println("The distinct centers in two labels is "+disCenterTotal);
		System.out.println("The size of the labelIn is :"+ labelInSize);
		System.out.println("The size of the labelOut is :"+ labelOutSize);
		
	}
	public int disCenter(HashMap<Integer, ArrayList<Integer>> label)
	{
		ArrayList<Integer> center=new ArrayList<Integer>();
		Set<Integer> keySet=label.keySet();
		for(int key:keySet)
		{
			ArrayList<Integer> temp=label.get(key);
			for(int vertex:temp)
				if(!center.contains(vertex))
					center.add(vertex);
		}
		return center.size();
	}
	public int disCenterTotal(HashMap<Integer, ArrayList<Integer>> labelIn,HashMap<Integer, ArrayList<Integer>> labelOut)
	{
		ArrayList<Integer> center=new ArrayList<Integer>();
		Set<Integer> keySet=labelIn.keySet();
		for(int key:keySet)
		{
			ArrayList<Integer> temp=labelIn.get(key);
			for(int vertex:temp)
				if(!center.contains(vertex))
					center.add(vertex);
		}
		keySet=labelOut.keySet();
		for(int key:keySet)
		{
			ArrayList<Integer> temp=labelOut.get(key);
			for(int vertex:temp)
				if(!center.contains(vertex))
					center.add(vertex);
		}
		return center.size();
	}
	public int labelSize(HashMap<Integer, ArrayList<Integer>> label)
	{
		int count=0;
		Set<Integer> keySet=label.keySet();
		for(int key:keySet)
			count+=label.get(key).size();
		return count;
	}
	public double averageSize()
	{
		return 0;
	}
	@SuppressWarnings("resource")
	public HashMap<Integer, ArrayList<Integer>> getLabel(String dataset,int LorN) throws IOException
	{
		HashMap<Integer, ArrayList<Integer>> Label=new HashMap<Integer, ArrayList<Integer>>();
		FileInputStream in;
		if(LorN==0)
			in=new FileInputStream("labellevel/"+dataset);
		else if(LorN==1)
			in=new FileInputStream("label/"+dataset);
		else
			in=new FileInputStream("labelByFS/"+dataset);
		
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String tempLine;
		while((tempLine=br.readLine())!=null)
		{
			String[] array=tempLine.split(" ");
			Label.put(Integer.parseInt(array[0]), new ArrayList<Integer>());
			for(int i=1;i<array.length;i++)
				Label.get(Integer.parseInt(array[0])).add(Integer.parseInt(array[i]));
		}
		return Label;
	}
	public static void main(String[] args) throws IOException {
		ArrayList<String> dataList=new ArrayList<String>();
		dataList.add("p2p-Gnutella04");
		dataList.add("p2p-Gnutella05");
		dataList.add("p2p-Gnutella06");
		dataList.add("p2p-Gnutella08");
		dataList.add("p2p-Gnutella09");
		dataList.add("p2p-Gnutella24");
		dataList.add("p2p-Gnutella25");
		dataList.add("p2p-Gnutella30");
		dataList.add("Wiki-Vote");
		for(String dataset:dataList)
		{
			Statistic st=new Statistic(dataset, 0);
			st.printInfo();
		}
		for(String dataset:dataList)
		{
			Statistic st=new Statistic(dataset, 2);
			st.printInfo();
		}
		dataList.clear();
		dataList.add("p2p-Gnutella04");
		dataList.add("p2p-Gnutella05");
		dataList.add("p2p-Gnutella06");
		dataList.add("p2p-Gnutella08");
		dataList.add("p2p-Gnutella09");
		dataList.add("Wiki-Vote");
		for(String dataset:dataList)
		{
			Statistic st=new Statistic(dataset, 1);
			st.printInfo();
		}
	}

}
