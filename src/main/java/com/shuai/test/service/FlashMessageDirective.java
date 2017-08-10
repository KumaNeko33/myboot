package com.shuai.test.service;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Title: FlashMessageDirective.java
 * 
 * @Package  net.showcoo.template.directive
 * 
 * @Description: TODO(  模板指令 - 瞬时消息)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015-06-09 下午16:11:09
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
@Component("flashMessageDirective")
public class FlashMessageDirective extends BaseDirective {

	/** "瞬时消息"属性名称 */
	public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = FlashMessageDirective.class.getName() + ".FLASH_MESSAGE";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "flashMessage";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
//		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//		if (requestAttributes != null) {
//			Message message = (Message) requestAttributes.getAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
//			if (body != null) {
//				setLocalVariable(VARIABLE_NAME, message, env, body);
//			} else {
//				if (message != null) {
//					Writer out = env.getOut();
//					out.write("$.message(\"" + message.getType() + "\", \"" + message.getContent() + "\");");
//				}
//			}
//		}
	}

}
