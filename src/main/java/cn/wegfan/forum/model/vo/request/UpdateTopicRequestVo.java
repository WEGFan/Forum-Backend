package cn.wegfan.forum.model.vo.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateTopicRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题帖编号
     */
    @NotNull
    private Long topicId;

    /**
     * 主题类型，0-普通主题，1-公告
     */
    @NotNull
    @Range(min = 0, max = 1)
    @JsonAlias("announcement")
    private Integer type;

    /**
     * 标题，最大30字符
     */
    @NotNull
    @Length(min = 1, max = 30)
    private String title;

    /**
     * 内容，最大150000字符
     */
    @NotNull
    @Length(min = 1, max = 150000)
    private String content;

    /**
     * 附件列表，最多10个附件
     */
    @NotNull
    @Size(max = 10)
    private List<AttachmentRequestVo> attachments;

}
