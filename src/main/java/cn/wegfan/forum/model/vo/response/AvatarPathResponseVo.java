package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AvatarPathResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 头像地址
     */
    private String avatarPath;

    public AvatarPathResponseVo(String avatarPath) {
        this.avatarPath = avatarPath;
    }

}
