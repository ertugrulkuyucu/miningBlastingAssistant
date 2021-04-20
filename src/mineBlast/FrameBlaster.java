package mineBlast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class FrameBlaster extends JFrame {

	JPanel inputPanel, outputPanel, textPanel, buttonPanel, labelPanel;
	JLabel labelRockProperty, labelBlasterName1, labelBlasterName2, labelHoleD, labelRockC, labelHoleAngle,
		   labelStepHigh;
	JLabel labelVmax, labelU, labelH, labelF, labelV, labelE, labelLdip, labelQdip, labelS, labelLkol, labelQkol, 
		   labelQtop,labelY,labelI, labelQ, labelW, label;

	JTextField textStepHigh, textRockC, textHoleD, textHoleAngle;
	JSplitPane sp;
	JButton button;
	JComboBox cBoxBlasterName1, cBoxBlasterName2;

	Calculations cal = new Calculations();

	DecimalFormat decimalFormatter = new DecimalFormat("#.###");
	
	public FrameBlaster() {

		this.setLayout(new BorderLayout());
		// ilk acilis boyutu
		this.setSize(1000, 600);
		// pencereyi ortada cikartiyor.
		this.setLocationRelativeTo(null);
		this.add(splitPane(0.9, JSplitPane.HORIZONTAL_SPLIT, outputPanel(), inputPanel()), BorderLayout.CENTER);
		this.setTitle("Patlatma Tasarimi");
		this.setVisible(true);
		// acilis icin full ekran
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public JSplitPane splitPane(double splitRatio, int splitDirection, JPanel p1, JPanel p2) {

		// Bu fonksiyon iki panel alıp belirledigimiz oranda ve yonde ekrana dagitiyor.
		// Fonksiyon consturcorun icinde kullaniliyor.
		sp = new JSplitPane(splitDirection);
		sp.setResizeWeight(splitRatio);
		sp.setEnabled(false);
		sp.add(p1);
		sp.add(p2);
		return sp;

	}

	public JPanel inputPanel() {
		
		// Bu fonksiyonda input paneli olusturuluyor. renk, satır, sutun sayisi, basliklar belirleniyor.

		inputPanel = new JPanel();
		inputPanel.setBackground(Color.red);

		inputPanel.setLayout(new BorderLayout());

		textPanel = new JPanel();
		textPanel.setBackground(Color.white);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.blue);

		textPanel.setLayout(new GridLayout(6, 2, 0, 30));
		textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Bilgilerinizi Giriniz"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		labelStepHigh = new JLabel("Basamak Yüksekliği (m) :");
		labelRockC = new JLabel("Kayaç Katsayısı :");
		labelHoleD = new JLabel("Delik Çapı (mm) :");
		labelHoleAngle = new JLabel("Delik Eğimi (°) :");
		labelBlasterName1 = new JLabel("Kolon Patlayıcı Tipi :");
		labelBlasterName2 = new JLabel("Dip Patlayıcı Tipi :");

		textStepHigh = new JTextField();
		textRockC = new JTextField();
		textHoleAngle = new JTextField();
		textHoleD = new JTextField();

		cBoxBlasterName1 = new JComboBox(cal.blasterNames);
		cBoxBlasterName2 = new JComboBox(cal.blasterNames);

		textPanel.add(labelStepHigh);
		textPanel.add(textStepHigh);
		textPanel.add(labelRockC);
		textPanel.add(textRockC);
		textPanel.add(labelHoleD);
		textPanel.add(textHoleD);
		textPanel.add(labelHoleAngle);
		textPanel.add(textHoleAngle);
		textPanel.add(labelBlasterName1);
		textPanel.add(cBoxBlasterName1);
		textPanel.add(labelBlasterName2);
		textPanel.add(cBoxBlasterName2);
		inputPanel.add(splitPane(0.8, JSplitPane.VERTICAL_SPLIT, textPanel, buttonPanel), BorderLayout.CENTER);

		button = new JButton("Hesapla");
		buttonPanel.add(button);
		button.setBackground(Color.pink);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String blasterName1 = String.valueOf(cBoxBlasterName1.getSelectedItem());
				String blasterName2 = String.valueOf(cBoxBlasterName2.getSelectedItem());
				double stepH = Double.parseDouble(textStepHigh.getText());
				double holeD = Double.parseDouble(textHoleD.getText());
				double rockC = Double.parseDouble(textRockC.getText());
				double angle = Double.parseDouble(textHoleAngle.getText());

				composeOutput(stepH, blasterName1, blasterName2, holeD, rockC, angle);
			}
		});

		return inputPanel;
	}

	public JPanel outputPanel() {
		outputPanel = new JPanel();
		outputPanel.setBackground(Color.red);
		outputPanel
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Tasarım Sonuları"),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));


		return outputPanel;
	}

	public void composeOutput(double stepH, String blasterName1, String blasterName2, double holeD, double rockC,
			double angle) {

		double[] results = cal.calculate(stepH, blasterName1, blasterName2, holeD, rockC, angle);

		outputPanel.removeAll();
		labelPanel = new JPanel();
		labelPanel.setBackground(Color.gray);
		labelPanel.setBorder(BorderFactory.createEmptyBorder(50, 250, 100, 130));
		
		outputPanel.add(labelPanel);
		
		labelVmax = formatLabel("Delik Ayna Uzaklığı(m)              :" , results[0]);
		labelU =formatLabel("Alt Delme Miktarı(m)                  :" , results[1]);
		labelH = formatLabel("Toplam Delik Boyu(m)               :" , results[2]);
		labelF = formatLabel("Delgi Hatası(m)                         :" , results[3]);
		labelV = formatLabel("Gerçek Delik Ayna Uzaklığı(m):" , results[4]);
		labelE =formatLabel("Delikler Arası Mesafe(m)          :" , results[5]);
		labelLdip =formatLabel("Dip Şarj Boyu(m)               :" , results[6]);
		labelQdip = formatLabel("Dip Şarj Miktarı(kg)          :" , results[7]);
		labelS = formatLabel("Sıkılama Boyu(m)                 :" , results[8]);
		labelLkol = formatLabel("Kolon Şarj Boyu(m)		       :" , results[9]);
		labelQkol = formatLabel("Kolon Şarj Miktarı(kg)	       :" , results[10]);
		labelQtop = formatLabel("Delik Toplam Şarj Miktarı(kg) :" , results[11]);
		labelY = formatLabel("Yemleyici(Dinamit) Miktarı(kg)   :" , results[12]);		
		labelI = formatLabel("Özgül Delik Miktarı(m/m³)	       :" , results[13]);
		labelQ = formatLabel("Özgül Şarj Miktarı(kg/m³)	       :" , results[14]);
		labelW = formatLabel("Bir Delik Ateşlemesi İle Gevşetilecek Malzeme Miktarı(m³):" , results[15]);
	
		labelPanel.setLayout(new GridLayout(16, 1));
		labelPanel.add(labelVmax);
		labelPanel.add(labelU);
		labelPanel.add(labelH);
		labelPanel.add(labelF);
		labelPanel.add(labelV);
		labelPanel.add(labelE);
		labelPanel.add(labelLdip);
		labelPanel.add(labelQdip);
		labelPanel.add(labelS);
		labelPanel.add(labelLkol);
		labelPanel.add(labelQkol);
		labelPanel.add(labelQtop);
		labelPanel.add(labelY);
		labelPanel.add(labelI);
		labelPanel.add(labelQ);
		labelPanel.add(labelW);

		outputPanel.validate();
		outputPanel.repaint();	

	}

	public JLabel formatLabel(String labelText, double number) {

		JLabel label = new JLabel(labelText + decimalFormatter.format(number));
		return label;

	}
}
