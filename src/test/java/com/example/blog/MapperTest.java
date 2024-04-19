package com.example.blog;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.UserService;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * @Author 三更 B站： https://space.bilibili.com/663528522
 */
@SpringBootTest
public class MapperTest {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testUserMapper()  {
        // 获取当前日期
        Date currentDate = new Date();
        // 获取当前月份
        int currentMonth = DateUtil.month(currentDate) + 1; // 注意：月份从0开始，所以需要加1
        // 计算当前半年的起始月份
        int startMonth = currentMonth <= 6 ? 1 : 7;
        // 获取当前半年的起始日期
        Date startOfHalfYear = DateUtil.beginOfMonth(DateUtil.offsetMonth(currentDate, -(currentMonth - startMonth)));
        // 输出当前半年的起始日期
        System.out.println("当前半年的起始日期：" + DateUtil.formatDate(startOfHalfYear));
    }
}
