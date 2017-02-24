package com.hemeng.study.dsa.sort;
/**
 * ��ϰ�򵥵������㷨
 *
 */
public class SimpleSortsRevise {
	/**
	 * ð�����򣨸Ľ���
	 * @param array
	 * @date 2017.02.24
	 */
	public static void bubbleSort(int[] array) {
		//����һ����־λ�������ֵ�ǰѭ������ʱ�����Ѿ�����ʱ����ֱ�ӽ�����������Ҫ��ѭ����
		boolean flag = true; 
		for(int i = 0, n = array.length - 1; i <= n && flag == true; i++) {
			flag = false;
			for(int j = array.length - 1; j > i; j--) {
				if(array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
					//�����һ����ѭ��û��ִ���κν�����������ô��˵��û�б�Ҫ�ٽ��к����Ĳ�����
					flag = true;
				}
			}
		}
	}
	
	

	/**
	 *	�������������㷨�Ĺ��ܣ����ڲ���
	 *	@param N ������������Ԫ�صĸ���
	 *	@date 2012.02.24
	 */
	public static void functionDemo(int N) {
		int[][] array = SimpleSorts.generateIntArray(4,N);
		System.out.println("ԭʼ���飨����" + array[0].length + "��Ԫ��)��");
		SimpleSorts.printArray(array[0]);

		/*System.out.println("\n���뷨��");
		SimpleSortsV2.insertionSort(array[0]); //�����뷨��
		SimpleSorts.printArray(array[0]);*/
		System.out.println("\nð�ݷ���");
		SimpleSortsRevise.bubbleSort(array[1]); //��ð�ݷ���
		SimpleSorts.printArray(array[1]);
		/*System.out.println("\nѡ�񷨣�");
		SimpleSortsV2.selectSort(array[2]); //��ѡ�񷨡�
		SimpleSorts.printArray(array[2]);
		System.out.println("\nϣ�����򷨣�");
		SimpleSortsV2.shellSort(array[3]); //��ϣ�����򷨡�
		SimpleSorts.printArray(array[3]);*/
		System.out.println("");
	}
	
	public static void main(String[] args) {
		int N = 20;
		SimpleSortsRevise.functionDemo(N);
	}
}




