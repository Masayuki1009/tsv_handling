package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Original;

@Repository
public class OriginalRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(Original original) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(original);
		String sql = "INSERT INTO original(id, name, condition_id, category_name, brand, price, shipping, description) values(:id, :name, :conditionId, :categoryName, :brand, :price, :shipping, :description);";
		template.update(sql, param);

	}
}
