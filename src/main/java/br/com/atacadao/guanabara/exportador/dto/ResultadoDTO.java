package br.com.atacadao.guanabara.exportador.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResultadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal valorTotal;
	private Date dtRelatorio;

	public ResultadoDTO(BigDecimal valorTotal, Date dtRelatorio) {
		super();
		this.valorTotal = valorTotal;
		this.dtRelatorio = dtRelatorio;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDtRelatorio() {
		return dtRelatorio;
	}

	public void setDtRelatorio(Date dtRelatorio) {
		this.dtRelatorio = dtRelatorio;
	}

}
