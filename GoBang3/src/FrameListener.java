import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Dimension;

//ʵ�ֶ�GoBangframe�������ļ����ӿڴ���
public class FrameListener implements MouseListener{
	public GoBangframe gf;
	
	public FrameListener(GoBangframe gf) {
		this.gf=gf;
	}
	
	public  boolean isGameOver(int Arrayi, int Arrayj, int chess) {
		 //�ж�ĳһ���Ƿ���5������
		int Blackimin=Arrayi-4,Blackimax=Arrayi+4;
		if(Blackimin<0) Blackimin=0;
		if(Blackimax>14) Blackimax=14;
		int count1=0;//�ж�������������
		for(int i=Blackimin;i<=Blackimax;i++) {
			if(gf.isAvail[i][Arrayj]==chess) count1++;
			//����������������ӣ�������û������ʱ�������¿�ʼ����
			else count1=0;
			if(count1==5) { 
				//gf.PopUp("��Ϸ���","�ڷ�Ӯ");
				gf.turn = 0;
				return true;
			}
		}			  
		  
		  
		
		 //�ж��з����Ƿ���5������
		  int Blackjmin=Arrayj-4,Blackjmax=Arrayj+4;
		  if(Blackjmin<0) Blackjmin=0;
		  if(Blackjmax>14) Blackjmax=14;
		  int count2=0;//�ж�������������
		  for(int j=Blackjmin;j<=Blackjmax;j++) {
			  if(gf.isAvail[Arrayi][j]==chess) count2++;
			  else count2=0;
			  if(count2==5) {
				  //gf.PopUp("��Ϸ���","�ڷ�Ӯ");
				  gf.turn = 0;
				  return true;
			  }						  
		  }
		  
		//135���ж�
		  //���Ƚ綨���鷶Χ����ֹԽ��
		  int count3=0;//�ж�������������
		  for(int i=-4;i<=4;i++) {
			  if((Arrayi+i>=0)&&(Arrayj+i>=0)&&(Arrayi+i<=14)&&(Arrayj+i<=14)) {
				  if(gf.isAvail[Arrayi+i][Arrayj+i]==chess) count3++;
					//����������������ӣ�������û������ʱ�������¿�ʼ����
				  else count3=0;
				  if(count3==5) {
					  //gf.PopUp("��Ϸ���","�ڷ�Ӯ");
					  gf.turn = 0;
					  return true;
				  }
			  }
		  }
		  //45���ж�
		  int count4=0;//�ж�������������
		  for(int i=-4;i<=4;i++) {
			  if((Arrayi+i>=0)&&(Arrayj-i>=0)&&(Arrayi+i<=14)&&(Arrayj-i<=14)) {
				  //System.out.print("count4:"+count4);
				  if(gf.isAvail[Arrayi+i][Arrayj-i]==chess) count4++;
					//����������������ӣ�������û������ʱ�������¿�ʼ����
				  else count4=0;
				  if(count4==5) {
					  //gf.PopUp("��Ϸ���","�ڷ�Ӯ");
					  gf.turn = 0;
					  return true;
				  }
			  }
		  }
		  
		return false;
	}
	
