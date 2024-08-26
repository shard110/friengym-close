package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 모든 상품 가져오기
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    

    // 인기상품 가져오기 
    public List<Product> findPopularProducts(Integer limit) {
        String sql =  "SELECT p.pNum, p.pName, p.pPrice, p.pCount, p.pImg, p.pDate, p.pIntro, SUM(d.docount) AS total_quantity " +
                 "FROM product p " +
                 "JOIN Dorder d ON p.pNum = d.pnum " +
                 "JOIN ordertbl o ON d.onum = o.onum " +
                 "GROUP BY p.pNum, p.pName, p.pPrice, p.pCount, p.pImg, p.pDate, p.pIntro " +
                 "ORDER BY total_quantity DESC ";
        if (limit != null) {
                sql += "LIMIT " + limit;
            }

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product();
            product.setPNum(rs.getInt("pNum"));
            product.setPName(rs.getString("pName"));
            product.setPPrice(rs.getInt("pPrice"));
            product.setPCount(rs.getInt("pCount"));
            product.setPImg(rs.getString("pImg"));
            product.setPDate(rs.getDate("pDate"));
            product.setPIntro(rs.getString("pIntro"));
            

            return product;
        });
    }

    // 최근 4개의 상품 가져오기 (최신순)
    public List<Product> findRecentProducts(int limit) {
        String sql = "SELECT * FROM product ORDER BY pDate DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product();
            product.setPNum(rs.getInt("pNum"));
            product.setPName(rs.getString("pName"));
            product.setPPrice(rs.getInt("pPrice"));
            product.setPCount(rs.getInt("pCount"));
            product.setPImg(rs.getString("pImg"));
            product.setPDate(rs.getDate("pDate"));
            product.setPIntro(rs.getString("pIntro"));
            return product;
        }, limit);
    }

    public List<Product> findProductsFromLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        String sql = "SELECT * FROM product WHERE pDate >= ? ORDER BY pDate DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product();
            product.setPNum(rs.getInt("pNum"));
            product.setPName(rs.getString("pName"));
            product.setPPrice(rs.getInt("pPrice"));
            product.setPCount(rs.getInt("pCount"));
            product.setPImg(rs.getString("pImg"));
            product.setPDate(rs.getDate("pDate"));
            product.setPIntro(rs.getString("pIntro"));
            return product;
        }, java.sql.Date.valueOf(oneMonthAgo));
    }

    // 카테고리별 상품 가져오기
    public List<Product> findProductsByCategory(int catenum) {
        return productRepository.findByCategory(catenum);
    }
}
