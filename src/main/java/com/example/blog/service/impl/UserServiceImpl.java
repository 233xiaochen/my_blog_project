package com.example.blog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.config.RedisCache;
import com.example.blog.mapper.UserMapper;
import com.example.blog.pojo.ResponseResult;
import com.example.blog.pojo.User;
import com.example.blog.service.UserService;
import com.example.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult<Map<String, String>> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            //将异常抛给security处理，返回统一格式
            throw new RuntimeException(e);
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //三十分钟
        String accessToken = JwtUtil.createJWT(userId, 30 * 60 * 1000L);
        //七天
        String refreshToken = JwtUtil.createJWT(userId, 7 * 24 * 60 * 60 * 1000L);
        //authenticate存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        //把token响应给前端
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return new ResponseResult<>(HttpStatus.OK.value(), "登陆成功", map);
    }

    @Override
    public ResponseResult<Map<String, String>> loginOut() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login:" + userid);
        return new ResponseResult<>(HttpStatus.OK.value(), "退出成功");
    }

    @Override
    public ResponseResult<Map<String, String>> register(User user) {
        //重名校验
        User selectOne = baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
        if (!Objects.isNull(selectOne)) {
            return new ResponseResult<>(600, "用户名重复");
        }
        //设置随机昵称
        user.setNickName(getRandomName());
        //密码加密存储
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return new ResponseResult<>(HttpStatus.OK.value(), "注册成功");
    }

    /**
     * 设置随机昵称
     *
     * @return
     */
    public String getRandomName() {
        String[] adjectives = {"Happy", "Sunny", "Funny", "Lucky", "Smart", "Brave", "Kind"};
        String[] nouns = {"Cat", "Dog", "Bird", "Fish", "Bear", "Tiger", "Lion"};
        String adjective = adjectives[RandomUtil.randomInt(adjectives.length)];
        String noun = nouns[RandomUtil.randomInt(nouns.length)];
        return adjective + noun;
    }

    /**
     * 停用账号
     *
     * @param id
     * @return
     */
    public boolean deactivateAccount(long id) {
        return update(Wrappers.<User>lambdaUpdate()
                .set(User::getStatus, "1")
                .eq(User::getDelFlag, "0")
                .eq(User::getId, id));
    }

    /**
     * 启用账号
     *
     * @param id
     * @return
     */
    @Override
    public boolean enableAccount(long id) {
        return update(Wrappers.<User>lambdaUpdate()
                .set(User::getStatus, "0")
                .eq(User::getDelFlag, "0")
                .eq(User::getId, id));
    }

}
