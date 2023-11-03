package com.dependency.test.demo.domains.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>, UsersCustomRepository {
}
