package com.unitap.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Bean
  public AcceptHeaderLocaleResolver localeResolver() {
    val localeResolver = new AcceptHeaderLocaleResolver();
    val defaultLocale = Locale.of("ru", "RU");

    localeResolver.setDefaultLocale(defaultLocale);

    val locales = List.of(defaultLocale);
    localeResolver.setSupportedLocales(locales);

    return localeResolver;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    val localeInterceptor = new LocaleChangeInterceptor();
    localeInterceptor.setParamName("lang");
    registry.addInterceptor(localeInterceptor);
  }
}
