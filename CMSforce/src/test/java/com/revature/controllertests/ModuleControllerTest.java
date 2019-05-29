package com.revature.controllertests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
//	public Set<Module> getAllModules();
//	
//	public Module getModuleById(int id);
//	
	
//	@Test
//	void createModule() throws Exception {
//		String JSON = "{\"id\": 1,\"subject\": \"Fakey McFake\",\"created\": 0,\"links\": null}";
//		Module module = gson.fromJson(JSON, Module.class);
//		Mockito.when(ms.createModule(module)).thenReturn(module);
//		ResultActions resultActions = mockmvc
//				.perform(post("/module").contentType(MediaType.APPLICATION_JSON_VALUE).content(JSON));
//		resultActions.andExpect(status().isOk());
//		String JOH = gson.toJson(module);
//		resultActions.andExpect(content().string(JOH));
//	}
	@Test
	void doubleCreationModule() throws Exception {
		String JSON = "{\"id\":1,\"subject\":\"Fakey McFake\",\"created\":0,\"links\": null}";
		
//		ResultActions resultActions2 = mockmvc
//				.perform(post("/module").contentType(MediaType.APPLICATION_JSON_VALUE).content(JSON));
		
		
		Module module = gson.fromJson(JSON, Module.class);
		Module realmodule = fakeModules().iterator().next();
		Mockito.when(ms.createModule(realmodule)).thenReturn(realmodule);
		ResultActions resultActions = mockmvc
				.perform(post("/module").contentType(MediaType.APPLICATION_JSON_VALUE).content(JSON));
		String JOH = gson.toJson(module);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().string(JOH));
	}
	@Test
	void getAllModules() throws Exception {

		Mockito.when(ms.getAllModules()).thenReturn(this.fakeModules());
		long time = System.currentTimeMillis();
		ResultActions resultActions = mockmvc.perform(get("/module"));	
		long time2 = System.currentTimeMillis();
		long diff = (time2 - time);
		time = time - diff;
		resultActions.andExpect(status().isOk());
//		resultActions.andExpect(content().json("[{\"id\":1,\"subject\":\"Fakey McFake\",\"created\":" + time + ",\"links\": null},{\"id\": 2,\"subject\": \"Mocky McMock\",\"created\":"+ time +",\"links\": null},{\"id\": 3,\"subject\": \"Pastiche McPastiche\",\"created\":"+ time +",\"links\": null}]"));
//		System.err.println(resultActions.andReturn().getResponse().getContentAsString());
		
	} 

	private Set<Module> fakeModules() {
		Module mod1 = new Module(1, "Fakey McFake", 0, null);
		Module mod2 = new Module(2, "Mocky McMock", 0, null);
		Module mod3 = new Module(3, "Pastiche McPastiche", 0, null);

		Set<Module> modules = new HashSet<Module>();
		modules.add(mod1);
		modules.add(mod2);
		modules.add(mod3);
		System.out.println(modules);

		return modules;

	}
}
