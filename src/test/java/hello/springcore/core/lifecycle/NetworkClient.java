package hello.springcore.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 빈 생명주기
 */
public class NetworkClient/* implements InitializingBean, DisposableBean */{
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url="+url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect:"+url);
    }

    // 서비스 시작시 호출
    public void call(String message) {
        System.out.println("call:"+ url + "message:"+message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: "+url);
    }

    // 의존관계가 끝나면 동작
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("===> 의존관계 주입 후(즉, 객체 생성이 완료된 시점)");
        connect();
        call("초기화 연결 메세지");
    }

    // 빈이 종료될 때, 호출
    @Override
    public void destroy() throws Exception {
        System.out.println("===> 빈 종료 직전");
        disconnect();
    }*/

    @PostConstruct
    public void init() {
        System.out.println("===> 의존관계 주입 후(즉, 객체 생성이 완료된 시점)");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("===> 빈 종료 직전");
        disconnect();
    }
}
