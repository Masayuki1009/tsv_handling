package com.example.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Original;
import com.example.repository.OriginalRepository;

/**
 * @author shibatamasayuki
 * tsvファイルを読み込み、Original Tableへの追加を行うController.
 *
 */
@Controller
@RequestMapping("/original")
public class InsertController {
	@Autowired
	private OriginalRepository repository;

	@Autowired
	private InsertCategoryController insertCategoryController;

	/**
	 * @return 完了画面
	 */
	@GetMapping("")
	public String insert() {
		// tsvファイルのpathを指定
		String filePath = "/Users/shibatamasayuki/業務/大量データ課題/train.tsv";

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;
			Original original = new Original();
			
			// tsvファイル1行目(カラム名が記載されている部分)は不必要なため、先に読み込んでおく
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				//　/t(タブがある箇所)ごとにデータを区切り、tsvファイルの各データを個別で配列に入れる
				String[] data = line.split("\t", 0);

				String id = data[0];
				String name = data[1];
				String conditionId = data[2];
				String categoryName = data[3];
				String brand = data[4];
				String price = data[5];
				String shipping = data[6];
				
				//descriptionが記載なしの場合エラーが発生するのでtry catchで対策
				try {
					String description = data[7];
					original.setDescription(description);
				} catch (ArrayIndexOutOfBoundsException e) {
					String description = "";
					original.setDescription(description);
				}

				int j = 1;

				while (j <= 7) {

					if (j == 1) {
						original.setId(Integer.parseInt(id));
					} else if (j == 2) {
						original.setName(name);
					} else if (j == 3) {
						original.setConditionId(Integer.parseInt(conditionId));
					} else if (j == 4) {
						original.setCategoryName(categoryName);
					} else if (j == 5) {
						original.setBrand(brand);
					} else if (j == 6) {
						original.setPrice(Double.parseDouble(price));
					} else if (j == 7) {
						original.setShipping(Integer.parseInt(shipping));
					}
					j++;
				}
				repository.insert(original);
				
				//カテゴリーが記載なしの分を除き、categoryNameをcategoryControllerに渡し、category Tableへの追加を行う.
				if (categoryName.length() > 0) {
					insertCategoryController.insertCategory(categoryName);
				}
			}
			
			//tsvファイルから全ての内容を読み込んだあと、BufferedReaderをcloseさせる
			br.close();
			System.out.println("Data has been inserted successfully.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "result";
	}

}
