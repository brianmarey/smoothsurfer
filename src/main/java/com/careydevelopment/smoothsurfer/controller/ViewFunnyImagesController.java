package com.careydevelopment.smoothsurfer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.smoothsurfer.entity.Images;
import com.careydevelopment.smoothsurfer.repository.ImagesRepository;
import com.careydevelopment.smoothsurfer.util.Constants;
import com.careydevelopment.smoothsurfer.util.PaginationHelper;


@Controller
public class ViewFunnyImagesController {
	
	@Autowired
	ImagesRepository imagesRepository;
	
    @RequestMapping("/viewFunnyImages")
    public String viewFunnyImages(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {
    	
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page, Constants.RESULTS_PER_PAGE);

    	Slice<Images> images = imagesRepository.findTop12ByOrderByDateDesc(pageable);
    	List<Images> ims = images.getContent();
    	
    	PaginationHelper.setPagination(images,model,page);
   
    	model.addAttribute("images", ims);
    	
        //get out
        return "funnyImages";
    }
}
