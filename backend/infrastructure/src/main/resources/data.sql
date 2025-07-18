INSERT INTO auth (id, password, user_role) VALUES
('user_1', '$2b$08$vcrnuJZ1xdy.zCWa8kcXv.jvSyVCDdylJF2A5BLN3HpdXBiHRCf2y', 'ROLE_USER'),
('user_2', '$2b$08$dgN5TSHmnaWS8Ryi/27c/uG8go3MqhBehyTDrjzgNyvoqNozaTDuu', 'ROLE_USER'),
('user_3', '$2b$08$IzY9cS7CM6A49Gp.LmFW.O8NEhu6QInWobd59EgNv5fPddlpgs3A2', 'ROLE_USER'),
('user_4', '$2b$08$ltt.j1Ow/jDqdKFuEPuUDuxnIIuBJsRI7wJW0M7faDCBcDf637fBy', 'ROLE_USER'),
('user_5', '$2b$08$0Pczwel.3NEaocUDfX1s9eUW35VxcDmbWsJaw2lAJm4EtikNnwdha', 'ROLE_USER'),
('user_6', '$2b$08$rPvEdkIktmtgabNTV7qcauOmINU0Q5GNWu1mnAc0MIKLH0dXQ0hYi', 'ROLE_USER'),
('user_7', '$2b$08$eFoosUpK/fQqrnH50pGMnek7Mh/057hYNitQoYEadFtOkEwgfjLUK', 'ROLE_USER'),
('user_8', '$2b$08$fcOHEQ4NCtTLlNPFFk50nOa5XB63OJ4ABBisFpWxfNEl4sQVCOS8.', 'ROLE_USER'),
('user_9', '$2b$08$S1cC2Mbd/jEJyZxqeWCzredJyV5I.lCHtmBHwUl6Lv2xfsT5WWl/.', 'ROLE_USER'),
('user_10', '$2b$08$OC.1NTm8Ynus9kyk0BgjL.REaa//x9ndvcn8bOO2TsehHq9KiR58q', 'ROLE_USER'),
('user_11', '$2b$08$VP8/u4ecGcguvmwSUHbfw.0NNyguQ/Zd2VSdsaFcnA89aqCi4R8jW', 'ROLE_USER'),
('user_12', '$2b$08$QzO0XQSjGQst8vyE4ngO5OmW0jpg1sLR2EjyHu1r6o9u8vCQad1QS', 'ROLE_USER'),
('user_13', '$2b$08$KPFyWGDTAYcKEkpk2VY5pOcLpU6HX7GoE.OFKK0/x.xJLR/S.s9Gq', 'ROLE_USER'),
('user_14', '$2b$08$LUAgl3JcGE9ZAg1J9te.puOjAurIAZn8prkPEI/I53j.AhhCCsY5W', 'ROLE_USER'),
('user_15', '$2b$08$t9m2Q7jbN128ryLLL13ajuxOpef8QR9.hm3lXSJtw9wSa4KlIJAH.', 'ROLE_USER'),
('user_16', '$2b$08$wzAmEruzC5fBcBAO00mdZOgqHHtfo5FXwkJ7WN2l3e/d2MXPXLoha', 'ROLE_USER'),
('user_17', '$2b$08$YcDuJuTK7dPFYvJos/hSWOrLx6.f85Apn0dC.z4mSUMPZpPg9HaYK', 'ROLE_USER'),
('user_18', '$2b$08$EAsFFDnVAXexGxoOB2j9BOA8XOH8y503gp14NIbWsGwXIv8.aMgLy', 'ROLE_USER'),
('user_19', '$2b$08$591Nz4QR2DXmdnRFnsait.l8dih29chKbEkkCmvgQk9hZ2LiHDe9m', 'ROLE_USER'),
('user_20', '$2b$08$zy.0BWv.y91KM8tELjPLTubMNUwEgPGYWtFT2.HAMo3e8C6z.Xkme', 'ROLE_USER');

INSERT INTO users (user_id, name) VALUES
('user_1', '山田 太郎'),
('user_2', '佐藤 花子'),
('user_3', '鈴木 一郎'),
('user_4', '高橋 二郎'),
('user_5', '田中 三郎'),
('user_6', '伊藤 四郎'),
('user_7', '渡辺 五郎'),
('user_8', '中村 さくら'),
('user_9', '小林 桃子'),
('user_10', '加藤 春菜'),
('user_11', '吉田 健一'),
('user_12', '山本 雄大'),
('user_13', '松本 直美'),
('user_14', '井上 美咲'),
('user_15', '林 涼介'),
('user_16', '清水 真由'),
('user_17', '斉藤 翔太'),
('user_18', '藤本 奈々'),
('user_19', '岡田 大輔'),
('user_20', '柴田 陽菜');

