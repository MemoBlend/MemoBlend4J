import { defineStore } from 'pinia';

/**
 * 日記のストアです。
 */
export const useDiaryStore = defineStore('diary', {
  state: () => ({
    diaryYear: new Date().getFullYear(), // 現在の年を取得
    diaryMonth: new Date().getMonth() + 1, // 現在の月を1から12の範囲で取得
  }),
  actions: {
    /**
     * 日記の年を設定します。
     * @param year 設定する年。
     */
    setDiaryYear(year: number) {
      this.diaryYear = year;
    },
    /**
     * 日記の月を設定します。
     * @param month 設定する月（1から12の範囲）。
     */
    setDiaryMonth(month: number) {
      this.diaryMonth = month;
    },
  },
  getters: {
    /**
     * 日記の年を取得します。
     * @param state 状態。
     * @returns 日記の年。
     */
    getDiaryYear(state) {
      return state.diaryYear;
    },
    /**
     * 日記の月を取得します。
     * @param state 状態。
     * @returns 日記の月（1から12の範囲）。
     */
    getDiaryMonth(state) {
      return state.diaryMonth;
    },
  },
});
