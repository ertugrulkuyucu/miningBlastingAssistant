package mineBlast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameBlaster extends JFrame {

	int counter;
	
	JLabel imageLabel;

	JPanel inputPanel, outputPanel, textPanel, buttonPanel, labelPanel, resultPanel, buttonPanel2;
	JLabel labelRockProperty, labelBlasterName1, labelBlasterName2, labelHoleD, labelRockC, labelHoleAngle,
			labelStepHigh;
	JLabel labelVmax, labelU, labelH, labelF, labelV, labelE, labelLdip, labelQdip, labelS, labelLkol, labelQkol,
			labelQtop, labelY, labelI, labelQ, labelW, label;

	JTextField textStepHigh, textRockC, textHoleD, textHoleAngle;
	JSplitPane sp;
	JButton button, button2, button3;
	JComboBox cBoxBlasterName1, cBoxBlasterName2;

	Calculations cal = new Calculations();

	final int HEIGHT = 1000, WIDTH = 600;
	
	DecimalFormat decimalFormatter = new DecimalFormat("#.###");

	public FrameBlaster() {

		this.setTitle("Patlatma Tasarimi");

		// ilk acilis boyutu
		this.setSize(HEIGHT, WIDTH);
		this.setResizable(false);

		// pencereyi ortada cikartiyor.
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setLayout(new BorderLayout());
		
	

		// resim boyunu frame'e fit etmek için
		ImageIcon imageIcon = new ImageIcon("images/aa.jpeg"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(1000, 600, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		
		imageLabel = new JLabel(new ImageIcon(newimg));
		this.setContentPane(imageLabel);
		
	

		this.setLayout(new BorderLayout());

		this.add(splitPane(0.9, JSplitPane.HORIZONTAL_SPLIT, outputPanel(), inputPanel()), BorderLayout.CENTER);
		this.setSize(HEIGHT-1, WIDTH-1);
		this.setSize(HEIGHT, WIDTH);

		// acilis icin full ekran
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	
	
	public JSplitPane splitPane(double splitRatio, int splitDirection, JPanel p1, JPanel p2) {

		// Bu fonksiyon iki panel alıp belirledigimiz oranda ve yonde ekrana dagitiyor.
		// Fonksiyon consturcorun icinde kullaniliyor.
		sp = new JSplitPane(splitDirection);
		sp.setResizeWeight(splitRatio);
		sp.setEnabled(false);
		sp.setOpaque(false);
		sp.add(p1);
		sp.add(p2);
		return sp;

	}

	public JPanel inputPanel() {

		// Bu fonksiyonda input paneli olusturuluyor. renk, satır, sutun sayisi,
		// basliklar belirleniyor.

		inputPanel = new JPanel();

		inputPanel.setOpaque(false);
		inputPanel.setLayout(new BorderLayout());

		textPanel = new JPanel();
		textPanel.setBackground(new Color(250, 250, 250, 150));

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(250, 250, 250, 150));

		textPanel.setLayout(new GridLayout(6, 2, 0, 30));
		textPanel
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Bilgilerinizi Giriniz"),
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
	
		//buttonlari alt alta koymak icin
		//buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		
		
		button2 = new JButton("Sıfırla");
		buttonPanel.add(button2);
		
		
		button2.setBackground(Color.black);
		button2.setForeground(Color.white);

		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String blasterName1 = String.valueOf(cBoxBlasterName1.getSelectedItem());
				String blasterName2 = String.valueOf(cBoxBlasterName2.getSelectedItem());
				double stepH = Double.parseDouble(textStepHigh.getText());
				double holeD = Double.parseDouble(textHoleD.getText());
				double rockC = Double.parseDouble(textRockC.getText());
				double angle = Double.parseDouble(textHoleAngle.getText());

				ImageIcon imageIcon = new ImageIcon("images/ab.jpeg"); // load the image to a imageIcon
				Image image = imageIcon.getImage(); // transform it
				Image newimg = image.getScaledInstance(1000, 600, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				
				imageLabel.setIcon(new ImageIcon(newimg));
				
				
				composeOutput(stepH, blasterName1, blasterName2, holeD, rockC, angle);

				
				
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				inputPanel.removeAll();
				outputPanel.removeAll();
				inputPanel();
				
			}
		});

		return inputPanel;
	}

	public JPanel outputPanel() {

		outputPanel = new JPanel();
		outputPanel.setOpaque(false);
		outputPanel.setLayout(new BorderLayout());



		outputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Tasarım Sonucları"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// eski calisan kodumuz, ustte yenisini deneyecegim
//		outputPanel = new JPanel();
//		outputPanel.setOpaque(false);
//		outputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Tasarım Sonucları"),
//				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		return outputPanel;
	}

	public void composeOutput(double stepH, String blasterName1, String blasterName2, double holeD, double rockC,
			double angle) {

		double[] results = cal.calculate(stepH, blasterName1, blasterName2, holeD, rockC, angle);


		button3 = new JButton("Sonuçları Şekil Üzerinde Göster");		
		button3.setBackground(Color.black);
		button3.setForeground(Color.white);
		
		
		
		outputPanel.removeAll();

		labelPanel = new JPanel();
		labelPanel.setBackground(new Color(250, 250, 250, 150));

		labelPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

		outputPanel.add(labelPanel);

		labelVmax = formatLabel("Delik Ayna Uzaklığı(m)               :", results[0]);
		labelU = formatLabel("Alt Delme Miktarı(m)                  :", results[1]);
		labelH = formatLabel("Toplam Delik Boyu(m)               :", results[2]);
		labelF = formatLabel("Delgi Hatası(m)                    :", results[3]);
		labelV = formatLabel("Gerçek Delik Ayna Uzaklığı(m):", results[4]);
		labelE = formatLabel("Delikler Arası Mesafe(m)          :", results[5]);
		labelLdip = formatLabel("Dip Şarj Boyu(m)               :", results[6]);
		labelQdip = formatLabel("Dip Şarj Miktarı(kg)          :", results[7]);
		labelS = formatLabel("Sıkılama Boyu(m)                 :", results[8]);
		labelLkol = formatLabel("Kolon Şarj Boyu(m)		       :", results[9]);
		labelQkol = formatLabel("Kolon Şarj Miktarı(kg)	       :", results[10]);
		labelQtop = formatLabel("Delik Toplam Şarj Miktarı(kg) :", results[11]);
		labelY = formatLabel("Yemleyici(Dinamit) Miktarı(kg)   :", results[12]);
		labelI = formatLabel("Özgül Delik Miktarı(m/m³)	       :", results[13]);
		labelQ = formatLabel("Özgül Şarj Miktarı(kg/m³)	       :", results[14]);
		labelW = formatLabel("Bir Delik Ateşlemesi İle Gevşetilecek Malzeme Miktarı(m³):", results[15]);

		labelPanel.setLayout(new GridLayout(17, 1));
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


		labelPanel.add(button3);
		
		button3.setSize(10, 5);

		outputPanel.validate();
		outputPanel.repaint();

	}

	public JLabel formatLabel(String labelText, double number) {

		JLabel label = new JLabel(labelText + decimalFormatter.format(number));
		return label;
	}

}
