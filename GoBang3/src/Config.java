import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Config {

	public static final int X=170;
	public static final int Y=20;
	public static final int SIZE=40;
	public static final int ROW=15;
	public static final int COLUMN=15;
	public static final int UIWIDTH=925;
	public static final int UIHIGHTH=685;
	public static final Image BLACKCHESS= new ImageIcon("pic\\black.png").getImage();	//���ﲻ����ImageIcon
	public static final Image WHITECHESS= new ImageIcon("pic\\white.png").getImage();	//���ﲻ����ImageIcon 
	public static final Image CHESSBOARD= new ImageIcon("pic\\ChessBoard.jpg").getImage();	//���ﲻ����ImageIcon 
	

	public static final Dimension dim1=new Dimension(UIWIDTH,50);//�����ұ���Ϣ���Ĵ�С
	public static final Dimension dim4=new Dimension(140,40);//�����ұ߰�ť����Ĵ�С
}
