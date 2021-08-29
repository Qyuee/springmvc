package hello.resttemplate.controller;

import hello.resttemplate.domain.SampleDomain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/sample/data")
    public SampleDomain data() {
        SampleDomain sampleDomain = new SampleDomain();
        sampleDomain.setKey("key1");
        sampleDomain.setValue("value1");
        return sampleDomain;
    }
}
