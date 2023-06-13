package com.example.compmath4.approximation;

import com.example.compmath4.service.TableHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuadraticApproximation extends LeastSquaresMethod {

    @Autowired
    public QuadraticApproximation(TableHandler tableHandler) {
        super(tableHandler, 2,"Квадратичная апроксимация");
    }
}
