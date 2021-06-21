package hello.springcore.singleton;

public class SingleTonService {
    // static 영역에 하나만 생성한다.
    private static final SingleTonService instance = new SingleTonService();

    public static SingleTonService getInstance() {
        return instance;
    }

    // 외부 클래스에서 new하는 것을 방지
    private SingleTonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 생성");
    }
}
