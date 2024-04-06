package com.example.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.test.mapper.MenuMapper;
import com.example.test.mapper.UserMapper;
import com.example.test.pojo.User;
import com.example.test.service.UserService;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;

/**
 * @Author 三更 B站： https://space.bilibili.com/663528522
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testUserMapper() throws IOException {
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery());
        System.out.println(users.size());
    }
}
