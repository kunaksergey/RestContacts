package ua.shield;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.shield.controller.ContactController;
import ua.shield.entity.Contact;
import ua.shield.service.ContactService;
import ua.shield.service.ContactServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sa on 18.09.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestingDataSourceConfig.class,Application.class})
@WebAppConfiguration
public class ContactControllerTest {
    private final String nameFilterOne="^A.*$";
    private final String nameFilterTwo="^.*[aei].*$";


    @Mock
    private ContactService contactServiceMock;

    private MockMvc mockMvc;



    @Before
    public void setup(){
       mockMvc = MockMvcBuilders.standaloneSetup(new ContactController(contactServiceMock)).build();
    }

    @Test
    public void withoutFilter() throws Exception {
        mockMvc.perform(get("/hello/contacts"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void withFilter() throws Exception {
        when(contactServiceMock.findAllByFilter(nameFilterOne)).thenReturn(Arrays.asList(
                new Contact(1, "Petya"),
                new Contact(2, "Vasya"),
                new Contact(3, "Sergey")));

        mockMvc.perform(get("/hello/contacts?nameFilter="+nameFilterOne))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Petya")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Vasya")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Sergey")));
    }

    @Test
    public void serviceThrowExeption() throws Exception {
        MockMvc exeptionMock = MockMvcBuilders.standaloneSetup(new ContactController(new ContactService() {
            @Override
            public List<Contact> findAll() {
                return null;
            }

            @Override
            public List<Contact> findAllByFilter(String filterPattern) throws RuntimeException {
                throw new RuntimeException();
            }
        })).build();
        exeptionMock.perform(get("/hello/contacts?nameFilter="+nameFilterOne))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testFilterOne() throws Exception{

    }

    @Test
    public void testFilterTwo() throws Exception{

    }
}
