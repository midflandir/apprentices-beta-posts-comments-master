package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers;



import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.generic.models.StoredEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.UpdateViewUseCase;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class QueueHandler implements Consumer<String> {
    private final Gson gson = new Gson();
    private final UpdateViewUseCase useCase;

    public QueueHandler(UpdateViewUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void accept(String received) {


        DomainEvent event = gson.fromJson(gson.fromJson(received, StoredEvent.class).getEventBody(), DomainEvent.class);

        useCase.accept(event);

        //Finish the implementation of this Method
    }
}
