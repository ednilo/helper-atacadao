package br.com.atacadao.guanabara.exportador.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuinzenaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "QuinzenaDTO [nmFuncionario=" + nmFuncionario + ", valorQuinzena=" + valorQuinzena + ", valorFaltas="
				+ valorFaltas + ", valorVale=" + valorVale + " valor a receber=" + getValorAReceber() + " ]";
	}

	private String nmFuncionario;
	private BigDecimal valorQuinzena = BigDecimal.ZERO;
	private BigDecimal valorFaltas = BigDecimal.ZERO;
	private BigDecimal valorVale = BigDecimal.ZERO;

	public String getNmFuncionario() {
		return nmFuncionario;
	}

	public void setNmFuncionario(String nmFuncionario) {
		this.nmFuncionario = nmFuncionario;
	}

	public BigDecimal getValorQuinzena() {
		return valorQuinzena;
	}

	public void setValorQuinzena(BigDecimal valorQuinzena) {
		this.valorQuinzena = valorQuinzena;
	}

	public BigDecimal getValorFaltas() {
		return valorFaltas;
	}

	public void setValorFaltas(BigDecimal valorFaltas) {
		this.valorFaltas = valorFaltas;
	}

	public BigDecimal getValorVale() {
		return valorVale;
	}

	public void setValorVale(BigDecimal valorVale) {
		this.valorVale = valorVale;
	}

	public BigDecimal getValorAReceber() {
		return getValorQuinzena().subtract(getValorFaltas()).subtract(getValorVale());
	}

}
