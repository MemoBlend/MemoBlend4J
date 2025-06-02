import { diaryApi } from '@/api-client';
import type {
  GetDiariesResponse,
  PostDiaryRequest,
  PutDiaryRequest,
} from '@/generated/api-client';

/**
 * 日記の一覧を取得します。
 * @returns　日記の一覧
 */
export async function getDiaries(): Promise<GetDiariesResponse> {
  const response = await diaryApi.getDiaries();
  return response.data;
}

/**
 * ID に対応する日記を取得します。
 * @param id 取得したい日記の ID 。
 * @returns　ID に対応する日記。
 */
export async function getDiary(id: number) {
  const response = await diaryApi.getDiary(id);
  return response.data;
}

/**
 * 日記を更新します。
 * @param diary 日記の更新リクエスト。
 */
export async function updateDiary(diary: PutDiaryRequest) {
  await diaryApi.putDiary(diary);
}

/**
 * 日記を削除します。
 * @param id 削除したい日記の ID 。
 */
export async function deleteDiary(id: number) {
  await diaryApi.deleteDiary(id);
}

/**
 * 日記を作成します。
 * @param diary 日記の作成リクエスト。
 */
export async function createDiary(diary: PostDiaryRequest) {
  await diaryApi.postDiary(diary);
}
