package SupervisorDeCarro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import CarrosParaTeste.*;
import Util.InformacoesCarro;
import Util.Infracao;
import Util.ParOrdenado;
import Util.TipoInfracoes;

public class Supervisor implements ObservadorSupervisor{
	private ArrayList<Infracao> infracoes; //(atributo)
	private HashMap<Integer, Carro> carros;
	private HashMap<Integer, InformacoesCarro> informacoesCarros;
	private double tempoInicioProvaSegundos;
	private boolean jaVerifiqueiRegra6;
	
	private final double distMin = 2;
	private final double velMax = 10;
	private final double acelMax = 2;
	private final double tempMax = 15;
	private final double distMinPare = 100;
	
	public Supervisor()
	{
		infracoes = new ArrayList<>();
		carros = new HashMap<Integer, Carro>();
		informacoesCarros = new HashMap<Integer, InformacoesCarro>();
		tempoInicioProvaSegundos = getTempoAtualSegundos();
		jaVerifiqueiRegra6 = false;
	}
	
	public void adicionaCarros(Carro carro)
	{
		carros.put(carro.getCarroId(), carro);
	}
	
	@Override
	public void atualizarSupervisor(Carro carro)
	{
		int carroId = carro.getCarroId();
		Carro carroAux = carros.get(carroId);
		//Verifica se o carro passado já está cadastrado no HashMap de carros
		if(carroAux != null)
		{
			InformacoesCarro infoCarroAux = informacoesCarros.get(carroId);
			//Verifica se o carro passado já está cadastrado no HashMap de informações do carro
			if(infoCarroAux != null)
			{
				VerificarRegras(carro, infoCarroAux, getTempoAtualSegundos(),true);
				AtualizaInfoCarro(carro, getTempoAtualSegundos());
			}
			else
			{
				InformacoesCarro infoCarro = new InformacoesCarro(carroId, getTempoAtualSegundos(), carro.getPosicao(), carro.getVelocidade());
				informacoesCarros.put(carroId, infoCarro);
				VerificarRegras(carro, infoCarro, getTempoAtualSegundos(), false);
			}
		}
	}	

	private double getTempoAtualSegundos()
	{
		return new Date().getTime()/1000.0;
	}

	private void AtualizaInfoCarro(Carro carro, double tempoAtual)
	{
		int carroId = carro.getCarroId();
		InformacoesCarro infoCarro = informacoesCarros.get(carroId);
		infoCarro.setUltimoTempo(tempoAtual);
		infoCarro.setUltimaPosicao(carro.getPosicao());
		infoCarro.setUltimaVelocidade(carro.getVelocidade());
		
	}

	private void VerificarRegras(Carro carro, InformacoesCarro infoCarro, double tempoAtual, boolean novo)
	{
		int carroId = carro.getCarroId();
		double ultimoTempo = infoCarro.getUltimoTempo();
		ParOrdenado posicao = carro.getPosicao();
		ParOrdenado velocidade  = carro.getVelocidade();
		System.out.println("A variação do tempo é " + (tempoAtual - informacoesCarros.get(1).getUltimoTempo()));
		if(novo)
		{//apenas as mutas para carro novo

			//multando pela regra 1
			if(verifRegra1(posicao))
			{
				Infracao infracao = new Infracao(carroId, 5 , TipoInfracoes.REGRA1.getTipoInfracao());
				infracoes.add(infracao);
				int multa = 5;
				carro.addPontuacao(multa);
			}
			
			//multando pela regra 5
			if(verifRegra5(velocidade))
			{
				Infracao infracao = new Infracao(carroId, 5 , TipoInfracoes.REGRA5.getTipoInfracao());
				infracoes.add(infracao);
				int multa = 5;
				carro.addPontuacao(multa);
			}
			
		}
		else
		{
			// regra 2 
		    ParOrdenado parC = carro.getPosicao();		
			int viaC=getVia(parC);
			Collection<Carro> todosCarros = carros.values();
			for (Carro c1 : todosCarros){
				ParOrdenado parC1 = c1.getPosicao();
				int viaC1 = getVia(parC1);
				if (viaC==viaC1){ //se as vias são iguais posso multar
					if(viaC1==1 ||viaC1==3 ||viaC1==6 ||viaC1==8)
					{ //nessas vias se ele esta atrás do carro sua coordenada e menor
						double x = parC.getX();
						double x1 = parC1.getX();
						double y = parC.getY();
						double y1 = parC1.getY();
						if ((y>y1)||(x>x1))
						{ //coordenada maior?
							if (verifRegra2(parC,parC1)) 
							{
								Infracao infracao = new Infracao(carroId, 5 ,  TipoInfracoes.REGRA2.getTipoInfracao());
								infracoes.add(infracao);
								int multa = 5;
								carro.addPontuacao(multa);
							}
						}
					}
					
					else if(viaC1==2 ||viaC1==4 ||viaC1==5 ||viaC1==7)
					{ //nessas vias se ele esta atrás do carro sua coordenada e maior
						double x = parC.getX();
						double x1 = parC1.getX();
						double y = parC.getY();
						double y1 = parC1.getY();
						if ((y<y1)||(x<x1)) { //coordenada menor?
							if (verifRegra2(parC,parC1))
							{
								Infracao infracao = new Infracao(carroId, 5 ,  TipoInfracoes.REGRA2.getTipoInfracao());
								infracoes.add(infracao);
								int multa = 5;
								carro.addPontuacao(multa);
							}
						}
					}
				}
			}
		//multando pela regra 3
			if (verifRegra3(velocidade))
			{
				Infracao infracao = new Infracao(carroId, 5 ,  TipoInfracoes.REGRA3.getTipoInfracao());
				infracoes.add(infracao);
				int multa = 5;
				carro.addPontuacao(multa);
			}
			
			//multando pela regra 4
			if (verifRegra4(velocidade, tempoAtual, ultimoTempo))
			{
				Infracao infracao = new Infracao(carroId, 5 ,  TipoInfracoes.REGRA4.getTipoInfracao());
				infracoes.add(infracao);
				int multa = 5;
				carro.addPontuacao(multa);
			}
			
			
			//multando pela regra 8
			if (verifRegra8(posicao, velocidade))
			{
				Infracao infracao = new Infracao(carroId, 5 ,  TipoInfracoes.REGRA8.getTipoInfracao());
				infracoes.add(infracao);
				int multa = 5;
				carro.addPontuacao(multa);
			}
		}
		if(!jaVerifiqueiRegra6 && tempoAtual - tempoInicioProvaSegundos > tempMax && carros.size() < 36)
		{
			jaVerifiqueiRegra6 = true;
			VerifRegra6();
		}
	}

