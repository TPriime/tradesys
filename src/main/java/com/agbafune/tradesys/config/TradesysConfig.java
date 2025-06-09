package com.agbafune.tradesys.config;

import com.agbafune.tradesys.domain.repository.AssetRepository;
import com.agbafune.tradesys.domain.repository.UserRepository;
import com.agbafune.tradesys.inmem.AssetRepositoryImpl;
import com.agbafune.tradesys.inmem.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradesysConfig {

	@Bean
	public UserRepository userRepository() {
		return new UserRepositoryImpl();
	}

	@Bean
	public AssetRepository assetRepository() {
		return new AssetRepositoryImpl();
	}
}
