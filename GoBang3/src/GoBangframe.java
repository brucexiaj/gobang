//构建五子棋界面GoBangframe类

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
	public Graphics g;//定义一支画笔
	public int[][] isAvail=new int [Config.COLUMN][Config.ROW];//定义一个二维数组来储存棋盘的落子情况
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//保存每一步的落子情况
	public int turn=0;//等于0时无法下棋
	
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//设置不同落子情况和相应权值的数组
	static {
		
		//被堵住
		map.put("01", 25);//眠1连
		map.put("02", 22);//眠1连
		map.put("001", 17);//眠1连
		map.put("002", 12);//眠1连
		map.put("0001", 17);//眠1连
		map.put("0002", 12);//眠1连
		
		map.put("0102",25);//眠1连，15
		map.put("0201",22);//眠1连，10
		map.put("0012",15);//眠1连，15
		map.put("0021",10);//眠1连，10
		map.put("01002",25);//眠1连，15
		map.put("02001",22);//眠1连，10
		map.put("00102",17);//眠1连，15
		map.put("00201",12);//眠1连，10
		map.put("00012",15);//眠1连，15
		map.put("00021",10);//眠1连，10

		map.put("01000",25);//活1连，15
		map.put("02000",22);//活1连，10
		map.put("00100",19);//活1连，15
		map.put("00200",14);//活1连，10
		map.put("00010",17);//活1连，15
		map.put("00020",12);//活1连，10
		map.put("00001",15);//活1连，15
		map.put("00002",10);//活1连，10

		//被墙堵住
		map.put("0101",65);//眠2连，40
		map.put("0202",60);//眠2连，30
		map.put("0110",80);//眠2连，40
		map.put("0220",76);//眠2连，30
		map.put("011",80);//眠2连，40
		map.put("022",76);//眠2连，30
		map.put("0011",65);//眠2连，40
		map.put("0022",60);//眠2连，30
		
		map.put("01012",65);//眠2连，40
		map.put("02021",60);//眠2连，30
		map.put("01102",80);//眠2连，40
		map.put("02201",76);//眠2连，30
		map.put("01120",80);//眠2连，40
		map.put("02210",76);//眠2连，30
		map.put("00112",65);//眠2连，40
		map.put("00221",60);//眠2连，30

		map.put("01100",80);//活2连，40
		map.put("02200",76);//活2连，30
		map.put("01010",75);//活2连，40
		map.put("02020",70);//活2连，30
		map.put("00110",75);//活2连，40
		map.put("00220",70);//活2连，30
		map.put("00011",75);//活2连，40
		map.put("00022",70);//活2连，30
		
		//被堵住
		map.put("0111",150);//眠3连，100
		map.put("0222",140);//眠3连，80
		
		map.put("01112",150);//眠3连，100
		map.put("02221",140);//眠3连，80
	
		map.put("01110", 1100);//活3连
		map.put("02220", 1050);//活3连
		map.put("01101",1000);//活3连，130
		map.put("02202",800);//活3连，110
		map.put("01011",1000);//活3连，130
		map.put("02022",800);//活3连，110
		
		map.put("01111",3000);//眠4连，300
		map.put("02222",3500);//活4连，280
	}
	public int[][] weightArray=new int[Config.COLUMN][Config.ROW];//定义一个二维数组，保存各个点的权值
	
	public void initUI() {
		//初始化一个界面,并设置标题大小等属性
		JFrame jf=new JFrame();
		jf.setTitle("五子棋"); 
		jf.setSize(Config.UIWIDTH,Config.UIHIGHTH);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);		
		jf.setLayout(new BorderLayout());//设置顶级容器JFrame为框架布局
		jf.add(this,BorderLayout.CENTER);//添加到框架布局的中间部分
		
		//实现右边的JPanel容器界面
		JPanel jp=new JPanel();
		jp.setPreferredSize(Config.dim1);//设置JPanel的大小
		jp.setBackground(new Color(195, 175, 60));//设置右边的界面颜色为白色
		jf.add(jp,BorderLayout.SOUTH);//添加到框架布局的东边部分
		FlowLayout flowLayout = new FlowLayout();
		jp.setLayout(flowLayout);//设置JPanel为流式布局
		flowLayout.setHgap(30);
								
		//接下来我们需要把按钮等组件依次加到那个JPanel上面
		String[] butname= {"开始新游戏","悔棋","认输"};
		JButton[] button=new JButton[3];
		
		
		//依次把三个按钮组件加上去
		for(int i=0;i<butname.length;i++) {
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(Config.dim4);
			jp.add(button[i]);
			button[i].setForeground(new Color(255,255,255));			
		}
		button[0].setBackground(new Color(26,173,25));
		button[1].setBackground(new Color(236,139,137));
		button[2].setBackground(new Color(0,200,200));
		
		
		//按钮监控类
		ButtonListener butListen=new ButtonListener(this);
		//对每一个按钮都添加状态事件的监听处理机制
		for(int i=0;i<butname.length;i++) {
			button[i].addActionListener(butListen);//添加发生操作的监听方法
		}
		

		
		FrameListener fl=new FrameListener(this);
		this.addMouseListener(fl);//给上面的jpanel绑定一个监听器
		
		jf.setVisible(true);
	}
	
	public void PopUp(String top,String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, top, JOptionPane.PLAIN_MESSAGE);
	}
	
	//界面刚刚加载的时候，会自动调用这个方法
	//重写重绘方法,这里重写的是第一个大的JPanel的方法
	public void paint(Graphics g) {
		super.paint(g);//画出白框
		//添加背景图片
		g.drawImage(Config.CHESSBOARD, 0, 0,this.getWidth(), this.getHeight(), this);
		
		//重绘出棋盘
		//g.setColor(Color.black);
		for(int i=0;i<Config.ROW;i++) {
			g.drawLine(Config.X, Config.Y+Config.SIZE*i, Config.X+Config.SIZE*(Config.COLUMN-1), Config.Y+Config.SIZE*i);
		}
		for(int j=0;j<Config.COLUMN;j++) {
			g.drawLine(Config.X+Config.SIZE*j, Config.Y, Config.X+Config.SIZE*j, Config.Y+Config.SIZE*(Config.ROW-1));
		}
		
		//重绘出棋子
		for(int i=0;i<Config.ROW;i++) {
			for(int j=0;j<Config.COLUMN;j++) {
				if(isAvail[i][j]==1) {//黑色棋子
					int countx=Config.SIZE*j+Config.SIZE/2;
					int county=Config.SIZE*i+Config.SIZE/2;
					g.drawImage(Config.BLACKCHESS,countx-Config.SIZE+Config.X, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
				}
				else if(isAvail[i][j]==2) {//白色棋子
					int countx=Config.SIZE*j+Config.SIZE/2;
					int county=Config.SIZE*i+Config.SIZE/2;
					g.drawImage(Config.WHITECHESS,countx-Config.SIZE+Config.X, county-Config.SIZE/2, Config.SIZE, Config.SIZE,null);
				}
			}
		}
	}
	
}
