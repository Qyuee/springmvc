package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Controller
public class RequestBodyStreamController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * 컨트롤러는 파라미터를 통해서 InputStream 및 Writer를 바로 받을 수 있다.
     * https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-arguments
     * @param inputStream For access to the raw request body as exposed by the Servlet API.
     * @param responseWriter For access to the raw response body as exposed by the Servlet API.
     * @throws IOException
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * For access to request headers and body. The body is converted with an HttpMessageConverter
     * HttpEntity 파라미터: 메시지 바디 정보를 직접 조회
     * HttpEntity 반환: 메시지 바디 정보를 직접 반환 -> @ResponseBody와 비슷
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();
        log.info("messageBody={}, headers={}", body, headers);
        return new HttpEntity<>("ok");
    }

    /**
     * HttpEntity를 상속받은 RequestEntity도 파라미터로 사용 할 수 있다.
     * 또한, ResponseEntity도 동일하게 사용 할 수 있다.
     */
    @PostMapping("/request-body-string-v3-v1")
    public ResponseEntity<String> requestBodyStringV3V1(RequestEntity<String> requestHttpEntity) throws IOException {
        String body = requestHttpEntity.getBody();
        HttpHeaders headers = requestHttpEntity.getHeaders();
        log.info("messageBody={}, headers={}", body, headers);

        // 응답코드: 201
        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

    /**
     * @RequestBody을 통해서 메세지 바디 데이터를 바로 사용한다.
     * @RequestHeader를 통해서는 헤더 정보를 받을 수 있다.
     * @param messageBody
     * @return
     */
    @ResponseBody
    @PostMapping("request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody,
                                      @RequestHeader Map<String, Object> headerMap,
                                      @RequestHeader MultiValueMap<String, Object> headerMultiValueMap) {
        headerMap.forEach((key, value) -> {
            log.info("header key={}, value={}", key, value);
        });

        headerMultiValueMap.forEach((key, values) -> {
            log.info("header key={}, value={}", key, values);
        });

        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
