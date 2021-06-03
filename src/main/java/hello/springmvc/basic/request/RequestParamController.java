package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * GET방식의 파라미터를 통해서 데이터를 전달하는 경우
     * POST+form을 통해서 데이터를 전달하는 경우 (content-type: application/x-www-form-urlencoded)
     *
     * 위 두가지 경우는 동일한 형태로 데이터를 전달한다.
     *  username=hello&age=20
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                log.info("headerName={}, headerValue={}", headerName, request.getHeader(headerName)));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }


    /**
     * @RequestParam - 전달된 파라미터 데이터를 사용 할 수 있다.
     * @ResponseBody - view 조회를 pass하고 Http message를 body에 직접 작성해서 응답한다.
     *  - @RestController를 바로 사용해도 된다. (class 레벨에서)
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String username,
                                 @RequestParam("age") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam의 ("~~")을 생략 할 수 있음
     * - 단, 전달되는 파라미터명과 변수명이 동일해야 함
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam마저도 생략 할 수 있다.
     * - 단, 전달되는 파라미터명과 변수명이 동일해야 함
     * - String, int, Integer 등의 단순 타입인 경우
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * required=true일 때, 값이 없으면 400 에러 발생
     * default: true
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {

        // int와 같은 기본형에는 null이 들어 갈 수 없음 -> 500에러
        // Integer는 null이 입력 될 수 있음

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     *  defaultValue를 사용하면 파라미터가 없을 때, 기본값을 지정 할 수 있다.
     *  단, 파라미터가 빈문자열일 경우에도 기본값이 할당된다.
     *  defaultValue를 사용하면 사실상 required는 필요없다.
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 파라미터를 Map으로 조회하기
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * 파라미터를 MultiValueMap으로 조회하기
     * 파라미터의 데이터가 복수인 경우에는 MultiValueMap을 통해서 조회하면 된다.
     */
    @ResponseBody
    @RequestMapping("/request-param-multi-value-map")
    public String requestParamMultiValueMap(@RequestParam MultiValueMap<String, Object> paramMap) {
        // http://localhost:8080/request-param-multi-value-map?username=hello&age=20,30
        // username=[hello], age=[20,30]
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * 객체에 자동으로 파라미터를 매핑해준다.
     * 동작순서
     * - HelloData 객체를 생성한다.
     * - 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. (파라미터명이 username이면 HelloData의 username이 있는지 확인)
     * - 그리고 해당 프로퍼티의 setter 함수를 호출하여 값을 바인딩한다.
     *
     * 만약 값의 타입이 맞지않는 경우 `바인딩 오류`가 발생한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 생략
     * - @RequestParam 생략 시 -> String, int, Integer와 같은 단순 타입에 적용
     * - @ModelAttribute 생략 시 -> 정의한 객체 (단, argument resolver는 제외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
