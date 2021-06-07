package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView modelAndView = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return modelAndView;
    }

    /**
     * Model을 파라미터로 받고 view의 이름을 반환
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /**
     * return값이 없지만 컨트롤러의 경로 이름과 view의 이름이 같으면 view가 동작함
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }


    /**
     * @ResponseBody가 없으면 뷰 리졸버가 동작하여 view를 찾아서 랜더링한다.
     * @ResponseBody가 있으면 Http Body에 메세지를 바로써서 응답한다.
     */

    @ResponseBody
    @RequestMapping("/test")
    public HelloData test(@RequestBody HelloData helloData) {
        return helloData;
    }
}
