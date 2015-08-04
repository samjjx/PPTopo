package com.sample;

import java.util.ArrayList;
import java.util.HashMap;

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
	public static void main(String[] args) {
		/**
		 * @Title: main
		 * @Description: TODO(这里用一句话描述这个方法的作用)
		 * @param:   
		 * @return:   
		 * @throws
		 */
	}
	
}