	//AI�����㷨
	public Integer unionWeight(Integer a,Integer b ) {
		//����Ҫ���ж�a,b������ֵ�ǲ���null
		if((a==null)||(b==null)) return 0;
		//һһ:101/202
	    else if((a>=22)&&(a<=25)&&(b>=22)&&(b<=25)) return 60;
		//һ������һ:1011/2022
		else if(((a>=22)&&(a<=25)&&(b>=76)&&(b<=80))||((a>=76)&&(a<=80)&&(b>=22)&&(b<=25))) return 800;
		//һ������һ������:10111/20222
		else if(((a>=10)&&(a<=25)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=10)&&(b<=25))||((a>=76)&&(a<=80)&&(b>=76)&&(b<=80)))
			return 3000;
		//����������һ����һ������һ
		else if(((a>=22)&&(a<=25)&&(b>=140)&&(b<=150))||((a>=140)&&(a<=150)&&(b>=22)&&(b<=25))) return 3000;
		//����������:110111
		else if(((a>=76)&&(a<=80)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=76)&&(b<=80))) return 3000;
		else return 0;
	}
	
	  public void mouseClicked(java.awt.event.MouseEvent e) {
		  int x=e.getX();
		  int y=e.getY();
		  //��������Ҫ�������̵��ĸ��������
		  int countx=((x-(Config.X-Config.SIZE/2))/Config.SIZE)*Config.SIZE+Config.X;
		  int county=(y/Config.SIZE)*Config.SIZE+Config.Y;
		  Graphics g=gf.getGraphics();
			//������������������������Ӧ��λ��
		  int Arrayj=(countx-Config.X)/Config.SIZE;
		  int Arrayi=(county-Config.Y)/Config.SIZE;
		  if((Arrayi<0)||(Arrayj<0)||(Arrayi>14)||(Arrayj>14)) return ;
		  
		  if(gf.turn!=1) return;//�ж��Ƿ���Խ�����Ϸ
		  if(gf.isAvail[Arrayi][Arrayj]!=0) {
			  gf.PopUp("������ʾ","�˴��Ѿ��������ˣ������������ط�");
			  return;
		  }

		  //�ֵ��ˣ�������		  
		  //��������
		 //��������
		g.drawImage(Config.BLACKCHESS,countx-Config.SIZE/2, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
		//���õ�ǰλ���Ѿ���������,����Ϊ����
		gf.isAvail[Arrayi][Arrayj]=1;
		//�ѵ�ǰ���µ�����λ�ñ����ڶ�̬������
		gf.ChessPositonList.add(new ChessPosition(Arrayi,Arrayj));
		gf.turn++;//�ֵ���ɫ��
					  
		if (isGameOver(Arrayi, Arrayj, 1)){
			gf.PopUp("��Ϸ���","�ڷ�Ӯ");
			return;
		}
		
		
		
		 
					  
					  //��������󣬻�������������
					  //�ȼ��������λ�õ�Ȩֵ
					  for(int i=0;i<gf.isAvail.length;i++) {//for1
						  for(int j=0;j<gf.isAvail[i].length;j++) {//for2
							  //�����жϵ�ǰλ���Ƿ�Ϊ��
							  if(gf.isAvail[i][j]==0) {
								  //��������
								  String ConnectType="0";
								  int jmin=Math.max(0, j-4);
								  for(int positionj=j-1;positionj>=jmin;positionj--) {
									  //���μ���ǰ�������
									  ConnectType=ConnectType+gf.isAvail[i][positionj];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
								  Integer valueleft=gf.map.get(ConnectType);
								  if(valueleft!=null) gf.weightArray[i][j]+=valueleft;
								  
								  //��������
								  ConnectType="0";
								  int jmax=Math.min(14, j+4);
								  for(int positionj=j+1;positionj<=jmax;positionj++) {
									  //���μ���ǰ�������
									  ConnectType=ConnectType+gf.isAvail[i][positionj];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
								  Integer valueright=gf.map.get(ConnectType);
								  if(valueright!=null) gf.weightArray[i][j]+=valueright;
								  
								  //�����жϣ��ж���
								  gf.weightArray[i][j]+=unionWeight(valueleft,valueright);
								  
								  //��������
								  ConnectType="0";
								  int imin=Math.max(0, i-4);
								  for(int positioni=i-1;positioni>=imin;positioni--) {
									  //���μ���ǰ�������
									  ConnectType=ConnectType+gf.isAvail[positioni][j];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
								  Integer valueup=gf.map.get(ConnectType);
								  if(valueup!=null) gf.weightArray[i][j]+=valueup;
								  
								  //��������
								  ConnectType="0";
								  int imax=Math.min(14, i+4);
								  for(int positioni=i+1;positioni<=imax;positioni++) {
									  //���μ���ǰ�������
									  ConnectType=ConnectType+gf.isAvail[positioni][j];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
								  Integer valuedown=gf.map.get(ConnectType);
								  if(valuedown!=null) gf.weightArray[i][j]+=valuedown;
								  
								  //�����жϣ��ж���
								  gf.weightArray[i][j]+=unionWeight(valueup,valuedown);
								  
								  //�����Ϸ�����,i,j,����ȥ��ͬ����
								  ConnectType="0";
								  for(int position=-1;position>=-4;position--) {
									  if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
									  ConnectType=ConnectType+gf.isAvail[i+position][j+position];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
								  Integer valueLeftUp=gf.map.get(ConnectType);
								  if(valueLeftUp!=null) gf.weightArray[i][j]+=valueLeftUp;
								  
								 //�����·�����,i,j,��������ͬ����
								  ConnectType="0";
								  for(int position=1;position<=4;position++) {
									  if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
									  ConnectType=ConnectType+gf.isAvail[i+position][j+position];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
								  Integer valueRightDown=gf.map.get(ConnectType);
								  if(valueRightDown!=null) gf.weightArray[i][j]+=valueRightDown;
								  
								  //�����жϣ��ж���
								  gf.weightArray[i][j]+=unionWeight(valueLeftUp,valueRightDown);
								  
								  //�����·�����,i��,j��
								  ConnectType="0";
								  for(int position=1;position<=4;position++) {
									  if((i+position>=0)&&(i+position<=14)&&(j-position>=0)&&(j-position<=14))
									  ConnectType=ConnectType+gf.isAvail[i+position][j-position];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
								  Integer valueLeftDown=gf.map.get(ConnectType);
								  if(valueLeftDown!=null) gf.weightArray[i][j]+=valueLeftDown;
								  
								  //�����Ϸ�����,i��,j��
								  ConnectType="0";
								  for(int position=1;position<=4;position++) {
									  if((i-position>=0)&&(i-position<=14)&&(j+position>=0)&&(j+position<=14))
									  ConnectType=ConnectType+gf.isAvail[i-position][j+position];
								  }
								  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
								  Integer valueRightUp=gf.map.get(ConnectType);
								  if(valueRightUp!=null) gf.weightArray[i][j]+=valueRightUp;
								  
								  //�����жϣ��ж���
								  gf.weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);
							  }
						  }//for2
					  }//for1��Ȩֵ�������
					  
					  //ȡ������Ȩֵ
					  int AIi=0,AIj=0;
					  int weightmax=0;
					  for(int i=0;i<Config.ROW;i++) {
						  for(int j=0;j<Config.COLUMN;j++) {
							  if(weightmax<gf.weightArray[i][j]) {
								  weightmax=gf.weightArray[i][j];
								  AIi=i;
								  AIj=j;
							  }
						  }
					  }
					  
					  //ȷ��λ�ã�����
					  //i��Ӧy��j��Ӧx
					  countx=Config.X+AIj*Config.SIZE;
					  county=Config.Y+AIi*Config.SIZE;
					  g.drawImage(Config.WHITECHESS,countx-Config.SIZE/2, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
					  //���õ�ǰλ���Ѿ��������ˣ�����Ϊ����
					  gf.ChessPositonList.add(new ChessPosition(AIi,AIj));
					  gf.isAvail[AIi][AIj]=2;
					  gf.turn--;//�ֵ��ڷ�����
					 
					  //�����Ժ�����Ȩֵ����weightArray
					  for(int i=0;i<Config.COLUMN;i++) 
						  for(int j=0;j<Config.ROW;j++)
							  gf.weightArray[i][j]=0;
					  
					  
					  if (isGameOver(AIi, AIj, 2)){
						  gf.PopUp("��Ϸ���","�׷�Ӯ");
						  return;
					  }	
				  		  
		  
	  }
	  
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mousePressed(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseReleased(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseEntered(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseExited(java.awt.event.MouseEvent e) {
		  
	  }
}
