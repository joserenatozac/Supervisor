package CarrosParaTeste;

import SupervisorDeCarro.ObservadorSupervisor;

public interface ObservadoSupervisor {
	public void adicionarObservador(ObservadorSupervisor o);
	public void removerObservador(ObservadorSupervisor o);
	public void notificaObservadores();
}
