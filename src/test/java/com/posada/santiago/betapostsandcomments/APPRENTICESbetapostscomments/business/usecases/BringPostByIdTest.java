package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BringPostByIdTest {
    @Mock
    private DomainViewRepository domainViewRepository;

    @InjectMocks
    private BringPostById useCase;

    @Test
    void bringPostByIdTest() {
        BDDMockito
                .when(this.domainViewRepository.findByAggregateId("33333"))
                .thenReturn(Mono.just(new PostViewModel(
                        "33333",
                        "The First One",
                        "Jean Camille",
                        new ArrayList<>()
                )));

        Mono<PostViewModel> posts = this.useCase.bringPostById("33333")
                ;

        StepVerifier.create(posts)
                .expectSubscription()
                .expectNextMatches(post1 ->
                                post1 instanceof PostViewModel
                && post1.getTitle().equals("Jean Camille"))
                .verifyComplete();

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findByAggregateId("33333");


    }
}

