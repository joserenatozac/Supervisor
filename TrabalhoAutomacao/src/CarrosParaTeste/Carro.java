package CarrosParaTeste;
import java.util.ArrayList;
import SupervisorDeCarro.ObservadorSupervisor;
import Util.ParOrdenado;

public class Carro implements ObservadoSupervisor
{
	private ArrayList<ObservadorSupervisor> observadores;
	private int carroId;
	private ParOrdenado posicao;
	private ParOrdenado velocidade;
	private static int numCarros;
	private int pontuacao;
	
	public Carro()
	{
		numCarros++;
		carroId = numCarros;
		posicao = new ParOrdenado();
		velocidade = new ParOrdenado();
		observadores = new ArrayList<>();
	}
	
	public int getCarroId()
	{
		return carroId;
	}
	
	public void setPosicao(double x, double y)
	{
		posicao.setX(x);
		posicao.setY(y);
		notificaObservadores();
	}
	
	public void setVelocidade(double x, double y)
	{
		velocidade.setX(x);
		velocidade.setY(y);
		notificaObservadores();
	}
	
	public void addPontuacao(int p){
		pontuacao = pontuacao + p;
	}
	
	public ParOrdenado getPosicao()
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

	public ParOrdenado getVelocidade() {
		return velocidade;
	}
	
	
}
