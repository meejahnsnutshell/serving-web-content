package hello.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/*
 This class configures basic auth security.
 Originally written to store login info in memory, is now altered to store locally on a mySQL db.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // encrypts password
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    // connects to queries defined in application.properties
    // Spring Security / Queries for AuthenticationManagerBuilder
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    // The following methods are boilerplate code for Spring basic auth security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeRequests()
                // allows anyone to access / and /home:
                    .antMatchers("/", "/home", "/registration").permitAll()
                // only users with admin role can access urls behind /admin:
                    .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                // any user must be authenticated
                .authenticated().and().csrf().disable().formLogin()
                .and()
                // specifies the login page url and gives access to all users
                .formLogin().loginPage("/login").permitAll()
                .and()
                // all users can access logout
                .logout().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // allows web access to anything in resources
        web
                .ignoring()
                .antMatchers("/resources/**");
    }
}
