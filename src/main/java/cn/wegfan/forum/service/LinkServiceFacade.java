package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.Link;
import cn.wegfan.forum.model.vo.request.LinkRequestVo;
import cn.wegfan.forum.model.vo.response.LinkResponseVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LinkServiceFacade {

    @Autowired
    private LinkService linkService;

    @Autowired
    private MapperFacade mapperFacade;

    public List<LinkResponseVo> getLinkList() {
        List<Link> linkList = linkService.selectNotDeletedList();
        List<LinkResponseVo> responseVoList = mapperFacade.mapAsList(linkList, LinkResponseVo.class);
        if (!SecurityUtils.getSubject().isPermitted("admin")) {
            responseVoList.forEach(element -> {
                element.setCreateTime(null);
                element.setUpdateTime(null);
            });
        }
        return responseVoList;
    }

    public int addLink(LinkRequestVo requestVo) {
        Link link = mapperFacade.map(requestVo, Link.class);
        return linkService.addLink(link);
    }

    public int updateLink(LinkRequestVo requestVo) {
        Link link = linkService.selectNotDeletedByLinkId(requestVo.getId());
        if (link == null) {
            throw new BusinessException(BusinessErrorEnum.LINK_NOT_FOUND);
        }
        mapperFacade.map(requestVo, Link.class);
        return linkService.updateLink(link);
    }

    public int deleteLink(Long linkId) {
        Link link = linkService.selectNotDeletedByLinkId(linkId);
        if (link == null) {
            throw new BusinessException(BusinessErrorEnum.LINK_NOT_FOUND);
        }
        return linkService.deleteLink(linkId);
    }

}
