package cbs.study.entity;

import lombok.*;

import javax.persistence.*;

@Entity //jpa로 관리되는 엔티티 객체 --> 자동 테이블 생성
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder    //객체 생성 처리
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk 자동 생성
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

}
