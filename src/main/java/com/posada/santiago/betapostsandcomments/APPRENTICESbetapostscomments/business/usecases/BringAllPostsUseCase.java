package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Slf4j
@Service
public class BringAllPostsUseCase {

    private final DomainViewRepository repository;

    public BringAllPostsUseCase(DomainViewRepository repository) {
        this.repository = repository;
    }

    public Flux<PostViewModel> BringAllPosts(){
        return repository.findAllPosts().doOnError(error -> log.error("Error - Posts couldn't be loaded." + error));
    }

    //Finish the implementation of this class using the functional interfaces
}
