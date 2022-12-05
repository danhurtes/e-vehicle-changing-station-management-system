package com.netvalue.evehicleschargemeter;

import com.netvalue.evehicleschargemeter.utils.CustomerUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

@SpringBootTest
class ElectricVehiclesChargeMeterApplicationTests {
	private static final Stream<String> DATES_STREAM = Stream.of(
			"2022-12-05 01:10:23.123456789",
			"2022-12-05 01:10:23.12345678",
			"2022-12-05 01:10:23.1234567",
			"2022-12-05 01:10:23.123456",
			"2022-12-05 01:10:23.12345",
			"2022-12-05 01:10:23.1234",
			"2022-12-05 01:10:23.123",
			"2022-12-05 01:10:23.12",
			"2022-12-05 01:10:23.1",
			"2022-12-05 01:10:23.1",
			"2022-12-05 01:10:23",
			"2022-12-05 01:10",
			"2022/12/05",
			"2022/12/05 01:10",
			"2022/12/05 01:10:23",
			"2022/12/05 01:10:23.1",
			"2022/12/05 01:10:23.123",
			"2022/12/05 01:10:23.1234",
			"05/12/2022 01:10",
			"05/12/2022"
	);

	@Test
	void differentDateFormats_check() {
		DATES_STREAM
				.map(CustomerUtils::getDateTimeFromString)
				.forEach(date -> Assertions.assertThat(date).isExactlyInstanceOf(LocalDateTime.class));

		Assertions.assertThatExceptionOfType(DateTimeParseException.class)
				.isThrownBy(() -> CustomerUtils.getDateTimeFromString("123"));
	}

	/**
	 Describe in words what unit tests you would implement to ensure the correct behaviour of the date
	 range filter used in the service listing the charging session:

	 First of all, I would create mechanism for making requests to the AdminController via mockMvc.perform(...).
	 Next, I would check different scenarios when I pass empty dates to the controller; when I pass not empty dates
	 to the controller but in different patterns; when I pass one of the dates and another one is empty.

	 Other parts of the test would check different date combinations. At the beginning of the test I would save a bunch
	 of ChargingSessions in the database with various startChargingTime/endChargingTime values. After that, I would send
	 some requests with a certain range of dates to get in return filtered ChargingSessions which can be then
	 checked on a condition where I would expect the quantity of the returned ChargingSessions to be equal to the number of
	 entities which I expect to get. For example, I saved a list of 5 ChargingSessions before sending  mockMvc.perform(...)
	 request to the controller. Next, after the request has completed, I use objectMapper to fetch the result from request
	 in a form of Page<ChargingSessionResponse> and check if this response has a number of 3 ChargingSessions after filtration.
	 Additionally, I can check if the fields of the returned list of ChargingSessionsResponse are equal to the same entities
	 that were saved before request.
	 **/
}
