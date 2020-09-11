package cn.wegfan.forum.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * HTML 处理工具类
 */
public class HtmlUtil {

    /**
     * 获取 HTML 文档去除标签后的内容，图片使用 {@code [图片]} 代替
     *
     * @param html HTML 文档
     *
     * @return HTML 文档去除标签后的内容
     */
    public static String getInnerTextWithImageLabel(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("img");
        for (Element element : elements) {
            element.text(" [图片] ");
        }
        return document.text();
    }

    /**
     * 获取HTML文档所有 {@code img} 标签的 {@code src}，即所有图片链接
     *
     * @param html HTML 文档
     *
     * @return 图片链接列表
     */
    public static List<String> getImageSourceList(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("img");
        return elements.eachAttr("src");
    }

}
