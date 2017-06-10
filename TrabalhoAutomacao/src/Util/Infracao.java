package Util;
public class Infracao {
	private int idCarro;
	private int pontuacao;
	private int tipoInfracao;
	
	public Infracao(int idCarro, int pontuacao, int tipoInfracao)
	{
		this.idCarro=idCarro;
		this.pontuacao=pontuacao;
		this.tipoInfracao=tipoInfracao;
	}

	public int getIdCarro()
	{
		return idCarro;
	}

	public int getPontuacao()
	{
		return pontuacao;
	}

	public int getTipoInfracao()
	{
		return tipoInfracao;
	}
}
