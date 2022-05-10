package cbs.study.repository;


import cbs.study.entity.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test
    //클래스 확인하기
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    @DisplayName("한번에 여러 개 엔티티 객체 저장")
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    @DisplayName("조회 작업 테스트")
    public void testSelect(){
        long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("=====================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        }
    }

    @Transactional
    @Test
    @DisplayName("getOne 조회 테스트")
    public void testSelect2(){
        long mno = 100L;

        Memo memo = memoRepository.getOne(mno);
        System.out.println("====================================");
        System.out.println(memo); //실제 객체가 필요한 순간에 sql을 실행한다.
    }

    @Test
    @DisplayName("수정 작업 테스트")
    public void testUpdate(){
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    @DisplayName("삭제 작업 테스트")
    public void testDelete(){
        Long mno =100L;

        memoRepository.deleteById(mno);
    }
}