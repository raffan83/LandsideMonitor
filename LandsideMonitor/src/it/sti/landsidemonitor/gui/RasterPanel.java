package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.sms.SendSMS;
import net.miginfocom.swing.MigLayout;


public class RasterPanel extends JPanel{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	  int width;
	  int height;
	  static JFrame myFrame=null;
	  private ArrayList<SensorDTO> listaSensori;
	  public static JButton reset;
	  
	  final static Logger logger = Logger.getLogger(RasterPanel.class);
	  
	  public RasterPanel(URL imageURL, JFrame g, ArrayList<SensorDTO> _listaSensori){
	    super(true); //crea un JPanel con doubleBuffered true
	   
	    
	    try{
	      listaSensori=_listaSensori;	
	      myFrame=g; 	
	      setLayout(null);
	      ImageIO.setUseCache(false);
	      setImage(ImageIO.read(imageURL));
	      aggiungiTasti();

	 //     this.addMouseListener(mouseHandler);
	  //    this.addMouseMotionListener(mouseHandler);
	        
	    } catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	  }
	  
	  
	  private void aggiungiTasti() {
		
		  reset = new JButton("RESET");
	      reset.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/update.png")));
	      reset.setFont(new Font("Arial", Font.BOLD, 14));
	     
	      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	      reset.setBounds(10, (int)dim.getHeight()-100, 120, 30);
	      
	      this.add(reset);
	      
	      reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
		 try {
			 
			JFrame progress = new JFrameProgress();			
			progress.setVisible(true);
			
			
			listaSensori=Core.getListaSensori();
			
			for (int i=0;i<listaSensori.size();i++) 
			{	
				int numero_tentativi=0;
				String idSonda = ""+listaSensori.get(i).getId();
               	
				int pr=(100/listaSensori.size())*(i+1);
				((JFrameProgress) progress).setMessage("Calibrazione sonda "+listaSensori.get(i).getIdentifier(), pr);
              
				cambiaStato(Integer.parseInt(idSonda), 0,0);
                cambiaStatoOriginale(Integer.parseInt(idSonda), 0);

               		
              	PortReader.write("C"+listaSensori.get(i).getIdentifier());
               
              	System.out.println("Chiamata 1");
               	double tempoStart=System.currentTimeMillis();
				
        		String msgCalibration="";
        		
               	while(true)
				{
					double tempoTrascorso=System.currentTimeMillis()-tempoStart;
					
					String message=PortReader.getMessage();
					
				//	System.out.println(message);
					if(message.startsWith("<CL-"+listaSensori.get(i).getIdentifier()))
						{
							System.out.println("MGR RESET "+message);
							msgCalibration=message;
							cambiaStato(Integer.parseInt(idSonda), 0,0);
		                 	cambiaStatoOriginale(Integer.parseInt(idSonda), 0);
		                 	
		                 	/*Esclusione sonda*/
		                 	String levBatt=message.split(",")[1];
		                 	
		                 
							if(levBatt!=null && !levBatt.equals("")) 
							{
								
								double tension=Double.parseDouble(levBatt);
								
								/*Eliminare*/
								if(listaSensori.get(i).getIdentifier().equals("H")) 
			                 	{
			                 		tension=tension+4;
			                 	}
										{
											if(tension<=Costanti.SOGLIA_BATTERIA) 
											{
												System.out.println("Esclusione sonda da ciclo:"+listaSensori.get(i).getIdentifier());
												logger.warn("Esclusione sonda da ciclo:"+listaSensori.get(i).getIdentifier()+" - low battery");
												listaSensori.get(i).setIdentifier(listaSensori.get(i).getIdentifier()+"_");
												cambiaStato(listaSensori.get(i).getId(), 5,5);
												cambiaStatoOriginale(listaSensori.get(i).getId(), 5);
											}
										}
							}
		                 	
							break;
						}
					
					if(tempoTrascorso>500) 
					{
						if(msgCalibration.equals(""))
						{
							cambiaStato(Integer.parseInt(idSonda), 5,5);
		                 	cambiaStatoOriginale(Integer.parseInt(idSonda), 5);
						}
						numero_tentativi++;
						if(numero_tentativi==3) 
						{
							break;
						}
						else 
						{
							PortReader.write("C"+listaSensori.get(i).getIdentifier());
							tempoStart=System.currentTimeMillis();
							System.out.println("Chiamata 2 ripetizione "+numero_tentativi);
						}
					}
				}	
					
				}
			MainFrame.listaSensori=listaSensori;
			PortReader.listaSensori=listaSensori;
			PortReader.puntiAttiviB= new HashMap<SensorDTO, Long>();
          	PortReader.sogliaAllerta= new HashMap<String, SensorDTO>();
          	((JFrameProgress) progress).close();
		 } catch (Exception e) {
				
				e.printStackTrace();
			}
		 
			}
			});
		
	}


	public void setImage(BufferedImage img){
	   
		/*Calcolo Ratio immagine/schermo*/  
		this.img = img;
	    width = img.getWidth();
	    height = img.getHeight();
	  }

	  public void paintComponent(Graphics g){
	    super.paintComponent(g);

	
	    g.drawImage(img, 0, 0, myFrame.getWidth(), myFrame.getHeight()-40,this);
	  
	    for (int i=0;i<MainFrame.listaSensori.size();i++) {
	    	
	    	int stato =MainFrame.listaSensori.get(i).getStato();
	    	
	    	Point point=MainFrame.listaSensori.get(i).getPoint();
	    	g.setColor(getStato(stato));
	    	if(stato==4)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("NON DEFINITO "+MainFrame.listaSensori.get(i).getIdentifier()+"("+MainFrame.listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==3)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("PRE ALLERTA "+MainFrame.listaSensori.get(i).getIdentifier()+"("+MainFrame.listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==2)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("ALLERTA "+MainFrame.listaSensori.get(i).getIdentifier()+"("+MainFrame.listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==1)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16));
	    		 g.drawString("ALLARME "+MainFrame.listaSensori.get(i).getIdentifier()+"("+MainFrame.listaSensori.get(i).getType()+")", point.x,point.y);
	    		  
	    	}
	    	else 
	    	{
	    		g.fillOval(point.x, point.y,20, 20);
	    		g.setFont(new Font("Arial", Font.BOLD,16));
			    g.drawString(""+MainFrame.listaSensori.get(i).getIdentifier()+"("+MainFrame.listaSensori.get(i).getType()+")", point.x,point.y);
			   
	    	}
		    
		 
		
		}
	    
	
	  }
	  
	  private Color getStato(int stato) {
		
		  if(stato==1)
		  {
			return Color.RED;  
		  }
		  if(stato==0)
		  {
			return Color.GREEN;  
		  }
		  
		  if(stato==2)
		  {
			return Color.ORANGE;  
		  }
		  if(stato==3)
		  {
			return Color.YELLOW;  
		  }
		  if(stato==4)
		  {
			return Color.MAGENTA;  
		  } 
		  if(stato==5)
		  {
			 /*Cambiare in nero*/ 
			return Color.BLACK;  
		  }
		  
		return null;
	}


	public void translate(String idSens,int x,int y) 
	  {

		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==Integer.parseInt(idSens))
				  
				  listaSensori.get(i).getPoint().translate(x, y);
			  
			  repaint();
		}
		
	  }
	  
