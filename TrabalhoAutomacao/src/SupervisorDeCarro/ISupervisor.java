package SupervisorDeCarro;
import java.util.ArrayList;
import CarrosParaTeste.Carro;
import Util.Infracao;

public interface ISupervisor
{
	public ArrayList<Infracao> getInfracoes();
	public void adicionaCarros(Carro carro);
}
