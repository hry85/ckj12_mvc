package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.UserSecurityDetailsService;
import com.mysql.cj.protocol.AuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserSecurityDetailsService userDetailsService;
	
 	
	
		
	@Autowired	
	public SecurityConfig(UserSecurityDetailsService userDetailsService) {
		
		this.userDetailsService = userDetailsService;
	}

	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()
		.antMatchers("/")
		.permitAll()
		.antMatchers("/notes/**")
		.authenticated()
		.antMatchers("/users/**")
		.hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/login")
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.and()
		.exceptionHandling()
		.accessDeniedPage("/login?denied=true")
		.and()
		.rememberMe().tokenValiditySeconds(2592000).userDetailsService(userDetailsService)
		.and()
		.csrf()
		.disable();
	}
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		UserDetails user1 = User.builder()
//								.username("huray")
//								.password(passwordEncoder().encode("ene19022016"))
//								.roles("USER")
//								.build();
//		UserDetails user2= User.builder()
//				               .username("Admin")
//				               .password(passwordEncoder().encode("admin"))
//				               .roles("ADMIN")
//				               .build();
//
//		auth.inMemoryAuthentication()
//			.withUser(user1)
//			.withUser(user2);
//	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		auth.authenticationProvider(authenticationProvider);
		super.configure(auth);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
