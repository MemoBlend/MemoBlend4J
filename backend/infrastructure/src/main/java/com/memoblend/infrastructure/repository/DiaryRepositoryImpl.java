package com.memoblend.infrastructure.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryRepository;
import com.memoblend.infrastructure.mybatis.generated.entity.DiaryEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.DiaryEntityExample;
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
    LocalDate startOfMonth = LocalDate.of(year, month, 1);
    LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

    Date startDate = Timestamp.valueOf(startOfMonth.atStartOfDay());
    Date endDate = Timestamp.valueOf(endOfMonth.atTime(LocalTime.MAX));

    DiaryEntityExample example = new DiaryEntityExample();
    example.createCriteria()
        .andCreatedDateGreaterThanOrEqualTo(startDate)
        .andCreatedDateLessThanOrEqualTo(endDate);
    example.setOrderByClause("created_date DESC");

    List<DiaryEntity> entities = diaryMapper.selectByExample(example);
    List<Diary> diaries = entities.stream()
        .map(EntityTranslator::diaryEntityToDiary)
        .toList();
    return diaries;
  }

  @Override
  public List<Diary> findByUserId(long userId) {
    DiaryEntityExample example = new DiaryEntityExample();
    example.createCriteria().andUserIdEqualTo(userId);
    example.setOrderByClause("created_date DESC");
    List<DiaryEntity> entities = diaryMapper.selectByExample(example);
    List<Diary> diaries = entities.stream()
        .map(EntityTranslator::diaryEntityToDiary)
        .toList();
    return diaries;
  }

  @Override
  public Diary findById(long id) {
    DiaryEntity entity = diaryMapper.selectByPrimaryKey(id);
    return EntityTranslator.diaryEntityToDiary(entity);
  }

  @Override
  public Diary add(Diary diary) {
    DiaryEntity entity = EntityTranslator.diaryToDiaryEntity(diary);
    diaryMapper.insert(entity);
    diary.setId(entity.getId());
    return diary;
  }

  @Override
  public long delete(long id) {
    return diaryMapper.deleteByPrimaryKey(id);
  }

  @Override
  public long update(Diary diary) {
    DiaryEntity entity = EntityTranslator.diaryToDiaryEntity(diary);
    return diaryMapper.updateByPrimaryKeySelective(entity);
  }
}
