package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

/**
 * items tableへのDBアクセスを行うrepository.
 * 
 * @author shibatamasayuki
 *
 */
@Repository
public class ItemsRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * items tableへの追加を行う.
	 * 
	 * @param items items
	 */
	public void insert(Items items) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(items);
		String sql = "Insert into items(name, condition, category, brand, price, shipping, description) values(:name, :condition, :category, :brand, :price, :shipping, :description);";
		template.update(sql, param);

	}
}
