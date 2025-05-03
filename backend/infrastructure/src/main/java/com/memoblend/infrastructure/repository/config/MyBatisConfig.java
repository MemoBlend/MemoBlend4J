package com.memoblend.infrastructure.repository.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis 用の設定クラスです。
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "com.memoblend.infrastructure.repository" })
public class MyBatisConfig {

  /**
   * MyBatis 設定のカスタマイズ。
   * 
   * @return カスタマイズされた MyBatis 設定。
   */
  @Bean
  ConfigurationCustomizer mybatisConfigurationCustomizer() {
    return configuration -> configuration.setMapUnderscoreToCamelCase(true);
  }
}
