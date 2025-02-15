package com.memoblend.applicationcore.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * ユーザーのドメインサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
public class UserDomainServiceTest {

  @Mock
  private UserRepository userRepository;

  private UserDomainService userDomainService;

  @BeforeEach
  void setUp() {
    userDomainService = new UserDomainService(userRepository);
  }

  @Test
  void testIsExistUser_正常系_ユーザーが存在する場合はtrueを返す() {
    // Arrange
    String name = "testName";
    User user = createUser(name);
    long id = user.getId();
    when(userRepository.findById(id)).thenReturn(user);
    // Act
    boolean actual = userDomainService.isExistUser(id);
    // Assert
    assertTrue(actual);
  }

  @Test
  void testIsExistUser_正常系_ユーザーが存在しない場合はfalseを返す() {
    // Arrange
    String name = "testName";
    User user = createUser(name);
    long id = user.getId();
    when(userRepository.findById(id)).thenReturn(null);
    // Act
    boolean actual = userDomainService.isExistUser(id);
    // Assert
    assertTrue(!actual);
  }

  private User createUser(String name) {
    long id = 1;
    User user = new User(id, name);
    return user;
  }
}
