package com.sample;

import java.util.ArrayList;
import java.util.HashMap;

/** 
 * @author ������  E-mail: samjjx@hotmail.com 
 * @version ����ʱ�䣺2015-8-3 ����2:55:23 
 * ����pptopo��ǩ
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
		 * @Description: TODO(������һ�仰�����������������)
		 * @param:   
		 * @return:   
		 * @throws
		 */
	}
	
}
