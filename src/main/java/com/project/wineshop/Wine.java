package com.project.wineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.Optional;

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

    public Object getWinery() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.project.wineshop");
        context.refresh();

        WineryRepository repository = context.getBean(WineryRepository.class);

        Optional<Winery> winery = repository.findById(Long.valueOf(this.winery)); //se wrappea en un optional por si no existiese el primero
        if (winery.isPresent()) {
            return winery.get();
        }
        return null;
    }

    public int getWineryId() {
        return winery;
    }

    public Object getType() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.project.wineshop");
        context.refresh();

        TypeRepository repository = context.getBean(TypeRepository.class);
        Optional<Type> type = repository.findById(Long.valueOf(this.type)); //se wrappea en un optional por si no existiese el primero
        if (type.isPresent()) {
            return type.get();
        }
        return null;
    }

    public int getTypeId() {
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

    public int getRegionId() {
        return region;
    }

    public Object getRegion() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.project.wineshop");
        context.refresh();

        RegionRepository repository = context.getBean(RegionRepository.class);
        Optional<Region> region = repository.findById(Long.valueOf(this.region)); //se wrappea en un optional por si no existiese el primero
        if (region.isPresent()) {
            return region.get();
        }
        return null;
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
