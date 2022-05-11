package cbs.study.repository;


import cbs.study.entity.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Test
    @DisplayName("페이징 처리 테스트")
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0, 10);//페이지당 10개
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        //페이징 처리에 사용하는 limit 사용된 것을 확인
        //두번째로는 count()를 이용하여 전체 개수를 처리한 것을 확인할 수 있다.

        System.out.println("---------------------------------");
        System.out.println("result.getTotalPages() = " + result.getTotalPages());   //총 페이지
        System.out.println("result.getTotalElements() = " + result.getTotalElements()); //전체 개수
        System.out.println("result.getNumber() = " + result.getNumber());   //현재 페이지 번호 -> 페이지 번호는 0부터
        System.out.println("result.getSize() = " + result.getSize());   //페이지당 개수
        System.out.println("result.hasNext() = " + result.hasNext());   //다음페이지 존재 여부
        System.out.println("result.isFirst() = " + result.isFirst());   //시작페이지 여부

        //실제 페이지 데이터 처리
        System.out.println("======실제 페이지 처리=========");
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    @DisplayName("정렬 조건 추가하기")  //순차적 정렬(asc), 역순 정렬(desc)
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0,10,sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo ->{
            System.out.println(memo);
        });
    }

    @Test
    @DisplayName("정렬 조건 여러개 추가하기")
    public void testSort2(){
        Sort sort1 = Sort.by("mno").descending(); //mno는 역순으로
        Sort sort2 = Sort.by("memoText").ascending(); //memoText는 순차적으로
        Sort sortAll = sort1.and(sort2);    //and를 이용하여 연결

        Pageable pageable = PageRequest.of(0,10,sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo ->{
            System.out.println(memo);
        });
    }
}