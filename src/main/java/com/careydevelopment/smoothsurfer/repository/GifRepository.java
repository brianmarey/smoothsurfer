package com.careydevelopment.smoothsurfer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.careydevelopment.smoothsurfer.entity.Gif;

public interface GifRepository extends MongoRepository<Gif, String> {

	Slice<Gif> findTop12ByOrderByIdDesc(Pageable pageable);
	Gif findByLink(String link);
	
	@Query(value = "{ 'tags' : {$in: [?0]}}")
	Slice<Gif> findTop12ByTagsOrderByIdDesc(String tag, Pageable pageable);
	
}
