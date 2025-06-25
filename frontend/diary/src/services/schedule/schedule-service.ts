import { diaryApi } from '@/api-client';
import type { GetRecommendedScheduleResponse } from '@/generated/api-client';

/**
 * おすすめのスケジュールを取得します。
 * @returns おすすめのスケジュール
 */
export async function getRecommendedSchedule(): Promise<GetRecommendedScheduleResponse> {
  const response = await diaryApi.getRecommendedSchedule(1);
  return response.data;
}
