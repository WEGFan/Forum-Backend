package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名 最大30字符
     */
    private String username;

    /**
     * 昵称 最大30字符
     */
    private String nickname;

    /**
     * 是否已被删除
     */
    private Boolean deleted;

}
