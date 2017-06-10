package Util;

public class InformacoesCarro
{
	private int carroId;
	private double ultimoTempo;
	private ParOrdenado ultimaPosicao;
	private ParOrdenado ultimaVelocidade;
	
	public InformacoesCarro(int carroId, double ultimoTempo, ParOrdenado ultimaPosicao, ParOrdenado ultimaVelocidade)
	{
		this.carroId = carroId;
		this.ultimoTempo = ultimoTempo;
		this.ultimaPosicao =  new ParOrdenado(ultimaPosicao.getX(), ultimaPosicao.getY());
		this.ultimaVelocidade = new ParOrdenado(ultimaVelocidade.getX(), ultimaVelocidade.getY());
	}
	
	public double getUltimoTempo()
	{
		return ultimoTempo;
	}

	public int getCarroId() {
		return carroId;
	}

	public void setCarroId(int carroId) {
		this.carroId = carroId;
	}

	public ParOrdenado getUltimaPosicao() {
		return ultimaPosicao;
	}

	public void setUltimaPosicao(ParOrdenado ultimaPosicao) {
		this.ultimaPosicao = new ParOrdenado(ultimaPosicao.getX(), ultimaPosicao.getY());
	}

	public ParOrdenado getUltimaVelocidade() {
		return ultimaVelocidade;
	}

	public void setUltimaVelocidade(ParOrdenado ultimaVelocidade) {
		this.ultimaVelocidade = new ParOrdenado(ultimaVelocidade.getX(), ultimaVelocidade.getY());
	}

	public void setUltimoTempo(double ultimoTempo) {
		this.ultimoTempo = ultimoTempo;
	}
}
