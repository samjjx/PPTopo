package com.test;

import java.util.ArrayList;
import java.util.HashMap;

/** 
 * @author ������  E-mail: samjjx@hotmail.com 
 * @version ����ʱ�䣺2015-8-3 ����12:26:50 
 * ��˵�� 
 */
public class HashMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<Integer,ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		ArrayList<Integer> tempArrayList=new ArrayList<Integer>();
		tempArrayList.add(100);
		tempArrayList.add(1000);
		hm.put(1111, tempArrayList);
		hm.get(1111).add(11111);
		hm.put(1111, new ArrayList<Integer>());
		System.out.println(hm.get(1111));
		System.out.println(hm.size());
	}

}
