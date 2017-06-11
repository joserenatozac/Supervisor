package CarrosParaTeste;
import java.util.ArrayList;
import SupervisorDeCarro.ObservadorSupervisor;


public class Carro implements ObservadoSupervisor
{
	private ArrayList<ObservadorSupervisor> observadores;
	private int carroId;
	private double[] posicao;
	private double velocidade;
	private static int numCarros;
	private int sentidoConversao;
	private int pontuacao;
	
	public Carro()
	{
		numCarros++;
		carroId = numCarros;
		posicao = null;
		observadores = new ArrayList<>();
	}
	
	public int getCarroId()
	{
		return carroId;
	}
	
	public void setPosicao(double x, double y)
	{
		posicao[0]=x;
		posicao[1]=y;
		notificaObservadores();
	}
	
	public void setVelocidade(double x, double y)
	{
		velocidade=y;
		notificaObservadores();
	}
	
	public void addPontuacao(int p){
		pontuacao = pontuacao + p;
	}
	
	public double[] getPosicao()
	{
		return posicao;
	}

	@Override
	public void adicionarObservador(ObservadorSupervisor o)
	{
		observadores.add(o);
	}

	@Override
	public void removerObservador(ObservadorSupervisor o)
	{
		int i = observadores.indexOf(o);
		if(i > 0)
			observadores.remove(i);
	}

	@Override
	public void notificaObservadores()
	{
		for(ObservadorSupervisor observador: observadores)
		{
			observador.atualizarSupervisor(this);
		}
	}

	public double getVelocidade() {
		return velocidade;
	}
	
	public void setSentidoConversao(int sentidoConversao)
	{
		this.sentidoConversao = sentidoConversao;
	}
	
	public int getSentidoConversao()
	{
		return sentidoConversao;
	}
	
}
