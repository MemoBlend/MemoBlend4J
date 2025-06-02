import axios from 'axios';
import * as apiClient from '@/generated/api-client';

/** api-client の共通の Configuration があればここに定義します。 */
function createConfig(): apiClient.Configuration {
  const config = new apiClient.Configuration();
  return config;
}

/** axios の共通の設定があればここに定義します。 */
const axiosInstance = axios.create({
  baseURL:
    import.meta.env.VITE_AXIOS_BASE_ENDPOINT_ORIGIN || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

/**
 * 日記の API クライアントです。
 */
const diaryApi = new apiClient.DiaryApi(createConfig(), '', axiosInstance);

/**
 * ユーザーの API クライアントです。
 */
const userApi = new apiClient.UserApi(createConfig(), '', axiosInstance);

export { diaryApi, userApi };
