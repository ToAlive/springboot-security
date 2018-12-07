package com.example.springbootsecurity.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登陆功能，如果没用登陆权限就会重定向到登陆页面
        //1./login请求来到spring默认的登录页
        //2.重定向到/login?error表示登陆失败

        /**
         * 指定自己的登陆页面
         * 规则：
         * 1.使用默认的登陆页面
         *      /login  method=post 是处理登陆的请求，method=get 是去登陆页
         *      security会来帮我们处理
         *2.默认提交的登陆参数是username/password
         *  可以通过usernameParameter() passwordParameter()指定我们提交的登陆参数
         *3.自定义的登陆页
         *   登陆的请求路径 method=get 是去登陆页
         *   登陆的请求路径 method=post  处理登陆请求    restful风格的请求
         *      /登陆的请求路径?error 是登陆错误
         *      /登陆的请求路径?logout 是退出
         *
         */
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("pwd")
                .loginPage("/userlogin");
        //指定处理登陆的请求.loginProcessingUrl("/userlogin");
        //不指定默认就是loginPage("/userlogin")

        //开启自动配置的默认注销功能
        //1.相当于访问/logout表示用户注销，清空session中的用户信息
        /**
         * 2.<form method="post" th:action="@{/logout}">
         * 	<input type="submit" th:value="注销"/>
         * </form>
         */
        //3.注销成功后会返回这个login?logout页面
        //logoutSuccessUrl 指定注销成功后返回的页面路径
        http.logout().logoutSuccessUrl("/");

        //开启记住我功能
        //登陆成功后，将cookie发送给浏览器保存，以后访问就带上这个cookie，只要通过检查就可以免登陆
        //点击注销会向浏览器发送请求，要求删除cookie
        //提交参数的名字默认时remember-me
        //可以使用rememberMeParameter()指定提交参数的名字
        http.rememberMe().rememberMeParameter("remember");
    }

    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("VIP1")
                .and()
                .withUser("lisi").password("123").roles("VIP2")
                .and()
                .withUser("wangwu").password("123").roles("VIP1","VIP2","VIP3");
    }
}
