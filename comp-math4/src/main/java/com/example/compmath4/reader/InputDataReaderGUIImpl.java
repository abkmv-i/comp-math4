package com.example.compmath4.reader;


import com.example.compmath4.gui.GUI;
import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class InputDataReaderGUIImpl extends JFrame {
    private JTextField numberOfTestsField;
    private JTextArea xValuesArea;
    private JTextArea yValuesArea;
    GUI gui;
    private ArrayList<Equation> processData;


    public InputDataReaderGUIImpl(GUI gui) {
        this.gui = gui;
    }

    TableOfValues tableOfValues;
    File selectedFile;

    public TableOfValues initUI() {
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Операция закрытия окна

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Лаба 4");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 3));

        JLabel numberOfTestsLabel = new JLabel("Количество точек (8-12):");
        numberOfTestsField = new JTextField();
        JLabel xValuesLabel = new JLabel("Значения для x (через пробел):");
        xValuesArea = new JTextArea();
        JLabel yValuesLabel = new JLabel("Значения для y (через пробел):");
        yValuesArea = new JTextArea();
        JButton submitButton = new JButton("Расчитать");
        JButton fileButton = new JButton("Файл");
        mainPanel.add(numberOfTestsLabel);
        mainPanel.add(numberOfTestsField);
        mainPanel.add(xValuesLabel);
        mainPanel.add(xValuesArea);
        mainPanel.add(yValuesLabel);
        mainPanel.add(yValuesArea);
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(fileButton);
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(mainPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
                try {
                    tableOfValues = getDataFromFile(new FileReader(selectedFile.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                gui.setTable(tableOfValues);
                if (processData != null) processData.clear();
                processData = gui.service.getApproximationFunction(tableOfValues);
                gui.outputProcessedData(processData);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableOfValues = readDataFromGUI();
                if (tableOfValues != null) {
                    gui.setTable(tableOfValues);

                    if (processData != null) processData.clear();

                    processData = gui.service.getApproximationFunction(tableOfValues);
                    gui.outputProcessedData(processData);
                }
            }

        });
        add(mainPanel);
        return tableOfValues;
    }

    private TableOfValues getDataFromFile(InputStreamReader inputStreamReader) {
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            int numberOfTests = Integer.parseInt(reader.readLine());
            if (numberOfTests < 8 || numberOfTests > 12) throw new NumberFormatException("Недостаточно опытов");
            Double[] xValues;
            Double[] yValues;
            String[] xValuesString = reader.readLine().split(" ");
            if (xValuesString.length != numberOfTests) throw new NumberFormatException("Количество x недостаточно");
            xValues = Arrays.stream(xValuesString).map(value -> Double.parseDouble(value.replaceAll(",", "."))).toArray(Double[]::new);
            String[] yValuesString = reader.readLine().split(" ");
            if (yValuesString.length != numberOfTests) throw new NumberFormatException("Количество y недостаточно");
            yValues = Arrays.stream(yValuesString).map(value -> Double.parseDouble(value.replaceAll(",", "."))).toArray(Double[]::new);
            return new TableOfValues(xValues, yValues, numberOfTests);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Некорректные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Некорректные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private TableOfValues readDataFromGUI() {
        try {
            int numberOfTests = Integer.parseInt(numberOfTestsField.getText());
            if (numberOfTests < 8 || numberOfTests > 12) {
                throw new NumberFormatException();
            }

            String[] xValuesString = xValuesArea.getText().split(" ");
            if (xValuesString.length != numberOfTests) {
                throw new NumberFormatException();
            }
            Double[] xValues = Arrays.stream(xValuesString)
                    .map(value -> Double.parseDouble(value.replaceAll(",", ".")))
                    .toArray(Double[]::new);

            String[] yValuesString = yValuesArea.getText().split(" ");
            if (yValuesString.length != numberOfTests) {
                throw new NumberFormatException();
            }

            Double[] yValues = Arrays.stream(yValuesString)
                    .map(value -> Double.parseDouble(value.replaceAll(",", ".")))
                    .toArray(Double[]::new);

            for (int i = 0; i < numberOfTests - 1; i++) {
                for (int j = 1; j < numberOfTests; j++) {
                    if (i == j) j++;
                    if (Objects.equals(xValues[i], xValues[j])||Objects.equals(yValues[i], yValues[j])) {
                        throw new NumberFormatException();
                    }

                }
            }
            return new TableOfValues(xValues, yValues, numberOfTests);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Некорректные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

}
