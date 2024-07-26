package com.example.mytv.adapter.out.mongo;

import org.springframework.data.repository.CrudRepository;

public interface CommentMongoRepository extends CrudRepository<CommentDocument, String> {
}
