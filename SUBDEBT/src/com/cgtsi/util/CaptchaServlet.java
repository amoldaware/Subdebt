package com.cgtsi.util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.font.TextAttribute;
 
public class CaptchaServlet extends HttpServlet {

	private int height=0;
	private int width=0;
		
	public static final String CAPTCHA_KEY = "captcha_key_name";

	/*
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
	 height=Integer.parseInt(getServletConfig().getInitParameter("height"));
     width=Integer.parseInt(getServletConfig().getInitParameter("width"));
  }

 
 protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
	  //Expire response
	  response.setHeader("Cache-Control", "no-cache");
	  response.setDateHeader("Expires", 0);
	  response.setHeader("Pragma", "no-cache");
	  response.setDateHeader("Max-Age", 0);
		
	  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
	  Graphics2D graphics2D = image.createGraphics();
	  Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
	  Random r = new Random();
	  //String token = Long.toString(Math.abs(r.nextLong()), 36);
	  String token = String.valueOf( Math.round(r.nextInt()));
	  String ch = token.substring(1,6);
	  	  
	  Color c = new Color(0.6662f, 0.4569f, 0.3232f);
	  GradientPaint gp = new GradientPaint(0, 4, c, 15, 25, Color.white, true);
	  graphics2D.setPaint(gp);
	  //Castellar,Snap ITC, Bauhaus 93, Verdana, Colonna MT, Harlow Solid Italic, Stencil
	  Font font=new Font("Castellar", Font.CENTER_BASELINE , 17);
	  graphics2D.setFont(font);
	  //graphics2D.drawArc(0, height/3, width, height/3, 30, 45);
	  graphics2D.drawLine(0, height/3, width, height/3);
	  
	  graphics2D.drawString(ch,2,12);
	  graphics2D.dispose();
	  
	  HttpSession session = req.getSession(true);
	  session.setAttribute(CAPTCHA_KEY,ch);

	  OutputStream outputStream = response.getOutputStream();
	  ImageIO.write(image, "jpeg", outputStream);
	  outputStream.close();



 }
//*/
	
	//*
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int width = 140;
		int height = 40;
		int charsToPrint = 6;
		String elegibleChars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy";
        char[] chars = elegibleChars.toCharArray();
        char[] numberChars = "123456789".toCharArray();
		StringBuffer finalString = new StringBuffer();
		/*Four Alphabets Character*/
		for (int i = 0; i < (charsToPrint-2); i++) {
            double randomValue = Math.random();
            int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
            char characterToShow = chars[randomIndex];
            finalString.append(characterToShow);
		}
		/*Two Numbers*/
		for (int i = 0; i < 2; i++) {
            double randomValue = Math.random();
            int randomIndex = (int) Math.round(randomValue * (numberChars.length - 1));
            char characterToShow = numberChars[randomIndex];
            finalString.append(characterToShow);
		}
		String captcha = finalString.toString();
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);		
		Graphics2D g2d = bufferedImage.createGraphics();
		Font font = new Font("Grunge", Font.PLAIN, 20);
		g2d.setFont(font);
		RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, 
		RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		GradientPaint gp = new GradientPaint(0, 0, 
		Color.blue, 0, height/2, Color.black, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(new Color(120, 120, 120));
		request.getSession().setAttribute(CAPTCHA_KEY, captcha );
		int x = 0; 
		int y = 0;
		Random r = new Random();
		for (int i=0; i<captcha.length(); i++) {
			x += 10 + (Math.abs(r.nextInt()) % 15);
			y = 20 + (Math.abs(r.nextInt()) % 20);
			g2d.drawChars(captcha.toCharArray(), i, 1, x, y);
		}
		g2d.dispose();
		
			response.setContentType("image/jpg");
			OutputStream os = response.getOutputStream();
			ImageIO.write(bufferedImage, "jpg", os);
			os.close();
		} 
		
		
	protected void doGet(HttpServletRequest request, 
	   HttpServletResponse response)
	       throws ServletException, IOException {
	processRequest(request, response);
	} 
	
	
	protected void doPost(HttpServletRequest request, 
	    HttpServletResponse response)
	        throws ServletException, IOException {
	processRequest(request, response);
	}
	//*/
}
