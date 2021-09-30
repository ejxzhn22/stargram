package com.sujin.stargram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {  // 두개의 컬럼이 유니크해야함 , 구독했으면 또 할 수 없음.
                @UniqueConstraint(
                        name="subscribe_uk",
                        columnNames = {"fromUserId", "toUserId"} //실제 컬럼명
                        )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
