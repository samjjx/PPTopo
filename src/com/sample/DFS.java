package com.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * @author 蒋家鑫  E-mail: samjjx@hotmail.com 
 * @version 创建时间：2015-8-3 下午3:08:55 
 * 类说明 
 */
public class DFS {

	/**
	 * @param args
	 */
	HashMap<Integer, ArrayList<Integer>> bigGraph;
	int nodes;
	int[] color;      //Judge if the related node is visited. 0:White 1：Gray 2 Black
	int time=0;       //program time
	int[] ans;        // the ancestor. -1 : NIL
	int[] discover;
	int[] finish;
	public DFS(HashMap<Integer, ArrayList<Integer>> bigGraph,int nodes)
	{
		this.bigGraph=bigGraph;
		this.nodes=nodes;
		color=new int[nodes];
		ans=new int[nodes];
		discover=new int[nodes];
		finish=new int[nodes];
	}
	public void traverse()
	{
		for(int i=0;i<nodes;i++)
		{
			color[i]=0;
			ans[i]=-1;
		}
		time=0;
		for(int start=0;start<nodes;start++)
		{
			if(color[start]==0)
				DFS_VISIT(start);
		}
	}
	public void DFS_VISIT(int start)
	{
		System.out.println(start);
		color[start]=1;
		time++;
		discover[start]=time;
		for(int v:bigGraph.get(start))
		{
			if(color[v]==0)
			{
				ans[v]=start;
				DFS_VISIT(v);
			}
		}
		color[start]=2;
		time++;
		finish[start]=time;
	}
	public static void main(String[] args) throws IOException {
		SnapReader sr=new SnapReader("dataset/Email-Enron.txt");
		DFS dfs=new DFS(sr.bigGraph,sr.nodes);
		dfs.traverse();
		
	}

}
