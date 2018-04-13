//package com.judge.interceptor;
//
//import com.alibaba.fastjson.JSONObject;
//import com.judge.utils.JsonUtils;
//import com.judge.utils.ResponseUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Aspect
//@Service
//public class LoginAspectj {
//    /**
//     * 登陆接口管理切点 拦截验证所有 controller包下的类的所有有以request为第一个参数的方法 除了login方法
//     */
//    @Pointcut("args(request,response,..) "
//    		+ "&& execution(* com.judge.controller..*(..)) "
//    		+ "&& !execution(* com.judge.controller.UserController.ssologin(..))"
//            + "&& !execution(* com.judge.controller.AdminController.adminLogin(..))")
//    public void pointCut(HttpServletRequest request,HttpServletResponse response){}
//
//
//    /**
//     * 异常通知
//     * @param request
//     */
//    @AfterThrowing("pointCut(request,response)")
//    public void afterThrowingLog(HttpServletRequest request,HttpServletResponse response) {
//        System.out.println("LoginAspectj-方法抛出异常后执行通知"+request);
//    }
//
//	/**
//	 * 登陆接口环绕通知
//	 * @param joinpoint
//	 * @param request
//	 * @return
//	 */
//    @Around("pointCut(request,response)")
//    public Object login_around(ProceedingJoinPoint joinpoint,HttpServletRequest request,HttpServletResponse response) {
//        Object result = null;
//        try {
//            System.out.println("LoginAspectj-环绕通知开始"+request);
//            HttpSession session = request.getSession();
//            if (session.getAttribute("current_user") != null) {
//                try {
//                    result = joinpoint.proceed(joinpoint.getArgs());
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//                return result;
//            } else {
//                String returnType = ((MethodSignature)joinpoint.getSignature()).getReturnType().getSimpleName().toString();
//                System.out.println(returnType);
//                if(returnType.equals("Object")){
//                	Map<String,Object> resMap = new HashMap<String,Object>();
//                    resMap.put("code", "302");
//                    resMap.put("desc","请登陆");
//                    return resMap;
//                }else if(returnType.equals("Map")){
//                    Map<String,Object> resMap = new HashMap<String,Object>();
//                    resMap.put("code", "302");
//                    resMap.put("desc","请登陆");
//                    return resMap;
//                }else {
//                    JSONObject resultObj = new JSONObject();
//                    resultObj.put("code", "302");
//                    resultObj.put("desc", "请登陆");
//                    ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
//                }
//            }
//        } catch (Throwable t) {
//        	t.printStackTrace();
//            System.out.println("LoginAspectj-出现错误");
//        }
//        System.out.println("LoginAspectj-环绕通知结束");
//        return result;
//    }
//}