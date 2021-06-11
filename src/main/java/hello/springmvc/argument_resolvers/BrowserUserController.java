package hello.springmvc.argument_resolvers;

import hello.springmvc.domain.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserUserController {

    @GetMapping("/argument-resolver-test")
    public String getBrowser(@UserInfo String clientInfo) {
        return clientInfo;
    }
}
