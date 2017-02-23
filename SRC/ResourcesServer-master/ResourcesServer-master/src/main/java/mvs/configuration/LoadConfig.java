package mvs.configuration;

/**
 * Created by Quang Minh on 3/22/2016.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.apache.catalina.Role;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

@Component
public class LoadConfig {
    @EventListener
    public void init(ContextRefreshedEvent event) throws IOException, ParseException, org.json.simple.parser.ParseException {
        mvs.LoadConfig.loadApi("D:\\ProjectWS\\ResourcesServer-master\\ResourcesServer-master\\src\\main\\resources\\config.json");



    }
}
