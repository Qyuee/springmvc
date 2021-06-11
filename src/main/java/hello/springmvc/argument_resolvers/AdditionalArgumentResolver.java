package hello.springmvc.argument_resolvers;

import hello.springmvc.domain.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * Custom ArgumentResolver 추가하기
 *
 */
@Component
public class AdditionalArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 호춯되는 컨트롤러의 파라미터를 검사한다.
     * 해당 파라미터 클래스가 지원이되지 아닌지 확인하여 적용여부 결정
     * @param parameter
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserInfo.class) != null
                && parameter.getParameterType().equals(String.class);
    }

    /**
     * supportsParameter에서 true를 반환하였을 때, 호출되는 콜백 함수
     * 여기에서 지정된 파라미터를 특정한 데이터로 변환한다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getHeader("User-Agent");
    }
}
