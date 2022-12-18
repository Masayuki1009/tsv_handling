package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domain.Original;

@SpringBootApplication
public class TsvHandlingApplication {

public static void main(String[] args) {
    //configulation
    String jdbcUrl = "jdbc:postgresql://localhost:5432/mercari";
    String username = "postgres";
    String password = "mercari";

    // tsvファイルのpathを指定
    String filePath = "/Users/shibatamasayuki/業務/大量データ課題/train.tsv";

    //tsvデータを1行(line)ごとに格納するリスト
    List<String[]> tsvDataList = new ArrayList<>();

    Connection connection = null;

    try {
        // DB接続
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        //自動コミットをオフに(理由は不明)
        connection.setAutoCommit(false);

        String sql = "Insert into original(id, name, condition_id, category_name, brand, price, shipping, description) values(?, ?, ?, ?, ?, ?, ?, ?);";            
        PreparedStatement statement = connection.prepareStatement(sql);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        Original original = new Original();
        //tsvファイル1行目(カラム名の部分)は不必要なため、先に読み込んでおく
        line = br.readLine();

        //DB接続〜Data振り分けロジック初め
                        while ((line = br.readLine()) != null){

                            String[] data = line.split("\t", 0);

                            String id = data[0];
                            String name = data[1];
                            String conditionId = data[2];
                            String categoryName = data[3];
                            String brand = data[4];
                            String price = data[5];
                            String shipping = data[6];
                            String description = data[7];

                            int j = 1;
                            while (j <= 8) {
                                if (j == 1) {
                                    original.setId(Integer.parseInt(id));
                                    statement.setInt(j, original.getId());
                                } else if (j == 2) {
                                    original.setName(name);
                                    statement.setString(j, original.getName());
                                } else if (j == 3) {
                                    original.setConditionId(Integer.parseInt(conditionId));
                                    statement.setInt(j, original.getConditionId());
                                } else if (j == 4) {
                                    original.setCategoryName(categoryName);
                                    statement.setString(j, original.getCategoryName());
                                } else if (j == 5) {
                                    original.setBrand(brand);
                                    statement.setString(j, original.getBrand());
                                } else if (j == 6) {
                                    original.setPrice(Double.parseDouble(price));
                                    statement.setDouble(j, original.getPrice());
                                } else if (j == 7) {
                                    original.setShipping(Integer.parseInt(shipping));
                                    statement.setInt(j, original.getShipping());
                                } else if (j == 8) {
                                    original.setDescription(description);
                                    statement.setString(j, original.getDescription());
                                }

                                j++;

                            }
                        //sql実行(DBに反映はcommit?)
                        statement.execute();
                        // 全データ追加せずに途中で実行を止めるため、確認上一旦ここでcommitを行う(そうでないとDB insertされない)
                        connection.commit();
                    }

        br.close();
        // autocommit = falseなのでここでcommitする
        connection.commit();
        connection.close();
        System.out.println("Data has been inserted successfully.");
    } catch (FileNotFoundException e) {
        e.printStackTrace();

    } catch (IOException e) {

        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }

}
}