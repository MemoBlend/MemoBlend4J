import axios from 'axios';
import type { InternalAxiosRequestConfig } from 'axios';
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

// リクエストインターセプターを追加してJWTトークンを自動的に付与
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = sessionStorage.getItem('jwt_token');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// レスポンスインターセプターを追加して401エラーを処理
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      // トークンが無効な場合はセッションをクリア
      sessionStorage.removeItem('jwt_token');
      sessionStorage.removeItem('username');
      sessionStorage.removeItem('name');
      sessionStorage.removeItem('isAuthenticated');

      // ログインページにリダイレクト（必要に応じて）
      if (window.location.pathname !== '/login') {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

/**
 * 認証の API クライアントです。
 */
const authControllerApi = new apiClient.AuthControllerApi(createConfig(), '', axiosInstance);

/**
 * 日記の API クライアントです。
 */
const diaryApi = new apiClient.DiaryApi(createConfig(), '', axiosInstance);

/**
 * ユーザーの API クライアントです。
 */
const userApi = new apiClient.UserApi(createConfig(), '', axiosInstance);

export { authControllerApi, diaryApi, userApi };
