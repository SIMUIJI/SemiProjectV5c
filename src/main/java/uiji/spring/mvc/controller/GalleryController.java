package uiji.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uiji.spring.mvc.service.GalleryService;
import org.springframework.web.servlet.ModelAndView;
import uiji.spring.mvc.service.GoogleCaptchaUtil;
import uiji.spring.mvc.vo.GalleryVO;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GalleryController {

    private GalleryService gsrv;
    private GoogleCaptchaUtil gcutil;

    @Autowired
    public GalleryController(GalleryService gsrv, GoogleCaptchaUtil gcutil) {
        this.gsrv = gsrv;
        this.gcutil = gcutil;
    }

    // list
    @RequestMapping(value = "/gallery/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action", "../gallery/list.jsp");

        return mv;

    }

    // view
    @RequestMapping(value = "/gallery/view")
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action", "../gallery/view.jsp");

        return mv;

    }

    // write
    @RequestMapping(value = "/gallery/write")
    public ModelAndView write() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout");

        mv.addObject("action", "../gallery/write.jsp");

        return mv;
    }

    // 새글쓰기
    // 여러 개의 이미지를 업로드 하는 경우
    // 이미지 폼 이름은 모두 동일하게 설정한다.
    // 이미지를 여러개 설정할필요 없이 배열로 설정
    // MultipartFile img1, MultipartFile img2, MultipartFile img3
    // 이렇게 여러개쓰는것보다 배열로 (MultipartFile[] img) 하는것이 좋다.
    @PostMapping(value = "gallery/write")
    public String writeok(GalleryVO gvo, MultipartFile[] img,
                          HttpServletRequest req,
                          RedirectAttributes rda) {

        String returnPage = "redirect:/gallery/write";
        String gCaptcha = req.getParameter("g-recaptcha");

        if(gcutil.checkCaptcha(gCaptcha)) {
            gsrv.newGallery(gvo, img);
            returnPage= "redirect:/gallery/list";
        }else {
            rda.addFlashAttribute("checkFail","자동가입방지 확인이 실패했습니다.");

        }
        return returnPage;
    }

    @RequestMapping(value = "gallery/view", method = RequestMethod.GET)
    public ModelAndView view(String gno) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("layout/layout"); // 뷰이름 지정

        return mv;
    }

}