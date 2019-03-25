package com.slsd.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片裁剪工具类
 */
public class ImageUtil {
	private ImageUtil() {
	}

	private BufferedImage image = null;

	public void load(File imageFile) throws IOException {
		image = ImageIO.read(imageFile);
	}
	
	/**
	 * 获取数据流
	 * @param extName
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream(String extName) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(this.image, extName, bos);// 输出到bos
		return new ByteArrayInputStream(bos.toByteArray());
	}

	public int getImageWidth() {
		return image.getWidth();
	}

	public int getImageHeight() {
		return image.getHeight();
	}

	/**
	 * 图片裁剪
	 * @param x 裁剪坐标x
	 * @param y 裁剪坐标y
	 * @param zoomWidth 显示图大小
	 * @param zoomHeight 显示图大小
	 * @param tarWidth 裁剪宽度
	 * @param tarHeight 裁剪高度
	 * @throws FileNotFoundException
	 */
	public void cutTo(int x, int y, int zoomWidth, int zoomHeight, int tarWidth, int tarHeight)
			throws FileNotFoundException {
		if (image == null) {
			throw new FileNotFoundException(
					"image file not be load.please execute 'load' function agin.");
		}
		int iSrcWidth = getImageWidth(); // 得到源图宽
		int iSrcHeight = getImageHeight(); // 得到源图长
		double ratio = (double) iSrcWidth / (double) zoomWidth;
		x = (int) (x * ratio);
		y = (int) (y * ratio);
		tarWidth = (int) (tarWidth * ratio);
		tarHeight = (int) (tarHeight * ratio);
		// 如果源图片的宽度或高度小于目标图片的宽度或高度，则直接返回出错
		if (iSrcWidth < tarWidth || iSrcHeight < tarHeight) {
			throw new RuntimeException("source image size too small.");
		}
		// 剪裁
		this.image = image.getSubimage(x, y, tarWidth, tarHeight);
	}

	/**
	 * @函数功能说明:图片缩放不生成新的图片
	 * @创建者：qianfuqiang 2013-4-23
	 * @修改者：
	 * @修改内容：
	 * @参数： @param tarWidth
	 * @参数： @param tarHeight
	 * @返回： void
	 * @抛出异常：
	 */
	public void zoomTo(int tarWidth, int tarHeight) {
		BufferedImage tagImage = new BufferedImage(tarWidth, tarHeight, BufferedImage.TYPE_INT_RGB); // 缩放图像
		Image image = this.image.getScaledInstance(tarWidth, tarHeight, Image.SCALE_SMOOTH);
		Graphics g = tagImage.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制目标图
		g.dispose();
		this.image = tagImage;
	}

	/**
	 * 保存
	 * 
	 * @param fileName
	 * @param formatName
	 * @throws IOException
	 */
	public void save(String fileName, String formatName) throws IOException {
		// 写文件
		FileOutputStream out = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(this.image, formatName, bos);// 输出到bos
			out = new FileOutputStream(fileName);
			out.write(bos.toByteArray()); // 写文件
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 缩放图片 生成新的图片
	 */
	public static boolean zoomImage(String srcFile, String dstFile, int width, int height,
			String formatName) {
		try {
			ImageUtil zoom = new ImageUtil();
			zoom.load(new File(srcFile));
			zoom.zoomTo(width, height);
			zoom.save(dstFile, formatName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static ImageUtil fromImageFile(File file) throws IOException {
		ImageUtil utils = new ImageUtil();
		utils.load(file);
		return utils;
	}

	/**
	 * 加载图片
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ImageUtil load(String fileName) throws IOException {
		File file = new File(fileName);
		return fromImageFile(file);
	}

	public static void main(String[] args) {
		try {
			// ImageUtil.queryImage(url, longAccount);
			// System.out.println(ImageUtil.queryImage(url, longAccount));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
	 * @param imgsrc 源图片地址
	 * @param imgdist 目标图片地址
	 * @param widthdist 压缩后图片宽度（当rate==null时，必传）
	 * @param heightdist 压缩后图片高度（当rate==null时，必传）
	 * @param rate 压缩比例
	 */
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,
								 int heightdist, Float rate) {
//		FileOutputStream out = null;
		try {
			File srcfile = new File(imgsrc);
			// 检查文件是否存在
			if (!srcfile.exists()) {
				return;
			}
			// 如果rate不为空说明是按比例压缩
			if (rate != null && rate > 0) {
				// 获取文件高度和宽度
				int[] results = getImgWidth(srcfile);
				if (results == null || results[0] == 0 || results[1] == 0) {
					return;
				} else {
					widthdist = (int) (results[0] * rate);
					heightdist = (int) (results[1] * rate);
				}
			}
			// 开始读取文件并进行压缩
			Image src = ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist,
					(int) heightdist, BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_SMOOTH), 0, 0, null);

//			out = new FileOutputStream(imgdist);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag);
			String formatName = imgdist.substring(imgdist.lastIndexOf(".") + 1);
			ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */ , new File(imgdist) /* target */ );

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取图片宽度
	 *
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int[] getImgWidth(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int result[] = {0, 0};
		try {
			is = new FileInputStream(file);
			src = ImageIO.read(is);
			// 得到源图宽
			result[0] = src.getWidth(null);
			// 得到源图高
			result[1] = src.getHeight(null);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 裁剪图片成圆形
	 * @param imgsrc 原图片路径
	 * @param imgdist 目标路径
	 * @throws Exception ex
	 */
	public static void tailorToRound(String imgsrc,String imgdist) throws Exception {
		String suffix =  imgsrc.substring(imgsrc.lastIndexOf(".") + 1);
		BufferedImage bi1 = ImageIO.read(new File(imgsrc));

		// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
				.getHeight());

		Graphics2D g2 = bi2.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
		g2.setClip(shape);
		// 使用 setRenderingHint 设置抗锯齿
		g2.drawImage(bi1, 0, 0, null);
		g2.dispose();
		ImageIO.write(bi2, suffix, new File(imgdist));
	}

}
