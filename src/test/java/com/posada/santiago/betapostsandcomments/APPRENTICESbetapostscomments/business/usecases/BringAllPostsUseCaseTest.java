package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.Post;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.values.Author;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.values.PostId;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.values.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BringAllPostsUseCaseTest {

    @Mock
    private DomainViewRepository domainViewRepository;

    @InjectMocks
    private BringAllPostsUseCase useCase;

    @Test
    void bringAllPostsTest() {

        BDDMockito
                .when(this.domainViewRepository.findAllPosts())
                .thenReturn(Flux.just(new PostViewModel(
                        "33333",
                        "The First One",
                        "Jean Camille",
                        new ArrayList<>()
                ), new PostViewModel(
                        "44444",
                        "The Second One",
                        "Jean Camille",
                        new ArrayList<>()
                )));

        Mono<List<PostViewModel>> posts = this.useCase.BringAllPosts()
                .collectList();

        StepVerifier.create(posts)
                .expectSubscription()
                .expectNextMatches(post1 ->
                        post1.size() == 2 &&
                                post1.get(0) instanceof PostViewModel)
                .verifyComplete();

        BDDMockito.verify(this.domainViewRepository, BDDMockito.times(1))
                .findAllPosts();


    }
}