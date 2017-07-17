package com.careydevelopment.smoothsurfer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.careydevelopment.smoothsurfer.entity.Images;
import com.careydevelopment.smoothsurfer.repository.ImagesRepository;
import com.careydevelopment.smoothsurfer.util.Constants;
import com.careydevelopment.smoothsurfer.util.PaginationHelper;

@SpringBootApplication
public class Application  implements CommandLineRunner {
	
    @Autowired
    private ImagesRepository repository;
    
    @Override
    public void run(String... args) throws Exception {
        System.err.println("here we go");

    	int page = PaginationHelper.getPage("1");
    	
    	Pageable pageable = new PageRequest(page, Constants.RESULTS_PER_PAGE);
    	
    	//Page<Images> imageSet = repository.queryFirst12ByOrderByDateDesc(pageable);
    	//System.err.println("imageset is " + imageSet.getSize());
    	
    	Slice<Images> images = repository.findTop12ByOrderByDateDesc(pageable);
    	System.err.println(images.getNumberOfElements());
    	System.err.println(images.getSize());
    	
    	List<Images> ims = images.getContent();
    	
    	for (Images im : ims) {
    		System.err.println(im.getImage() + " " + im.getDate());
    	}
//    	List<Images> images = new ArrayList<Images>();
//    	
//    	for (Images image : imageSet) {
//    		images.add(image);
//    		//System.err.println(image.getImage() + " " + image.getDate());
//    	}
    }

    
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
