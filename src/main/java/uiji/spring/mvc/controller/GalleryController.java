package uiji.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uiji.spring.mvc.service.GalleryService;
import org.springframework.web.servlet.ModelAndView;
import uiji.spring.mvc.vo.GalleryVO;

@Controller
public class GalleryController {

    private GalleryService gsrv;

    @Autowired
    public GalleryController(GalleryService gsrv) {
        this.gsrv = gsrv;
    }

    // list
    @RequestMapping(value = "/gallery/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action","../gallery/list.jsp");

        return mv;

}

    // view
    @RequestMapping(value = "/gallery/view")
    public ModelAndView view(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action","../gallery/view.jsp");

        return mv;

    }
    // write
    @RequestMapping(value = "/gallery/write")
    public ModelAndView write(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action","../gallery/write.jsp");

        return mv;
    }

    //새글쓰기
    @PostMapping(value = "/gallery/write")
    public String writeok(GalleryVO gvo) {

        gsrv.newGallery(gvo);

        return "redirect:/gallery/list";
    }
}