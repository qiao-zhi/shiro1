package cn.qlq.authentication;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 测试MD5算法
* @author: qlq
* @date :  2017年7月28日下午9:31:45
 */
public class MD5Test {
	
	public static void main(String[] args) {
		
		//原始 密码 
		String source = "111111";
		//盐
		String salt = "qwerty";
		//散列次数
		int hashIterations = 2;
		//上边散列1次：f3694f162729b7d0254c6e40260bf15c
		//上边散列2次：36f2dfa24d0a9fa97276abbe13e596fc
		
		
		//构造方法中：
		//第一个参数：明文，原始密码 
		//第二个参数：盐，通过使用随机数
		//第三个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
		Md5Hash md5Hash = new Md5Hash(source, salt, hashIterations);
		String password_md5 =  md5Hash.toString();
		System.out.println(password_md5);
		
		
//		第二种方法
		//第一个参数：散列算法 
		SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
		System.out.println(simpleHash.toString());
		
		
		
	}

}
