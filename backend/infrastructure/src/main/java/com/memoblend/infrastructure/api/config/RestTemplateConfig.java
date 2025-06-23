package com.memoblend.infrastructure.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate の設定クラスです。
 */
@Configuration
public class RestTemplateConfig {
  /**
   * RestTemplate の Bean を定義します。
   * 
   * @return RestTemplate のインスタンス。
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
