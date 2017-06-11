package Util;

public class InformacoesCarro
{
	private int carroId;
	private double ultimoTempo;
	private double[] ultimaPosicao;
	private double ultimaVelocidade;
	
	public InformacoesCarro(int carroId, double ultimoTempo, double[] ultimaPosicao, double ultimaVelocidade)
	{
		this.carroId = carroId;
		this.ultimoTempo = ultimoTempo;
		this.ultimaPosicao[0] = ultimaPosicao[0];
		this.ultimaPosicao[1] = ultimaPosicao[1];
		this.ultimaVelocidade = ultimaVelocidade;
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

	public double[] getUltimaPosicao() {
		return ultimaPosicao;
	}

	public void setUltimaPosicao(double[] ultimaPosicao) {
		this.ultimaPosicao[0] = ultimaPosicao[0];
		this.ultimaPosicao[1] = ultimaPosicao[1];
	}

	public double getUltimaVelocidade() {
		return ultimaVelocidade;
	}

	public void setUltimaVelocidade(double ultimaVelocidade) {
		this.ultimaVelocidade = ultimaVelocidade;
	}

	public void setUltimoTempo(double ultimoTempo) {
		this.ultimoTempo = ultimoTempo;
	}
}
