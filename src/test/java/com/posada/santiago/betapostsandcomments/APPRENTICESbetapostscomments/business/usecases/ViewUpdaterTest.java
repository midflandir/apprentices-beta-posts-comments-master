package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ViewUpdaterTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private DomainViewRepository domainEventRepository;
    @InjectMocks
    private ViewUpdater viewUpdater;


    @Test
    void AddCommentUseCaseTest() {
        UpdateViewUseCase useCase = new UpdateViewUseCase(eventBus, viewUpdater);
        PostCreated event = new PostCreated(
                "我的中文不好",
                "Jean Camille"
        );
        event.setAggregateRootId("01");
        event.setAggregateName("post");

        CommentAdded event2 = new CommentAdded(
                "1",
                "Jean Camille",
                "你好！我是Juan Camilo， 我是哥伦比亚人"
        );
        event2.setAggregateRootId("01");
        event.setAggregateName("comment");

        List<CommentViewModel> comments = new ArrayList<>();

        comments.add(new CommentViewModel(
                "1",
                "01",
                "Jean Camille",
                "你好！我是Juan Camilo， 我是哥伦比亚人"));

        BDDMockito
                .when(this.domainEventRepository.saveNewPost(ArgumentMatchers.any(PostViewModel.class)))
                .thenReturn(Mono.just(
                                new PostViewModel(
                                        "01",
                                        "The Last One",
                                        "Jean Camille",
                                        new ArrayList<>())
                        )
                );

        BDDMockito
                .when(this.domainEventRepository.addCommentToPost(ArgumentMatchers.any(CommentViewModel.class)))
                .thenReturn(Mono.just(
                                new PostViewModel(
                                        "01",
                                        "The Last One",
                                        "Jean Camille",
                                        comments)
                        )
                );


        useCase.accept(event);

         useCase.accept(event2);
      /*  StepVerifier.create(triggeredevents1)
                .expectSubscription()
                .expectNextMatches(domainEvents ->
                                domainEvents instanceof PostCreated)
                .verifyComplete();
*/
      /*  BDDMockito.verify(this.eventBus, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));
*/
        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .saveNewPost(ArgumentMatchers.any(PostViewModel.class));

        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
            .addCommentToPost(ArgumentMatchers.any(CommentViewModel.class));


}
}