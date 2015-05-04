package com.panzer.memo.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panzer.memo.service.MemoService;
import com.panzer.memo.service.MemoVO;

@Service
public class MemoServiceImpl implements MemoService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemoServiceImpl.class);
	
	@Autowired
    private SqlSession sqlSession;

	@Override
	public void saveMemo(MemoVO memo) {
		// TODO Auto-generated method stub
		logger.info("==> saveMemo");
		sqlSession.getMapper(MemoMapper.class).insertMemo(memo);
	
	}

	@Override
	public List<MemoVO> getMemo() {
		// TODO Auto-generated method stub
		logger.info("<== getMemo");
		return sqlSession.getMapper(MemoMapper.class).selectMemo();
	}

}
