// $Id$

package napkin.examples;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;

import napkin.NapkinLookAndFeel;
import napkin.NapkinTheme;

public class NapkinQuickTest implements SwingConstants {

    /**
     * Run this class as a program
     *
     * @param args The command line arguments.
     *
     * @throws Exception Exception we don't recover from.
     */
    public static void main(String[] args) throws Exception {
        LookAndFeel laf;
        if (args.length == 1) {
            if (!args[0].equals("none"))
                UIManager.setLookAndFeel(args[0]);
            laf = null;
        } else {
            laf = new NapkinLookAndFeel();
            UIManager.setLookAndFeel(laf);
        }

        final NapkinLookAndFeel napkinLAF = (NapkinLookAndFeel) laf;

        final Set toDisable = new HashSet();

        final JFrame top = new JFrame();
        top.setBackground(Color.cyan);
        JTabbedPane tabbed = new JTabbedPane();
        JPanel mainPanel = new JPanel();
        tabbed.addTab("Main Stuff", mainPanel);
        top.getContentPane().add(tabbed);

        JLabel label = new JLabel("-- Label --");

        if (napkinLAF != null)
            napkinLAF.setIsFormal(top, true, true);
        mainPanel.setLayout(new GridLayout(4, 2));
        if (napkinLAF != null)
            napkinLAF.setIsFormal(label, true, false);
        mainPanel.add(label);
        toDisable.add(label);

        JButton button = new JButton("Button!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println();
                if (napkinLAF != null)
                    napkinLAF.dumpFormality(top, System.out);
            }
        });
        if (napkinLAF != null)
            napkinLAF.setIsFormal(button, true, false);
        mainPanel.add(button);
        boolean formal = (napkinLAF != null && napkinLAF.isFormal(label));
        label.setText(formal ? "formal" : "napkin");
        toDisable.add(button);

        JCheckBox checkBox = new JCheckBox("Check?");
        mainPanel.add(checkBox);
        final JCheckBox disableButton = new JCheckBox("Disable");
        disableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean enable = !disableButton.isSelected();
                for (Iterator it = toDisable.iterator(); it.hasNext();) {
                    JComponent component = (JComponent) it.next();
                    component.setEnabled(enable);
                }
            }
        });
        mainPanel.add(disableButton);
        toDisable.add(checkBox);

        ButtonGroup bgrp = new ButtonGroup();
        JRadioButton r1 = new JRadioButton("Radio?");
        JRadioButton r2 = new JRadioButton("Radio!");
        bgrp.add(r1);
        bgrp.add(r2);
        mainPanel.add(r1);
        mainPanel.add(r2);
        toDisable.add(r1);
        toDisable.add(r2);

        String[] themeNames = NapkinTheme.Manager.themeNames();
        final JComboBox comboBox = new JComboBox(themeNames);
        String currentTheme = NapkinTheme.Manager.getCurrentTheme().getName();
        comboBox.setSelectedItem(currentTheme);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String name = (String) comboBox.getSelectedItem();
                    NapkinTheme.Manager.setCurrentTheme(name);
                    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(top);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        mainPanel.add(comboBox);
        toDisable.add(comboBox);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        int majorSpacing = 50;
        slider.setMajorTickSpacing(majorSpacing);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        Dictionary labels = slider.createStandardLabels(majorSpacing);
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
        mainPanel.add(slider);
        toDisable.add(slider);

        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 400; i++) {
            if (sb.length() > 0)
                sb.append(' ');
            sb.append(i);
            if (i > 0 && i % 20 == 0) {
                textArea.append(sb.toString());
                textArea.append("\n");
                sb.delete(0, 1000);
            }
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        textPanel.add(scrollPane, BorderLayout.CENTER);
        JButton loadButton = new JButton("Load File");
        textPanel.add(loadButton, BorderLayout.SOUTH);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(textPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    loadFile(textArea, chooser.getSelectedFile());
                }
            }
        });
        tabbed.addTab("Text", textPanel);

        JPanel fields = new JPanel();
        fields.setLayout(new BorderLayout());
        JTextField field = new JTextField("of dreams", 20);
        fields.add(new JLabel("Field:"), BorderLayout.WEST);
        fields.add(field, BorderLayout.CENTER);
        tabbed.addTab("Fields", fields);

        for (int i = 0; i < 4; i++)
            tabbed.addTab("Tab " + i, new JLabel("Just a Label #" + i, CENTER));

        JPanel tabCtrls = new JPanel();
        ButtonGroup ctlGrp = new ButtonGroup();
        tabCtrls.setLayout(new GridLayout(2, 2));
        addCtrl(tabbed, tabCtrls, ctlGrp, "top", TOP, true);
        addCtrl(tabbed, tabCtrls, ctlGrp, "right", RIGHT, false);
        addCtrl(tabbed, tabCtrls, ctlGrp, "left", LEFT, false);
        addCtrl(tabbed, tabCtrls, ctlGrp, "bottom", BOTTOM, false);
        tabbed.addTab("Controls", tabCtrls);

        top.pack();
        top.show();
    }

    private static void loadFile(JTextArea textArea, File file) {
        Reader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            textArea.read(in, file);
        } catch (IOException e) {
            PrintWriter out = new PrintWriter(new StringWriter());
            e.printStackTrace(out);
            out.close();
            textArea.setText(out.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    ;   // we tried
                }
            }
        }
    }

    private static void addCtrl(final JTabbedPane tabs, Container ctrls,
            ButtonGroup grp, String lab, final int side, boolean on) {
        JRadioButton button = new JRadioButton(lab, on);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabs.setTabPlacement(side);
            }
        });
        grp.add(button);
        ctrls.add(button);
    }
}