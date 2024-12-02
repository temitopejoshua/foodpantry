package edu.bowiestateuni.groupproj.foodpantry;

import edu.bowiestateuni.groupproj.foodpantry.dao.EmployeeDAO;
import edu.bowiestateuni.groupproj.foodpantry.dao.UserDAO;
import edu.bowiestateuni.groupproj.foodpantry.entities.EmployeeEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.UserEntity;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.RoleTypeConstant;
import edu.bowiestateuni.groupproj.foodpantry.entities.constant.UserTypeConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class FoodPantryApplication implements CommandLineRunner {
	@Value("${pantry.system.email}")
	private String systemEmail;
	@Value("${pantry.system.password}")
	private String systemPassword;
	private final EmployeeDAO employeeDAO;
	private final UserDAO userDAO;

    public FoodPantryApplication(EmployeeDAO employeeDAO, UserDAO userDAO) {
        this.employeeDAO = employeeDAO;
        this.userDAO = userDAO;
    }

    public static void main(String[] args) {
		SpringApplication.run(FoodPantryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createSystemUser();
	}
	private void createSystemUser() {
		if(!userDAO.existsByEmailAddress(systemEmail)){
			final UserEntity userEntity =UserEntity.builder()
					.emailAddress(systemEmail)
					.name("System")
					.userType(UserTypeConstant.EMPLOYEE)
					.password(systemPassword)
					.build();
			final EmployeeEntity employeeEntity = EmployeeEntity.builder()
					.user(userEntity)
					.employeeRole(RoleTypeConstant.CEO)
					.ssn("XXXX-XX-XXXX")
					.accountNumber("1234567890")
					.dateOfBirth(LocalDate.now())
					.dateJoined(LocalDate.now())
					.routingNumber("12324243505")
					.build();
			employeeDAO.save(employeeEntity);
		}

	}
}
