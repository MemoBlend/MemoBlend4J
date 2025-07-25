package com.memoblend.infrastructure.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryRepository;
import com.memoblend.infrastructure.mybatis.generated.entity.DiaryEntity;
import com.memoblend.infrastructure.mybatis.generated.mapper.DiaryMapper;
import com.memoblend.infrastructure.translator.EntityTranslator;

import lombok.AllArgsConstructor;

/**
 * 日記のリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository {

  private final DiaryMapper diaryMapper;

  @Override
  public List<Diary> findByYearAndMonth(int year, int month) {
    return diaryMapper.findByYearAndMonth(year, month);
  }

  @Override
  public List<Diary> findByUserId(long userId) {
    return diaryMapper.findByUserId(userId);
  }

  @Override
  public Diary findById(long id) {
    return diaryMapper.findById(id);
  }

  @Override
  public Diary add(Diary diary) {
    DiaryEntity entity = EntityTranslator.DiaryToDiaryEntity(diary);
    diaryMapper.insert(entity);
    return diary;
  }

  @Override
  public long delete(long id) {
    return diaryMapper.delete(id);
  }

  @Override
  public long update(Diary diary) {
    return diaryMapper.update(diary);
  }
}
