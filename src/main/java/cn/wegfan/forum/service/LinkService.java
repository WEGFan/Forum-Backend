package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LinkService extends IService<Link> {

    List<Link> listNotDeletedLinks();

    Link getNotDeletedLinkByLinkId(Long linkId);

    int addLink(Link link);

    int updateLink(Link link);

    int deleteLinkByLinkId(Long linkId);

}
