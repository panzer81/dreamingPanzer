package com.panzer.memo.service;

import java.util.List;

public interface MemoService {
	
	void saveMemo(MemoVO memo);
	List<MemoVO> getMemo();
	

}
