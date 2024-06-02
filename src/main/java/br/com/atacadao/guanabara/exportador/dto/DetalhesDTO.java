package br.com.atacadao.guanabara.exportador.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DetalhesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nmFuncionario;
	private Date dtOcorrencia;
	private String tpOcorrencia;
	private BigDecimal vlOcorrencia;

	@Override
	public String toString() {
		return "DetalhesDTO [nmFuncionario=" + nmFuncionario + ", dtOcorrencia=" + dtOcorrencia + ", tpOcorrencia="
				+ tpOcorrencia + ", vlOcorrencia=" + vlOcorrencia + "]";
	}

	public String getNmFuncionario() {
		return nmFuncionario;
	}

	public void setNmFuncionario(String nmFuncionario) {
		this.nmFuncionario = nmFuncionario;
	}

	public Date getDtOcorrencia() {
		return dtOcorrencia;
	}

	public void setDtOcorrencia(Date dtOcorrencia) {
		this.dtOcorrencia = dtOcorrencia;
	}

	public String getTpOcorrencia() {
		return tpOcorrencia;
	}

	public void setTpOcorrencia(String tpOcorrencia) {
		this.tpOcorrencia = tpOcorrencia;
	}

	public BigDecimal getVlOcorrencia() {
		return vlOcorrencia;
	}

	public void setVlOcorrencia(BigDecimal vlOcorrencia) {
		this.vlOcorrencia = vlOcorrencia;
	}

}