	public ArrayList<Infracao> getInfracoes(){
		ArrayList<Infracao> copiaInfracoes = new ArrayList<Infracao>() ;
		for(Infracao i: infracoes){
			Infracao infracao = new Infracao(i.getIdCarro(), i.getPontuacao(), i.getTipoInfracao());
			copiaInfracoes.add(infracao);
		}
		return (copiaInfracoes);
	}

	
	private int getVia(ParOrdenado par1){
		double valorX= par1.getX();
		double valorY= par1.getY();
		
		if (valorX>-20 && valorX<20 && valorY>-20 && valorY<20 ){
			return 0;
		}
		
		else if (valorY==10 && valorX<-20){
			return 1;
		}
		
		else if (valorY==-10 && valorX<-20){
			return 2;
		}

		else if (valorY<-20 && valorX==-10){
			return 3;
		}
		
		else if (valorY<-20 && valorX==10){
			return 4;
		}

		else if (valorY==-10 && valorX>20){
			return 5;
		}

		else if (valorY==10 && valorX>20){
			return 6; 
		}

		else if (valorY>20 && valorX==10){
			return 7;
		}

		else if (valorY>20 && valorX==-10){
			return 8;
		}
		
		else 
			return -1;
	}
	
	private int getLocalizarPare(ParOrdenado par1){
		double valorX= par1.getX();
		double valorY= par1.getY();
		if (valorX==-10 && valorY==20){
			return 1;
		}
		else if (valorX==-20 && valorY==-10){
			return 2;
		}		
		else if (valorX==20 && valorY==10){
			return 3;
		}		
		else if (valorX==10 && valorY==-20){
			return 4;
		}
		else
			return -1;
}
	
	//Regra 1 é a regra da distancia inicial < 100
	private boolean verifRegra1 (ParOrdenado par1)
	{ 
		double valorX= par1.getX();
		double valorY= par1.getY();
		double distancia = Math.sqrt(Math.pow(valorX,2) + Math.pow(valorY,2));
		
		if(distancia<distMinPare)
		{
			return true;
		}
		
		else{
			return false;
		}		
	}
	
	/*
	 * Temos que blindar esse metodo de gerar multa pro carro 
	 * que ta na frente. Pra isso temos que definir padrão de vias
	 * ou seja, enumerar cada mão de cada via.
	 * Depois disso basta comparar o valor da coordenada x ou y
	 * que saberemos qual carro está na frente e qual carro está
	 * atrás.
	 */

	//Regra 2 é a regra da distancia entre carros < 2
	private boolean verifRegra2 (ParOrdenado par1, ParOrdenado par2)
	{
		double valorX1= par1.getX();
		double valorY1= par1.getY();
		
		double valorX2= par2.getX();
		double valorY2= par2.getY();

		double distancia = Math.sqrt(Math.pow(valorX1-valorX2,2) + Math.pow(valorY1-valorY2,2));
		
		if(distancia<distMin)
		{
			return true;
		}
		
		else{
			return false;
		}		
	}
	
