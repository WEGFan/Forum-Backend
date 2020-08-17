package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.LinkMapper;
import cn.wegfan.forum.model.entity.Link;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "link")
@Transactional(rollbackFor = Exception.class)
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    @Cacheable(key = "'all'")
    public List<Link> listNotDeletedLinks() {
        return linkMapper.selectNotDeletedList();
    }

    @Override
    public Link getNotDeletedLinkByLinkId(Long linkId) {
        return linkMapper.selectNotDeletedByLinkId(linkId);
    }

    @Override
    @CacheEvict(key = "'all'")
    public int addLink(Link link) {
        Date now = new Date();
        link.setCreateTime(now);
        link.setUpdateTime(now);
        if (StringUtils.isBlank(link.getIconUrl())) {
            link.setIconUrl(null);
        }
        int result = linkMapper.insert(link);
        return result;
    }

    @Override
    @CacheEvict(key = "'all'")
    public int updateLink(Link link) {
        Date now = new Date();
        link.setUpdateTime(now);
        if (StringUtils.isBlank(link.getIconUrl())) {
            link.setIconUrl(null);
        }
        int result = linkMapper.updateById(link);
        return result;
    }

    @Override
    @CacheEvict(key = "'all'")
    public int deleteLinkByLinkId(Long linkId) {
        int result = linkMapper.deleteByLinkId(linkId);
        return result;
    }

}
