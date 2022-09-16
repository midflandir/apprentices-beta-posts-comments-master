package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository;


import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Repository
public class MongoViewRepository implements DomainViewRepository {
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        /**Make the implementation, using the template, to find a post by its aggregateId*/
        return template.findOne(Query.query(Criteria.where("aggregateId").is(aggregateId)), PostViewModel.class)
                .doFinally(attheend -> log.info("Congratulations Post by id was founded.")
        ).doOnError(error -> log.error("Error - Posts couldn't be loaded." + error));
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        /**make the implementation, using the template, of the method find all posts that are stored in the db*/
        return template.findAll(PostViewModel.class).doFinally(attheend ->
                log.info("Congratulations all Posts where founded.")
        ).doOnError(error -> log.error("Error - Posts couldn't be loaded." + error));
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {

        /** make the implementation, using the template, to save a post*/
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {

        /** make the implementation, using the template, to find the post in the database that you want to add the comment to,
         * then add the comment to the list of comments and using the Update class update the existing post
         * with the new list of comments*/

        return findByAggregateId(comment.getPostId()).flatMap(post -> {
            List<CommentViewModel> commentlist = new ArrayList<>(post.getComments());
            commentlist.add(comment);
            post.setComments(commentlist);
            return template.save(post).doFinally(attheend ->
                    log.info("Congratulations Post was saved.")
            ).doOnError(error -> log.error("Error - Posts couldn't be save." + error));
        }

        );

    }
}
