package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TsvHandlingApplication {

	public static void main(String[] args) {
		// runはボタンでするから不要?
		SpringApplication.run(TsvHandlingApplication.class, args);

		// tsvファイルのpathを指定
		String filePath = "/Users/shibatamasayuki/業務/大量データ課題/train.tsv";
		List<String> tsvDataList = new ArrayList<>(); 
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;

			for (int i = 0; i <= 2; i++) {
				line = br.readLine();
				System.out.println("line : " + line);
			}
//			 br.readLine: 1行(改行などがされるまでの)
//			while ((line = br.readLine()) != null) {
//
//				String[] data = line.split("\t", 0); // 行をタブで区切り、配列に変換
//
//				for (String eachData : data) {
//					System.out.println("eachData情報 : " + eachData);
//					tsvDataList.add(eachData);
//					System.out.println("tsvDataList : " + tsvDataList);
//					System.out.println("lineのlength : " + line.length());
//				}
//			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		

	}
//	public static void main(String[] args) {
//		SpringApplication.run(TsvHandlingApplication.class, args);
//	}

}
