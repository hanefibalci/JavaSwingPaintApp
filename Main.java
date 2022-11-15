import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
		new MainFrame();


	}
}

	class MainFrame extends JFrame{
		JPanel setPanel;
		public JButton cizBtn,rectBtn,tasiBtn;
		public JPanel blackPnl,greenPnl,bluePnl;
		DrawArea area;

		public MainFrame(){
			setSize(500,500);
			setTitle("Paint");
			//setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			setPanel=new JPanel();
			setPanel.setLayout(new GridLayout(1,6));
			cizBtn= new JButton("Ciz");
			rectBtn= new JButton("Dikdortgen");
			tasiBtn= new JButton("Tasi");
			blackPnl= new JPanel();
			blackPnl.setBackground(Color.black);
			bluePnl= new JPanel();
			bluePnl.setBackground(new Color(50,80,140));
			greenPnl= new JPanel();
			greenPnl.setBackground(new Color(90,140,60));
			add(setPanel,BorderLayout.NORTH);
			setPanel.add(cizBtn);
			cizBtn.addMouseListener(new endingListener());
			setPanel.add(rectBtn);
			rectBtn.addMouseListener(new endingListener());
			setPanel.add(tasiBtn);
			tasiBtn.addMouseListener(new endingListener());
			setPanel.add(blackPnl);
			blackPnl.addMouseListener(new endingListener());
			setPanel.add(greenPnl);
			greenPnl.addMouseListener(new endingListener());
			setPanel.add(bluePnl);
			bluePnl.addMouseListener(new endingListener());
			area = new DrawArea();
			add(area,BorderLayout.CENTER);
			setVisible(true);
		}

		class endingListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getSource()==greenPnl){
					DrawArea.color=0;
					//System.out.println("renk yesil oldu");
				}else if(e.getSource()==blackPnl){
					DrawArea.color=1;
				}else if(e.getSource()==bluePnl){
					DrawArea.color=2;
					//System.out.println("renk blue oldu");
				}else if(e.getSource()==cizBtn){
					area.cizAktif= true;
					area.rectAktif=false;
					area.tasiAktif=false;
					//System.out.println("çiz");
				}else if(e.getSource()==rectBtn){
					area.cizAktif= false;
					area.rectAktif=true;
					area.tasiAktif=false;
				}else if(e.getSource()==tasiBtn){
					area.cizAktif= false;
					area.rectAktif=false;
					area.tasiAktif=true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		}

	}
//-----------------------------------------------------------------------------------

	class DrawArea extends JComponent implements MouseInputListener {
		int prex,prey,curx,cury;
		public static int color=1;
		int rectx,recty;
		int preRectx,preRecty;
		public RectPoints temp;
		public ArrayList<MyPoints> points = new ArrayList<>();
		public ArrayList<RectPoints> rects = new ArrayList<>();
		public RectPoints[] tempRect = new RectPoints[1];

		public boolean rectAktif =false;
		public boolean tasiAktif=false;
		public boolean cizAktif =true;

		public DrawArea(){
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {



			if(cizAktif) {
				points.add(new MyPoints(curx, cury, color, true));



			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!tasiAktif) {
				prex = e.getX();
				prey = e.getY();
			}
			preRectx=e.getX();
			preRecty=e.getY();

			if(tasiAktif){
				temp=new RectPoints();
				for (int i=0;i<rects.size();i++){
					if(		preRectx>=rects.get(i).prex &&
							preRecty>=rects.get(i).prey &&
							preRectx<=rects.get(i).prex+rects.get(i).curx &&
							preRecty<=rects.get(i).prey+rects.get(i).cury
					){
						temp = rects.get(i);

					}
				}
			}


			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(cizAktif)points.add(new MyPoints(curx,cury,color,false));
			if(rectAktif){
				rects.add(new RectPoints(Math.min(prex, curx), Math.min(prey, cury), Math.max(prex, curx)-Math.min(prex, curx), Math.max(prey, cury)-Math.min(prey, cury),color));
				tempRect = new RectPoints[1];

			}

			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			curx = e.getX();
			cury = e.getY();
			rectx=e.getX();
			recty=e.getY();
			if(rectAktif){
				tempRect[0]=new RectPoints(Math.min(prex, curx), Math.min(prey, cury), Math.max(prex, curx)-Math.min(prex, curx), Math.max(prey, cury)-Math.min(prey, cury),color);
			}
			if(cizAktif)points.add(new MyPoints(curx,cury,color,true));


			if(tasiAktif){
				if(rectx>=preRectx && recty >=preRecty){
					temp.prex+=rectx-preRectx;
					temp.prey+=recty-preRecty;
				}
				else if(rectx>=preRectx && recty <=preRecty){
					temp.prex+=Math.abs(rectx-preRectx);
					temp.prey-=Math.abs(recty-preRecty);
				}
				else if(rectx<=preRectx && recty >=preRecty){
					temp.prex-=Math.abs(rectx-preRectx);
					temp.prey+=Math.abs(recty-preRecty);
				}
				else{
					temp.prex-=Math.abs(rectx-preRectx);
					temp.prey-=Math.abs(recty-preRecty);
				}
			}

			preRecty=recty;
			preRectx=rectx;
			repaint();

		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}


		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			for(int i =0;i<points.size()-1;i++){
				if(points.get(i).leak){
								if(points.get(i).color==0)g.setColor(new Color(90,140,60));
								else if(points.get(i).color==1)g.setColor(Color.black);
								else g.setColor(new Color(50,80,140));
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.drawLine(points.get(i).curx,points.get(i).cury,points.get(i+1).curx,points.get(i+1).cury);
					((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				}
			}

			for (RectPoints rect : rects) {
				if(rect.color==0)g.setColor(new Color(90,140,60));
				else if (rect.color==1)g.setColor(Color.black);
				else g.setColor(new Color(50,80,140));
				g.fillRect(rect.prex, rect.prey, rect.curx, rect.cury);
			}

			if(rectAktif){
				try {
					if(tempRect[0].color==0)g.setColor(new Color(90,140,60));
					else if (tempRect[0].color==1)g.setColor(Color.black);
					else g.setColor(new Color(50,80,140));
					g.fillRect(tempRect[0].prex, tempRect[0].prey, tempRect[0].curx, tempRect[0].cury);
				}catch (Exception e){

				}
			}



		}

	}
	class MyPoints{
		int curx,cury,color;
		boolean leak;
		public MyPoints( int curx, int cury, int color,boolean leak ) {
			this.curx = curx;
			this.cury = cury;
			this.color = color;
			this.leak=leak;
		}
	}
	class RectPoints{
		int prey,prex,curx,cury,color;
		public RectPoints(){

		}
		public RectPoints(int prex, int prey, int curx, int cury, int color) {
			this.prey = prey;
			this.prex = prex;
			this.curx = curx;
			this.cury = cury;
			this.color = color;
		}
		public String toString(){
			return this.prex+"--"+this.prey+"--"+this.curx+"--"+this.cury;
		}

	}