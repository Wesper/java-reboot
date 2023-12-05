package ru.sberbank.edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class App {

    private static final WeatherProvider weatherProvider = new WeatherProvider();
    private static final WeatherCache weatherCache = new WeatherCache(weatherProvider);

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
                        processing(reader, writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void processing(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.println("Process start in thread " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
        String city = getCityFromRequest(reader);
        WeatherInfo weatherInfo = city == null ? null : weatherCache.getWeatherInfo(city);
        sendResponse(writer, weatherInfo);
    }

    private static String getCityFromRequest(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null && line.contains("GET /getWeatherInfo?city=")) {
            Pattern pattern = Pattern.compile("(?<==)([a-zA-Z]+)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return line.substring(matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private static void sendResponse(PrintWriter writer, WeatherInfo weatherInfo) {
        if (weatherInfo == null) {
            String response = "The requested city was not found or an error occurred\n";
            writer.write("HTTP/1.1 404 Not found\n");
            writer.write("Content-Length: " + response.length() + "\n");
            writer.println();
            writer.write(response);
            writer.println();
        } else {
            writer.write("HTTP/1.1 200 OK\n");
            writer.write("Content-Length: " + weatherInfo.toString().length() + "\n");
            writer.println();
            writer.write(weatherInfo + "\n");
            writer.println();
        }
        writer.flush();
    }
}
