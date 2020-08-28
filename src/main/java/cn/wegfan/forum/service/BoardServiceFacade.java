package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BoardListSortEnum;
import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.Board;
import cn.wegfan.forum.model.entity.Category;
import cn.wegfan.forum.model.entity.Permission;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.AddBoardRequestVo;
import cn.wegfan.forum.model.vo.request.UpdateBoardRequestVo;
import cn.wegfan.forum.model.vo.response.BoardResponseVo;
import cn.wegfan.forum.model.vo.response.IdNameResponseVo;
import cn.wegfan.forum.model.vo.response.PageResultVo;
import cn.wegfan.forum.model.vo.response.PermissionResponseVo;
import cn.wegfan.forum.util.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardAdminService boardAdminService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public PageResultVo<BoardResponseVo> getAdminBoardList(BoardListSortEnum sortEnum, long pageIndex, long pageSize) {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Board> pageResult = boardService.listNotDeletedAdminBoardsWithBoardCategoryAdminByPageAndUserId(page, userId, sortEnum);

        List<BoardResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), BoardResponseVo.class);
        responseVoList.forEach(item -> {
            Long boardId = item.getId();
            List<User> boardAdminList = userService.listNotDeletedBoardAdminsByBoardId(boardId);
            item.setBoardAdmin(mapperFacade.mapAsList(boardAdminList, IdNameResponseVo.class));

            Category category = categoryService.getNotDeletedCategoryByCategoryId(item.getCategoryId());
            item.setCategoryName(category.getName());

            Permission boardPermission = Optional.ofNullable(permissionService.getBoardPermissionByBoardId(boardId))
                    .orElse(Permission.getDefaultPermission());
            item.setBoardPermission(mapperFacade.map(boardPermission, PermissionResponseVo.class));
        });

        return new PageResultVo<>(responseVoList, pageResult);
    }

    public int addBoard(AddBoardRequestVo requestVo) {
        Board sameNameBoard = boardService.getNotDeletedBoardByNameAndCategoryId(requestVo.getName(), requestVo.getCategoryId());
        if (sameNameBoard != null) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_BOARD_NAME);
        }

        Board board = mapperFacade.map(requestVo, Board.class);
        Permission boardPermission = mapperFacade.map(requestVo.getBoardPermission(), Permission.class);

        int result = boardService.addBoard(board);
        boardPermission.setBoardId(board.getId());
        permissionService.addOrUpdateBoardPermission(boardPermission);
        return result;
    }

    public int updateBoard(UpdateBoardRequestVo requestVo) {
        Board board = boardService.getNotDeletedBoardByBoardId(requestVo.getId());
        if (board == null) {
            throw new BusinessException(BusinessErrorEnum.BOARD_NOT_FOUND);
        }
        Board sameNameBoard = boardService.getNotDeletedBoardByNameAndCategoryId(requestVo.getName(), board.getCategoryId());
        if (sameNameBoard != null && !sameNameBoard.getId().equals(board.getId())) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_BOARD_NAME);
        }

        mapperFacade.map(requestVo, board);
        Permission boardPermission = mapperFacade.map(requestVo.getBoardPermission(), Permission.class);

        int result = boardService.updateBoard(board);
        boardPermission.setBoardId(requestVo.getId());
        permissionService.addOrUpdateBoardPermission(boardPermission);
        return result;
    }

    public int deleteBoard(Long boardId) {
        // TODO: 删除板块内的帖子、回复、版主
        Board board = boardService.getNotDeletedBoardByBoardId(boardId);
        if (board == null) {
            throw new BusinessException(BusinessErrorEnum.BOARD_NOT_FOUND);
        }
        int result = boardService.deleteBoardByBoardId(boardId);
        boardAdminService.deleteBoardAdminByBoardId(boardId);
        return result;
    }

}
