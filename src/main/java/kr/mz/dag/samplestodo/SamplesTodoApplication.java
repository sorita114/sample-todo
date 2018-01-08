package kr.mz.dag.samplestodo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SamplesTodoApplication implements CommandLineRunner {

	private TodoRepository repository;

	public SamplesTodoApplication(TodoRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SamplesTodoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Arrays.asList("반찬사오기", "전화하기", "멋쟁이되기")
				.forEach(text -> repository.save(new Todo(text)));
	}

}

@Configuration
class RepositoryConfig extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Todo.class);
		config.setBasePath("v1");
	}

}

@Entity(name = "TODO")
class Todo {

	public Todo() {}

	public Todo(String text) {
		this.text = text;
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	private String id;

	@Access(AccessType.PROPERTY)
	@Column(nullable = false)
	private String text;

	@Column(nullable = false, updatable = false)
	private Date createdDatetime = new Date();

	@Access(AccessType.PROPERTY)
	@Column
	private Date doneDatetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Date getDoneDatetime() {
		return doneDatetime;
	}

	public void setDoneDatetime(Date doneDatetime) {
		this.doneDatetime = doneDatetime;
	}

}

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE}, maxAge = 3600)
@RepositoryRestResource( path = "todo")
interface TodoRepository extends JpaRepository<Todo, String> {

	List<Todo> findByTextLike(@Param("text") String text);

}