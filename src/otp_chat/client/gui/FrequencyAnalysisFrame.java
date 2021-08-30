package otp_chat.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import util.FrequencyAnalysis;
import util.FrequencyAnalysis.CharFreqPair;

import javax.swing.JTable;

public class FrequencyAnalysisFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2802382032020483692L;
	private JPanel contentPane;
	private JTable table;

	public static void show(MessageView message) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrequencyAnalysisFrame frame = new FrequencyAnalysisFrame(message);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrequencyAnalysisFrame(MessageView message) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Frequency analysis");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		table = new JTable(new TableModel() {
			CharFreqPair[][] data = new CharFreqPair[][] { FrequencyAnalysis.analyze(message.plaintext),
					FrequencyAnalysis.analyze(message.message.key),
					FrequencyAnalysis.analyze(message.message.ciphertext) };
			private final int rowCount = Math.max(data[0].length, Math.max(data[1].length, data[2].length));

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				// Do nothing; data cannot be changed
			}

			@Override
			public void removeTableModelListener(TableModelListener l) {
				// Do nothing; data doesn't change
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				int valID = columnIndex % 4;
				columnIndex = (columnIndex / 4) % 3;
				if (data[columnIndex].length <= rowIndex)
					return "";
				CharFreqPair element = data[columnIndex][rowIndex];
				switch (valID) {
				case 0:
					return ((int) element.character);
				case 1:
					return element.character;
				case 2:
					return element.absoluteFrequency;
				case 3:
					return ((double) Math.round(element.relativeFrequency * 10000) / 100.0) + "%";
				default:
					return "Illegal valID: " + valID;
				}
			}

			@Override
			public int getRowCount() {
				return rowCount;
			}

			@Override
			public String getColumnName(int columnIndex) {
				return switch (columnIndex / 4) {
				case 0:
					yield "Plaintext";
				case 1:
					yield "Key";
				case 2:
					yield "Ciphertext";
				default:
					yield "unknown";
				} + " - " + switch (columnIndex % 4) {
				case 0:
					yield "Char code";
				case 1:
					yield "Character";
				case 2:
					yield "Absolute frequency";
				case 3:
					yield "Relative frequency";
				default:
					yield "unknown";
				};
			}

			@Override
			public int getColumnCount() {
				return 12;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public void addTableModelListener(TableModelListener l) {
				// Do nothing. The data doesn't change
			}
		});

		JScrollPane scrlPne = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		contentPane.add(scrlPne);

		contentPane.add(message, BorderLayout.NORTH);
	}

}
