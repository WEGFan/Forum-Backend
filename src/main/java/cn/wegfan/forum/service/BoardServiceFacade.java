package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.Board;
import cn.wegfan.forum.model.entity.Permission;
import cn.wegfan.forum.model.vo.request.AddBoardRequestVo;
import cn.wegfan.forum.model.vo.request.UpdateBoardRequestVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
