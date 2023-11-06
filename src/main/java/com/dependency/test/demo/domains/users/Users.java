package com.dependency.test.demo.domains.users;

import com.dependency.test.demo.domains.goods.Goods;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", schema = "test")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String userId;

    @OneToMany(mappedBy = "users", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Goods> goodsList;
}
