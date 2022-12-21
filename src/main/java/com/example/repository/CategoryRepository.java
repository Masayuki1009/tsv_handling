package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * Categoryテーブルに追加するRepository.
 * 
 * @author shibatamasayuki
 *
 * 
 */
@Repository
public class CategoryRepository {

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 
	 * category tableへの追加を行う.
	 * @param category category
	 */
	public void insert(Category category) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(category);
		String sql = "Insert into category(parent, name, name_all) values(:parent, :name, :nameAll);";
		template.update(sql, param);

	}

	/**
	 * 引数の大カテゴリー名と一致する大カテゴリーのidを取得する.
	 * 
	 * @param bigCategory 大カテゴリー名
	 * @return 大カテゴリーのid
	 */
	public Integer findBigCategoryIdByName(String bigCategory) {
		String sql = "SELECT id FROM category WHERE name = :name AND parent IS NULL AND name_all IS NULL";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", bigCategory);
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return category.getId();
	}

	/**
	 * 引数の中カテゴリー名と大カテゴリーのidを持つcategory(＝ 一意の、中カテゴリー)のidを取得する.
	 * 
	 * @param mediumCategory 中カテゴリー名
	 * @param bigCategoryId 大カテゴリーのid
	 * @return 中カテゴリーのid
	 */
	public Integer findMediumCategoryIdByName(String mediumCategory, Integer bigCategoryId) {
		String sql = "SELECT id, parent, name, name_all FROM category WHERE name = :name AND parent = :parent AND name_all IS NULL";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", mediumCategory).addValue("parent", bigCategoryId);
		Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
		return category.getId();
	}
}