//	   private class MouseHandler extends MouseAdapter {
//
//	        @Override
//	        public void mousePressed(MouseEvent e) {
//	            
////	        	drawing = true;
////	        	
////	        	p1=null;
////	        	
////	        	double posMouseX=e.getPoint().getX();
////	        	double posMouseY=e.getPoint().getY();
////	        	
////	        	for (int i=0 ;i<listaPoint.size();i++) {
////					
////	        		Point point=listaPoint.get(i);
////	        		
////	        		if(((point.getX()>=(posMouseX-20) && point.getX()<=(posMouseX+20)) &&
////	        				point.getY()>=(posMouseY-20) && point.getY()<=(posMouseY+20))) 
////	        		{
////	        			p1=point;
////	        		}
////	        	}
////	        	System.out.println(listaPoint.get(0).getX()+"  -  "+listaPoint.get(0).getY());
////	        	System.out.println(e.getPoint().getX()+"  -  "+e.getPoint().getY());
////	        //    p1 = e.getPoint();
////	          
////	            repaint();
//	        }
//
//	    
//	    }

	public void removePoint(String idSonda) {

		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==Integer.parseInt(idSonda))
				  
				  listaSensori.get(i).getPoint().setLocation(-100, -100);
			  
			  repaint();
		}
		
	}


	public void addPoint(SensorDTO s) 
	{

		listaSensori.add(s);
		
		repaint();
	}


	public void cambiaStato(int idSonda , int stato, int statoOriginale) {
		
		System.out.println("[CAMBIO STATO RASTERPANEL] ["+ idSonda+"] - STATO["+stato+"]"+ " - STATO ORIGINALE["+statoOriginale+"]");
		
		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==idSonda) 
			  {  
				  listaSensori.get(i).setStato(stato);
				  try 
				  {
					Core.cambiaStato(idSonda, stato);  
					
					if(stato==1 || stato==2 ||  stato==3) 
						
					{
						listaSensori.get(i).setStatoOriginale(stato);
						if(stato!=statoOriginale) 
						{
						String id=listaSensori.get(i).getIdentifier()+" (GR."+listaSensori.get(i).getType()+")";
						
						SendEmailBO mail = new SendEmailBO(id, stato,1);
						new Thread(mail).start();
						
						SendSMS sms = new SendSMS(id, stato);
						new Thread(sms).start();
						
						PortReader.alarmDuration=System.currentTimeMillis();
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  
		}
		 repaint();
	}


	public void spostaSonda(String idSonda, int x, int y) {
		
		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==Integer.parseInt(idSonda))
				  
				  listaSensori.get(i).getPoint().translate(x, y);
			  
			  repaint();
		}
		
	}


	public SensorDTO getSonda(String idSonda) {
		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==Integer.parseInt(idSonda))
				  
				 return listaSensori.get(i);
			
		}
		 return null;
	}


	public void cambiaStatoOriginale(int idSonda, int statoOriginale) {
		
		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==idSonda) 
			  {  
				  listaSensori.get(i).setStatoOriginale(statoOriginale);
			  }
			  
		}
		 repaint();
		
	}
	
	
	class JFrameProgress extends JFrame
	{
	
		JProgressBar pb;
		JLabel lblPb;
		JLabel lblMessPb;
		JFrame g;
		public JFrameProgress() 
		{
			g=this;
			setUndecorated(true);
			setIconImage(Toolkit.getDefaultToolkit().getImage(InitSplash.class.getResource("/image/covic.png")));
			setResizable(false);
			getContentPane().setBackground(Color.WHITE);
			
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(Color.RED, 2, true));
			panel.setBackground(Color.WHITE);
			setSize(480, 80);
			panel.setSize(480,60);
			panel.setLayout(new MigLayout("", "[grow]", "[30px][25px]"));
		
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (dim.width - panel.getWidth()) / 2;
			int y = (dim.height - panel.getHeight()) / 2;
			setLocation(x, y);
			
			getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
			 
			pb = new JProgressBar();
			panel.add(pb, "flowx,cell 0 1,grow");
			
			lblPb = new JLabel("0%");
			lblPb.setFont(new Font("Arial", Font.BOLD, 12));
			panel.add(lblPb, "cell 0 1,width :30:");
			
			lblMessPb = new JLabel("-");
			lblMessPb.setFont(new Font("Arial", Font.PLAIN, 12));
			panel.add(lblMessPb, "flowx,cell 0 2,growx");
			
			getContentPane().add("cell 0 0 ,grow", panel);
		}
		
		public  void setMessage(final String mess, final int progress)
		{
		
			
			Runnable updateGUI = new Runnable() {  
				public void run() {  
					pb.setValue(progress);
					lblPb.setText(progress+"%");
					lblMessPb.setText(mess);
					g.update(g.getGraphics());
				}  
			};  
			try{
					Thread t = new Thread(updateGUI);  
					t.start();
					
					Thread.sleep(10);
			}catch 
			(Exception e) {
				e.printStackTrace();
			}
			
		}

		public  void close() {
			
			g.dispose();
		}
	}
	}

