package Util;

public enum TipoInfracoes
{
	REGRA1(1), REGRA2(2), REGRA3(3), REGRA4(4), REGRA5(5), REGRA6(6), REGRA7(7), REGRA8(8), REGRA9(9);
	private int tipoInfracao;
	TipoInfracoes(int valor)
	{
		tipoInfracao = valor;
	}
	
	public int getTipoInfracao()
	{
		return tipoInfracao;
	}
}
