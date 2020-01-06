package com.jinxiang.order.tool;

/**
 * Created by chLang on 2019/12/20
 */


import com.jinxiang.order.pojo.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException ex) {
        ex.printStackTrace();
        String msg = ex.getMessage();
        if (msg.toLowerCase().contains("reference 约束") && msg.toLowerCase().contains("fk"))
        {
            return new ResponseEntity<>(1500, false, "记录已被引用", null);
        }

        return new ResponseEntity<>(1500, false, "运行时异常", null);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<?> nullPointerExceptionHandler(NullPointerException ex) {
        System.err.println("NullPointerException:");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","空指针异常");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResponseEntity<?> classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","类型转换异常");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<?> iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","IO异常");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResponseEntity<?> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","未知方法异常");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResponseEntity<?> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","数组越界异常");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //1400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<?> requestNotReadable(HttpMessageNotReadableException ex) {
        System.out.println("1400..requestNotReadable");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","Http消息不可读");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1400, false, null, r);
    }

    //1400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<?> requestTypeMismatch(TypeMismatchException ex) {
        System.out.println("1400..TypeMismatchException");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","类型不匹配");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1400, false, null, r);
    }

    //1400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseEntity<?> requestMissingServletRequest(MissingServletRequestParameterException ex) {
        System.out.println("1400..MissingServletRequest");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","缺少Servlet请求参数");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1400, false, null, r);
    }

    //404错误
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public ResponseEntity<?> requestNoHandlerFoundExceptionRequest(NoHandlerFoundException ex) {
        System.out.println("404 url not found");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","404 url not found");
        r.put("detail",ex.getMessage());

        return new ResponseEntity<>(404, false, null, r);
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseEntity<?> request405(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","不支持Http请求方法");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(405, false, null, r);
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResponseEntity<?> request406(HttpMediaTypeNotAcceptableException ex) {
        System.out.println("406...");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","Http媒体类型不可接受");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(406, false, null, r);
    }

    //1500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResponseEntity<?> server1500(RuntimeException ex) {
        System.out.println("1500...");
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","ConversionNotSupported");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    @ResponseBody
    public ResponseEntity<?> requestStackOverflow(StackOverflowError ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","栈溢出");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }

    //除数不能为0
    @ExceptionHandler({ArithmeticException.class})
    @ResponseBody
    public ResponseEntity<?> arithmeticException(ArithmeticException ex) {
        ex.printStackTrace();
        Map<String, Object> r = new HashMap<>();
        r.put("msg","除数不能为0");
        r.put("detail",ex.getMessage());
        return new ResponseEntity<>(1500, false, null, r);
    }


    //其他错误
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<?> exception(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(1500, false, ex.getMessage(), null);
    }

}
