package dev.zvaryyka.rebalancerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = ["dev.zvaryyka.rebalancerservice", "dev.zvaryyka.notificationservice"])
class RebalancerServiceApplication

fun main(args: Array<String>) {
	runApplication<RebalancerServiceApplication>(*args)
}
