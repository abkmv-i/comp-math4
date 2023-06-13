package com.example.compmath4;
import com.example.compmath4.gui.GUI;
import com.example.compmath4.reader.InputDataReaderGUIImpl;
import com.example.compmath4.service.TableHandlerImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompMathLab4Application {

    public static void main(String[] args) {
        TableHandlerImpl tableHandler=new TableHandlerImpl();
        GUI gui = new GUI(tableHandler);
        InputDataReaderGUIImpl inputDataReaderGUI = new InputDataReaderGUIImpl(gui);
        gui.guis(inputDataReaderGUI);
    }

}
