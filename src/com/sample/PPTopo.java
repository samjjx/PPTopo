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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap[] CreatePPTopo(HashMap<Integer, ArrayList<Integer>> graph)
	{
		HashMap[] label=new HashMap[2];
		int nodes=graph.size();
		label[0]=new HashMap<Integer, ArrayList<Integer>>();
		label[1]=new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0;i<nodes;i++)
		{
			label[0].put(i, new ArrayList<Integer>());
			label[1].put(i, new ArrayList<Integer>());
		}
		int[]topoNumber=createTopoNumber(graph);
		HashMap<Integer, ArrayList<Integer>> level=seperateLevel(topoNumber);
		int L=0;
		while((L=PickLevel(graph, level))!=-1)
		{
			ArrayList<Integer> rm=level.get(L);
			for(int i=0;i<rm.size();i++)
			{
				int c=rm.get(i);
				addDesLabel(graph, label[0], c);
				addAnsLabel(graph, label[1], c);
				remove(graph, c);
			}
			level.remove(L);
		}
		return label;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap[] CreatePPTopoNode(HashMap<Integer, ArrayList<Integer>> graph)
	{
		HashMap[] label=new HashMap[2];
		int nodes=graph.size();
		label[0]=new HashMap<Integer, ArrayList<Integer>>();
		label[1]=new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0;i<nodes;i++)
		{
			label[0].put(i, new ArrayList<Integer>());
			label[1].put(i, new ArrayList<Integer>());
		}
		int L=0;
		while((L=PickNode(graph))!=-1)
		{
			int c=L;
			addDesLabel(graph, label[0], c);
			addAnsLabel(graph, label[1], c);
			remove(graph, c);
			
		}
		return label;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap[] CreatePPTopoBrFS(HashMap<Integer, ArrayList<Integer>> graph)
	{
		HashMap[] label=new HashMap[2];
		int nodes=graph.size();
		label[0]=new HashMap<Integer, ArrayList<Integer>>();
		label[1]=new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0;i<nodes;i++)
		{
			label[0].put(i, new ArrayList<Integer>());
			label[1].put(i, new ArrayList<Integer>());
		}
		int[]topoNumber=createTopoNumber(graph);
		HashMap<Integer, ArrayList<Integer>> level=seperateLevel(topoNumber);
		int L=0;
		while((L=PickLevelByFS(graph, level))!=-1)
		{
			ArrayList<Integer> rm=level.get(L);
			for(int i=0;i<rm.size();i++)
			{
				int c=rm.get(i);
				addDesLabel(graph, label[0], c);
				addAnsLabel(graph, label[1], c);
				remove(graph, c);
			}
			level.remove(L);
		}
		return label;
	}
	public int PickLevelByFS(HashMap<Integer, ArrayList<Integer>> graph,HashMap<Integer, ArrayList<Integer>> level)
	{
		double temp=1;
		int result=-1;
		Set<Integer> keyset=level.keySet();
		for(int key:keyset)
		{
			double value=UtilByFS(graph, level.get(key));
			if(value>temp)
			{
				temp=value;
				result=key;
			}
		}
		return result;
	}
	public double UtilByFS(HashMap<Integer, ArrayList<Integer>> graph,ArrayList<Integer> level)
	{
		int sum=0;
		HashMap<Integer, ArrayList<Integer>> inverseGraph=inverse(graph);
		for(int i=0;i<level.size();i++)
			sum+=(graph.get(level.get(i)).size()+inverseGraph.get(level.get(i)).size());
		return (double)sum/level.size();
	}
	public void addDesLabel(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer,ArrayList<Integer>> lin,int start)
	{
		ArrayList<Integer> que=new ArrayList<Integer>();
		que.add(start);
		int mark=0;
		while(mark<que.size())
		{
			int temp=que.get(mark);
			ArrayList<Integer> tempArrayList=graph.get(temp);
			for(int vertex:tempArrayList)
				if(!que.contains(vertex))
					que.add(vertex);
			mark++;
		}
		for(int i=0;i<que.size();i++)
			lin.get(que.get(i)).add(start);
		if(ansNumber(graph, start)==0)
		{
			ArrayList<Integer> temp=lin.get(start);
			for(int i=0;i<temp.size();i++)
				if(temp.get(i)==start)
					temp.remove(i);
		}
	}
	public void addAnsLabel(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer,ArrayList<Integer>> lout,int start)
	{
		HashMap<Integer, ArrayList<Integer>> graphInverse=inverse(graph);
		addDesLabel(graphInverse, lout, start);
	}
	
	public int PickLevel(HashMap<Integer, ArrayList<Integer>> graph,HashMap<Integer, ArrayList<Integer>> level)
	{
		double temp=1;
		int result=-1;
		Set<Integer> keyset=level.keySet();
		for(int key:keyset)
		{
			double value=Util(graph, level.get(key));
			if(value>temp)
			{
				temp=value;
				result=key;
			}
		}
		return result;
	}
	public double oneLevel(HashMap<Integer, ArrayList<Integer>> graph,ArrayList<Integer> level)
	{
		int sum=0;
		for(int i=0;i<level.size();i++)
			sum+=ansNumber(graph, level.get(i))+desNumber(graph, level.get(i));
		if(level.size()!=0)
			return (double)sum/level.size();
		else
			return sum;
	}
	public int PickNode(HashMap<Integer, ArrayList<Integer>> graph)
	{
		double temp=1;
		int result=-1;
		Set<Integer> keyset=graph.keySet();
		for(int key:keyset)
		{
			int value=(ansNumber(graph, key)+1)*(desNumber(graph, key)+1);
			if(value>temp)
			{
				temp=value;
				result=key;
			}
		}
		
		return result;
	}
	public double Util(HashMap<Integer, ArrayList<Integer>> graph,ArrayList<Integer> level)
	{
		int sum=0;
		for(int i=0;i<level.size();i++)
			sum+=(ansNumber(graph, level.get(i))+1)*(desNumber(graph, level.get(i))+1);
		return (double)sum/level.size();
	}
	public void remove(HashMap<Integer, ArrayList<Integer>> graph,int vertex)
	{
		graph.remove(vertex);
		Set<Integer> keyset=graph.keySet();
		
		for(int key:keyset)
		{
			ArrayList<Integer> temp=graph.get(key);
			for(int i=0;i<temp.size();i++)
				if(temp.get(i)==vertex)
					temp.remove(i);
		}
	}
	public HashMap<Integer, ArrayList<Integer>> seperateLevel(int[] topoNumber)
	{
		HashMap<Integer, ArrayList<Integer>> level=new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0;i<topoNumber.length;i++)
		{
			if(level.containsKey(topoNumber[i]))
				level.get(topoNumber[i]).add(i);
			else {
				level.put(topoNumber[i], new ArrayList<Integer>());
				level.get(topoNumber[i]).add(i);
			}
		}
		return level;
	}
	public int[] createTopoNumber(HashMap<Integer, ArrayList<Integer>> dag)
	{
		DFS dfs=new DFS(dag,dag.size());
		dfs.traverse();
		dfs.topoNumber();
		return dfs.topoNumber;
	}
	public int desNumber(HashMap<Integer, ArrayList<Integer>> graph, int start)
	{
		ArrayList<Integer> que=new ArrayList<Integer>();
		int mark=0;

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
		return que.size();
	}
	public int ansNumber(HashMap<Integer, ArrayList<Integer>> graph,int start)
	{
		HashMap<Integer, ArrayList<Integer>> graphInverse=inverse(graph);
		return desNumber(graphInverse, start);
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
	
}
