package com.panzer.memo.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panzer.memo.service.MemoService;
import com.panzer.memo.service.MemoVO;

@Controller
public class MemoController {

	@Autowired
    private MemoService memoService;
    
    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
    
    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public void upload(Locale locale) {
    	logger.info("Welcome upload.", locale);
    }
    
    
    @RequestMapping(value="/uploadMultiple", method = RequestMethod.GET)
    public void uploadMultiple(Locale locale) {
    	logger.info("Welcome uploadMuliple.", locale);
    }
    
    
    
    @RequestMapping(value="/main", method = RequestMethod.GET)
    public ModelAndView main(Locale locale, Model model) {
        logger.info("Welcome main.", locale);
        
        ModelAndView result = new ModelAndView();
        List<MemoVO> memoList = memoService.getMemo();
        result.addObject("result", memoList);
        //result.setViewName("main");
        return result;
    }
    
    @RequestMapping(value ="/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request){
        
        
        MemoVO memo = new MemoVO();
        memo.setMessage((String) request.getParameter("message"));
        
        logger.info("param : "+memo);
        
        memoService.saveMemo(memo);
        logger.info("insert complet");
        
        //ModelAndView result = new ModelAndView();
        //List<MemoVO> memoList = memoService.getMemo();
        //model.addAttribute("result", memoList);
        //result.setViewName("main");
        return "redirect:/main"; 
    }
	
}
