package vttp2022.paf.day12giphy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.paf.day12giphy.services.GiphyServices;

@SpringBootTest
class Day12GiphyApplicationTests {


	@Autowired
	private GiphyServices gsvc;

	@Test
	void shouldLoad10Images() {
		List<String> gif = gsvc.getGiphs("pokemon");
		assertEquals(10, gif.size());;
	}

}
