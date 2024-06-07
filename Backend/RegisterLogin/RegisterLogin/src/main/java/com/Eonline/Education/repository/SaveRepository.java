package com.Eonline.Education.repository;

import com.Eonline.Education.modals.Post;
import com.Eonline.Education.modals.SaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SaveRepository extends JpaRepository<SaveEntity, Integer> {
    void deleteByPostId(Optional<Post> postId);

	

}
