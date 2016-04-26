package com.hemeng;

class TreeTest 
{
	/**
	* �Ƚ��������ʣ��ж�����һ�������ܷ�������һ������ͨ���滻���е�һ����ĸ�õ�
	* @param word1 ��һ������
	* @param word2 �ڶ�������
	* @return �����жϽ�������򷵻�true�����򷵻�false
	*/
	private static boolean oneCharOff(String word1,String word2)
	{
		if(word1.length()!=word2.length())
			return false;
		int diff=0;
		for(int i=0;i<word1.length();i++)
			if(word1.charAt(i)!=word2.charAt(i))
				if(++diff>=2)
					return false;
		return diff==1;
	}

	public static void main(String[] args) 
	{
		TreeTest myTreeTest=new TreeTest();
		String word1=new String("Hallo");
		String word2=new String("Hello");
		System.out.println(myTreeTest.oneCharOff(word1,word2));
	}
}
