package ES;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * ESOperations
 */
public class ESOperations {

    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";
 
    private static RestHighLevelClient restHighLevelClient;
    private static ObjectMapper objectMapper = new ObjectMapper();
 
    private static final String INDEX = "persondata";
    private static final String INDEX_SCHOOL = "schooldata";

    private static final String TYPE = "person";

    /**
 * Implemented Singleton pattern here
 * so that there is just one connection at a time.
 * @return RestHighLevelClient
 */
public static synchronized RestHighLevelClient makeConnection() {
 
    if(restHighLevelClient == null) {
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                    
                        new HttpHost(HOST, PORT_ONE, SCHEME),//localhost 9200 http
                        new HttpHost(HOST, PORT_TWO, SCHEME)));
    }
 
    return restHighLevelClient;
}

public static synchronized void closeConnection() throws IOException {
    restHighLevelClient.close();
    restHighLevelClient = null;
}

public static Person insertPerson(Person person){
    person.setPersonId(UUID.randomUUID().toString());
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("personId", person.getPersonId());
    dataMap.put("name", person.getName());
    IndexRequest indexRequest= new IndexRequest(INDEX).id(person.getPersonId())
    .source(dataMap, XContentType.JSON);

    try {
        IndexResponse response = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
    } catch(ElasticsearchException e) {
        e.getDetailedMessage();
    } catch (java.io.IOException ex){
        ex.getLocalizedMessage();
    }
    return person;
}

public static Person getPersonById(String id){
    GetRequest request = new GetRequest(INDEX).id(id);
    GetResponse getResponse = null;
    try {
        getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
    } catch (java.io.IOException e){
        e.getLocalizedMessage();
    }
    return getResponse != null ?
            objectMapper.convertValue(getResponse.getSourceAsMap(), Person.class) : null;
}

public static Person updatePersonById(String id, Person person){
    UpdateRequest updateRequest = new UpdateRequest(INDEX,id)
            .fetchSource(true);    // Fetch Object after its update
    try {
        String personJson = objectMapper.writeValueAsString(person);
        updateRequest.doc(personJson, XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        return objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Person.class);
    }catch (JsonProcessingException e){
        e.getMessage();
    } catch (java.io.IOException e){
        e.getLocalizedMessage();
    }
    System.out.println("Unable to update person");
    return null;
}

public static void deletePersonById(String id) {
    DeleteRequest deleteRequest = new DeleteRequest(INDEX, id);
    try {
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    } catch (java.io.IOException e){
        e.getLocalizedMessage();
    }
}
    
public static School createSchoolIndex(School school){
    //school=school ==null? new School():school;
    school.setId(UUID.randomUUID().toString());
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("name", school.getName());
    dataMap.put("id", school.getId());
    dataMap.put("street", school.getStreet());
    dataMap.put("city", school.getCity());
    dataMap.put("state", school.getState());
    dataMap.put("zip", school.getZip());
    dataMap.put("fees", school.getFees());
    dataMap.put("tags", school.getTags());
    dataMap.put("rating", school.getRating());
    dataMap.put("description", school.getDescription());

    IndexRequest indexRequest= new IndexRequest(INDEX_SCHOOL).id(school.getId())
    .source(dataMap, XContentType.JSON);

    try {
        IndexResponse response = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
    } catch(ElasticsearchException e) {
        e.getDetailedMessage();
    } catch (java.io.IOException ex){
        ex.getLocalizedMessage();
    }
    return school;
}

public static School getSchoolById(String id){
    GetRequest request = new GetRequest(INDEX_SCHOOL).id(id);
    GetResponse getResponse = null;
    try {
        getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
    } catch (java.io.IOException e){
        e.getLocalizedMessage();
    }
    return getResponse != null ?
            objectMapper.convertValue(getResponse.getSourceAsMap(), School.class) : null;
}


}