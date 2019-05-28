package com.revature.controllertests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.google.gson.Gson;
import com.revature.entities.Content;
import com.revature.entities.Link;
import com.revature.entities.Module;
import com.revature.services.ModuleService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.revature.cmsforce.CMSforceApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	ModuleService ms;

	@Autowired
	Gson gson;

	@Test
	void createContent() throws Exception {
		String JSON = "{\"id\":88,\"title\":\"Dadaism\",\"format\":\"Ironing Board\",\"description\":\"Anti-art\",\"url\":\"www.dadaism.test\",\"links\":[{\"id\":12,\"contentId\":88,\"moduleId\":3,\"affiliation\":\"relaventTo\"}]}";
		Content cont = gson.fromJson(JSON, Content.class);
		Mockito.when(ms.createModule(module).createContent(cont)).thenReturn(cont);
		ResultActions resultActions = mockmvc
				.perform(post("/content").contentType(MediaType.APPLICATION_JSON_VALUE).content(JSON));
		resultActions.andExpect(status().isOk());
		String JOH = gson.toJson(cont);
		resultActions.andExpect(content().string(JOH));
	}

	private Set<Module> fakeContents() {
		Module mod1 = new Module(1, "Fakey McFake", 0, null);
		Module mod2 = new Module(2, "Mocky McMock", 0, null);
		Module mod3 = new Module(3, "Pastiche McPastiche", 0, null);

		Set<Module> modules = new HashSet<Module>();
		modules.add(mod1);
		modules.add(mod2);
		modules.add(mod3);

		return modules;

	}
}
