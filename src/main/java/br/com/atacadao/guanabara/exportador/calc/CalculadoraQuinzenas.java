package br.com.atacadao.guanabara.exportador.calc;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import br.com.atacadao.guanabara.exportador.dto.QuinzenaDTO;

public class CalculadoraQuinzenas {
	
	public final Integer COLUNA_NOME_FUNCIONARIO_QUINZENA = 19;
	public final Integer COLUNA_VALOR_FUNCIONARIO_DIARIA_QUINZENA = 20;

	public final Integer COLUNA_NOME_FUNCIONARIO_FALTAS = 10;
	public final Integer COLUNA_NOME_FUNCIONARIO_FALTAS_VALOR = 11;
	
	public final Integer COLUNA_NOME_FUNCIONARIO_VALE = 4;
	public final Integer COLUNA_NOME_FUNCIONARIO_VALE_VALOR = 5;

	public static void main(String[] args) {
		CalculadoraQuinzenas cq = new CalculadoraQuinzenas();
		try {
			cq.readCsvFile("C:\\Users\\ingri\\Downloads\\Vales_e_quinzenas.csv");
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
	
	public List<QuinzenaDTO> readCsvFile(String path) throws IOException, CsvValidationException{
		List<QuinzenaDTO> result = new ArrayList<QuinzenaDTO>();
		
		//Primeira leitura é pra obter os nomes dos funcionarios e o valor da Quinzena
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path, StandardCharsets.UTF_8)).withSkipLines(2).build()) {
			
			String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
            	QuinzenaDTO dto = new QuinzenaDTO();
                String nmFuncionario = nextLine[COLUNA_NOME_FUNCIONARIO_QUINZENA];
                String strVlQuinzena = nextLine[COLUNA_VALOR_FUNCIONARIO_DIARIA_QUINZENA];
                
                if(StringUtils.isNotEmpty(nmFuncionario)) {
                	dto.setNmFuncionario(nmFuncionario);
                	
                	if(StringUtils.isNotEmpty(strVlQuinzena)) {
                		dto.setValorQuinzena(new BigDecimal(strVlQuinzena.replace("R$", "").replace(",",".").trim()));
                	}
                	
                	result.add(dto);
                }
            }
        }
		
		//Segunda leitura eh pra obter o total de faltas, total de Vale
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path, StandardCharsets.UTF_8)).withSkipLines(2).build()) {
			
			String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
            	QuinzenaDTO dto = new QuinzenaDTO();
                String nmFuncionario = nextLine[COLUNA_NOME_FUNCIONARIO_QUINZENA];
                String strVlQuinzena = nextLine[COLUNA_VALOR_FUNCIONARIO_DIARIA_QUINZENA];
                
                if(StringUtils.isNotEmpty(nmFuncionario)) {
                	dto.setNmFuncionario(nmFuncionario);
                	
                	if(StringUtils.isNotEmpty(strVlQuinzena)) {
                		dto.setValorQuinzena(new BigDecimal(strVlQuinzena.replace("R$", "").replace(",",".").trim()));
                	}
                	
                	result.add(dto);
                }
            }
        }
		
		result.forEach(System.out::println);
		
		return result;
	}
	
}
