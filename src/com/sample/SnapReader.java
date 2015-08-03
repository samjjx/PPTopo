package com.sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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
	int[] color;      //Judge if the related node is visited
	int nodes;
	int edges;
	public SnapReader(String filePath) throws IOException
	{
		FileInputStream in=new FileInputStream(filePath);
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
					color=new int[nodes];
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
	public static void main(String[] args) throws IOException {
		SnapReader sr=new SnapReader("dataset/Email-Enron.txt");
	}

}
