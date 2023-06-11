package dao;

import dto.PostRecommendBean;

public interface PostRecommendDAO extends DAO<PostRecommendBean> {
	PostRecommendBean select(String ip, int post_id);
}
