package com.mypack.ui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypack.ui.dto.DashboardDto;
import com.mypack.ui.dto.SubItemDto;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DashboardService {

    private final ObjectMapper objectMapper;
    private static final String TILES_CONFIG_FILE = "tiles.json";

    public DashboardService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Loads and returns dashboard items from the JSON configuration file
     * @return List of DashboardDto objects
     */
    public List<DashboardDto> getDashboardItems() {
        try {
            return loadTilesFromJsonFile();
        } catch (Exception e) {
            System.err.println("Error loading tiles from JSON: " + e.getMessage());
            return getDefaultTiles();
        }
    }

    /**
     * Reads tiles.json file using Jackson ObjectMapper
     */
    private List<DashboardDto> loadTilesFromJsonFile() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TILES_CONFIG_FILE)) {
            if (inputStream == null) {
                System.err.println("Tiles configuration file not found: " + TILES_CONFIG_FILE);
                return getDefaultTiles();
            }
            DashboardDto[] tiles = objectMapper.readValue(inputStream, DashboardDto[].class);
            return Arrays.asList(tiles);
        }
    }

    /**
     * Returns default tiles if JSON file is not found or fails to load
     */
    private List<DashboardDto> getDefaultTiles() {
        List<DashboardDto> defaultTiles = new ArrayList<>();
        defaultTiles.add(DashboardDto.builder()
                .title("Test1")
                .description("Test1 Description")
                .icon("CHECK")
                .route("")
                .build());
        defaultTiles.add(DashboardDto.builder()
                .title("Test2")
                .description("Test2 Description")
                .icon("HOME")
                .route("")
                .build());
        
        List<SubItemDto> exampleSubItems = new ArrayList<>();
        exampleSubItems.add(SubItemDto.builder()
                .title("Example 1")
                .route("example1")
                .build());
        exampleSubItems.add(SubItemDto.builder()
                .title("Example 2")
                .route("example2")
                .build());
        
        defaultTiles.add(DashboardDto.builder()
                .title("Example")
                .description("Explore the example pages with route navigation links displayed in the header next to the dashboard button.")
                .icon("LAPTOP")
                .route("example")
                .subItems(exampleSubItems)
                .build());
        return defaultTiles;
    }
}
