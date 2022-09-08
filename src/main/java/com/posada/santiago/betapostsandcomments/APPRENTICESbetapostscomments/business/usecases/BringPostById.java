package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BringPostById {
    // finish the implementation of this class using the functional interfaces
    private final DomainViewRepository repository;

    public BringPostById(DomainViewRepository repository) {
        this.repository = repository;
    }

    public Mono<PostViewModel> bringPostById(String id){
        return repository.findByAggregateId(id);
    }
}
