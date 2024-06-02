package br.com.atacadao.guanabara.exportador.calc;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.atacadao.guanabara.exportador.dto.ResultadoDTO;

public class CalculadoraMercadoPago {

	//
	public static final String DATE_FORMAT_INPUT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String QUEBRA_LINHA = System.lineSeparator();

	public static final int COLUNA_PAYMENT_METHOD_TYPE = 3;
	public static final int COLUNA_PAYMENT_METHOD = 4;
	public static final int COLUNA_TRANSACTION_DATE = 10;
	public static final int COLUNA_REAL_AMOUNT = 15;

	public static void main(String[] args) {
		CalculadoraMercadoPago mp = new CalculadoraMercadoPago();
		ResultadoDTO resultado = mp.getSomatorioTotalFromMercadoPago(
				"C:\\Users\\ingri\\Downloads\\settlement-1095415499-2024-03-19-104018.csv");

		System.out.println("Valor Total do dia: " + resultado.getValorTotal());
	}

	public ResultadoDTO getSomatorioTotalFromMercadoPago(String path) {
		try {
			List<String> linhas = Files.readAllLines(Paths.get(path));

			String dataTransacaoPrint = null;
			BigDecimal valorTotal = BigDecimal.ZERO;
			for (String linha : linhas.subList(1, linhas.size())) {
				String[] linhSplitted = linha.split(";");

				@SuppressWarnings("unused")
				String tipoMetodoPagamento = linhSplitted[COLUNA_PAYMENT_METHOD_TYPE];
				String metodoPagamento = linhSplitted[COLUNA_PAYMENT_METHOD];
				String dataTransacao = linhSplitted[COLUNA_TRANSACTION_DATE];
				dataTransacaoPrint = dataTransacao;
				String valor = linhSplitted[COLUNA_REAL_AMOUNT];

				/*
				 * System.out.println("tipoMetodoPagamento: " + tipoMetodoPagamento);
				 * System.out.println("metodoPagamento: " + metodoPagamento);
				 * System.out.println("dataTransacao: " + dataTransacao);
				 * System.out.println("valor: " + valor);
				 * System.out.println("_______________________________");
				 */
				if ("pix".equalsIgnoreCase(metodoPagamento) || "pix_am".equalsIgnoreCase(metodoPagamento)
						|| "visa".equalsIgnoreCase(metodoPagamento)) {
					BigDecimal valorDaTransacao = new BigDecimal(valor);
					if (valorDaTransacao.doubleValue() > 0.0) {
						valorTotal = valorTotal.add(valorDaTransacao);
					}
				}
			}

			try {
				Date dtRelatorio = new SimpleDateFormat(DATE_FORMAT_INPUT).parse(dataTransacaoPrint);
				return new ResultadoDTO(valorTotal, dtRelatorio);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
