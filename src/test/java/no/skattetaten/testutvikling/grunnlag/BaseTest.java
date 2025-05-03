package no.skattetaten.testutvikling.grunnlag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setup() {
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected <T> T parseResponsTilObjekt(MvcResult mvcResult, Class<T> klasse) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), klasse);
    }

    protected MockHttpServletRequestBuilder hentPostBuilder(String sti, Object innhold, Object... uriVars)
        throws JsonProcessingException {
        return post(sti, uriVars)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getObjectMapper().writeValueAsString(innhold));
    }

    protected MockHttpServletRequestBuilder hentGetBuilder(String sti, Object... uriVars) {
        return get(sti, uriVars);
    }

    protected MockHttpServletRequestBuilder hentDeleteBuilder(String sti, Object... uriVars) {
        return delete(sti, uriVars);
    }
}
