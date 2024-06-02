package br.com.atacadao.guanabara.exportador.calc;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import br.com.atacadao.guanabara.exportador.dto.DetalhesDTO;
import br.com.atacadao.guanabara.exportador.dto.QuinzenaDTO;

public class CalculadoraQuinzenas {

	// Create a NumberFormat for Brazilian locale
	private final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public final Integer COLUNA_NOME_FUNC_DETALHES_VALE = 0;
	public final Integer COLUNA_VALOR_OCORRENCIA_DETALHES_VALE = 1;
	public final Integer COLUNA_DATA_OCORRENCIA_DETALHES_VALE = 2;

	public final Integer COLUNA_NOME_FUNC_DETALHES_FALTA = 7;
	public final Integer COLUNA_DATA_OCORRENCIA_DETALHES_FALTA = 8;

	public final Integer COLUNA_NOME_FUNCIONARIO_DIARIA = 19;
	public final Integer COLUNA_VALOR_DO_FUNCIONARIO_DIARIA = 20;

	public final Integer COLUNA_NOME_FUNCIONARIO_QUINZENA = 13;
	public final Integer COLUNA_VALOR_DO_FUNCIONARIO_QUINZENA = 14;

	public final Integer COLUNA_NOME_FUNCIONARIO_FALTAS = 10;
	public final Integer COLUNA_VALOR_DO_FUNCIONARIO_FALTAS = 11;

	public final Integer COLUNA_NOME_FUNCIONARIO_VALE = 4;
	public final Integer COLUNA_VALOR_DO_FUNCIONARIO_VALE = 5;

	public static void main(String[] args) {
		CalculadoraQuinzenas cq = new CalculadoraQuinzenas();
		try {
			/*
			 * Map<String, QuinzenaDTO> gerarValoresQuinzena =
			 * cq.gerarValoresQuinzena("C:\\Users\\ingri\\Downloads\\Vales_e_quinzenas.csv")
			 * ;
			 * 
			 * String userHome = System.getProperty("user.home"); String downloadsPath =
			 * Paths.get(userHome, "Downloads").toString();
			 * 
			 * cq.writeMapToQuinzenaCsv(gerarValoresQuinzena, downloadsPath + File.separator
			 * + "ValoresQuinzenais.csv");
			 */

			cq.gerarDetalhesValesEFaltas("C:\\Users\\ingri\\Downloads\\Vales_e_quinzenas.csv");
		} catch (IOException | CsvException | ParseException e) {
			e.printStackTrace();
		}
	}

