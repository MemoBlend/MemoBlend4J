package com.memoblend.applicationcore.applicationservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.AuthRepository;
import com.memoblend.applicationcore.auth.UserRole;

/**
 * 認証関連のアプリケーションサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
public class AuthApplicationServiceTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthRepository authRepository;

  private AuthApplicationService authApplicationService;

  @BeforeEach
  void setUp() {
    authApplicationService = new AuthApplicationService(passwordEncoder, authRepository);
  }

  @Test
  void testAuthenticate_正常系_認証が成功する() {
    // Arrange
    String authId = "testuser";
    String password = "testpassword";
    String hashedPassword = "hashedpassword";

    UserRole role = new UserRole(1, "ROLE_USER");
    Auth auth = new Auth(authId, hashedPassword, List.of(role));

    when(authRepository.findById(authId)).thenReturn(auth);
    when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

    // Act
    UserDetails result = authApplicationService.authenticate(authId, password);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(authId);
    assertThat(result.getPassword()).isEqualTo(hashedPassword);
    assertThat(result.getAuthorities()).hasSize(1);
    assertThat(result.getAuthorities())
        .extracting("authority")
        .contains("ROLE_USER");
  }

  @Test
  void testAuthenticate_異常系_ユーザーが存在しない場合nullを返す() {
    // Arrange
    String authId = "nonexistentuser";
    String password = "testpassword";

    when(authRepository.findById(authId)).thenReturn(null);

    // Act
    UserDetails result = authApplicationService.authenticate(authId, password);

    // Assert
    assertThat(result).isNull();
  }

  @Test
  void testAuthenticate_異常系_パスワードが一致しない場合nullを返す() {
    // Arrange
    String authId = "testuser";
    String password = "wrongpassword";
    String hashedPassword = "hashedpassword";

    UserRole role = new UserRole(1, "ROLE_USER");
    Auth auth = new Auth(authId, hashedPassword, List.of(role));

    when(authRepository.findById(authId)).thenReturn(auth);
    when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

    // Act
    UserDetails result = authApplicationService.authenticate(authId, password);

    // Assert
    assertThat(result).isNull();
  }

  @Test
  void testAuthenticate_正常系_複数のロールを持つユーザーの認証が成功する() {
    // Arrange
    String authId = "adminuser";
    String password = "adminpassword";
    String hashedPassword = "hashedadminpassword";

    UserRole userRole = new UserRole(1, "ROLE_USER");
    UserRole adminRole = new UserRole(2, "ROLE_ADMIN");
    Auth auth = new Auth(authId, hashedPassword, List.of(userRole, adminRole));

    when(authRepository.findById(authId)).thenReturn(auth);
    when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

    // Act
    UserDetails result = authApplicationService.authenticate(authId, password);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(authId);
    assertThat(result.getPassword()).isEqualTo(hashedPassword);
    assertThat(result.getAuthorities()).hasSize(2);
    assertThat(result.getAuthorities())
        .extracting("authority")
        .containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
  }

  @Test
  void testAuthenticate_正常系_ロールが空のユーザーの認証が成功する() {
    // Arrange
    String authId = "noroleuser";
    String password = "testpassword";
    String hashedPassword = "hashedpassword";

    Auth auth = new Auth(authId, hashedPassword, List.of());

    when(authRepository.findById(authId)).thenReturn(auth);
    when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

    // Act
    UserDetails result = authApplicationService.authenticate(authId, password);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(authId);
    assertThat(result.getPassword()).isEqualTo(hashedPassword);
    assertThat(result.getAuthorities()).isEmpty();
  }
}
