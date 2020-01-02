package com.cmfz.controller;

import com.cmfz.util.ValidateImageCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("code")
public class CodeController {

    @RequestMapping("codes")
    public void getCode(HttpServletResponse response, HttpSession session){

        //获取验证码
        String code = ValidateImageCodeUtils.getSecurityCode();
        session.setAttribute("code",code);
        //绘制验证码图片
        BufferedImage image = ValidateImageCodeUtils.createImage(code);

        //通过输出流打印到页面上
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
