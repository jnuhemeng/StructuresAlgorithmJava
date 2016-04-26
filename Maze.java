/*
 *	������Դ�����磺http://blog.csdn.net/zhutulang/article/details/7793967
 *		�����õ��Լ�����Ĳ��ཻ���ࣨcom.hemeng.DisjSets����
 *		���ɵ��Թ��ܹ���֤����㣨���Ͻǣ����յ㣨���½ǣ���ͨ·�����ǲ�����
 *		��֤���еĵ�Ԫ������ͨ����Щ��Ԫ�������ڣ�
 */

package com.hemeng;  
  
import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Graphics;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  

import com.hemeng.DisjSets; //�Զ���Ĳ��ཻ����
  
public class Maze extends JFrame {  
  
      
    private static final long serialVersionUID = 4581809619616228704L;  
    //�Թ��ߴ�  
    int m=50,n=50;  
	//int m=10,n=10;  
    //�Թ�����ߴ�(������)  
    int rSize=10;  
    //�Թ������봰�ھ���(�����ϵľ���d*rSize)  
    int d=10;  
    //���ڳߴ�  
    static int wHeight=700;  
    static int wWidth=700;  
    JPanel panel=new JPanel();  
      
    public Maze(){  
          
        panel.setLayout(null);  
        //panel.setBorder(BorderFactory.createTitledBorder("�Թ���"));   
        add(panel, BorderLayout.CENTER);  
    }  
      
    public void paint(Graphics g){  
          
        //�����ɰ�ɫ  
        g.setColor(Color.white);  
        g.fillRect(0, 0, wWidth, wHeight);  
        g.setColor(Color.black);  
          
        for(int i=0;i<n;i++)  
            for(int j=0;j<m;j++)  
                {  
                  //������  
                  g.drawRect(rSize*(i+d),rSize*(j+d), rSize, rSize);  
                }  
        //�Թ��ĳ��ں���ڴ�  
        g.setColor(Color.white);  
        //��ں�ǽ      -1��+1��Ϊ�˲��ڸ���ǽ������Сȱ��(���滭��ʱͬ��)  
        g.drawLine(rSize*d, rSize*d, rSize*(d+1)-1,rSize*d);   
        //�����ǽ  
        g.drawLine(rSize*d, rSize*d, rSize*d,rSize*(d+1)-1);   
        //���ں�ǽ  
        g.drawLine((n-1+d)*rSize+1, (m+d)*rSize, (n+d)*rSize,(m+d)*rSize);   
        //������ǽ  
        g.drawLine((n+d)*rSize, (m-1+d)*rSize+1, (n+d)*rSize,(m+d)*rSize);  
          
        //���ཻ������   
        DisjSets dSets=new DisjSets(m*n);    //DisjSets
        Random random=new Random();  
          
          
        //����0��mn-1û����ͨ  
        while(dSets.find(0)!=dSets.find(m*n-1)){  
              
            //�������һ�������a  0<=a<=mn-1  
            int a=random.nextInt(m*n);  
            //a�����ڷ�����list���  
            List<Integer> neighbor=new ArrayList<Integer>();  
            //�ֱ��ж�a���ϡ��ҡ��¡��󷿼��Ƿ���ڣ������ڷ���neighbor  
            if(a-n>=0)  
                neighbor.add(a-n);  
            if( a+1<( (int)(a/n)+1 )*n )  
                neighbor.add(a+1);  
            if(a+n<m*n)  
                neighbor.add(a+n);  
            if( a-1>=( (int)(a/n) )*n )  
                neighbor.add(a-1);  
            //���������index, 0<=index<=neighbor.size()-1  
            int index=random.nextInt(neighbor.size());  
            //b������a�����ڷ���ţ����ǿ��������������Ƿ���ͨ  
            int b=neighbor.get(index);  
              
            //a��b�Ƿ���ͨ   
            if(dSets.find(a)==dSets.find(b))  
                //a��b��ͨ�Ļ����¿����µķ���  
              continue;    
            else{  
             //a��b����ͨ��union���ǵļ���  
                int seta=dSets.find(a);  
                int setb=dSets.find(b);  
                dSets.union(seta, setb);  
             //�������������֮���"ǽ"  
                //���ȵõ���С�����  
                int s=Math.min(a, b);  
                //����"ǽ"������  
                  int x1,y1,x2,y2;  
                  //�������Ų���1���������ǵ�����ǽ  
                if(Math.abs(a-b)==1)  
                {  
                      
                    //���                      
                    if(s<n)  
                        x1=(s+1+d)*rSize;  
                    else   
                        x1=(s%n+1+d)*rSize;  
                    y1=( (int)(s/n)+d )*rSize+1;  
                    //�յ�  
                    x2=x1;  
                    y2=( (int)(s/n)+1+d )*rSize-1;  
                }else {  
                    //�����Ǻ�ǽ  
                    //���  
                    if(s<n)  
                        x1=(s+d)*rSize+1;  
                    else  
                        x1=(s%n+d)*rSize+1;  
                    y1=( (int)(s/n)+1+d )*rSize;  
                    //�յ�  
                    if(s<n)  
                        x2=(s+1+d)*rSize-1;  
                    else  
                        x2=(s%n+1+d)*rSize-1;  
                    y2=y1;  
                }  
             //��ǽ��ʵ�������ð��߰�ǽĨ��  
                g.setColor(Color.white);  
                g.drawLine(x1, y1, x2, y2);  
                  
            }  
        }          
    }  
          
    public static void main(String[] args) {  
  
        Maze maze=new Maze();  
        maze.setTitle("�Թ�");  
        maze.setSize(wWidth,wHeight);  
        maze.setVisible(true);  
        maze.setLocationRelativeTo(null);  
        maze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
              
    }  
}  
