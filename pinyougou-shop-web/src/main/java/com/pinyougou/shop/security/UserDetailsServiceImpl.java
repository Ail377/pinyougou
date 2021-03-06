package com.pinyougou.shop.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.shop.security *
 * @since 1.0
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("UserDetailsServiceImpl======经过了这个方法======");

        //


        //1.从数据库中获取用户的信息(用户的密码)

        //1.1 先根据页面传递过来的用户名（seller_id） 查询用户对象
        TbSeller one = sellerService.findOne(username);
        //1.2 判断如果用户不存在  直接返回null
        if(one==null){
            return null;
        }

        //1.3  判断 用户 是否已经被审核了 如果没有审核 return

        if(!one.getStatus().equals("1")){
            return null;
        }

        //1.4 如果 用户 存在  需要匹配密码（自动完成） 就获取用户的密码





        String password =one.getPassword();


        //3.给用户设置权限
        /*List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));//授权角色
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//授权角色*/

        //2.交给springsecurity框架 自动的匹配
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
