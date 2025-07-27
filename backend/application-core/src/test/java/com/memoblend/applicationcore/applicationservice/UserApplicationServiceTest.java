package com.memoblend.applicationcore.applicationservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserDomainService;
import com.memoblend.applicationcore.appuser.AppUserNotFoundException;
import com.memoblend.applicationcore.appuser.AppUserRepository;
import com.memoblend.applicationcore.appuser.AppUserValidationException;

/**
 * ユーザーのアプリケーションサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.messages.basename=applicationcore.messages")
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
class UserApplicationServiceTest {

  @Mock
  private AppUserRepository userRepository;

  @Mock
  private AppUserDomainService userDomainService;

  @Autowired
  private MessageSource messages;

  private AppUserApplicationService userApplicationService;

  @BeforeEach
  void setUp() {
    userApplicationService = new AppUserApplicationService(userRepository, userDomainService, messages);
  }

  @Test
  void testGetUsers_正常系_リポジトリのfindAllを1回呼び出す() throws AppUserValidationException {
    // Arrange
    List<String> names = new ArrayList<>();
    names.add("testName");
    List<AppUser> users = createUsers(names);
    when(userRepository.findAll()).thenReturn(users);
    // Act
    userApplicationService.getUsers();
    // Assert
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testGetUsers_正常系_ユーザーのリストを返す() throws AppUserValidationException {
    // Arrange
    List<String> names = new ArrayList<>();
    names.add("testName");
    List<AppUser> users = createUsers(names);
    when(userRepository.findAll()).thenReturn(users);
    // Act
    List<AppUser> actual = userApplicationService.getUsers();
    // Assert
    assertThat(actual).isEqualTo(users);
  }

  @Test
  void testGetUser_正常系_リポジトリのfindByIdを1回呼び出す() throws AppUserNotFoundException, AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userRepository.findById(id)).thenReturn(user);
    // Act
    userApplicationService.getUser(id);
    // Assert
    verify(userRepository, times(1)).findById(id);
  }

  @Test
  void testGetUser_正常系_指定したidのユーザーを返す() throws AppUserNotFoundException, AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userRepository.findById(id)).thenReturn(user);
    // Act
    AppUser actual = userApplicationService.getUser(id);
    // Assert
    assertThat(actual).isEqualTo(user);
  }

  @Test
  void testGetUser_異常系_指定したidのユーザーが存在しない場合UserNotFoundExceptionがスローされる() {
    // Arrange
    long id = 1;
    when(userRepository.findById(id)).thenReturn(null);
    // Act
    Executable action = () -> {
      userApplicationService.getUser(id);
    };
    // Assert
    assertThrows(AppUserNotFoundException.class, action);
  }

  @Test
  void testAddUser_正常系_リポジトリのaddを1回呼び出す() throws AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userDomainService.isExistUser(id)).thenReturn(false);
    when(userRepository.add(user)).thenReturn(user);
    // Act
    userApplicationService.addUser(user);
    // Assert
    verify(userRepository, times(1)).add(user);
  }

  @Test
  void testAddUser_正常系_追加されたユーザーを返す() throws AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userDomainService.isExistUser(id)).thenReturn(false);
    when(userRepository.add(user)).thenReturn(user);
    // Act
    AppUser actual = userApplicationService.addUser(user);
    // Assert
    assertThat(actual).isEqualTo(user);
  }

  @Test
  void testUpdateUser_正常系_リポジトリのupdateを1回呼び出す() throws AppUserNotFoundException, AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userDomainService.isExistUser(id)).thenReturn(true);
    // Act
    userApplicationService.updateUser(user);
    // Assert
    verify(userRepository, times(1)).update(user);
  }

  @Test
  void testUpdateUser_異常系_更新しようとしたユーザーが存在しない場合UserNotFoundExceptionがスローされる() throws AppUserValidationException {
    // Arrange
    String name = "testName";
    AppUser user = createUser(name);
    long id = user.getId();
    when(userDomainService.isExistUser(id)).thenReturn(false);
    // Act
    Executable action = () -> {
      userApplicationService.updateUser(user);
    };
    // Assert
    assertThrows(AppUserNotFoundException.class, action);
  }

  @Test
  void testDeleteUser_正常系_リポジトリのdeleteを1回呼び出す() throws AppUserNotFoundException {
    // Arrange
    long id = 1;
    when(userDomainService.isExistUser(id)).thenReturn(true);
    // Act
    userApplicationService.deleteUser(id);
    // Assert
    verify(userRepository, times(1)).delete(id);
  }

  @Test
  void testDeleteUser_異常系_削除しようとしたユーザーが存在しない場合UserNotFoundExceptionがスローされる() {
    // Arrange
    long id = 1;
    when(userDomainService.isExistUser(id)).thenReturn(false);
    // Act
    Executable action = () -> {
      userApplicationService.deleteUser(id);
    };
    // Assert
    assertThrows(AppUserNotFoundException.class, action);
  }

  private List<AppUser> createUsers(List<String> names) throws AppUserValidationException {
    List<AppUser> users = new ArrayList<>();
    for (String name : names) {
      users.add(createUser(name));
    }
    return users;
  }

  private AppUser createUser(String name) throws AppUserValidationException {
    long id = 1L;
    boolean isDeleted = false;
    String authId = "auth_" + id; // Example authId
    return new AppUser(id, name, isDeleted, authId);
  }
}
