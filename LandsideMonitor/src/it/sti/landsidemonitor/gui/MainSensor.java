package it.sti.landsidemonitor.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.LimitExceededException;
import javax.swing.SwingWorker;

import org.apache.commons.mail.EmailException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class MainSensor extends SwingWorker<Integer, Integer>{


	RasterPanel mainP=null;
	PortReader portReader;
	ArrayList<String> msgs=null;
	SensorDTO sensor=null;

	int VALORE_MANCATA_RICEZIONE_SONDA;

	boolean statoPreallarme_1=true;
	boolean statoPreallarme_2=true;
	boolean statoPreallarme_3=true;

	boolean statoAllarme=true;
	boolean statoMancataRicezione=true;

	public int getIterazioni_preallarme_1() {
		return iterazioni_preallarme_1;
	}

	public void setIterazioni_preallarme_1(int iterazioni_preallarme_1) {
		this.iterazioni_preallarme_1 = iterazioni_preallarme_1;
	}

	public int getIterazioni_preallarme_2() {
		return iterazioni_preallarme_2;
	}

	public void setIterazioni_preallarme_2(int iterazioni_preallarme_2) {
		this.iterazioni_preallarme_2 = iterazioni_preallarme_2;
	}

	public int getIterazioni_preallarme_3() {
		return iterazioni_preallarme_3;
	}

	public void setIterazioni_preallarme_3(int iterazioni_preallarme_3) {
		this.iterazioni_preallarme_3 = iterazioni_preallarme_3;
	}


	public int getVALORE_MANCATA_RICEZIONE_SONDA() {
		return VALORE_MANCATA_RICEZIONE_SONDA;
	}

	public void setVALORE_MANCATA_RICEZIONE_SONDA(int vALORE_MANCATA_RICEZIONE_SONDA) {
		VALORE_MANCATA_RICEZIONE_SONDA = vALORE_MANCATA_RICEZIONE_SONDA;
	}
	
	public int iterazioni_preallarme_1=0;
	public int iterazioni_preallarme_2=0;
	public int iterazioni_preallarme_3=0;

	public MainSensor(RasterPanel mainPanel, PortReader pr, SensorDTO _sensor) {
		mainP=mainPanel;
		portReader=pr;
		sensor=_sensor;
		VALORE_MANCATA_RICEZIONE_SONDA=0;
	}

	@Override
	protected Integer doInBackground() throws Exception {

		try{

			while(true)
			{
				Thread.sleep(200);

				String value=portReader.getValue();
					
			     

				if(Costanti.DEBUG) {System.out.println("val "+value);}



				if(sensor.getStato()==0) 
				{
					statoPreallarme_1=true;
					statoPreallarme_2=true;
					statoPreallarme_3=true;
					statoAllarme=true;
					statoMancataRicezione=true;
				}
				
			
				
				String pivot=value.split(",")[0];/*Recupero l'ID del sensore dalla stringa di lettura*/

				if(pivot.equals(sensor.getIdentifier())) 
				{

					VALORE_MANCATA_RICEZIONE_SONDA=0;

					mainP.cambiaStato(sensor.getId(), sensor.getStatoOriginale());	
					double acc_X=0.0;
					double acc_Y=0.0;
					double acc_Z=0.0;

					double acc_X_sign=0.0;
					double acc_Y_sign=0.0;
					double acc_Z_sign=0.0;

					if(value.split(",")[1].length()>0) 
					{
						acc_X=Double.parseDouble(value.split(",")[1]);
					}

					if(value.split(",")[2].length()>0)
					{
						acc_Y=Double.parseDouble(value.split(",")[2]);
					}

					if(value.split(",")[3].length()>0) 
					{ 
						acc_Z=Double.parseDouble(value.split(",")[3]);
					}

					acc_X_sign=acc_X;
					acc_Y_sign=acc_Y;
					acc_Z_sign=acc_Z;

					acc_X=Math.abs(acc_X);
					acc_Y=Math.abs(acc_Y);
					acc_Z=Math.abs(acc_Z);





					/*ASSE X*/

					if( controllaEvento(acc_X,acc_Y,acc_Z,1)) 
					{
						if(sensor.getStato()!=1) 
						{
							if(statoPreallarme_1==true) 
							{
								portReader.write("B");
								statoPreallarme_1=false;

								timer1();

								/*Blocco mail*/
								if(Costanti.DEST_MAIL_PRE!=null && Costanti.DEST_MAIL_PRE.length()>0) 
								{
									String[] destinatati=Costanti.DEST_MAIL_PRE.split(";");
									for (String dest : destinatati) 
									{
										inviaMail(dest,sensor.getIdentifier(),2,acc_X_sign,acc_Y_sign,acc_Z_sign);
									}
								}
							}

							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStatoOriginale(2);
							Core.cambiaStato(sensor.getId() ,2);
							iterazioni_preallarme_1++;



						}
						Core.registraEvento(sensor.getIdentifier(),2,acc_X_sign,acc_Y_sign,acc_Z_sign);
						System.out.println("++ ITERAZIONE P1 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_1+"]");
					}

					if(controllaEvento(acc_X,acc_Y,acc_Z,2))
					{
						if(sensor.getStato()!=1) 
						{

							if(statoPreallarme_2==true) 
							{
								portReader.write("B");
								statoPreallarme_2=false;
								timer2();

								/*Blocco mail*/
								if(Costanti.DEST_MAIL_PRE!=null && Costanti.DEST_MAIL_PRE.length()>0) 
								{
									String[] destinatati=Costanti.DEST_MAIL_PRE.split(";");
									for (String dest : destinatati) 
									{
										inviaMail(dest,sensor.getIdentifier(),3,acc_X_sign,acc_Y_sign,acc_Z_sign);
									}
								}
							}

							mainP.cambiaStato(sensor.getId(), 3);
							sensor.setStatoOriginale(3);
							Core.cambiaStato(sensor.getId() ,3);
							iterazioni_preallarme_2++;
						}
						Core.registraEvento(sensor.getIdentifier(),3,acc_X_sign,acc_Y_sign,acc_Z_sign);
						System.out.println("++ ITERAZIONE P2 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_2+"]");

					}

					if(controllaEvento(acc_X,acc_Y,acc_Z,3))
					{
						if(sensor.getStato()!=1) 
						{

							if(statoPreallarme_3==true) 
							{
								portReader.write("B");
								statoPreallarme_3=false;
								timer3();

								/*Blocco mail*/
								if(Costanti.DEST_MAIL_PRE!=null && Costanti.DEST_MAIL_PRE.length()>0) 
								{
									String[] destinatati=Costanti.DEST_MAIL_PRE.split(";");
									for (String dest : destinatati) 
									{
										inviaMail(dest,sensor.getIdentifier(),4,acc_X_sign,acc_Y_sign,acc_Z_sign);
									}
								}
							}


							mainP.cambiaStato(sensor.getId(), 4);
							sensor.setStatoOriginale(4);
							Core.cambiaStato(sensor.getId() ,4);
							iterazioni_preallarme_3++;
						}
						Core.registraEvento(sensor.getIdentifier(),4,acc_X_sign,acc_Y_sign,acc_Z_sign);
						System.out.println("++ ITERAZIONE P3 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_3+"]");

					}


					if(sensor.getStato()!=1) 
					{
						if( acc_X>Costanti.LIMITE_MAX_P3|| acc_Y > Costanti.LIMITE_MAX_P3 || acc_Z > Costanti.LIMITE_MAX_P3) 
						{
							System.out.println("ALLARME "+acc_X+" "+acc_Y+" "+acc_Z );
							if(statoAllarme==true) 
							{

								Core.registraEvento(sensor.getIdentifier(),1,acc_X_sign,acc_Y_sign,acc_Z_sign);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");
								statoAllarme=false;

								iterazioni_preallarme_1=0;
								iterazioni_preallarme_2=0;
								iterazioni_preallarme_3=0;

								/*Blocco mail*/
								if(Costanti.DEST_MAIL_ALARM!=null && Costanti.DEST_MAIL_ALARM.length()>0) 
								{
									String[] destinatati=Costanti.DEST_MAIL_ALARM.split(";");
									for (String dest : destinatati) 
									{
										inviaMail(dest,sensor.getIdentifier(),4,acc_X_sign,acc_Y_sign,acc_Z_sign);
									}
								}

							}


						}
						else 
						{
							if(iterazioni_preallarme_1>=Costanti.TEMPO_ALLERTA_1 && iterazioni_preallarme_2<Costanti.TEMPO_ALLERTA_2 && iterazioni_preallarme_3<Costanti.TEMPO_ALLERTA_3) 
							{
								System.out.println("INSIDE P1");


								Core.registraEvento(sensor.getIdentifier(),1,acc_X_sign,acc_Y_sign,acc_Z_sign);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);	
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");


							}
							if(iterazioni_preallarme_2>=Costanti.TEMPO_ALLERTA_2 && iterazioni_preallarme_3<Costanti.TEMPO_ALLERTA_3) 
							{
								System.out.println("INSIDE P2");


								Core.registraEvento(sensor.getIdentifier(),1,acc_X_sign,acc_Y_sign,acc_Z_sign);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");


							}
							if(iterazioni_preallarme_3>=Costanti.TEMPO_ALLERTA_3) 
							{
								System.out.println("INSIDE P3");

								Core.registraEvento(sensor.getIdentifier(),1,acc_X_sign,acc_Y_sign,acc_Z_sign);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");
								statoPreallarme_3=false;
							}
						}

					}

				}else
				{
					VALORE_MANCATA_RICEZIONE_SONDA++;



					if(VALORE_MANCATA_RICEZIONE_SONDA > Costanti.VALORE_MANCATA_RICEZIONE_SONDA) 
					{
						mainP.cambiaStato(sensor.getId(),5);
						Core.cambiaStato(sensor.getId() ,5);
						if(statoMancataRicezione==true) 
						{
							Core.registraEvento(sensor.getIdentifier(),5,0,0,0);
							statoMancataRicezione=false;
						}
					}
				}

			}
		}catch (Exception e) {
			e.printStackTrace();
		}


		return 0;	

	}

	private boolean controllaEvento(double acc_X, double acc_Y, double acc_Z, int i) throws SQLException {
		
		double min=0;
		double max=0;
	
		
		if(i==1) 
		{
			min=Costanti.LIMITE_MIN_P1;
			max=Costanti.LIMITE_MAX_P1;
		}
		if(i==2) 
		{
			min=Costanti.LIMITE_MIN_P2;
			max=Costanti.LIMITE_MAX_P2;
		}
		if(i==3) 
		{
			min=Costanti.LIMITE_MIN_P3;
			max=Costanti.LIMITE_MAX_P3;
		}
		
		
		if((acc_X>=min && acc_X<=max)|| 
		(acc_Y>=min && acc_Y<=max)||
		(acc_Z>=min && acc_Z<=max))
		{
			System.out.println("EVENTO");
			if((acc_X>=2.4 && acc_X<=2.6) && acc_Y<=Costanti.LIMITE_MIN_P1 && acc_Z<=Costanti.LIMITE_MIN_P1 ) 
			{
			//	Core.registraEvento("S1",5,acc_X,acc_Y,acc_Z);
				return false;
			}
			else if((acc_Y>=2.4 && acc_Y<=2.6) && acc_X<=Costanti.LIMITE_MIN_P1 && acc_Z<=Costanti.LIMITE_MIN_P1 ) 
			{
		//		Core.registraEvento("S1",5,acc_X,acc_Y,acc_Z);
				return false;
			}
			else if((acc_Z>=2.4 && acc_Z<=2.6) && acc_Y<=Costanti.LIMITE_MIN_P1 && acc_X<=Costanti.LIMITE_MIN_P1 ) 
			{
		//		Core.registraEvento("S1",5,acc_X,acc_Y,acc_Z);
				return false;
			}
			else 
			{
				System.out.println("TRUE");
				return true;
			}
			
			
		}
		
		return false;
	}

	private void inviaMail(String destinataro,String idSonda, int tipoAllarme, double acc_X, double acc_Y, double acc_Z) {


		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					SendEmailBO.sendEmailAlarm(destinataro, idSonda,tipoAllarme, acc_X, acc_Y, acc_Z);
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t.start();
	}

	private void timer1() {


		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Timer 1 "+new Date());
					Thread.sleep(Costanti.TIMER_ITERAZIONI*1000);
					iterazioni_preallarme_1=0;
					statoPreallarme_1=true;
					System.out.println("Timer 1 FINE "+new Date());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
		t.start();
	}

	private void timer2() {


		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					System.out.println("Timer 2 "+new Date());
					Thread.sleep(Costanti.TIMER_ITERAZIONI*1000);
					iterazioni_preallarme_2=0;
					statoPreallarme_2=true;
					System.out.println("Timer 2 FINE "+new Date());


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
		t.start();
	}

	private void timer3() {


		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					System.out.println("Timer 3 "+new Date());
					Thread.sleep(Costanti.TIMER_ITERAZIONI*1000);
					iterazioni_preallarme_3=0;
					statoPreallarme_3=true;
					System.out.println("Timer 3 FINE "+new Date());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
		t.start();
	}


}
