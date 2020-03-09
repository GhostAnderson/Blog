package com.laurence.blog.Configure;

import com.laurence.blog.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import sun.security.util.Password;


@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Value("${rememberme.key}") private String remembermeKey;
	@Autowired
	MyUserDetailService myUserDetailService;

	@Autowired
	RememberMeServices rememberMeServices;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/archives/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/mindfuck").permitAll()
				.antMatchers("/gallery").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/signup").permitAll()
				.anyRequest().hasRole("admin")
				.and()
				.formLogin()
				.loginPage("/login")
				.and()
				.rememberMe().key(remembermeKey).rememberMeServices(rememberMeServices())
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("JSESSION")
				.logoutSuccessUrl("/");
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RememberMeServices rememberMeServices()
	{
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(remembermeKey,
				myUserDetailService);
		return tokenBasedRememberMeServices;
	}
}