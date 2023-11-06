package com.dependency.test.demo;

import com.dependency.test.demo.domains.users.Users;
import com.dependency.test.demo.domains.users.UsersRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemoApplicationTests {

	@Autowired
	UsersRepository usersRepository;

	private static String SCHEMA = "test";
	private static String USER_NAME = "test_user";
	private static String USER_PASSWORD = "test_password";

	private static JdbcTemplate jdbcTemplate;

	//todo : multidatasource를 사용해야 하는 경우의 test container 설정 및 간략한 코드 추가

	@Container
	private static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8")
			.withUsername(USER_NAME)
			.withPassword(USER_PASSWORD);

//	@Container
//	private static OracleContainer oracleContainer = new OracleContainer("gvenzl/oracle-xe:21-slim-faststart")
//			.withUsername(USER_NAME)
//			.withPassword(USER_PASSWORD);

	@BeforeAll
	static void startUp() {
//		mysqlContainer.withInitScript("src/test/resources/sql/init.sql");
		mysqlContainer.start();

		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(mysqlContainer.getDriverClassName());
		hikariDataSource.setUsername(USER_NAME);
		hikariDataSource.setPassword(USER_PASSWORD);
		hikariDataSource.setJdbcUrl(mysqlContainer.getJdbcUrl());
		hikariDataSource.setMaximumPoolSize(5);
		jdbcTemplate = new JdbcTemplate(hikariDataSource);
	}

	@AfterAll
	static void endUp() {
		mysqlContainer.close();
	}

	@Test
	void startUpSuccess() {
		assertTrue(mysqlContainer.isCreated());
		assertTrue(mysqlContainer.isRunning());
	}

	@Test
	void checkDatabaseInitInfo() {
		assertEquals(mysqlContainer.getDatabaseName(), SCHEMA);
		assertEquals(mysqlContainer.getUsername(), USER_NAME);
		assertEquals(mysqlContainer.getPassword(), USER_PASSWORD);
		assertEquals(mysqlContainer.getTestQueryString(), "SELECT 1");
	}

	@Test
	@Transactional
	void checkInitSqlExecuted() {
		Optional<Users> optionalUsers1 = usersRepository.findById(1L);
		Optional<Users> optionalUsers2 = usersRepository.findById(2L);

		assertTrue(optionalUsers1.isPresent());
		assertEquals(optionalUsers1.get().getUserId(), "user1");

		assertTrue(optionalUsers2.isPresent());
		assertEquals(optionalUsers2.get().getUserId(), "user2");

		Users users = Users.builder().no(3L).userId("user3").build();
//		usersRepository.saveAndFlush(users);
	}

	@Test
	void checkTransactionResultOfCheckInitSqlExecutedFunction() {
		//앞선 함수의 명시적인 save나 saveAndFlush의 영향을 받지 않음. dirty checking을 통해 저장된 entity도 영향 없음.
		Optional<Users> optionalUsers = usersRepository.findById(3L);
		assertFalse(optionalUsers.isPresent());
	}

}