	//Regra 3 é a regra da velocidade > 10
	private boolean verifRegra3 (ParOrdenado par1)
	{ 
		double valorX= par1.getX();
		double valorY= par1.getY();
		double velocidade = Math.sqrt(Math.pow(valorX,2) + Math.pow(valorY,2));
		
		if(velocidade>velMax)
		{
			return true;
		}
		
		else{
			return false;
		}		
	}
	
	// Regra 4 Aceleracao != 1 km/h a cada 0.5 s NÃO ESTÁ FEITA !!!

	private boolean verifRegra4 (ParOrdenado par1, double tempoAtual, double ultimoTempo)
	{ 
		double valorX1= par1.getX();
		double valorY1= par1.getY();
		
		double velocidade = Math.sqrt(Math.pow(valorX1,2) + Math.pow(valorY1,2));
		double aceleracao = velocidade/(tempoAtual-ultimoTempo); 
		
		if(aceleracao>acelMax)
		{
			return true;
		}
		
		else{
			return false;
		}		
	}
	
	//Regra 5 é a regra da velocidade inicial > 0
	private boolean verifRegra5 (ParOrdenado par1)
	{ 
		double valorX= par1.getX();
		double valorY= par1.getY();
		double velocidade = Math.sqrt(Math.pow(valorX,2) + Math.pow(valorY,2));
		
		if(velocidade>0)
		{
			return true;
		}
		
		else{
			return false;
		}		
	}
	
	private void VerifRegra6()
	{
		Collection<Carro> todosCarros = carros.values();
		ArrayList<Carro> carrosC1 = new ArrayList<>();
		ArrayList<Carro> carrosC2 = new ArrayList<>();
		ArrayList<Carro> carrosC3 = new ArrayList<>();
		ArrayList<Carro> carrosC4 = new ArrayList<>();
		ArrayList<Carro> carrosC5 = new ArrayList<>();
		ArrayList<Carro> carrosC6 = new ArrayList<>();
		int contadorEquipe1 = 0;
		int contadorEquipe2 = 0;
		int contadorEquipe3 = 0;
		int contadorEquipe4 = 0;
		int contadorEquipe5 = 0;
		int contadorEquipe6 = 0;
		for(Carro carroAux : todosCarros)
		{
			int carroId = carroAux.getCarroId();
			if(carroId > 10 && carroId < 20 )
			{
				contadorEquipe1++;
				carrosC1.add(carroAux);
			}
			if(carroId > 20 && carroId < 30 )
			{
				contadorEquipe2++;
				carrosC2.add(carroAux);
			}
			if(carroId > 30 && carroId < 40 )
			{
				contadorEquipe3++;
				carrosC3.add(carroAux);
			}
			if(carroId > 40 && carroId < 50 )
			{
				contadorEquipe4++;
				carrosC4.add(carroAux);
			}
			if(carroId > 50 && carroId < 60 )
			{
				contadorEquipe5++;
				carrosC5.add(carroAux);
			}
			if(carroId > 60 && carroId < 70 )
			{
				contadorEquipe6++;
				carrosC6.add(carroAux);
			}
		}
		if(contadorEquipe1 < 6)
			puneEquipe(carrosC1);
		if(contadorEquipe2 < 6)
			puneEquipe(carrosC2);
		if(contadorEquipe3 < 6)
			puneEquipe(carrosC3);
		if(contadorEquipe4 < 6)
			puneEquipe(carrosC4);
		if(contadorEquipe5 < 6)
			puneEquipe(carrosC5);
		if(contadorEquipe6 < 6)
			puneEquipe(carrosC6);
	}
	
	private void puneEquipe(ArrayList<Carro> carros)
	{
		for(Carro carroAux : carros)
		{
			Infracao infracao = new Infracao(carroAux.getCarroId(), 5 ,  TipoInfracoes.REGRA6.getTipoInfracao());
			infracoes.add(infracao);
			int multa = 5;
			carroAux.addPontuacao(multa);
		}
		
	}

	// Regra 7 Virar no sentido errado NÃO ESTÁ FEITA !!!
	/*
	 *  Aqui temos que convencionar cada sentido de cada via 
	 *  como um numero. Dai se ele disser que ia virar pra um lado
	 *  a distancia X ou Y dele é fixa e pré-estabelecida pra cada via
	 *  Se a distancia não for a mesma, significa que ele virou pro lado errado. 
	 */
	private boolean verifRegra7 (ParOrdenado par1)
	{
		return false;
	}

	
	// Regra 8 Velocidade no PARE != 0

	private boolean verifRegra8 (ParOrdenado par1, ParOrdenado par2)
	{ 
		double valorX= par2.getX();
		double valorY= par2.getY();
		
		double velocidade =  Math.sqrt(Math.pow(valorX,2) + Math.pow(valorY,2));; //aqui temos que saber
		//qual o tempo que temos que dividir a velocidade 
		
		int i = getLocalizarPare(par1);
		if (i!=-1)
		{
			if(velocidade>0)
			{
				return true;
			}
			else{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}