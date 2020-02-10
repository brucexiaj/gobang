//�������������GoBangframe��

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
public class GoBangframe extends JPanel{
	public Graphics g;//����һ֧����
	public int[][] isAvail=new int [Config.COLUMN][Config.ROW];//����һ����ά�������������̵��������
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//����ÿһ�����������
	public int turn=0;//����0ʱ�޷�����
	
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//���ò�ͬ�����������ӦȨֵ������
	static {
		
		//����ס
		map.put("01", 25);//��1��
		map.put("02", 22);//��1��
		map.put("001", 17);//��1��
		map.put("002", 12);//��1��
		map.put("0001", 17);//��1��
		map.put("0002", 12);//��1��
		
		map.put("0102",25);//��1����15
		map.put("0201",22);//��1����10
		map.put("0012",15);//��1����15
		map.put("0021",10);//��1����10
		map.put("01002",25);//��1����15
		map.put("02001",22);//��1����10
		map.put("00102",17);//��1����15
		map.put("00201",12);//��1����10
		map.put("00012",15);//��1����15
		map.put("00021",10);//��1����10

		map.put("01000",25);//��1����15
		map.put("02000",22);//��1����10
		map.put("00100",19);//��1����15
		map.put("00200",14);//��1����10
		map.put("00010",17);//��1����15
		map.put("00020",12);//��1����10
		map.put("00001",15);//��1����15
		map.put("00002",10);//��1����10

		//��ǽ��ס
		map.put("0101",65);//��2����40
		map.put("0202",60);//��2����30
		map.put("0110",80);//��2����40
		map.put("0220",76);//��2����30
		map.put("011",80);//��2����40
		map.put("022",76);//��2����30
		map.put("0011",65);//��2����40
		map.put("0022",60);//��2����30
		
		map.put("01012",65);//��2����40
		map.put("02021",60);//��2����30
		map.put("01102",80);//��2����40
		map.put("02201",76);//��2����30
		map.put("01120",80);//��2����40
		map.put("02210",76);//��2����30
		map.put("00112",65);//��2����40
		map.put("00221",60);//��2����30

		map.put("01100",80);//��2����40
		map.put("02200",76);//��2����30
		map.put("01010",75);//��2����40
		map.put("02020",70);//��2����30
		map.put("00110",75);//��2����40
		map.put("00220",70);//��2����30
		map.put("00011",75);//��2����40
		map.put("00022",70);//��2����30
		
		//����ס
		map.put("0111",150);//��3����100
		map.put("0222",140);//��3����80
		
		map.put("01112",150);//��3����100
		map.put("02221",140);//��3����80
	
		map.put("01110", 1100);//��3��
		map.put("02220", 1050);//��3��
		map.put("01101",1000);//��3����130
		map.put("02202",800);//��3����110
		map.put("01011",1000);//��3����130
		map.put("02022",800);//��3����110
		
		map.put("01111",3000);//��4����300
		map.put("02222",3500);//��4����280
	}
	public int[][] weightArray=new int[Config.COLUMN][Config.ROW];//����һ����ά���飬����������Ȩֵ
	
	public void initUI() {
		//��ʼ��һ������,�����ñ����С������
		JFrame jf=new JFrame();
		jf.setTitle("������"); 
		jf.setSize(Config.UIWIDTH,Config.UIHIGHTH);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);		
		jf.setLayout(new BorderLayout());//���ö�������JFrameΪ��ܲ���
		jf.add(this,BorderLayout.CENTER);//��ӵ���ܲ��ֵ��м䲿��
		
		//ʵ���ұߵ�JPanel��������
		JPanel jp=new JPanel();
		jp.setPreferredSize(Config.dim1);//����JPanel�Ĵ�С
		jp.setBackground(new Color(195, 175, 60));//�����ұߵĽ�����ɫΪ��ɫ
		jf.add(jp,BorderLayout.SOUTH);//��ӵ���ܲ��ֵĶ��߲���
		FlowLayout flowLayout = new FlowLayout();
		jp.setLayout(flowLayout);//����JPanelΪ��ʽ����
		flowLayout.setHgap(30);
								
		//������������Ҫ�Ѱ�ť��������μӵ��Ǹ�JPanel����
		String[] butname= {"��ʼ����Ϸ","����","����"};
		JButton[] button=new JButton[3];
		
		
		//���ΰ�������ť�������ȥ
		for(int i=0;i<butname.length;i++) {
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(Config.dim4);
			jp.add(button[i]);
			button[i].setForeground(new Color(255,255,255));			
		}
		button[0].setBackground(new Color(26,173,25));
		button[1].setBackground(new Color(236,139,137));
		button[2].setBackground(new Color(0,200,200));
		
		
		//��ť�����
		ButtonListener butListen=new ButtonListener(this);
		//��ÿһ����ť�����״̬�¼��ļ����������
		for(int i=0;i<butname.length;i++) {
			button[i].addActionListener(butListen);//��ӷ��������ļ�������
		}
		

		
		FrameListener fl=new FrameListener(this);
		this.addMouseListener(fl);//�������jpanel��һ��������
		
		jf.setVisible(true);
	}
	
	public void PopUp(String top,String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, top, JOptionPane.PLAIN_MESSAGE);
	}
	
	//����ոռ��ص�ʱ�򣬻��Զ������������
	//��д�ػ淽��,������д���ǵ�һ�����JPanel�ķ���
	public void paint(Graphics g) {
		super.paint(g);//�����׿�
		//��ӱ���ͼƬ
		g.drawImage(Config.CHESSBOARD, 0, 0,this.getWidth(), this.getHeight(), this);
		
		//�ػ������
		//g.setColor(Color.black);
		for(int i=0;i<Config.ROW;i++) {
			g.drawLine(Config.X, Config.Y+Config.SIZE*i, Config.X+Config.SIZE*(Config.COLUMN-1), Config.Y+Config.SIZE*i);
		}
		for(int j=0;j<Config.COLUMN;j++) {
			g.drawLine(Config.X+Config.SIZE*j, Config.Y, Config.X+Config.SIZE*j, Config.Y+Config.SIZE*(Config.ROW-1));
		}
		
		//�ػ������
		for(int i=0;i<Config.ROW;i++) {
			for(int j=0;j<Config.COLUMN;j++) {
				if(isAvail[i][j]==1) {//��ɫ����
					int countx=Config.SIZE*j+Config.SIZE/2;
					int county=Config.SIZE*i+Config.SIZE/2;
					g.drawImage(Config.BLACKCHESS,countx-Config.SIZE+Config.X, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
				}
				else if(isAvail[i][j]==2) {//��ɫ����
					int countx=Config.SIZE*j+Config.SIZE/2;
					int county=Config.SIZE*i+Config.SIZE/2;
					g.drawImage(Config.WHITECHESS,countx-Config.SIZE+Config.X, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
				}
			}
		}
	}
	
}
