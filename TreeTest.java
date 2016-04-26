package com.hemeng;

class TreeTest 
{
	/**
	* 比较两个单词，判断其中一个单词能否由另外一个单词通过替换其中的一个字母得到
	* @param word1 第一个单词
	* @param word2 第二个单词
	* @return 返回判断结果，能则返回true，否则返回false
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
