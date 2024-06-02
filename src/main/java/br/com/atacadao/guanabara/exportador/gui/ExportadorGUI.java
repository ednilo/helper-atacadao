package br.com.atacadao.guanabara.exportador.gui;

import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.atacadao.guanabara.exportador.calc.CalculadoraMercadoPago;
import br.com.atacadao.guanabara.exportador.dto.ResultadoDTO;

public class ExportadorGUI implements Runnable {

	public static final String DATE_FORMAT_PRINT = "dd/MM/yyyy";

	private String pathArquivoRelatorio = null;

	public void run() {
		JFrame frame = new JFrame("Calculadora Mercado Pago");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 130);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setLayout(new FlowLayout());

		final JTextField tfPathArquivo = new JTextField(30);
		tfPathArquivo.setBounds(20, 50, 190, 30);
		frame.add(tfPathArquivo);

		final JFileChooser jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
		jFileChooser.setFileFilter(filter);
		
		final JButton btnProcurar = new JButton("Procurar");
		btnProcurar.setBounds(250, 50, 80, 30);
		btnProcurar.addActionListener(e -> {
			int x = jFileChooser.showOpenDialog(null);
			if (x == JFileChooser.APPROVE_OPTION) {
				tfPathArquivo.setText(jFileChooser.getSelectedFile().getAbsolutePath());
				pathArquivoRelatorio = jFileChooser.getSelectedFile().getAbsolutePath();
			}
		});
		frame.add(btnProcurar);

		final JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(250, 100, 80, 30);
		btnCalcular.addActionListener(e -> {
			if (pathArquivoRelatorio == null) {
				JOptionPane.showMessageDialog(null, "Selecione o arquivo.");
				return;
			}

			CalculadoraMercadoPago calcMP = new CalculadoraMercadoPago();
			ResultadoDTO resultado = calcMP.getSomatorioTotalFromMercadoPago(pathArquivoRelatorio);
			if(resultado == null) {
				JOptionPane.showMessageDialog(null, "Problemas ao gerar relatório.");
				return;
			}

			JOptionPane.showMessageDialog(null, "Valor Total do dia " + new SimpleDateFormat(DATE_FORMAT_PRINT).format(resultado.getDtRelatorio())
					+ " : R$ " + resultado.getValorTotal());
		});
		frame.add(btnCalcular);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ExportadorGUI());
	}

}
