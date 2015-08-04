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
 * @version 创建时间：2015-8-3 上午10:32:41 
 * 类说明 读取snap数据集
 */
public class SnapReader {

	/**
	 * @param args
	 */
	HashMap<Integer,ArrayList<Integer>> bigGraph=new HashMap<Integer,ArrayList<Integer>>();    // Structure which stores the bigGraph

	int nodes;
	int edges;
	public SnapReader(String filePath) throws IOException
	{
		FileInputStream in=new FileInputStream(filePath);
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String tempLine;
		while((tempLine=br.readLine())!=null)
		{
			if(tempLine.charAt(0)=='#')      //filter the information of the graph
			{
				if(tempLine.substring(0, 8).equals("# Nodes:"))
				{
					String[] graphInfo=tempLine.split(" ");
					nodes=Integer.parseInt(graphInfo[2]);
					edges=Integer.parseInt(graphInfo[4]);
					continue;
				}
				else
					continue;
			}
			String[] vertexPair=tempLine.split("\t");
			int vertex1=Integer.parseInt(vertexPair[0]);
			int vertex2=Integer.parseInt(vertexPair[1]);
			if(bigGraph.containsKey(vertex1))
				bigGraph.get(vertex1).add(vertex2);
			else {
				ArrayList<Integer> vList=new ArrayList<Integer>();
				vList.add(vertex2);
				bigGraph.put(vertex1, vList);
			}
		}
	}
	public HashMap<Integer, ArrayList<Integer>> inverse(HashMap<Integer, ArrayList<Integer>> bigGraph)
	{
		HashMap<Integer, ArrayList<Integer>> bigGraphInverse=new HashMap<Integer, ArrayList<Integer>>();
		Set<Integer> vertexSet=bigGraph.keySet();
		for(int vertexV:vertexSet)
			bigGraphInverse.put(vertexV, new ArrayList<Integer>());
		for(int vertexV:vertexSet)
		{
			ArrayList<Integer> linkList=bigGraph.get(vertexV);
			for(int vertexU:linkList)
			{
				if(bigGraphInverse.containsKey(vertexU))
						bigGraphInverse.get(vertexU).add(vertexV);
				else {
					ArrayList<Integer> uList=new ArrayList<Integer>();
					uList.add(vertexV);
					bigGraphInverse.put(vertexU, uList);
				}
			}
		}
		return bigGraphInverse;
	}
	public HashMap<Integer, ArrayList<Integer>> eliminateSCC(HashMap<Integer, ArrayList<Integer>> bigGraph)
	{
		HashMap<Integer, ArrayList<Integer>> bigGraphScc=new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> bigGraphInverse=inverse(bigGraph);
		DFS dfs=new DFS(bigGraph,nodes);
		dfs.traverse();
		
		DFS dfsInverse=new DFS(bigGraphInverse,nodes);
		dfsInverse.finish=dfs.finish;
		dfsInverse.traverseInverse();
		HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();  //
		int count=0;
		for(int i=0;i<dfsInverse.dfsOrder.size();i++)
		{
			int temp=dfsInverse.dfsOrder.get(i);
			if(temp!=-1)
				map.put(temp, count);
			else 
				count++;
		}
		for(int i=0;i<count;i++)
			bigGraphScc.put(i, new ArrayList<Integer>());
		Set<Integer> keySet=bigGraph.keySet();
		for(int key:keySet)
		{
			ArrayList<Integer> tempArrayList=bigGraph.get(key);
			int vertexV=map.get(key);
			for(int j=0;j<tempArrayList.size();j++)
			{
				int vertexU=map.get(tempArrayList.get(j));
				
				if(!bigGraphScc.get(vertexV).contains(vertexU)&&vertexV!=vertexU)
					bigGraphScc.get(vertexV).add(vertexU);
			}
		}
		return bigGraphScc;
	}
	public HashMap<Integer, ArrayList<Integer>> format(HashMap<Integer, ArrayList<Integer>> bigGraph)
	{
		HashMap<Integer, ArrayList<Integer>> fmGraph=new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, Integer> maping=new HashMap<Integer, Integer>();
		Set<Integer> keySet=bigGraph.keySet();
		int count=0;
		for(int vertexV:keySet)
		{
			if(!maping.containsKey(vertexV))
			{
				maping.put(vertexV, count);
				count++;
			}
			if(!fmGraph.containsKey(maping.get(vertexV)))
				fmGraph.put(maping.get(vertexV), new ArrayList<Integer>());
			ArrayList<Integer> tempArrayList=bigGraph.get(vertexV);
			for(int vertexU:tempArrayList)
			{
				if(!maping.containsKey(vertexU))
				{
					maping.put(vertexU, count);
					count++;
				}
				fmGraph.get(maping.get(vertexV)).add(maping.get(vertexU));
			}
		}
		return fmGraph;
	}
	public void printBigGraph(HashMap<Integer, ArrayList<Integer>> bigGraph)
	{
		Set<Integer> keySet=bigGraph.keySet();
		for(int vertex:keySet)
			System.out.println("[ "+vertex+" ]"+bigGraph.get(vertex));
		
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		SnapReader sr=new SnapReader("dataset/test.txt");
		System.out.println(sr.bigGraph.size());
		HashMap<Integer, ArrayList<Integer>> temp=sr.format(sr.bigGraph);
		System.out.println(temp.size());
		temp=sr.eliminateSCC(temp);
		System.out.println("Scc eliminated");
		//System.out.println(temp);
		PPTopo pptlable=new PPTopo();
		HashMap<Integer, ArrayList<Integer>>[] result=pptlable.CreatePPTopo(temp);
		System.out.println(result[0]);
		System.out.println(result[1]);
	}

}
