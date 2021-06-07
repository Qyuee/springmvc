package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http 응답 메세지를 body에 바로 작성하여 응답하기
 * @Controller를 사용해서 구현이 가능하고 @RestController를 사용해도 됨
 */
@Slf4j
@Controller
@ResponseBody // class 레벨에 붙이면 컨트롤러 전체가 body에 응답을 작성하여 응답하게 된다.
//@RestController // @Controller + @ResponseBody
public class ResponseBodyController {

    /**
     * Http서블릿을 사용하는 가장 기본적인 형태
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("dslee03");
        helloData.setAge(20);

        return new ResponseEntity<HelloData>(helloData, HttpStatus.OK);
    }

    /**
     * @ResponseStatus를 통해서 응답값을 반환 할 수 있다.
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData ResponseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("dslee03");
        helloData.setAge(20);

        return helloData;
    }
}
