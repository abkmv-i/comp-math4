package com.example.compmath4.approximation;

import com.example.compmath4.service.TableHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("LinearApproximation")
public class LinearApproximation extends LeastSquaresMethod {

    @Autowired
    public LinearApproximation(TableHandlerImpl tableHandler) {
        super(tableHandler, 1, "Линейная апроксимация");
    }
}
