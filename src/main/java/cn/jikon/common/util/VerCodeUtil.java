package cn.jikon.common.util;


import static org.springframework.util.StreamUtils.BUFFER_SIZE;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import org.apache.tomcat.util.codec.binary.Base64;

public class VerCodeUtil {
  private static Map<String, String> codeMap = new HashMap<String,String>();

  public static void checkVerCode(String verCodeId, String inVerCode) throws BizException {
    String outVerCode = codeMap.get(verCodeId).toLowerCase();

    String VerCode = inVerCode.toLowerCase();

    if (null == outVerCode) {
      throw new BizException(EduErrors.CODE_100024, "验证码错误");
    }
    if (!outVerCode.equals(VerCode)) {
      throw new BizException(EduErrors.CODE_100024, "验证码错误");
    }
    codeMap.remove(verCodeId);
  }

  public void saveMessage(String verCodeId, String verCode) {
     codeMap.put(verCodeId,verCode);
  }
  private static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
      'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','2', '3', '4', '5', '6', '7', '8',
      '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
      'n', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
  private String image;// 图像
  private String str;// 验证码
  /**
   *  构造方法调用初始化属性方法
   */
  private VerCodeUtil() throws BizException {
    init();
  }
  /**
   * 取得RandomNumUtil实例
   */
  public static VerCodeUtil instance() throws BizException {
    return new VerCodeUtil();
  }
  /**
   * 取得验证码图片
   */
  public String getImage() {
    return this.image;
  }
  /**
   * 取得图片的验证码
   */
  public String getString() {
    return this.str;
  }
  /**
   * 初始化属性否具体方法
   */
  private void init() throws BizException {
    // 在内存中创建图象
    int width = 85, height = 18;
    //设置图形的高度和宽度，以及RGB类型
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 获取图形上下文
    Graphics g = image.getGraphics();
    // 生成随机类
    Random random = new Random();
    // 设定背景色
    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);
    // 设定字体
    g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    // 随机产生255条干扰线，使图象中的认证码不易被其它程序探测到
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 255; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      g.drawLine(x, y, x + xl, y + yl);
    }
    // 取随机产生的认证码(4位数字)
    StringBuffer sRand = new StringBuffer();
    for (int i = 0; i < 4; i++) {
      String rand = String.valueOf(CHARS[random.nextInt(CHARS.length-1)]);//从字符数组中随机产生一个字符
      sRand.append(rand);
      // 将认证码显示到图象中
      g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
      // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
      g.drawString(rand, 13 * i + 6, 17);
    }
    // 赋值验证码
    this.str = sRand.toString();
    // 图象生效
    g.dispose();
    //下面将生成的图形转变为图片
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ByteArrayInputStream input = null;
    byte[] bytes = new byte[BUFFER_SIZE];
    try {
      ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
      ImageIO.write(image, "JPEG", imageOut);//将图像按JPEG格式写入到imageOut中，即存入到output的字节流中
      imageOut.close();//关闭写入流
      bytes = output.toByteArray();
    } catch (Exception e) {
      throw new BizException(EduErrors.CODE_100005, "工具类生成验证码失败");
    }
    this.image = Base64.encodeBase64String(bytes);;/* 赋值图像 */
  }
  /*
  * 给定范围获得随机颜色
  */
  private Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255)
      fc = 255;
    if (bc > 255)
      bc = 255;
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }


}


