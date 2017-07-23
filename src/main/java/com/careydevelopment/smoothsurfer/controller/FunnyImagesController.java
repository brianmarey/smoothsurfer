package com.careydevelopment.smoothsurfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.smoothsurfer.entity.Images;
import com.careydevelopment.smoothsurfer.model.PaginatedRestResponse;
import com.careydevelopment.smoothsurfer.repository.ImagesRepository;
import com.careydevelopment.smoothsurfer.util.Constants;
import com.careydevelopment.smoothsurfer.util.PaginationHelper;


@RestController
public class FunnyImagesController {
	
	@Autowired
	ImagesRepository imagesRepository;
	
    @RequestMapping("/funnyImages")
    public PaginatedRestResponse<Images> viewFunnyImages(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {
    	
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page, Constants.RESULTS_PER_PAGE);

    	Slice<Images> images = imagesRepository.findTop12ByCategoryOrderByDateDesc("Funny",pageable);
    	
    	PaginatedRestResponse<Images> response = new PaginatedRestResponse<Images>(images, page);
    	
        return response;
    }
}
