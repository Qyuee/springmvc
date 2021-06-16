package hello.springcore.core.member;

import java.util.HashMap;
import java.util.Map;

//@Component
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
