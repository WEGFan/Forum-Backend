package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LinkService extends IService<Link> {

    List<Link> selectNotDeletedList();

    Link selectNotDeletedByLinkId(Long linkId);

    int addLink(Link link);

    int updateLink(Link link);

    int deleteLink(Long linkId);

}
