package com.careydevelopment.smoothsurfer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.careydevelopment.smoothsurfer.entity.Images;

public interface ImagesRepository extends MongoRepository<Images, String> {

	Page<Images> queryFirst12ByOrderByDateDesc(Pageable pageable);
	Slice<Images> findTop12ByOrderByDateDesc(Pageable pageable);
	Images findByImage(String image);
}
