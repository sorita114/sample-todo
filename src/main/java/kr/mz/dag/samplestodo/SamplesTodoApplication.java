package kr.mz.dag.samplestodo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.*;
import java.util.Date;

@SpringBootApplication
public class SamplesTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamplesTodoApplication.class, args);
	}

}

@Configuration
class RepositoryConfig extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Todo.class);
	}

}

@Entity(name = "TODO")
class Todo {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private Date createdDatetime = new Date();

	@Column
	private String doneDatetime;

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

	public String getDoneDatetime() {
		return doneDatetime;
	}

	public void setDoneDatetime(String doneDatetime) {
		this.doneDatetime = doneDatetime;
	}
}

@RepositoryRestResource(path = "todo")
interface TodoRepository extends JpaRepository<Todo, String> {}