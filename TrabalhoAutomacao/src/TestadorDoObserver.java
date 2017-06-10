import java.util.concurrent.TimeUnit;
import CarrosParaTeste.*;
import SupervisorDeCarro.*;
import Util.*;


public class TestadorDoObserver 
{
	public static void main(String[] args) {
		Supervisor Supervisor = new Supervisor();
		Carro carro1 = new Carro();
		Carro carro2 = new Carro();
		carro1.adicionarObservador(Supervisor);
		carro2.adicionarObservador(Supervisor);
		Supervisor.adicionaCarros(carro1);
		carro1.setPosicao(11, 15);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		carro1.setPosicao(42, 23);
		for(TipoInfracoes tipoInfracao : TipoInfracoes.values())
		{
			System.out.print("Tipo de infração " + tipoInfracao + " = " + tipoInfracao.getTipoInfracao() +"\n");
		}
	}
}