INSERT INTO diaries (user_id, title, content, created_date) VALUES
(7, '朝の散歩', '今日は近くの公園を散歩しました。空気が澄んでいて気持ちよかったです。', '2025-01-09'),
(13, '仕事の進捗', 'プロジェクトの第一フェーズが無事完了しました。次のステップに向けて準備中。', '2025-02-09'),
(8, '買い物リスト', '今日はスーパーでりんご、バナナ、オレンジを購入しました。', '2025-01-19'),
(16, 'ランチの感想', '新しいカフェで美味しいパスタを食べました。とても満足！', '2025-02-11'),
(15, 'トレーニング計画', '週3回の筋トレを計画中。継続は力なり！', '2025-01-14'),
(10, '映画レビュー', '最新のアクション映画を観ました。迫力満点で楽しめました！', '2025-02-15'),
(3, '週末の予定', '友人とハイキングに行く予定です。天気が良いといいな。', '2025-01-12'),
(19, '読書感想', '最近読んだ本の感想を書きました。とても面白かったです。', '2025-02-08'),
(2, '料理のレシピ', '新しいレシピでカレーを作りました。家族にも好評でした！', '2025-01-18'),
(18, '旅行計画', '次の旅行先を検討中。温泉に行きたいな。', '2025-02-20'),
(20, '健康管理', '毎日の歩数を記録しています。目標は1万歩！', '2025-01-25'),
(12, '読書メモ', '小説の最初の3章を読みました。とても面白い展開です。', '2025-03-01'),
(14, '旅行日記', '週末に美しい山道を訪れました。景色が最高でした！', '2025-02-16'),
(1, '料理メモ', '今日は自家製ピザを作ってみました。なかなかの出来栄え。', '2025-02-17'),
(11, 'テクノロジートレンド', '最新のAIと機械学習のトレンドについて調べました。', '2025-03-02'),
(4, '週末の計画', 'ハイキングとピクニックの計画を立てました。楽しみです！', '2025-03-05'),
(6, '朝ヨガ', '今日は朝ヨガを30分しました。心がスッキリしました。', '2025-01-10'),
(20, 'カフェ巡り', '新しくオープンしたカフェでコーヒーを楽しみました。', '2025-02-13'),
(9, '仕事の目標', '今月の仕事の目標を設定しました。達成できるよう頑張ります。', '2025-01-13'),
(5, '映画レビュー', '話題の映画を観ました。とても感動的なストーリーでした。', '2025-01-22'),
(2, 'ランニング', '今日は5kmランニングしました。良い汗をかきました。', '2025-02-05'),
(13, '新しい趣味', '水彩画を始めました。初めてだけど楽しいです。', '2025-02-10'),
(7, '読書記録', '最近読んだ本の感想をまとめました。', '2025-03-02'),
(17, 'ガーデニング', '庭の花が綺麗に咲きました。自然に癒されます。', '2025-03-03'),
(11, 'DIYプロジェクト', '自作の本棚を作りました。満足の仕上がりです。', '2025-01-15'),
(14, '音楽鑑賞', '最近のお気に入りの曲リストを作成しました。', '2025-01-22'),
(19, '夕方の散歩', '今日は夕方に川沿いを散歩しました。風が心地よかったです。', '2025-02-05'),
(13, '新しいプロジェクト', '次のプロジェクトの計画を立て始めました。ワクワクします。', '2025-01-20'),
(18, '週末の買い物', 'パンとチーズとワインを買いました。週末が楽しみです。', '2025-02-20'),
(9, 'ディナーの感想', '今日はイタリアンレストランでピザを食べました。美味しかった！', '2025-03-01'),
(1, 'ジョギング計画', '週4回のジョギングを始めます。健康第一！', '2025-01-14'),
(5, '読書ノート', '新しいビジネス書を読み始めました。学びが多いです。', '2025-02-18'),
(4, '温泉旅行', '週末に温泉に行きました。最高にリラックスできました。', '2025-03-01'),
(6, 'お菓子作り', '今日はチョコレートケーキを作ってみました。家族にも好評！', '2025-02-10'),
(13, '最新ガジェット', '新しいスマートウォッチを試しました。便利です！', '2025-02-06'),
(15, 'ハイキング記録', '山頂からの景色が素晴らしかったです。また行きたい！', '2025-03-02'),
(4, '夜ヨガ', '今日は寝る前にヨガをしました。リラックスできました。', '2025-02-14'),
(11, '新しいカフェ', '最近見つけたカフェで美味しいラテを飲みました。', '2025-02-20'),
(7, '新しいチャレンジ', '仕事で新しいスキルを学び始めました。成長を実感！', '2025-03-04'),
(17, 'ドラマレビュー', '話題のドラマを観ました。次の展開が気になります！', '2025-02-16'),
(9, 'サイクリング', '今日は10kmサイクリングしました。気持ちよかった！', '2025-03-01'),
(1, '写真撮影', 'カメラを持って公園に行きました。良い写真が撮れました。', '2025-01-15'),
(18, '新しい本', '最近購入した小説を読み始めました。夢中になっています。', '2025-02-06'),
(11, '家庭菜園', 'トマトの苗を植えました。成長が楽しみです。', '2025-03-01'),
(3, '手作り家具', '新しいテーブルをDIYしました。満足の出来栄え！', '2025-02-11'),
(19, 'ライブ鑑賞', '好きなバンドのライブを観に行きました。最高の時間でした！', '2025-01-20'),
(1, '映画の感想', '最近観た映画について感想をまとめました。ストーリーに引き込まれました。', '2025-02-05'),
(9, '趣味の時間', '休日はガーデニングを楽しんでいます。新しい植物を育て始めました。', '2025-02-06'),
(20, '料理レシピ', '新しいレシピでディナーを作りました。家族にも好評でした。', '2025-02-14'),
(11, 'ジムの記録', '今日はジムで筋トレしました。身体が軽く感じます。', '2025-02-10'),
(18, '新しい書籍', 'ビジネス書を1冊読んだので、感想を書きました。', '2025-02-09'),
(14, 'リラックス方法', '瞑想を始めました。静かな時間がとても心地よいです。', '2025-01-13'),
(3, '写真撮影', '新しいカメラで写真を撮ってみました。初めての撮影が楽しかった！', '2025-02-09'),
(16, 'ガジェットレビュー', '最近購入したイヤホンをレビューしました。音質が素晴らしい！', '2025-03-01'),
(4, 'モーニングラン', '朝ランニングしてきました。天気が良く、気持ちよかったです。', '2025-02-13'),
(6, 'お散歩', '今日は公園で散歩をしました。自然に癒されました。', '2025-03-04'),
(8, '美味しいランチ', '新しいレストランでランチを楽しみました。とても美味しかったです。', '2025-01-20'),
(9, '新しい趣味', '最近始めた写真撮影が楽しいです。色々な角度で撮ってみました。', '2025-03-02'),
(10, '新しいレシピ', '今日は新しいレシピで夕食を作りました。家族に好評でした。', '2025-03-03'),
(12, 'プログラミング学習', '今日はプログラミングの勉強をしました。新しいスキルを習得中です。', '2025-02-15'),
(15, '映画鑑賞', '話題の映画を観に行きました。感動的なストーリーでした。', '2025-01-18'),
(17, '美術館訪問', '今日は美術館に行きました。美しい絵画に癒されました。', '2025-02-22'),
(19, 'カフェで読書', 'カフェで読書をしました。静かな時間が心地よかったです。', '2025-03-04'),
(2, 'サイクリング', '今日はサイクリングをしました。気持ちの良い風を感じました。', '2025-02-08'),
(4, '料理教室', '料理教室に参加しました。新しい料理のレパートリーが増えました。', '2025-01-25'),
(6, 'ガーデニング', '庭の手入れをしました。花が綺麗に咲いて嬉しいです。', '2025-03-01'),
(8, 'ジムでトレーニング', '今日はジムでトレーニングをしました。良い汗をかきました。', '2025-02-12'),
(10, '手芸', '手芸に挑戦しました。初めての作品が完成して嬉しいです。', '2025-01-28'),
(12, '公園でピクニック', '公園でピクニックをしました。楽しい時間を過ごしました。', '2025-03-05'),
(14, '温泉旅行', '温泉旅行に行きました。日頃の疲れを癒すことができました。', '2025-02-19'),
(16, '読書会', '読書会に参加しました。色々な意見交換ができて楽しかったです。', '2025-01-21'),
(18, '映画鑑賞', '今日は家で映画鑑賞をしました。感動して涙が出ました。', '2025-02-25'),
(20, 'ヨガ', 'ヨガで心身をリフレッシュしました。明日も頑張れそうです。', '2025-03-02'),
(1, 'ランニング', '今日は10kmランニングしました。自己ベスト更新です！', '2025-02-09'),
(3, '新しいカフェ', '新しいカフェを見つけました。落ち着いた雰囲気で気に入りました。', '2025-01-26'),
(5, '美術館', '美術館に行き、美しい絵画を鑑賞しました。心が洗われました。', '2025-03-03'),
(7, '料理', '今日は家族に手料理を振る舞いました。喜んでくれて嬉しかったです。', '2025-02-13'),
(9, 'プログラミング', 'プログラミングの勉強をしました。新しい知識が増えて楽しいです。', '2025-01-29'),
(11, '映画', '話題の映画を観ました。予想外の展開に驚きました。', '2025-03-06'),
(13, '旅行', '週末は旅行に行きました。美味しいものをたくさん食べました。', '2025-02-20'),
(15, '読書', '読書に没頭しました。時間を忘れて読みふけってしまいました。', '2025-01-22'),
(17, '散歩', '近所の公園を散歩しました。綺麗な花が咲いていました。', '2025-02-26'),
(19, 'ヨガ', 'ヨガで体を動かしました。心も体もリフレッシュできました。', '2025-03-03'),
(2, '料理', '新しいレシピに挑戦しました。美味しくできて満足です。', '2025-02-10'),
(4, 'プログラミング', 'プログラミングの課題に取り組みました。難しいけど楽しいです。', '2025-01-27'),
(6, '映画', '映画館で映画を観ました。迫力のある映像に感動しました。', '2025-03-04'),
(8, '旅行', '旅行の計画を立てました。今から楽しみで待ちきれません。', '2025-02-21'),
(10, '読書', '読書をして知識を深めました。新しい発見がありました。', '2025-01-23'),
(12, '散歩', '海辺を散歩しました。夕日が綺麗で癒されました。', '2025-02-27'),
(14, 'ヨガ', 'ヨガで体の柔軟性を高めました。体が軽くなった気がします。', '2025-03-05'),
(16, '料理', '得意料理を作りました。家族みんなが喜んでくれました。', '2025-02-11'),
(18, 'プログラミング', 'プログラミングのスキルアップを目指して勉強しました。', '2025-01-28'),
(20, '映画', '友達と映画を観に行きました。笑いあり涙ありで楽しかったです。', '2025-03-06'),
(1, '旅行', '旅行の準備をしました。持ち物を確認して準備万端です。', '2025-02-22'),
(3, '読書', '読書感想文を書きました。自分の考えをまとめる良い機会になりました。', '2025-01-24'),
(5, '散歩', '森林公園を散歩しました。新鮮な空気を吸ってリフレッシュしました。', '2025-02-28'),
(7, 'ヨガ', 'ヨガで瞑想をしました。心が落ち着いて穏やかな気持ちになりました。', '2025-03-07'),
(9, '料理', '新しい食材を使って料理に挑戦しました。美味しくできて満足です。', '2025-02-12'),
(11, 'プログラミング', 'プログラミングのコードレビューをしました。改善点を見つけることができました。', '2025-01-29'),
(13, '映画', '映画の予告編を観ました。面白そうで公開が待ち遠しいです。', '2025-03-08'),
(15, '旅行', '旅行の写真を見返しました。楽しい思い出が蘇ってきました。', '2025-02-23'),
(17, '読書', '読書中に面白いフレーズを見つけました。メモしておこうと思います。', '2025-01-25'),
(19, '散歩', '夜景を見に散歩に行きました。都会の夜景は綺麗で感動しました。', '2025-03-09'),
(2, 'ヨガ', 'ヨガのポーズを練習しました。難しいポーズができるようになりました。', '2025-02-13'),
(4, '料理', '料理の盛り付けにこだわってみました。見た目も味も最高でした。', '2025-01-30'),
(6, 'プログラミング', 'プログラミングのバグ修正をしました。原因がわかってスッキリしました。', '2025-03-10'),
(8, '映画', '映画のサントラを聴きました。映画のシーンが思い出されて感動しました。', '2025-02-24');