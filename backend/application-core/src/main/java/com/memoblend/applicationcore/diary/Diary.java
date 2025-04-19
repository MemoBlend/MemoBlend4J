package com.memoblend.applicationcore.diary;

import java.time.LocalDate;
import org.springframework.lang.NonNull;
import com.memoblend.applicationcore.diary.valueobject.Content;
import com.memoblend.applicationcore.diary.valueobject.CreatedDate;
import com.memoblend.applicationcore.diary.valueobject.Title;

/**
 * 日記のエンティティを表すクラスです。
 */
public class Diary {

  @NonNull
  private long id;
  @NonNull
  private long userId;
  @NonNull
  private Title title;
  @NonNull
  private Content content;
  @NonNull
  private CreatedDate createdDate;
  @NonNull
  private boolean isDeleted;

  /**
   * {@link Diary} クラスの新しいインスタンスを初期化します。
   * 
   * @param id          ID。
   * @param userId      ユーザー ID。
   * @param title       タイトル。
   * @param content     コンテンツ。
   * @param createdDate 作成日時。
   * @param isDeleted   削除フラグ。
   * @throws DiaryValidationException 日記が不正な場合。
   */
  public Diary(long id, long userId, String title, String content, LocalDate createdDate, boolean isDeleted)
      throws DiaryValidationException {
    this.id = id;
    this.userId = userId;
    this.title = new Title(title);
    this.content = new Content(content);
    this.createdDate = new CreatedDate(createdDate);
    this.isDeleted = isDeleted;
  }

  /**
   * ID を取得します。
   * 
   * @return ID。
   */
  public long getId() {
    return this.id;
  }

  /**
   * ユーザー ID を取得します。
   * 
   * @return ユーザー ID。
   */
  public long getUserId() {
    return this.userId;
  }

  /**
   * タイトルを取得します。
   * 
   * @return タイトル。
   */
  public String getTitle() {
    return this.title.getValue();
  }

  /**
   * コンテンツを取得します。
   * 
   * @return コンテンツ。
   */
  public String getContent() {
    return this.content.getValue();
  }

  /**
   * 作成日時を取得します。
   * 
   * @return 作成日時。
   */
  public LocalDate getCreatedDate() {
    return this.createdDate.getValue();
  }

  /**
   * 削除フラグを取得します。
   * 
   * @return 削除フラグ。
   */
  public boolean getIsDeleted() {
    return this.isDeleted;
  }

  /**
   * ID を設定します。
   * 
   * @param id ID。
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * ユーザー ID を設定します。
   * 
   * @param userId ユーザー ID。
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }

  /**
   * タイトルを設定します。
   * 
   * @param title タイトル。
   * @throws DiaryValidationException タイトルが不正な場合。
   */
  public void setTitle(String title) throws DiaryValidationException {
    this.title = new Title(title);
  }

  /**
   * コンテンツを設定します。
   * 
   * @param content コンテンツ。
   * @throws DiaryValidationException コンテンツが不正な場合。
   */
  public void setContent(String content) throws DiaryValidationException {
    this.content = new Content(content);
  }

  /**
   * 作成日時を設定します。
   * 
   * @param createdDate 作成日時。
   * @throws DiaryValidationException 作成日時が不正な場合。
   */
  public void setCreatedDate(LocalDate createdDate) throws DiaryValidationException {
    this.createdDate = new CreatedDate(createdDate);
  }

  /**
   * 削除フラグを設定します。
   * 
   * @param isDeleted 削除フラグ。
   */
  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}
