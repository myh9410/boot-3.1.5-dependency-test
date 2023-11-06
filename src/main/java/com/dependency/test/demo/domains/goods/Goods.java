package com.dependency.test.demo.domains.goods;


import com.dependency.test.demo.domains.users.Users;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "goods", schema = "test")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_no", referencedColumnName = "no")
    private Users users;

    private String name;

}
