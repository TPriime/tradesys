package com.agbafune.tradesys;

import com.agbafune.tradesys.api.AssetChangeSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class TradesysApplication {

	@Autowired
	private AssetChangeSimulator assetChangeSimulator;

	public static void main(String[] args) {
		SpringApplication.run(TradesysApplication.class, args);
	}

	// simulate asset price changes
	@Scheduled(fixedRateString = "${simulation.asset-change-secs}", timeUnit = TimeUnit.SECONDS)
	public void updateAssets() {
		assetChangeSimulator.updateAssetPrices();
	}
}
