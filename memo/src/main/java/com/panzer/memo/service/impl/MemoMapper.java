package com.panzer.memo.service.impl;

import java.util.List;
import com.panzer.memo.service.MemoVO;

public interface MemoMapper {
	
	List<MemoVO> selectMemo();
	
    void insertMemo(MemoVO memo);
   

}
