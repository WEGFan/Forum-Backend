package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageUrlResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片地址
     */
    private String imageUrl;

    public ImageUrlResponseVo(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
