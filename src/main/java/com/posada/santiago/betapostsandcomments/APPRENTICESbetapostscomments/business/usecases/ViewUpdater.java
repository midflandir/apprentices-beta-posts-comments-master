package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;

@Service
public class ViewUpdater extends DomainUpdater {
    private final DomainViewRepository repository;
    private final EventBus bus;
    public ViewUpdater(DomainViewRepository repository, EventBus bus){
        this.repository = repository;
        this.bus = bus;

        listen((PostCreated event)-> {
            PostViewModel post =
                    new PostViewModel(event.aggregateRootId(), event.getAuthor(), event.getTitle(), null);
            repository.saveNewPost(post).subscribe();

                this.bus.publishPost(post);

        });

        listen((CommentAdded event)-> {
            CommentViewModel comment =
                    new CommentViewModel(event.getId(),
                            event.aggregateRootId(),
                            event.getAuthor(),
                            event.getContent()
                          //  ,event.getFont()
                    );
            this.bus.publishcomment(comment);
            repository.addCommentToPost(comment).subscribe();
        });
    }
}
