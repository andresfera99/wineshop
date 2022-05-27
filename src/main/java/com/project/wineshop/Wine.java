package com.project.wineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Wine {
    private @Id
    @GeneratedValue Long id;
    private String name;

    @Column(name = "`year`")
    private String year;
    private double price;
    private float rating;
    private int num_reviews;

    private String body;
    private String acidity;
    @ManyToOne
    @JoinColumn(name = "winery")
    private Winery winery;
    @ManyToOne
    @JoinColumn(name = "`type`")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "region")
    private Region region;

    public Wine() {
    }

    public Wine(String name, String year, double price, float rating, int num_reviews, String body, String acidity, Winery winery, Type type, Region region) {
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

    public Winery getWinery() {
        return this.winery;
    }

    public Type getType() {
        return this.type;
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

    public void setWinery(Winery winery) {
        this.winery = winery;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
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
