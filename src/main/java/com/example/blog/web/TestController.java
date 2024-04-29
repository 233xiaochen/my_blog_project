package com.example.blog.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.blog.pojo.FriendVo;
import com.example.blog.pojo.NoticeVo;
import com.example.blog.pojo.ResponseResult;
import com.example.blog.pojo.RotatingPicturesVo;
import com.example.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： chenlin
 * @create： 2024/3/18 1:44
 */
@Slf4j
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/notice/frontList")
    public ResponseResult<List<NoticeVo>> test11(){
        List<NoticeVo> list = new ArrayList<>();
        NoticeVo vo1 = new NoticeVo();
        vo1.setNoteContent("测试文章通告111");
        NoticeVo vo2 = new NoticeVo();
        vo2.setNoteContent("测试文章通告222");
        list.add(vo1);
        list.add(vo2);
        return new ResponseResult<>(200,list);
    }
    @GetMapping("/blogCatg/classBar")
    public String tdddest(){
        return null;
    }
//    @GetMapping("videoCatg/frontList")
//    public ResponseResult<Map<String,Object>> wwrwer(@RequestBody SearchVo searchVo){
//        Map<String,Object> map = new HashMap<>();
//        long startTime = System.currentTimeMillis(); // 记录发送请求的时间
//        List<Article> articleList = articleService.queryList(searchVo.getSelectName(), searchVo.getPage(),searchVo.getPageSize(),searchVo.getType());
//        long count = articleService.queryArticleCount(searchVo.getSelectName(),searchVo.getType());
//        long endTime = System.currentTimeMillis(); // 记录收到响应的时间
//        long responseTimeMillis = endTime - startTime; // 计算响应时间（毫秒）
//        // 计算响应时间（秒）
//        double responseTimeSeconds = responseTimeMillis / 1000.0;
//        map.put("data",articleList);
//        map.put("total",String.valueOf(count));
//        map.put("searchTime",responseTimeSeconds+"s");
//        return new ResponseResult<>(200,map);
//    }

//    @GetMapping("/blogArticle/readTop")
//    public ResponseResult<List<Article>> asssdasf(){
//        List<Article> articleList = articleService.queryArticleTopTen();
//        return new ResponseResult<>(200,articleList);
//    }
    @GetMapping("/rotationChart/list")
    public ResponseResult<List<List<RotatingPicturesVo>>> tdfghdghest(){
        List<List<RotatingPicturesVo>> list = new ArrayList<>();
        List<RotatingPicturesVo> innerList1 = new ArrayList<>();
        RotatingPicturesVo vo1 = new RotatingPicturesVo();
        vo1.setBamId("1781771674714120194");
        vo1.setBamType("image");
        vo1.setBamImgTitle("https://img0.baidu.com/it/u=350592823,3182430235&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800");
        vo1.setRcType("1");
        RotatingPicturesVo vo2 = new RotatingPicturesVo();
        vo2.setBamId("1781771674714120194");
        vo2.setBamType("image");
        vo2.setBamImgTitle("https://img2.baidu.com/it/u=399019925,2499719398&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        vo2.setRcType("1");
        RotatingPicturesVo vo5 = new RotatingPicturesVo();
        vo5.setBamId("1781771674714120194");
        vo5.setBamType("image");
        vo5.setBamImgTitle("https://img2.baidu.com/it/u=3164965497,4239686043&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        vo5.setRcType("1");
        innerList1.add(vo1);
        innerList1.add(vo2);
        innerList1.add(vo5);
        list.add(innerList1);
        List<RotatingPicturesVo> innerList2 = new ArrayList<>();
        RotatingPicturesVo vo3 = new RotatingPicturesVo();
        vo3.setBamId("1781771674714120194");
        vo3.setBamType("image");
        vo3.setBamImgTitle("https://img1.baidu.com/it/u=3055872450,2506139798&fm=253&fmt=auto&app=120&f=JPEG?w=1423&h=800");
        vo3.setBamTitle("文章标题");
        RotatingPicturesVo vo4 = new RotatingPicturesVo();
        vo4.setBamId("1781771674714120194");
        vo4.setBamType("image");
        vo4.setBamImgTitle("https://img1.baidu.com/it/u=684770079,849342797&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");
        vo4.setBamTitle("测试文章");
        innerList2.add(vo3);
        innerList2.add(vo4);
        list.add(innerList2);
        System.out.println(JSONUtil.toJsonStr(list));
        return new ResponseResult<>(200,list);
    }

    /*
     *下载接口
     */
    @GetMapping("/{fileuuid}")
    public void getFiles(@PathVariable String fileuuid, HttpServletResponse response){
        OutputStream os;//新建一个文件输出流对象
        String basePath = System.getProperty("user.dir")+"/src/main/resources/picture/";//定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);//获取所有的文件名称
        String fileName = fileNames.stream().filter(name->name.contains(fileuuid)).findAny().orElse("");//找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath+fileName);//通过文件的路径读取文件字节流
                os=response.getOutputStream();//通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/myFriend/homePageList")
    public ResponseResult testadsgsdf(){
        List<FriendVo> list = new ArrayList<>();
        FriendVo vo = new FriendVo();
        vo.setFlImg("https://img0.baidu.com/it/u=1913485219,1027471738&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        vo.setFlName("小西瓜");
        vo.setFlUrl("www.baidu.com");
        FriendVo vo1 = new FriendVo();
        vo1.setFlImg("https://img2.baidu.com/it/u=1241569610,3177184194&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        vo1.setFlName("编程随想");
        vo1.setFlUrl("www.baidu.com");
        list.add(vo);
        list.add(vo1);
        list.add(vo1);
        list.add(vo1);
        return new ResponseResult<>(200,list);
    }
    @GetMapping("/jrebel/leases")
    public String sdfadsfgerrgg(){
        return "hello world";
    }
}
