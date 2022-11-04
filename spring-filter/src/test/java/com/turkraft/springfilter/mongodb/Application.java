package com.turkraft.springfilter.mongodb;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.turkraft.springfilter.boot.Filter;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@RestController
public class Application implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public void run(ApplicationArguments args) throws IOException {
    Car audi = new Car();
    audi.setName("Audi");
    audi.setSomeDate(Date.from(Instant.now()));
    Car merco = new Car();
    merco.setName("Merco");
    merco.setSomeDate(Date.from(Instant.now().minusSeconds(60 * 60 * 24)));
    mongoTemplate.save(audi);
    mongoTemplate.save(merco);
  }

  @Autowired
  private CarRepository carRepository;

  @GetMapping(value = "car")
  public List<Car> getCars(@Filter(entityClass = Car.class) Document filter) {
    return carRepository.findAll(filter);
  }

}