	public Map<String, List<DetalhesDTO>> gerarDetalhesValesEFaltas(String path)
			throws IOException, CsvException, ParseException {
		Map<String, List<DetalhesDTO>> result = new HashMap<>();
		Map<String, BigDecimal> mapFuncionarioDiaria = new HashMap<>();
		List<String[]> linhas = null;
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path, StandardCharsets.UTF_8)).withSkipLines(2)
				.build()) {
			linhas = reader.readAll();
			// Primeira iteração é pra pegar os valores das diárias
			for (String[] linha : linhas) {
				String nmFuncionarioDiaria = linha[COLUNA_NOME_FUNCIONARIO_DIARIA];
				String strVlDiaria = linha[COLUNA_VALOR_DO_FUNCIONARIO_DIARIA];

				if (StringUtils.isNotEmpty(nmFuncionarioDiaria)) {
					if (StringUtils.isNotEmpty(strVlDiaria)) {
						BigDecimal vlDiariaFormatado = getDecimalFormattedBR(strVlDiaria);
						mapFuncionarioDiaria.put(nmFuncionarioDiaria, vlDiariaFormatado);
					}
				}
			}
		}

		// Segunda iteração é para montar os detalhes
		for (String[] linha : linhas) {

			// Detalhes dos Vales
			String nmFuncionarioDetalhesDoVale = linha[COLUNA_NOME_FUNC_DETALHES_VALE];
			String strVlOcorrenciaDetalhesDoVale = linha[COLUNA_VALOR_OCORRENCIA_DETALHES_VALE];
			String strDtOcorrenciaDetalhesDoVale = linha[COLUNA_DATA_OCORRENCIA_DETALHES_VALE];
			if (result.containsKey(nmFuncionarioDetalhesDoVale)) {
				DetalhesDTO detalhes = criarDetalhesDTO(nmFuncionarioDetalhesDoVale, strVlOcorrenciaDetalhesDoVale,
						strDtOcorrenciaDetalhesDoVale, "Vale");
				result.get(nmFuncionarioDetalhesDoVale).add(detalhes);
			} else {
				DetalhesDTO detalhes = criarDetalhesDTO(nmFuncionarioDetalhesDoVale, strVlOcorrenciaDetalhesDoVale,
						strDtOcorrenciaDetalhesDoVale, "Vale");
				List<DetalhesDTO> newList = new ArrayList<>();
				newList.add(detalhes);
				result.put(nmFuncionarioDetalhesDoVale, newList);
			}

			// Detalhes das Faltas
			String nmFuncionarioDetalhesDaFalta = linha[COLUNA_NOME_FUNC_DETALHES_FALTA];
			String strVlOcorrenciaDetalhesDaFalta = linha[COLUNA_DATA_OCORRENCIA_DETALHES_FALTA];
			if (result.containsKey(nmFuncionarioDetalhesDaFalta)) {
				DetalhesDTO detalhes = criarDetalhesDTO(nmFuncionarioDetalhesDaFalta,
						String.valueOf(mapFuncionarioDiaria.get(nmFuncionarioDetalhesDaFalta)),
						strVlOcorrenciaDetalhesDaFalta, "Falta");
				result.get(nmFuncionarioDetalhesDaFalta).add(detalhes);
			} else {
				DetalhesDTO detalhes = criarDetalhesDTO(nmFuncionarioDetalhesDaFalta,
						String.valueOf(mapFuncionarioDiaria.get(nmFuncionarioDetalhesDaFalta)),
						strVlOcorrenciaDetalhesDaFalta, "Falta");
				List<DetalhesDTO> newList = new ArrayList<>();
				newList.add(detalhes);
				result.put(nmFuncionarioDetalhesDaFalta, newList);
			}

		}
		
		result.forEach((e, v) -> {
			System.out.println();
		});

		return result;
	}

	private DetalhesDTO criarDetalhesDTO(String nmFuncionario, String strVlOcorrencia, String strDtOcorrencia,
			String tipoOcorrencia) throws ParseException {
		DetalhesDTO detalhes = new DetalhesDTO();
		detalhes.setNmFuncionario(nmFuncionario);
		detalhes.setTpOcorrencia(tipoOcorrencia);

		BigDecimal vlOcorrenciaFormatado = getDecimalFormattedBR(strVlOcorrencia);
		detalhes.setVlOcorrencia(vlOcorrenciaFormatado);

		detalhes.setDtOcorrencia(sdf.parse(strDtOcorrencia));
		return detalhes;
	}

	public Map<String, QuinzenaDTO> gerarValoresQuinzena(String path) throws IOException, CsvException, ParseException {
		Map<String, QuinzenaDTO> result = new HashMap<>();

		List<String[]> linhas = null;

		// Primeira iteração é para obter os nomes dos funcionarios e o valor da
		// Quinzena
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path, StandardCharsets.UTF_8)).withSkipLines(2)
				.build()) {
			linhas = reader.readAll();
			for (String[] linha : linhas) {
				QuinzenaDTO dto = new QuinzenaDTO();
				String nmFuncionario = linha[COLUNA_NOME_FUNCIONARIO_QUINZENA];
				String strVlQuinzena = linha[COLUNA_VALOR_DO_FUNCIONARIO_QUINZENA];

				if (StringUtils.isNotEmpty(nmFuncionario)) {
					dto.setNmFuncionario(nmFuncionario);

					if (StringUtils.isNotEmpty(strVlQuinzena)) {
						BigDecimal vlQuinzenaFormatado = getDecimalFormattedBR(strVlQuinzena);
						dto.setValorQuinzena(dto.getValorQuinzena().add(vlQuinzenaFormatado));
					}

					result.put(nmFuncionario, dto);
				}
			}
		}

		// Segunda iteração é para obter os totais de falta e totais de vale
		for (Entry<String, QuinzenaDTO> entry : result.entrySet()) {
			for (String[] linha : linhas) {
				String nmFuncionarioFaltas = linha[COLUNA_NOME_FUNCIONARIO_FALTAS];
				String strVlFaltas = linha[COLUNA_VALOR_DO_FUNCIONARIO_FALTAS];

				if (entry.getKey().equals(nmFuncionarioFaltas)) {
					if (StringUtils.isNotEmpty(strVlFaltas) && !strVlFaltas.contains("-")) {
						BigDecimal vlFaltasFormatado = getDecimalFormattedBR(strVlFaltas);
						entry.getValue().setValorFaltas(entry.getValue().getValorFaltas().add(vlFaltasFormatado));
					}
				}

				String nmFuncionarioVales = linha[COLUNA_NOME_FUNCIONARIO_VALE];
				String strVlVales = linha[COLUNA_VALOR_DO_FUNCIONARIO_VALE];

				if (entry.getKey().equals(nmFuncionarioVales)) {
					if (StringUtils.isNotEmpty(strVlVales) && !strVlVales.contains("-")) {
						BigDecimal vlValeFormatado = getDecimalFormattedBR(strVlVales);
						entry.getValue().setValorVale(entry.getValue().getValorVale().add(vlValeFormatado));
					}
				}
			}
		}

		return result;
	}

	private BigDecimal getDecimalFormattedBR(String strVlQuinzena) throws ParseException {
		Number numeroFormatadoBR = nf.parse(strVlQuinzena.replace("R$", "").trim());
		BigDecimal vlQuinzenaFormatado = BigDecimal.valueOf(numeroFormatadoBR.doubleValue());
		return vlQuinzenaFormatado;
	}

	public void writeMapToQuinzenaCsv(Map<String, QuinzenaDTO> map, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Write the header
			writer.write("Nome;Valor");
			writer.newLine();

			// Write the map entries
			for (Map.Entry<String, QuinzenaDTO> entry : map.entrySet()) {
				writer.write(entry.getKey() + ";" + nf.format(entry.getValue().getValorAReceber()));
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
