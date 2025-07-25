package com.memoblend.applicationcore.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserDomainService;
import com.memoblend.applicationcore.appuser.AppUserRepository;
import com.memoblend.applicationcore.appuser.AppUserValidationException;

/**
 * ユーザーのドメインサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
class AppUserDomainServiceTest {

  @Mock
  private AppUserRepository userRepository;

  private AppUserDomainService userDomainService;

  @BeforeEach
  void setUp() {
    userDomainService = new AppUserDomainService(userRepository);
  }

  @Test
  void testIsExistUser_正常系_ユーザーが存在する場合はtrueを返す() throws AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
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
    long id = 999;
    when(userRepository.findById(id)).thenReturn(null);
    // Act
    boolean actual = userDomainService.isExistUser(id);
    // Assert
    assertTrue(!actual);
  }

  private AppUser createUser(String name) throws AppUserValidationException {
    long id = 1L;
    boolean isDeleted = false;
    String authId = "auth_" + id;
    return new AppUser(id, name, isDeleted, authId);
  }
}
