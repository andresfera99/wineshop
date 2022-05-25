package com.project.wineshop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Wine {
    private @Id
    @GeneratedValue Long id;
    private String name;
    private String year;
    private double price;
    private float rating;
    private int num_reviews;

    private String body;
    private String acidity;
    private int winery;
    private int type;

    private int region;

    Wine() {
    }

    public Wine(String name, String year, double price, float rating, int num_reviews, String body, String acidity, int winery, int type, int region) {
        this.name = name;
        this.year = year;
        this.price = price;
        this.rating = rating;
        this.num_reviews = num_reviews;
        this.body = body;
        this.acidity = acidity;
        this.winery = winery;
        this.type = type;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public int getNum_reviews() {
        return num_reviews;
    }

    public String getBody() {
        return body;
    }

    public String getAcidity() {
        return acidity;
    }

    public int getWinery() {
        return winery;
    }

    public int getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setNum_reviews(int num_reviews) {
        this.num_reviews = num_reviews;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAcidity(String acidity) {
        this.acidity = acidity;
    }

    public void setWinery(int winery) {
        this.winery = winery;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wine wine = (Wine) o;
        return Double.compare(wine.price, price) == 0 && Float.compare(wine.rating, rating) == 0 && num_reviews == wine.num_reviews && winery == wine.winery && type == wine.type && region == wine.region && id.equals(wine.id) && name.equals(wine.name) && Objects.equals(year, wine.year) && Objects.equals(body, wine.body) && Objects.equals(acidity, wine.acidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, price, rating, num_reviews, body, acidity, winery, type, region);
    }

    @Override
    public String toString() {
        return "Wine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", rating=" + rating +
                ", num_reviews=" + num_reviews +
                ", body=" + body +
                ", acidity=" + acidity +
                ", winery=" + winery +
                ", type=" + type +
                ", region=" + region +
                '}';
    }
}
