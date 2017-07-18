package com.careydevelopment.smoothsurfer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.smoothsurfer.entity.Gif;
import com.careydevelopment.smoothsurfer.repository.GifRepository;
import com.careydevelopment.smoothsurfer.util.Constants;
import com.careydevelopment.smoothsurfer.util.PaginationHelper;


@Controller
public class ViewReactionGifsController {
	
	@Autowired
	GifRepository gifRepository;
	
    @RequestMapping("/viewReactionGifs")
    public String viewReactionGifs(Model model, @RequestParam(value="pageNum", required=false) String pageNum,
    	@RequestParam(value="tag", required=false) String tag) {
    	
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page, Constants.RESULTS_PER_PAGE);
    	
    	Slice<Gif> images = null;
    	if (tag != null) {
    		images = gifRepository.findTop12ByTagsOrderByIdDesc(tag,pageable);
    	} else {
    		images = gifRepository.findTop12ByOrderByIdDesc(pageable);
    	}

    	if (images != null){
    		List<Gif> ims = images.getContent();
    		PaginationHelper.setPagination(images,model,page);
    		model.addAttribute("images", ims);	
    	} else {
    		model.addAttribute("images", new ArrayList<Gif>());
    	}
    	
        //get out
        return "reactionGifs";
    }
}
