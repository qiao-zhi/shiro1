package cn.qlq.authorization;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
/**
 * 授权测试
* @author: qlq
* @date :  2017年7月29日上午9:52:51
 */
public class AuthorizationTest {

	@Test
	public void testActhorization() {
		// 1.创建securityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");

		// 2.创建securityManager
		SecurityManager securityManager = factory.getInstance();

		// 3.将securityManager绑定到运行环境
		SecurityUtils.setSecurityManager(securityManager);

		// 4.创建主体
		Subject subject = SecurityUtils.getSubject();

		// 5.创建token用于认证
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

		// 6.登录(认证)
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("认证状态：" + subject.isAuthenticated());

		// 7.授权 基于角色授权 基于资源的授权

		// 7.1基于角色授权
		// 授权单个
		boolean permitted = subject.hasRole("role1");
		System.out.println("这是授权单个:" + permitted);

		boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1", "role2", "role3"));
		System.out.println("这是多个授权:" + hasAllRoles);

		// 使用check方法进行授权，如果授权不通过会抛出异常
		// subject.checkRole("role13");

		// 7.2 基于资源的授权
		// isPermitted传入权限标识符
		boolean isPermitted = subject.isPermitted("user:create:1");
		System.out.println("单个权限判断" + isPermitted);

		boolean isPermittedAll = subject.isPermittedAll("user:create:1", "user:delete");
		System.out.println("多个权限判断" + isPermittedAll);

		// 使用check方法进行授权，如果授权不通过会抛出异常
		subject.checkPermission("items:create:1");

	}
	
	
	// 自定义realm进行资源授权测试
		@Test
		public void testAuthorizationCustomRealm() {

			// 创建SecurityManager工厂
			Factory<SecurityManager> factory = new IniSecurityManagerFactory(
					"classpath:shiro-realm.ini");

			// 创建SecurityManager
			SecurityManager securityManager = factory.getInstance();

			// 将SecurityManager设置到系统运行环境，和spring后将SecurityManager配置spring容器中，一般单例管理
			SecurityUtils.setSecurityManager(securityManager);

			// 创建subject
			Subject subject = SecurityUtils.getSubject();

			// 创建token令牌
			UsernamePasswordToken token = new UsernamePasswordToken("zhangsan",
					"111111");

			// 执行认证
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("认证状态：" + subject.isAuthenticated());
			// 认证通过后执行授权

			// 基于资源的授权，调用isPermitted方法会调用CustomRealm从数据库查询正确权限数据
			// isPermitted传入权限标识符，判断user:create:1是否在CustomRealm查询到权限数据之内
			boolean isPermitted = subject.isPermitted("user:create:1");
			System.out.println("单个权限判断" + isPermitted);

			boolean isPermittedAll = subject.isPermittedAll("user:create:1",
					"user:create");
			System.out.println("多个权限判断" + isPermittedAll);

			// 使用check方法进行授权，如果授权不通过会抛出异常
			subject.checkPermission("items:add:1");

		}
}
