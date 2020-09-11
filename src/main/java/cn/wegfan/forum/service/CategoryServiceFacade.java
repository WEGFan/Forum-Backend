package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.CategoryListSortEnum;
import cn.wegfan.forum.model.entity.Category;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.CategoryRequestVo;
import cn.wegfan.forum.model.vo.response.CategoryResponseVo;
import cn.wegfan.forum.model.vo.response.IdNameResponseVo;
import cn.wegfan.forum.model.vo.response.PageResultVo;
import cn.wegfan.forum.util.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private CategoryAdminService categoryAdminService;

    public PageResultVo<CategoryResponseVo> getAdminCategoryList(CategoryListSortEnum sortEnum, long pageIndex, long pageSize) {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Category> pageResult = categoryService.listNotDeletedAllAdminCategoriesByPageAndUserId(page, userId, sortEnum);

        List<CategoryResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), CategoryResponseVo.class);
        responseVoList.forEach(item -> {
            Long categoryId = item.getId();
            List<User> categoryAdminList = userService.listNotDeletedCategoryAdminsByCategoryId(categoryId);
            item.setCategoryAdmin(mapperFacade.mapAsList(categoryAdminList, IdNameResponseVo.class));
        });

        return new PageResultVo<>(responseVoList, pageResult);
    }

    public List<IdNameResponseVo> getAdminCategoryNameList() {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();

        List<Category> categoryList = categoryService.listNotDeletedAllAdminCategoriesByUserId(userId, CategoryListSortEnum.ORDER);
        List<IdNameResponseVo> responseVoList = mapperFacade.mapAsList(categoryList, IdNameResponseVo.class);
        return responseVoList;
    }

    public int addCategory(CategoryRequestVo requestVo) {
        Category sameNameCategory = categoryService.getNotDeletedCategoryByName(requestVo.getName());
        if (sameNameCategory != null) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_CATEGORY_NAME);
        }
        Category category = mapperFacade.map(requestVo, Category.class);
        return categoryService.addCategory(category);
    }

    public int updateCategory(CategoryRequestVo requestVo) {
        Category category = categoryService.getNotDeletedCategoryByCategoryId(requestVo.getId());
        if (category == null) {
            throw new BusinessException(BusinessErrorEnum.CATEGORY_NOT_FOUND);
        }
        Category sameNameCategory = categoryService.getNotDeletedCategoryByName(requestVo.getName());
        if (sameNameCategory != null && !sameNameCategory.getId().equals(category.getId())) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_CATEGORY_NAME);
        }

        mapperFacade.map(requestVo, category);
        return categoryService.updateCategory(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryService.getNotDeletedCategoryByCategoryId(categoryId);
        if (category == null) {
            throw new BusinessException(BusinessErrorEnum.CATEGORY_NOT_FOUND);
        }
        // 删除分区
        categoryService.deleteCategoryByCategoryId(categoryId);
        // 删除分区的分区版主
        categoryAdminService.deleteCategoryAdminByCategoryId(categoryId);
        // 删除分区的板块
        boardService.deleteBoardByCategoryId(categoryId);
        // 删除分区的主题
        topicService.batchCascadeDeleteTopic(null, categoryId, null);
        // 删除分区的回复帖
        replyService.batchCascadeDeleteReply(null, null, categoryId, null);
        // 删除分区的附件
        attachmentService.deleteAttachmentByCategoryId(categoryId);
    }

}
