package com.nekretnine.services;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nekretnine.models.Administrator;
import com.nekretnine.models.Authority;
import com.nekretnine.models.UserAuthority;
import com.nekretnine.repository.AuthorityRepository;
import com.nekretnine.repository.UserAuthorityRepository;
import com.nekretnine.repository.UserRepository;

@Component
public class ApplicationLoader implements ApplicationRunner {

	private UserRepository userRepository;
	private UserAuthorityRepository userAuthRepository;
	private AuthorityRepository authorityRepository;
	
	public ApplicationLoader(AuthorityRepository authRepo, UserRepository userRepo,
			UserAuthorityRepository userAuthRepo) {
		this.userRepository = userRepo;
		this.authorityRepository = authRepo;
		this.userAuthRepository = userAuthRepo;
	}
	
	/**
	 * Method used to put initial data in the Database:
	 * Default User roles (Authorities) and 
	 * Default Administrator, fist user in the Application
	 * @author Stefan Plazic, Nemanja Zunic
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Authority authority;

		if (authorityRepository.findByName("ADMINISTRATOR") == null) {
			//save administrator
			authority = new Authority();
			authority.setName("ADMINISTRATOR");
			authorityRepository.save(authority);
		}
		if (authorityRepository.findByName("MODERATOR") == null) {
			// save moderator
			authority = new Authority();
			authority.setName("MODERATOR");
			authorityRepository.save(authority);
		}

		if (authorityRepository.findByName("CUSTOMER") == null) {
			// save customer
			authority = new Authority();
			authority.setName("CUSTOMER");
			authorityRepository.save(authority);
		}
		if (authorityRepository.findByName("ADVERTISER") == null) {
			// save advertiser
			authority = new Authority();
			authority.setName("ADVERTISER");
			authorityRepository.save(authority);
		}
		
		Administrator admin = new Administrator();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//if default admin doesn't exist, insert him into the Database
		if (userRepository.findByEmail("admin") == null) {
			
			admin.setFirstName("admin");
			admin.setLastName("admin");
			admin.setEmail("admin");
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("admin"));
			admin.setVerifyCode("admin needs no code");
			admin.setVerified(true);
			userRepository.save(admin);
			
			UserAuthority userAuth = new UserAuthority();
			userAuth.setUser(admin);
			userAuth.setAuthority(authorityRepository.findByName("ADMINISTRATOR"));
			userAuthRepository.save(userAuth);
			
		}
	}

}
