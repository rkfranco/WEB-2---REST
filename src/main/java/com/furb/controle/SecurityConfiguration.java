package com.furb.controle;
// crie uma nova classe no PACKAGE principal e dentro dela

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: Arrumar segurança (Atualmente tá tudo desabilitado)
        http.authorizeRequests().antMatchers("/").permitAll();
        http.csrf().disable();
    }
}