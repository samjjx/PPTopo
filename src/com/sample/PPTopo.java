package com.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/** 
 * @author 蒋家鑫  E-mail: samjjx@hotmail.com 
 * @version 创建时间：2015-8-3 下午2:55:23 
 * 生成pptopo标签
 */
public class PPTopo {

	/**
	 * @param args
	 */
	public void createTopoNumber(HashMap<Integer, ArrayList<Integer>> dag)
	{
		DFS dfs=new DFS(dag,dag.size());
		dfs.traverse();
		dfs.topoNumber();
	}
	public int desNumber(HashMap<Integer, ArrayList<Integer>> graph, int start)
	{
		ArrayList<Integer> count=new ArrayList<Integer>();
		ArrayList<Integer> que=new ArrayList<Integer>();
		int mark=0;
		for(int i=0;i<graph.get(start).size();i++)
			que.addAll(graph.get(start));
		while(mark<que.size())
		{
			int temp=que.get(mark);
			ArrayList<Integer> tempArrayList=graph.get(temp);
			for(int vertex:tempArrayList)
				if(!que.contains(vertex))
					que.add(vertex);
			mark++;
		}
		return count.size();
	}
	public int ansNumber(HashMap<Integer, ArrayList<Integer>> graph,int start)
	{
		graph=inverse(graph);
		return desNumber(graph, start);
	}
	public HashMap<Integer, ArrayList<Integer>> inverse(HashMap<Integer, ArrayList<Integer>> bigGraph)
	{
		HashMap<Integer, ArrayList<Integer>> bigGraphInverse=new HashMap<Integer, ArrayList<Integer>>();
		Set<Integer> vertexSet=bigGraph.keySet();
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
	public static void main(String[] args) {
		
	}
	
}
