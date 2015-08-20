package com.sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/** 
 * @author 蒋家鑫  E-mail: samjjx@hotmail.com 
 * @version 创建时间：2015-8-8 上午1:44:19 
 * 类说明 
 */
public class Check {

	/**
	 * @param args
	 */
	String dataset="";
	HashMap<Integer, ArrayList<Integer>> bigGraph=new HashMap<Integer,ArrayList<Integer>>(); ;
	HashMap<Integer, ArrayList<Integer>> labelIn;
	HashMap<Integer, ArrayList<Integer>> labelOut;
	public Check(String dataset)
	{
		this.dataset=dataset;
	}
	public void start(int times,int NorL) throws NumberFormatException, IOException
	{
		labelIn=getLabel(dataset+"(in).txt", NorL);
		labelOut=getLabel(dataset+"(out).txt", NorL);
		SnapReader sr=new SnapReader("dataset/"+dataset+".txt");
		this.bigGraph=sr.eliminateSCC(sr.format(sr.bigGraph));
//		System.out.println("labelIn is: "+labelIn);
//		System.out.println("labelOut is: "+labelOut);
//		System.out.println("graph is :"+bigGraph);
		System.out.println("load finish,start checking");
		int nodes=bigGraph.size();
		Random rand=new Random();
		for(int i=0;i<times;i++)
		{
			int a=rand.nextInt(nodes);
			int b=rand.nextInt(nodes);
			if(a==b)
				continue;
			System.out.println("a is :"+a+"; b is :"+b);
			System.out.println("The "+i+" time check:");
			boolean cL=checkLabel(a, b);
			boolean cg=checkGraph(a, b);
			System.out.println("Label is "+cL+" ;Graph is "+cg);
			System.out.println("*******************************************");
			if(cL!=cg)
			{
				printRelate(a, b);
				break;
			}
		}
		
	}
	public boolean checkLabel(int a,int b)
	{
		ArrayList<Integer> out=labelOut.get(a);
		ArrayList<Integer> in=labelIn.get(b);
		if(a==3&&b==4)
			pase();
		for(int i=0;i<out.size();i++)
			if(in.contains(out.get(i)))
				return true;
		return false;
	}
	public void pase()
	{
		return ;
	}
	public boolean checkGraph(int a,int b)
	{
		ArrayList<Integer> que=new ArrayList<Integer>();
		que.add(a);
		for(int i=0;i<que.size();i++)
		{
			ArrayList<Integer> tempArrayList=bigGraph.get(que.get(i));
			for(int j=0;j<tempArrayList.size();j++)
				if(!que.contains(tempArrayList.get(j)))
					que.add(tempArrayList.get(j));
		}
		if(que.contains(b))
			return true;
		else
			return false;
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
	public void printRelate(int a,int b)
	{
		System.out.println("Error is here:");
		System.out.println(a+" out set:"+labelOut.get(a));
		System.out.println(b+" in set:"+labelIn.get(b));
		System.out.println("Showing the path:");
		ArrayList<Integer> que=new ArrayList<Integer>();
		que.add(a);
		for(int i=0;i<que.size();i++)
		{
			printNode(que.get(i));
			ArrayList<Integer> tempArrayList=bigGraph.get(que.get(i));
			for(int j=0;j<tempArrayList.size();j++)
				if(!que.contains(tempArrayList.get(j)))
					que.add(tempArrayList.get(j));
		}
	}
	public void printNode(int a)
	{
		System.out.println("[ "+a+" ]"+bigGraph.get(a));
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		Check ck=new Check("p2p-Gnutella05");
		//1 stand node-time, 0 stand for level
		ck.start(500000,2);
		
	}

}
