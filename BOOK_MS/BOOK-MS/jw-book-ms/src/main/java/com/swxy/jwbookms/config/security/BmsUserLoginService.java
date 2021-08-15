package com.swxy.jwbookms.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swxy.jwbookms.mapper.BmsUserMapper;
import com.swxy.jwbookms.pojo.BmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BmsUserLoginService implements UserDetailsService {

    private final BmsUserMapper bmsUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<BmsUser> bmsUsers = bmsUserMapper.selectByMap(new HashMap<String, Object>() {{
            put("username", username);
        }});

        if (null == bmsUsers || bmsUsers.size() == 0) {
            throw new UsernameNotFoundException(username);
        }
        BmsUser bmsUser = bmsUsers.get(0);
        String[] split = bmsUser.getAuthority().split(",");

        UserDetails user = User.builder()
                .username(bmsUser.getUsername())
                .password(bmsUser.getPwd())
                .roles(split)
                .disabled(bmsUser.getEnabled())
                .build();
        return user;
    }
}
