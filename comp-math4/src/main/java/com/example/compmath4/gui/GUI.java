package com.example.compmath4.gui;

import com.example.compmath4.graph.Graph;
import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;
import com.example.compmath4.reader.InputDataReaderGUIImpl;
import com.example.compmath4.service.EquationService;
import com.example.compmath4.service.TableHandlerImpl;
import org.jfree.ui.RefineryUtilities;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GUI{
    public   EquationService service;
    private TableOfValues table;


    public GUI(TableHandlerImpl tableHandler){
        service = new EquationService(tableHandler);
    };

    public void setTable(TableOfValues tableOfValues) {
        this.table = tableOfValues;
    }

    public TableOfValues getTable() {
        return table;
    }

    public void guis(InputDataReaderGUIImpl inputDataReaderGUI) {
        inputDataReaderGUI.setVisible(true);
        inputDataReaderGUI.initUI();
    }

    public void outputProcessedData(ArrayList<Equation> processData) {
        Graph graph = new Graph("Графики апроксимации", "Графики", table, processData);
        graph.pack();
        RefineryUtilities.positionFrameOnScreen(graph, 200,300);
        graph.setVisible(true);
    }


}
