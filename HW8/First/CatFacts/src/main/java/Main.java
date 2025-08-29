import org.apache.http.client.config.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            List<CatFact> filteredFacts = getFilteredCatFacts();

            System.out.println("Факты о кошках с голосами:");
            System.out.println("--------------------------------------------");

            for (CatFact fact : filteredFacts) {
                System.out.println(fact);
                System.out.println("--------------------------------------------");
            }

            System.out.println("Всего найдено фактов: " + filteredFacts.size());

        } catch (IOException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

    public static List<CatFact> getFilteredCatFacts() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("HTTP ошибка: " + response.getStatusLine().getStatusCode());
            }

            String body = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            List<CatFact> allFacts = Arrays.asList(objectMapper.readValue(body, CatFact[].class));

            List<CatFact> filteredFacts = allFacts.stream()
                    .filter(fact -> fact.getUpvotes() != null)
                    .collect(Collectors.toList());

            return filteredFacts;
        } finally {
            httpClient.close();
        }
    }
}
