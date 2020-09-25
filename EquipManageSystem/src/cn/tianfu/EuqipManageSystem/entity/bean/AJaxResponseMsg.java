package cn.tianfu.EuqipManageSystem.entity.bean;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
1. 响应类
*/
@Component("ajaxResponseMsg")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class AJaxResponseMsg<T> implements Serializable
{
	private static final long serialVersionUID = -5007654155748473782L;

    /**
     * 状态码
     */
    private String code;
    /**
     * 消息（错误信息）
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public String getCode() {
        return code;
    }
    public AJaxResponseMsg<T> setCode(String code) {
        this.code = code;
        return this;
    }
    public String getMsg() {
        return msg;
    }
    public AJaxResponseMsg<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public T getData() {
        return data;
    }
    public AJaxResponseMsg<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 状态码定义
     */
    public interface ResponseCode{

        /**
         * 成功
         */
        public static final String SUCCESS = "0";
        /**
         * 业务报错
         */
        public static final String ERROR = "1";
        /**
         * 程序异常
         */
        public static final String EXCEPTION = "-1";
        /**
         * login succ
         */
        public static final String LOGIN_SUCC = "10";
        /**
         * login succ
         */
        public static final String LOGIN_FAILED = "-10";
        /**
         * register SUCC
         */
        public static final String REGISTER_SUCC = "11";
        /**
         * register FAILed
         */
        public static final String REGISTER_FAILED = "-11";
        /**
         * add equipment suncc
         */
        public static final String ADD_EQUIP_SUCC = "12";
        /**
         * add equipment failed
         */
        public static final String ADD_EQUIP_FAILED = "-12";
        /**
         * select equipment succ
         */
        public static final String SELECT_EQUIP_SUCC = "13";
        
        /**
         * select equipment failed
         */
        public static final String SELECT_EQUIP_FAILED = "-13";
    }

    /**
     * 默认创建方法
     * @return
     */
    public static <T> AJaxResponseMsg<T> build()
    {
        AJaxResponseMsg<T> AJaxResponseMsg = new AJaxResponseMsg<T>();
        return AJaxResponseMsg.setCode(ResponseCode.SUCCESS);
    }

    /**
     * 默认抛出异常
     * @return
     */
    public static <T> AJaxResponseMsg<T> exception()
    {
        AJaxResponseMsg<T> AJaxResponseMsg = new AJaxResponseMsg<T>();
        return AJaxResponseMsg.setCode(ResponseCode.EXCEPTION).setMsg("服务器发生异常，请联系管理员！");
    }

    /**
     * 默认错误
     * @return
     */
    public static <T> AJaxResponseMsg<T> error()
    {
        AJaxResponseMsg<T> AJaxResponseMsg = new AJaxResponseMsg<T>();
        return AJaxResponseMsg.setCode(ResponseCode.ERROR);
    }
}

