package cbs.study.repository;

import cbs.study.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
        /*
        * insert 작업: save(엔터티 객체)
        * select 작업: findById(키 타입), getOne(키 타입)
        * update 작업: save(엔터티 객체)
        * delete 작업: deleteById(키 타입), delete(엔터티 객체)
         * */


}
