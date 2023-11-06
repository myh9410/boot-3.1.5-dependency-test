package com.dependency.test.demo.domains.goods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsCustomRepository {
}
