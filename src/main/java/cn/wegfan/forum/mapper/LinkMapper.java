package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkMapper extends BaseMapper<Link> {

    List<Link> selectNotDeletedList();

    Link selectNotDeletedByLinkId(Long linkId);

    int deleteByLinkId(Long linkId);

}
