package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dao.MainDAO;
import it.sti.landsidemonitor.dto.SensorDTO;


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
		
		  JButton reset = new JButton("RESET");
	      reset.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/update.png")));
	      reset.setFont(new Font("Arial", Font.BOLD, 14));
	      reset.setBounds(10, 800, 120, 30);
	      
	      this.add(reset);
	      
	      reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
               
			for (int i=0;i<listaSensori.size();i++) 
			{		
				String idSonda = ""+listaSensori.get(i).getId();
               	 try {
             
               	cambiaStato(Integer.parseInt(idSonda), 0);
                cambiaStatoOriginale(Integer.parseInt(idSonda), 0);

               		
               	PortReader.write("C"+listaSensori.get(i).getIdentifier());
               	
               	double tempoStart=System.currentTimeMillis();
				
        		String msgCalibration="";
        		
               	while(true)
				{
					double tempoTrascorso=System.currentTimeMillis()-tempoStart;
					
					String message=PortReader.getMessage();
				
					
					if(message.startsWith("<CL-"+listaSensori.get(i).getIdentifier()))
						{
							System.out.println("MGR RESET "+message);
							msgCalibration=message;
						}
					
					if(tempoTrascorso>1000) 
					{
						if(msgCalibration.equals(""))
						{
							cambiaStato(Integer.parseInt(idSonda), 5);
		                 	cambiaStatoOriginale(Integer.parseInt(idSonda), 5);
						}
						break;
					}
				}	
               	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               	 
					
				}
		//	dispose();
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
	  
	    for (int i=0;i<listaSensori.size();i++) {
	    	
	    	int stato =listaSensori.get(i).getStato();
	    	
	    	Point point=listaSensori.get(i).getPoint();
	    	g.setColor(getStato(stato));
	    	if(stato==4)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("NON DEFINITO "+listaSensori.get(i).getIdentifier()+"("+listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==3)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("PRE ALLARME "+listaSensori.get(i).getIdentifier()+"("+listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==2)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16)); 
	    		 g.drawString("ALLERTA "+listaSensori.get(i).getIdentifier()+"("+listaSensori.get(i).getType()+")", point.x,point.y);
	    		
	    	}
	    	else if(stato==1)
	    	{
	    		 g.fillOval(point.x, point.y,20, 20);
	    		 g.setFont(new Font("Arial", Font.BOLD, 16));
	    		 g.drawString("ALLARME "+listaSensori.get(i).getIdentifier()+"("+listaSensori.get(i).getType()+")", point.x,point.y);
	    		  
	    	}
	    	else 
	    	{
	    		g.fillOval(point.x, point.y,20, 20);
	    		g.setFont(new Font("Arial", Font.BOLD,16));
			    g.drawString(""+listaSensori.get(i).getIdentifier()+"("+listaSensori.get(i).getType()+")", point.x,point.y);
			   
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


	public void cambiaStato(int idSonda , int stato) {
		
		 for (int i = 0; i < listaSensori.size(); i++) 
		  {
			  if(listaSensori.get(i).getId()==idSonda) 
			  {  
				  listaSensori.get(i).setStato(stato);
				  try 
				  {
					Core.cambiaStato(idSonda, stato);  
				
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
	
	
	}
