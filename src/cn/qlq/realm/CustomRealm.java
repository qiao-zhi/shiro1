package cn.qlq.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm。根据上面穿下来的token去数据库查信息，查到返回一个SimpleAuthenticationInfo，查不到返回null
* @author: qlq
* @date :  2017年7月28日下午8:45:52
 */
public class CustomRealm extends AuthorizingRealm {

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	// 用于认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		// token是用户输入的
		// 第一步从token中取出身份信息（token代表用户输入的传下来的信息）
		String userCode = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		// ....从数据库查数据
	

		// 如果查询不到返回null
		//数据库中用户账号是zhangsansan
		/*if(!userCode.equals("zhangsansan")){//
			return null;
		}*/
		
		
		// 模拟从数据库查询到密码
		String password = "111111";

		// 如果查询到返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				userCode, password, this.getName());

		return simpleAuthenticationInfo;
	}

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
