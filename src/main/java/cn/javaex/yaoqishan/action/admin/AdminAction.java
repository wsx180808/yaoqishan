package cn.javaex.yaoqishan.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.yaoqishan.constant.ErrorMsg;
import cn.javaex.yaoqishan.exception.QingException;
import cn.javaex.yaoqishan.service.user_info.UserInfoService;
import cn.javaex.yaoqishan.util.MD5;
import cn.javaex.yaoqishan.view.Result;
import cn.javaex.yaoqishan.view.UserInfo;

@Controller
@RequestMapping("admin")
public class AdminAction {

	@Autowired
	private UserInfoService userInfoService;

	// 鐧诲綍椤甸潰鏄剧ず
	@RequestMapping("login.action")
	public String login() {
		return "admin/login";
	}
	
	/**
	 * 绠＄悊鍛樼櫥褰曞悗鍙�
	 * @param loginName 鐧诲綍鍚�
	 * @param passWord 鐧诲綍瀵嗙爜
	 * @throws Exception 
	 */
	@RequestMapping("login.json")
	@ResponseBody
	public Result login(HttpServletRequest request) throws Exception {
		
		// 1.0 鑾峰彇鐧诲綍鍙傛暟
		String szLoginName = request.getParameter("login_name");
		String szPassWord = request.getParameter("pass_word");
		
		// 2.0 鏍￠獙鐢ㄦ埛
		// 2.1 鏍￠獙鐢ㄦ埛鍚嶆垨瀵嗙爜鏄惁濉啓
		if (StringUtils.isEmpty(szLoginName) || StringUtils.isEmpty(szPassWord)) {
			throw new QingException(ErrorMsg.ERROR_100001);
		}
		
		// 2.2 鏍￠獙鐢ㄦ埛鍚嶃�佸瘑鐮佹槸鍚︽纭�
		UserInfo userInfo = userInfoService.selectUser(szLoginName, MD5.md5(szPassWord));
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100002);
		}
		
		// 2.3 鏍￠獙鏄惁鏄鐞嗗憳
		if (!"绠＄悊鍛�".equals(userInfo.getGroupName())) {
			throw new QingException(ErrorMsg.ERROR_100002);
		}
		
		// 3.0 鏍￠獙鎴愬姛锛岃缃畇ession
		request.getSession().setAttribute("userInfo", userInfo);
		
		return Result.success();
	}
	
	/**
	 * 娓呴櫎session
	 */
	@RequestMapping("logout.action")
	public String logout(HttpSession session) {
		// 娓呴櫎session
		session.invalidate();
		
		return "redirect:login.action";
	}

	/**
	 * 鑾峰彇褰撳墠鐧诲綍鐨勭鐞嗗憳淇℃伅
	 * @return
	 */
	@RequestMapping("get_admin.json")
	@ResponseBody
	public Result getAdmin(HttpServletRequest request) {
		
		// 鍒ゆ柇session
		HttpSession session  = request.getSession();
		// 浠巗ession涓彇鍑虹敤鎴疯韩浠戒俊鎭�
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

		return Result.success().add("userInfo", userInfo);
	}
	
	/**
	 * 绠＄悊涓績棣栭〉
	 */
	@RequestMapping("center.action")
	public String center(ModelMap map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>绋嬪簭鐗堟湰锛�</p></div>");
		sb.append("	<div class='right'>yaoqishan 1.0.0</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>浣滆�咃細</p></div>");
		sb.append("	<div class='right'>闄堥湏娓�</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>鑱旂郴鏂瑰紡锛�</p></div>");
		sb.append("	<div class='right'>QQ锛�291026192</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>瀹樼綉锛�</p></div>");
		sb.append("	<div class='right'><a href='http://www.javaex.cn/' target='_blank'>http://www.javaex.cn/</a></div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>鐗堟潈鎵�鏈夛細</p></div>");
		sb.append("	<div class='right'>闄堥湏娓�</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		
		map.put("info", sb.toString());
		
		return "admin/index";
	}
	
	/**
	 * 鏀惰垂鏈嶅姟
	 */
	@RequestMapping("service.action")
	public String service(ModelMap map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>鍔熻兘瀹氬埗锛�</p></div>");
		sb.append("	<div class='right'>1000璧�</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>妯℃澘瀹氬埗锛�</p></div>");
		sb.append("	<div class='right'>5000璧�/濂楋紝500璧�/鍗曢〉</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>缃戠珯瀹氬埗锛�</p></div>");
		sb.append("	<div class='right'>鎺ュ彈浠讳綍绫诲瀷鐨勯」鐩畾鍒讹紝浠锋牸璇﹁皥</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>鑱旂郴鏂瑰紡锛�</p></div>");
		sb.append("	<div class='right'>闄堥湏娓咃紝QQ锛�291026192</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		
		map.put("info", sb.toString());
		
		return "admin/service/service";
	}
	
	/**
	 * 棣栭〉
	 */
	@RequestMapping("index.action")
	public String index() {
		return "admin/menu/index";
	}
	
	/**
	 * 鍏ㄥ眬
	 */
	@RequestMapping("basic.action")
	public String basic() {
		return "admin/menu/basic";
	}
	
	/**
	 * 鐣岄潰
	 */
	@RequestMapping("layout.action")
	public String layout() {
		return "admin/menu/layout";
	}
	
	/**
	 * 鍒嗙被
	 */
	@RequestMapping("type.action")
	public String type() {
		return "admin/menu/type";
	}
	
	/**
	 * 鐢ㄦ埛
	 */
	@RequestMapping("user.action")
	public String user() {
		return "admin/menu/user";
	}
	
	/**
	 * 鍐呭
	 */
	@RequestMapping("content.action")
	public String content() {
		return "admin/menu/content";
	}
	
	/**
	 * 鎺ュ彛
	 */
	@RequestMapping("api.action")
	public String api() {
		return "admin/menu/api";
	}
	
	/**
	 * 绔欓暱
	 */
	@RequestMapping("founder.action")
	public String founder() {
		return "admin/menu/founder";
	}
}
