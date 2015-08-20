package com.sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
	String labelPath="";
	public SnapReader(String filePath) throws IOException
	{
		labelPath=labelStorePath(filePath);
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
	public String labelStorePath(String Path)
	{
		String[] temp=Path.split("/");
		return temp[1].split(".txt")[0];
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
	public void printLabel(HashMap<Integer, ArrayList<Integer>> Label)
	{
		printBigGraph(Label);
	}
	public int labelSize(HashMap<Integer, ArrayList<Integer>> Label)
	{
		int count=0;
		Set<Integer> keySet=Label.keySet();
		for(int key:keySet)
			count+=Label.get(key).size();
		return count;
	}
	public void storeLabel(HashMap<Integer, ArrayList<Integer>> Label,String path,String outIn) throws FileNotFoundException 
	{
		path="labellevel/"+path+"("+outIn+").txt";
		 PrintStream out = new PrintStream(path);  
		 System.setOut(out);
		 Set<Integer> keySet=Label.keySet();
		 for(int vertex:keySet)
		 {
			 System.out.print(vertex+" ");
			 for(int center:Label.get(vertex))
				 System.out.print(center+" ");
			 System.out.println();
		 }
		 System.setOut(System.out);
	}
	public int disCenter(HashMap<Integer, ArrayList<Integer>> Label)
	{
		Set<Integer> keySet=Label.keySet();
		ArrayList<Integer> center=new ArrayList<Integer>();
		for(int key:keySet)
		{
			ArrayList<Integer> temp=Label.get(key);
			for(int vertex:temp)
				if(!center.contains(vertex))
					center.add(vertex);
		}
		return center.size();
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		SnapReader sr=new SnapReader("dataset/test.txt");
		HashMap<Integer, ArrayList<Integer>> temp=sr.format(sr.bigGraph);
		temp=sr.eliminateSCC(temp);
		System.out.println("Scc eliminated");
		PPTopo pptlable=new PPTopo();
		HashMap<Integer, ArrayList<Integer>>[] label=pptlable.CreatePPTopo(temp);
		System.out.println(label[0]);
		System.out.println(label[1]);

		PrintStream out = System.out;
		sr.storeLabel(label[0], sr.labelPath,"in");
		sr.storeLabel(label[1], sr.labelPath,"out");
		System.setOut(out);
		System.out.println("return");
		
	}

}
