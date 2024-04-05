package com.example.test;

import com.example.test.mapper.MenuMapper;
import com.example.test.mapper.UserMapper;
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

/**
 * @Author 三更 B站： https://space.bilibili.com/663528522
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testUserMapper() throws IOException {
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("fruit");
//        createIndexRequest.mapping("{\n" +
//                "    \"properties\": {\n" +
//                "      \"id\":{\n" +
//                "        \"type\": \"integer\"\n" +
//                "      },\n" +
//                "      \"title\":{\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\"\n" +
//                "      },\n" +
//                "      \"price\":{\n" +
//                "        \"type\": \"double\"\n" +
//                "      },\n" +
//                "      \"created_at\":{\n" +
//                "        \"type\": \"date\"\n" +
//                "      },\n" +
//                "      \"description\":{\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }", XContentType.JSON);
//        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        System.out.println(createIndexResponse.isAcknowledged());
//        restHighLevelClient.close();
    }
}
