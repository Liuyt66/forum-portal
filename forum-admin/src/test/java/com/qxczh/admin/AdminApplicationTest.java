package com.qxczh.admin;

import com.qxczh.admin.service.*;
import com.qxczh.common.dao.AdminUserDao;
import com.qxczh.common.entity.AdminUser;
import com.qxczh.common.entity.Posts;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:admin.properties"})
@SpringBootTest
public class AdminApplicationTest {

    @Autowired
    DataSource dataSource;

    @Autowired(required=true)
    AdminUserService adminUserService;

    @Autowired(required=true)
    PermissionService permissionService;

    @Autowired(required=true)
    RoleService roleService;

    @Autowired
    AdminUserDao dao;

    @Autowired
    UserService userService;

    @Autowired
    PostsService postsService;

    @Autowired
    JedisPool jedisPool;
    @Test
    public void testJedis(){

        String value = jedisPool.getResource().get("foo");
        System.out.println(value);
    }

    @Test
    public void testPassword(){

    }









    public String algorithmName = "md5";
    public int hashIterations = 2;
    @Test
    public void encryptPassword() {
        AdminUser user = new AdminUser();
        user.setUsername("liuyuting");
        user.setEnable(1);
        user.setPassword("123456");
        //String salt=randomNumberGenerator.nextBytes().toHex();
        Object obj = ByteSource.Util.bytes(user.getUsername());
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),  ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        //String newPassword = new SimpleHash(algorithmName, user.getPassword()).toHex();
        //user.setPassword(newPassword);
        System.out.println(newPassword+" "+obj);
    }

    @Test
    public void testDataSource() {
       System.out.println(dao.findOne(3));

    }



    @Test
    public void testRole(){
//        AdminUser user1 = new AdminUser();
//        AdminUser user2 = new AdminUser();
//        AdminUser user3 = new AdminUser();
//        user1.setId(11);
//        user2.setId(12);
//        user3.setId(13);
        adminUserService.saveAdminEnable(new Integer[]{11,12,13});
    }

    @Test
    public void testPosts(){
        Posts posts = new Posts();
//        posts.setId(1);
        posts.setTitle("测试");
        posts.setUser(userService.findOne(2));
        Page<Posts> page = postsService.findByPage(posts, 0, 10);
        System.out.println(page.getContent());
    }
}
