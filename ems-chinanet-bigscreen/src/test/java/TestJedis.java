import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestJedis extends AbstractJUnit4SpringContextTests {
	private JedisConnectionFactory jredisConnectionFactory;
	private JedisConnection jedisConnection;

	@Before
	public void before() {
		jedisConnection = jredisConnectionFactory.getConnection();
	}

	@After
	public void after() {
		jedisConnection.close();
	}

	private void print(Collection<RedisServer> c) {
		for (Iterator<RedisServer> iter = c.iterator(); iter.hasNext();) {
			RedisServer rs = (RedisServer) iter.next();
			System.out.println(rs.getHost() + ":" + rs.getPort());
		}
	}

	@Test
	public void test1() {
		if (!jedisConnection.exists(new String("zz").getBytes())) {
			jedisConnection.set(new String("zz").getBytes(), new String("zz").getBytes());
		}
	}

	@Test
	public void test2() {
		Set<byte[]> keys = jedisConnection.keys(new String("*").getBytes());
		for (Iterator<byte[]> iter = keys.iterator(); iter.hasNext();) {
			System.out.println(new String(iter.next()));
		}
	}

	@Autowired
	public void setJredisConnectionFactory(JedisConnectionFactory jredisConnectionFactory) {
		this.jredisConnectionFactory = jredisConnectionFactory;
	}
}
