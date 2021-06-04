package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JSON 데이터를 받아서 처리 할 수 있는 컨트롤러
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody를 통해서 바로 json데이터를 객체에 할당한다.
     * 이 때, json데이터를 HelloData 객체에 변환해주는 것은 별도의 기능인 `메세지 컨버터`이다.
     *
     * @RequestBody가 생략되면 @ModelAttribute가 적용되는 것과 같다.
     * 즉, 메세지의 바디가 처리되는게 아니라 파라미터가 HelloData 객체에 변환된다.
     * 파라미터에는 username, age 값이 없으므로 기본값이 노출된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * HttpEntity를 통해서도 json 형태의 메세지 바디를 객체에 변환 할 수 있다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData = httpEntity.getBody();
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @RequestBody: 'JSON데이터' -> 'HTTP메세지 컨버터' -> '객체'
     * @ResponseBody: '객체' -> 'HTTP 메세지 컨버터' -> 'JSON데이터'
     *
     * HttpMessageConverter가 MappingJackson2HttpMessageConverter를 적용한다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        // 객체를 JSON 데이터로 변환하여 응답
        return helloData;
    }
}
