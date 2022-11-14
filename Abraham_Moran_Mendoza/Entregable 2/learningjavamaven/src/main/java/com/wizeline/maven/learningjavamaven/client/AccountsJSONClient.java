package com.wizeline.maven.learningjavamaven.client;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="getAccountsClient", url="https://jsonplaceholder.typicode.com/")
public interface AccountsJSONClient {
  @GetMapping(value = "/posts/{postId}", produces = "application/json")
  PostDTO getPostById(@PathVariable("postId") Long postId);
}
